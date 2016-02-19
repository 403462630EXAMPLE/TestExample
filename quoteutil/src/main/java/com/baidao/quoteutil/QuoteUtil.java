package com.baidao.quoteutil;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidao.quotemodel.Category;
import com.baidao.quotemodel.Quote;
import com.baidao.server.Server;
import com.baidao.tools.BigDecimalUtil;
import com.baidao.tools.FileUtil;
import com.baidao.tools.SharedPreferenceUtil;
import com.baidao.tools.UserHelper;
import com.baidao.tools.Util;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hexi on 14/12/10.
 */
public class QuoteUtil {
    private static final String TAG = "QuoteUtil";
    private static final String QUOTE_DEFAULT = "--";
    private static final int SCALE_OF_UPDROP_PERCENT = 2;
    public static final String KEY_USER_SELECTED_QUOTE_CATEGORIES = "user_selected_quote_categories";

    public static String formatWithDefault(double value, int scale) {
        if (value == 0) {
            return QUOTE_DEFAULT;
        }
        return BigDecimalUtil.format(value, scale);
    }

    public static String format(double priceTarget, int scale) {
        return BigDecimalUtil.format(priceTarget, scale);
    }

    //preference里面保存的是category.sid
    public static @NonNull String[] getCustomQuoteCategoryIds(Context context) {
        return FluentIterable.from(getCustomCategories(context))
                .transform(new Function<Category, String>() {
                    @Override
                    public String apply(Category input) {
                        return input.id;
                    }
                }).toArray(String.class);
    }

    private static boolean saveCustomCategories(Context context, List<Category> categories) {
        return SharedPreferenceUtil.getSharedPreference(context)
                .edit()
                .putString(KEY_USER_SELECTED_QUOTE_CATEGORIES + UserHelper.getInstance(context).getUser().getUsername(),
                        new Gson().toJson(categories))
                .commit();
    }

    public static boolean saveSelectedCategory(Context context, Category newCategory) {
        List<Category> originCategoryList = new ArrayList<>();
        originCategoryList.addAll(getCustomCategories(context));
        boolean exist = false;
        for (int i = 0; i < originCategoryList.size(); i++) {
            if (originCategoryList.get(i).id.equals(newCategory.id)) {
                originCategoryList.set(i, newCategory);
                exist = true;
                break;
            }
        }
        if (!exist) {
            originCategoryList.add(newCategory);
        }
        return saveCustomCategories(context, originCategoryList);
    }

    public static boolean removeSelectedCategory(Context context, Category deletedCategory) {
        List<Category> originCategoryList = new ArrayList<>();
        originCategoryList.addAll(getCustomCategories(context));

        int position = -1;
        for (int i = 0; i < originCategoryList.size(); i++) {
            if (originCategoryList.get(i).id.equals(deletedCategory.id)) {
                originCategoryList.set(i, deletedCategory);
                position = i;
                break;
            }
        }
        if (position != -1) {
            originCategoryList.remove(deletedCategory);
        }
        return saveCustomCategories(context, originCategoryList);
    }

    private static Map<String, ArrayList<Category>>  objDefaultCategories = null;

    public static List<Category> getCustomCategories(Context context) {
        List<Category> categories = null;
        String userName = UserHelper.getInstance(context).getUser().getUsername();
        if (SharedPreferenceUtil.getSharedPreference(context).contains(KEY_USER_SELECTED_QUOTE_CATEGORIES + userName)) {
            String json = SharedPreferenceUtil.getSharedPreference(context)
                    .getString(KEY_USER_SELECTED_QUOTE_CATEGORIES + userName, "");

            categories = new Gson().
                    fromJson(json, new TypeToken<ArrayList<Category>>() {}.getType());
            if (categories == null) {
                categories = new ArrayList<>();
            }
        }

        if (categories == null) {
            categories = getDefaultCustomCategories(context);
        }

        return categories;
    }

    public static List<Category> getDefaultCustomCategories(Context context, boolean isTourist) {
        if (objDefaultCategories == null) {
            String jsonDefaultCategories = FileUtil.toStringFromAsset(context, "config/default_custom_categories.json");
            objDefaultCategories =
                    new Gson().fromJson(jsonDefaultCategories, new TypeToken<Map<String, ArrayList<Category>>>() {
                    }.getType());
        }
        String defaultKey = "tourist";
        String serverName = Server.from(Util.getCompanyId(context)).name;
        if (isTourist) {
            if (objDefaultCategories.containsKey(serverName + "_" + defaultKey)) {
                return objDefaultCategories.get(serverName + "_" + defaultKey);
            } else {
                return objDefaultCategories.get(defaultKey);
            }
        } else {
            if (UserHelper.getInstance(context).isLogin()) {
                return objDefaultCategories.get(serverName);
            } else {
                if (objDefaultCategories.containsKey(serverName + "_" + defaultKey)) {
                    return objDefaultCategories.get(serverName + "_" + defaultKey);
                } else {
                    return objDefaultCategories.get(defaultKey);
                }
            }
        }
    }

