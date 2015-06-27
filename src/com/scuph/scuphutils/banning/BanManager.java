package com.scuph.scuphutils.banning;

import com.scuph.scuphutils.util.AbstractService;
import com.scuph.scuphutils.ScuphUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.util.FileUtils;
import org.bukkit.configuration.ConfigurationSection;

public class BanManager extends AbstractService {

    private final YamlConfig config;
    private final Set<Ban> rawBans;
    private final Map<UUID, Ban> uuidBans;
    private final Map<String, Ban> ipBans;

    public BanManager(ScuphUtils plugin) {
        super(plugin);

        this.config = new YamlConfig(plugin, FileUtils.getPluginFile(plugin, "bans.yml"), false);
        this.rawBans = new HashSet<>();
        this.uuidBans = new HashMap<>();
        this.ipBans = new HashMap<>();
    }

    @Override
    protected void onStart() {
        loadAll();

        logger.info("Loaded " + rawBans.size() + " player bans (" + uuidBans.size() + " UUIDs, " + ipBans.size() + " IPs)");
    }

    @Override
    protected void onStop() {
        saveAll();
    }

    public boolean addBan(Ban ban) {
        return addBan(ban, true);
    }

    private boolean addBan(Ban ban, boolean save) {
        if (!ban.isValid()) {
            logger.warning("Not banning UUID for ban '" + ban.getId() + "' Ban is not valid!");
            return false;
        }

        if (!rawBans.add(ban)) {
            return false;
        }

        if (save) {
            parseBan(ban);
            saveAll();
        }
        return true;
    }

    public boolean unban(UUID uuid) {
        Iterator<Ban> bans = rawBans.iterator();
        while (bans.hasNext()) {
            final Ban ban = bans.next();
            if (ban.getType() != BanType.UUID) {
                continue;
            }

            if (!ban.getUuid().equals(uuid)) {
                continue;
            }

            // Unban uuid ban
            removeBan(ban, false);

            // Unban all IP bans that may match
            for (String ip : ban.getIps()) {
                unban(ip, false);
            }

            reparseBans();
            saveAll();
            return true;
        }

        return false;
    }

    public boolean unban(String ip) {
        return unban(ip, true);
    }

    private boolean unban(String ip, boolean save) {
        Iterator<Ban> bans = rawBans.iterator();
        while (bans.hasNext()) {
            final Ban ban = bans.next();

            if (!ban.getIps().contains(ip)) {
                continue;
            }

            ban.removeIp(ip);

            if (ban.getType() == BanType.IP && !ban.hasIps()) {
                removeBan(ban, false);
            }
        }

        if (save) {
            reparseBans();
            saveAll();
        }
        return false;
    }

    public boolean removeBan(Ban ban) {
        return removeBan(ban, true);
    }

    private boolean removeBan(Ban ban, boolean save) {
        if (!rawBans.remove(ban)) {
            return false;
        }

        if (save) {
            reparseBans();
            saveAll();
        }
        return true;
    }

    private void loadAll() {
        if (!config.exists()) {
            return;
        }

        config.load();
        rawBans.clear();

        // UUID bans
        ConfigurationSection uuidSection = config.getConfigurationSection("uuids");
        if (uuidSection != null) {

            for (String id : uuidSection.getKeys(false)) {
                if (!uuidSection.isConfigurationSection(id)) {
                    logger.warning("Not loading UUID ban '" + id + "'. Section is not a configuration section!");
                    continue;
                }

                final Ban ban = new Ban(BanType.UUID, id);
                ban.loadFrom(uuidSection.getConfigurationSection(id));

                if (!ban.isValid()) {
                    logger.warning("Not loading UUID ban '" + id + "'. Ban is not valid!");
                    continue;
                }

                rawBans.add(ban);
            }
        }

        // UUID bans
        ConfigurationSection section = config.getConfigurationSection("ips");
        if (section != null) {

            for (String id : section.getKeys(false)) {
                if (!section.isConfigurationSection(id)) {
                    logger.warning("Not loading IP ban '" + id + "'. Section is not a configuration section!");
                    continue;
                }

                final Ban ban = new Ban(BanType.IP, id);
                ban.loadFrom(section.getConfigurationSection(id));

                if (!ban.isValid()) {
                    logger.warning("Not loading IP ban '" + id + "'. Ban is not valid!");
                    continue;
                }

                rawBans.add(ban);
            }
        }

        // Parses rawBans into ipBans and uuidBans
        reparseBans();
    }

    private void saveAll() {
        ConfigurationSection uuidSection = config.createSection("uuids");
        ConfigurationSection ipSection = config.createSection("ips");

        for (Ban ban : rawBans) {
            if (ban.getType() == BanType.UUID) {
                ban.saveTo(uuidSection.createSection(ban.getId()));
            } else if (ban.getType() == BanType.IP) {
                ban.saveTo(uuidSection.createSection(ban.getId()));
            }
        }

        config.save();
    }

    private void reparseBans() {
        ipBans.clear();
        uuidBans.clear();
        for (Ban ban : rawBans) {
            parseBan(ban);
        }
    }

    private void parseBan(Ban ban) {
        // All bans may include IPs
        for (String ip : ban.getIps()) {
            ipBans.put(ip, ban);
        }

        if (ban.getType() == BanType.UUID) {
            uuidBans.put(ban.getUuid(), ban);
        }
    }

}
