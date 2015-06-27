package com.scuph.scuphutils.banning;

import com.scuph.scuphutils.util.ConfigLoadable;
import com.scuph.scuphutils.util.ConfigSaveable;
import com.scuph.scuphutils.util.Validatable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import net.pravian.bukkitlib.util.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class Ban implements ConfigLoadable, ConfigSaveable, Validatable {

    @Getter
    private final BanType type = null;
    @Getter
    private final String id = null;
    //
    private final List<String> ips;
    @Getter
    @Setter
    private UUID uuid = null;
    @Getter
    @Setter
    private String reason = null;
    @Getter
    @Setter
    private String by = null;
    @Getter
    @Setter
    private Date expiryDate = null;

    public Ban(BanType type, String id) {
        this.ips = new ArrayList<>();
    }

    public boolean hasIps() {
        return !ips.isEmpty();
    }

    public void addIp(String ip) {
        if (!ips.contains(ip.trim())) {
            this.ips.add(ip.trim());
        }
    }

    public void addIps(List<String> ips) {
        for (String ip : ips) {
            addIp(ip);
        }
    }

    public List<String> getIps() {
        return Collections.unmodifiableList(ips);
    }

    public void clearIps() {
        ips.clear();
    }

    public boolean containsIp(String ip) {
        return ips.contains(ip.trim());
    }

    public void removeIp(String ip) {
        if (containsIp(ip)) {
            ips.remove(ip.trim());
        }
    }

    public String getIp() {
        return (ips.size() > 0 ? ips.get(0) : null);
    }

    public String getTarget() {
        if (type == BanType.IP && hasIps()) {
            return getIp();
        }
        return id;
    }

    public String getKickMessage() {
        return ChatColor.RED
                + "Your " + (type == BanType.IP ? "IP-Address" : "UUID") + " is banned from this server.\n"
                + "Reason: " + reason + "\n"
                + "Expires: " + TimeUtils.parseDate(expiryDate) + "\n"
                + "Banned by: " + by;
    }

    public boolean hasExpiryDate() {
        return expiryDate != null;
    }

    public boolean isExpired() {
        if (expiryDate == null) {
            return false;
        }

        return !expiryDate.after(new Date());
    }

    @Override
    public void loadFrom(ConfigurationSection config) {

        try {
            uuid = UUID.fromString(config.getString("uuid", null));
        } catch (Exception ex) {
            uuid = null;
        }

        ips.clear();
        ips.addAll(config.getStringList("ips"));

        by = config.getString("by", null);
        reason = config.getString("reason", null);
        expiryDate = TimeUtils.parseString(config.getString("expires", null));
    }

    @Override
    public void saveTo(ConfigurationSection config) {
        config.set("uuid", uuid.toString());
        config.set("ips", ips.isEmpty() ? null : ips);
        config.set("by", by);
        config.set("reason", reason);
        config.set("expires", TimeUtils.parseDate(expiryDate));
    }

    @Override
    public boolean isValid() {
        return id != null
                && getTarget() != null
                // See BanType.java
                && ((type == BanType.UUID && uuid != null)
                || (type == BanType.IP && uuid == null && hasIps()));

    }
}
