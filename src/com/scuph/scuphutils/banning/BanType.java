package com.scuph.scuphutils.banning;

public enum BanType {

    // UUID - MUST include UUID, MAY include IPs
    // IP - MUST NOT include UUID, MUST include IPs
    UUID("Player ban"),
    IP("IP ban");

    private final String name;

    private BanType(String type) {
        this.name = type;
    }

    @Override
    public String toString() {
        return name;
    }
}
