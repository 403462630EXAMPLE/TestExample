package com.baidao;

public enum Server {
    TT(1, "tt", "02195049"),
    TD(2, "td", "4008213988"),
    YG(3, "yg", "4001616003"),
    SSY(8, "ssy", "02136129099"),//深石油
    BSY(9, "bsy", "4001858003"),//北石油
    DX(10, "dx", "400-1611-003"),
    JD(11, "jd", "02195049");

    public int serverId;
    public String name;
    public String phoneNumber;

    private Server(int serverId, String name, String phoneNumber) {
        this.serverId = serverId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static Server from(int serverId) {
        for (Server server : values()) {
            if (server.serverId == serverId) {
                return server;
            }
        }
        return JD;
    }

    public String getName() {
        return name;
    }
}
