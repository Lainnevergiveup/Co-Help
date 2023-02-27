package com.cohelp.task_for_stu.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SessionUtils {
        public static final String LOGIN = "login";
        public static final String COOKIEVAL = "cookieval";
        public static final String ACTIVITY = "Activities";
        private static Gson gson = new GSON().gsonSetter();

    public static void saveCookiePreference(Context context, String value) {
            SharedPreferences preference = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(COOKIEVAL, value);
            editor.apply();
        }

        public static void saveActivityPreference(Context context, String value) {
        SharedPreferences preference = context.getSharedPreferences(ACTIVITY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(ACTIVITY, value);
        editor.apply();
        }
    public static void deleteActivityPreference(Context context) {
        SharedPreferences preference = context.getSharedPreferences(ACTIVITY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(ACTIVITY);
        editor.apply();
    }
        public static String getCookiePreference(Context context) {
            SharedPreferences preference = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
            String cookieval = preference.getString(COOKIEVAL, "");
            return cookieval;
        }
    public static List<DetailResponse> getActivityPreference(Context context) {
        SharedPreferences preference = context.getSharedPreferences(ACTIVITY, Context.MODE_PRIVATE);
        List<DetailResponse> list= gson.fromJson(preference.getString(ACTIVITY, ""),new TypeToken<List<DetailResponse>>(){}.getType());
        return list;
    }
}
