package com.nepplus.apifinalpractice_20210501.utils

import android.content.Context

class ContextUtil {

    companion object {

        private val prefName = "Daily10minutesPref"

        private val AUTO_LOGIN = "AUTO_LOGIN"

        private val LOGIN_TOKEN = "LOGIN_TOKEN"

        //자동 로그인 설정여부 저장 함수

        fun setAutoLogin(context: Context, autoLogin: Boolean) {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            pref.edit().putBoolean(AUTO_LOGIN, autoLogin).apply()
        }

        fun getAutoLogin(context: Context): Boolean {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            return pref.getBoolean(AUTO_LOGIN, false)
        }

        fun setLoginToken(context: Context, token: String) {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            pref.edit().putString(LOGIN_TOKEN, token).apply()
        }

        fun getLoginToken(context: Context): String {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            return pref.getString(LOGIN_TOKEN, "")!!


        }


    }
}