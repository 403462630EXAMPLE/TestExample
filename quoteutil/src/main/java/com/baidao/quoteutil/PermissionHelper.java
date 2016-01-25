package com.baidao.quoteutil;

import android.content.Context;

import com.baidao.quotemodel.Category;
import com.baidao.quotemodel.Quote;
import com.baidao.server.Server;
import com.baidao.tools.UserHelper;
import com.baidao.tools.Util;
import com.google.common.collect.ImmutableMap;

/**
 * Created by hexi on 15/1/21.
 */
public class PermissionHelper {
    private static final ImmutableMap<Integer, Integer> permissionIndexOfLogin = ImmutableMap.<Integer, Integer>builder()
            .put(Server.TT.serverId, 0)
            .put(Server.TD.serverId, 1)
            .put(Server.YG.serverId, 2)
            .put(Server.SSY.serverId, 6)
            .put(Server.BSY.serverId, 8)
            .put(Server.DX.serverId, 10)
            .put(Server.JD.serverId, 1)//TODO
            .build();
    private static final ImmutableMap<Integer, Integer> permissionIndexOfLogout = ImmutableMap.<Integer, Integer>builder()
            .put(Server.TT.serverId, 3)
            .put(Server.TD.serverId, 4)
            .put(Server.YG.serverId, 5)
            .put(Server.SSY.serverId, 7)
            .put(Server.BSY.serverId, 9)
            .put(Server.DX.serverId, 11)
            .put(Server.JD.serverId, 4)//TODO
            .build();


    public static final int LENGTH_PER_PERMISSION = 4;
    public static boolean hasPermission(Context context, Category category) {
        return getPermissionValue(context, category) > 0;
    }

    private static Integer getReservedStringIndex(Context context) {
        boolean isLogin = UserHelper.getInstance(context).isLogin();
        int serverId = Util.getCompanyId(context);
        Integer index = isLogin? permissionIndexOfLogin.get(serverId)
                : permissionIndexOfLogout.get(serverId);
        if (index == null) {
            index = isLogin ? 0 : 3;
        }
        return index;
    }

    public static int getPermissionValue(Context context, Category category){
        Integer index = getReservedStringIndex(context);
        int start = index * LENGTH_PER_PERMISSION;
        int end = start + LENGTH_PER_PERMISSION;
        if (start >= category.reserveString_1.length()) {
            return 0;
        }
        return Integer.parseInt(category.reserveString_1.substring(start, end));
    }

    public static int getPermissionValue(Context context, Quote quote){
        Integer index = getReservedStringIndex(context);
        int start = index * LENGTH_PER_PERMISSION;
        int end = start + LENGTH_PER_PERMISSION;
        if (start >= quote.reservedString.length()) {
            return 0;
        }
        return Integer.parseInt(quote.reservedString.substring(start, end));
    }
}
