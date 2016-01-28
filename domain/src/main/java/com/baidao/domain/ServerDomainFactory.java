package com.baidao.domain;

import android.content.Context;

import com.baidao.server.Server;

import java.util.Map;

/**
 * Created by hexi on 16/1/28.
 */
public abstract class ServerDomainFactory {
    public abstract Map<ServerDomainType,String> getDomain(Context context, Server server, boolean isDebug);
}
