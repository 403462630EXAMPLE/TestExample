package com.baidao.quoteutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.baidao.data.LoginMessage;
import com.baidao.quotemodel.Category;
import com.baidao.quotemodel.CategoryNotice;
import com.baidao.quotemodel.Quote;
import com.baidao.quotemodel.Snapshot;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hexi on 15/1/27.
 */
public class CategoryHelper {
    static private Map<String, String> quoteCache = new HashMap<>();
    static private Map<String, Category> categoryCache = new HashMap<>();

    private static final String FILE_NAME = "quote";
    public static final String KEY_CATEGORY = "categories";
    private static final String KEY_SERVER = "server";
    private static final String KEY_SNAPSHOT_PREFIX = "snapshot.";
    private static final String CATEGORY_RETRIEVE_TIME = "category_time";

    public static void setCategories(Context context, Collection<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return;
        }
        updateCategoriesToFile(context, categories);
        updateCategoriesToCache(categories);
    }

    private static void updateCategoriesToCache(Collection<Category> categories) {
        categoryCache = collectionToMap(categories);
    }

    private static void updateCategoriesToFile(Context context, Collection<Category> categories) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_CATEGORY, new Gson().toJson(categories));
        editor.commit();

        initSnapshot(context, categories);
    }

    private static void initSnapshot(Context context, Collection<Category> categories) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        for (Category category : categories) {
            String snapshotKey = getSnapshotKey(category);
            if (!sharedPreferences.contains(snapshotKey)) {
                editor.putString(snapshotKey, Quote.build(category).toString());
            }
        }
        editor.commit();
    }

    private static String getSnapshotKey(Category category) {
        return KEY_SNAPSHOT_PREFIX + category.id;
    }

    public static void updateSnapshot(Quote quote) {
        String snapshotKey = getSnapshotKey(quote);
        quoteCache.put(snapshotKey, quote.toString());
    }

    public static void clearQuoteCache() {
        quoteCache.clear();
    }

    public static Quote getSnapshotById(String categoryId) {
        String snapshotKey = KEY_SNAPSHOT_PREFIX + categoryId;
        return new Gson().fromJson(quoteCache.get(snapshotKey), Quote.class);
    }

    private static String getSnapshotKey(Quote quote) {
        return KEY_SNAPSHOT_PREFIX + quote.getSid();
    }

    private String getSnapshotKey(Snapshot snapshot) {
        return KEY_SNAPSHOT_PREFIX + snapshot.getSid();
    }

    public static boolean hasCategories(Context context) {
        Map<String, Category> categoryMap = getCategories(context);
        return !categoryMap.isEmpty();
    }

    public static Map<String, Category> getCategories(Context context) {
        if (!categoryCache.isEmpty()) {
            return categoryCache;
        }
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String categoriesJson = sharedPreferences.getString(KEY_CATEGORY, null);
        if (TextUtils.isEmpty(categoriesJson)) {
            return Collections.EMPTY_MAP;
        }
        List<Category> categoryList = new Gson().fromJson(categoriesJson, new TypeToken<List<Category>>() {}.getType());

        categoryCache = collectionToMap(categoryList);
        return categoryCache;
    }

    public static List<Category> getCategoryList(Context context) {
        return new ArrayList<>(getCategories(context).values());
    }

    private static Map<String, Category> collectionToMap(Collection<Category> categories) {
        Map<String, Category> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.id, category);
        }
        return categoryMap;
    }

    @Nullable
    public static Category getCategoryById(Context context, String id) {
        return getCategories(context).get(id);
    }

    public static void setServer(Context context, LoginMessage server) {
        if (server == null) {
            return;
        }
        Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_SERVER, server.toString());
        editor.commit();
    }

    @Nullable
    public static LoginMessage getServer(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String serverJson = sharedPreferences.getString(KEY_SERVER, null);


        return new Gson().fromJson(serverJson, LoginMessage.class);
    }

    public static void registerCategoryChangedListener(Context context, OnSharedPreferenceChangeListener onCategoryChangedListener) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.registerOnSharedPreferenceChangeListener(onCategoryChangedListener);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void updateCategory(Context context, CategoryNotice categoryNotice) {
        Map<String, Category> categoryMap = getCategories(context);
        if (categoryMap.isEmpty()) {
            return;
        }
        Category category = categoryMap.get(categoryNotice.getSid());
        if (category == null) {
            return ;
        }
        category.preSettlementPx = categoryNotice.preSettlementPx;
        category.prevClosedPx = categoryNotice.prevClosedPx;

        updateCategoriesToFile(context, categoryMap.values());
    }

    public static void setCategoryTimestamp(Context context, long timestamp) {
        Editor editor = getSharedPreferences(context).edit();
        editor.putLong(CATEGORY_RETRIEVE_TIME, timestamp);
        editor.commit();
    }

    public static long getCategoryTimestamp(Context context) {
        return getSharedPreferences(context).getLong(CATEGORY_RETRIEVE_TIME, 0);
    }

    public static List<Category> getCategoriesByMarket(Context context, String market) {
        Collection<Category> categories = getCategories(context).values();
        List<Category> result = Lists.newArrayList();
        for (Category category : categories) {
            if (category.market.equals(market)) {
                result.add(category);
            }
        }
        return result;
    }

    public static List<Category> getCategoryByIds(Context context, Collection<String> ids) {
        List<Category> result = new ArrayList<>();
        Map<String, Category> categoryMap = getCategories(context);
        for (String id : ids) {
            if (categoryMap.containsKey(id)) {
                result.add(categoryMap.get(id));
            }
        }
        return result;
    }
}