    public static List<Category> getDefaultCustomCategories(Context context) {
        return getDefaultCustomCategories(context, false);
    }

    public static boolean syncCustomCategory(Context context) {
        if (!UserHelper.getInstance(context).isLogin()) {
            return false;
        }
        String json = SharedPreferenceUtil.getSharedPreference(context).getString(KEY_USER_SELECTED_QUOTE_CATEGORIES, "");
        if (TextUtils.isEmpty(json)) {
            SharedPreferenceUtil.removeKey(context, KEY_USER_SELECTED_QUOTE_CATEGORIES);
            return false;
        }
        List<Category> list = new Gson().fromJson(json, new TypeToken<ArrayList<Category>>() {
        }.getType());
        List<Category> categories = getCustomCategories(context);
        boolean savedSuccess = false;
        if (categories == null || categories.size() == 0) {
            Log.i(TAG, "syncCustomCategory: DefaultCustomCategories is null");
            savedSuccess = saveCustomCategories(context, list);
        } else {
            int size = categories.size();
            if (size < 9) {
                List<Category> newCategories = Lists.newArrayList(categories);
                for (Category category1 : list) {
                    boolean flag = false;
                    for (Category category2 : categories) {
                        if (category1.id.equals(category2.id)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        Log.i(TAG, "syncCustomCategory: add category id:" + category1.id);
                        newCategories.add(category1);
                        size ++;
                    }
                    if (size >= 9) {
                        Log.i(TAG, "syncCustomCategory: size >= 9");
                        break;
                    }
                }
                savedSuccess = saveCustomCategories(context, newCategories);
            }
        }
        SharedPreferenceUtil.removeKey(context, KEY_USER_SELECTED_QUOTE_CATEGORIES);
        return savedSuccess;
    }

    private static final String UNIT_OF_TEN_THOUSAND = "万";
    private static final String UNIT_OF_A_HUNDRED_MILLION = "亿";
    private static final double TEN_THOUSAND = 10000;
    private static final double A_HUNDRED_MILLION = 10000 * 10000;
    public static String formatTotalVolume(double totalTradeVolume, int scale) {
        if (totalTradeVolume == 0) {
            return QUOTE_DEFAULT;
        }
        if (totalTradeVolume < TEN_THOUSAND) {
            return BigDecimalUtil.format(totalTradeVolume, scale);
        }
        if (totalTradeVolume < A_HUNDRED_MILLION) {
            //单位万
            return BigDecimalUtil.div(totalTradeVolume, TEN_THOUSAND, scale) + UNIT_OF_TEN_THOUSAND;
        }
        //单位亿
        return BigDecimalUtil.div(totalTradeVolume, A_HUNDRED_MILLION, scale) + UNIT_OF_A_HUNDRED_MILLION;
    }

    public static boolean checkHasPermission(Context context, String categoryId) {
        if (UserHelper.getInstance(context).isLogin()) {
            Category category = CategoryHelper.getCategoryById(context, categoryId);
            if (category == null || !PermissionHelper.hasPermission(context, category)) {
                Toast.makeText(context, "该行情暂停服务", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @NonNull
    public static Map<String, List<Category>> filterByPermissionAndGroup(Context context, List<Category> categoryList) {
        Map<String, List<Category>> filteredCategoryMap = new HashMap<>();
        for (Category category : categoryList) {
            if (!PermissionHelper.hasPermission(context, category)) {
                continue;
            }
            if (!filteredCategoryMap.containsKey(category.market)) {
                filteredCategoryMap.put(category.market, new ArrayList<Category>());
            }
            filteredCategoryMap.get(category.market).add(category);
        }
        return filteredCategoryMap;
    }

    @NonNull
    public static List<Category> pickSortedMarket(Context context, Map<String, List<Category>> filteredCategoryMap) {
        List<Category> marketList = new ArrayList<>();
        for (String market : filteredCategoryMap.keySet()) {
            marketList.add(filteredCategoryMap.get(market).get(0));
        }

        sortCategoryList(context, marketList);
        return marketList;
    }

    private static void sortCategoryList(final Context context, List<Category> list){
        Collections.sort(list, new Comparator<Category>() {
            @Override
            public int compare(Category lhs, Category rhs) {
                int permissionL = PermissionHelper.getPermissionValue(context, lhs);
                int permissionR = PermissionHelper.getPermissionValue(context, rhs);
                if (permissionL > permissionR) {
                    return 1;
                } else if (permissionL == permissionR) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }

    public static ArrayList<Quote> convertToQuoteAndSort(Context context, List<Category> categoryList) {
        sortCategoryList(context, categoryList);
        ArrayList<Quote> quotesOfMarket = Lists.newArrayList(Collections2.transform(categoryList, new Function<Category, Quote>() {
            @Override
            public Quote apply(Category category) {
                Quote quote = CategoryHelper.getSnapshotById(category.id);
                if (quote == null) {
                    quote = Quote.build(category);
                }
                return quote;
            }
        }));

        return quotesOfMarket;
    }
}
