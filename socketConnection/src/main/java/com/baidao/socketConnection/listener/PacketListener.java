package com.baidao.socketConnection.listener;


import com.baidao.socketConnection.network.Packet;
import com.baidao.socketConnection.network.SocketConnection;

public interface PacketListener<T> {
    boolean shouldProcess(Packet<T> packet);
    void processPacket(Packet<T> packet, SocketConnection socketConnection);
    T getContent(String content);
}