package com.baidao.socketConnection.listener;


import com.baidao.socketConnection.network.SocketConnection;

public interface ConnectionListener {
    void connected(SocketConnection connection);

    void connectionClosed();

    void connectionError(Exception exception);

    void reconnectingIn(int time);
}