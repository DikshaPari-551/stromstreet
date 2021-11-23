package com.example.myapplication.util

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.orhanobut.hawk.Hawk
import java.util.function.DoubleUnaryOperator

class SavedPrefManager(var context: Context) {
    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    fun setKeyEndPointARN(keyEndPointARN: String) {
        setStringValue(KEY_END_POINT_ARN, keyEndPointARN)
    }

    //    public static void SaveString(String key,String value){
    //
    //        Hawk.put(key,value);
    //
    //    }
    //
    //    public static String GetString(String key){
    //
    //
    //        return Hawk.get(key);
    //    }
    fun setKeyIsRememberMe(keyIsRememberMe: Boolean) {
        setBooleanValue(KEY_IS_REMEMBER_ME, keyIsRememberMe)
    }

    val isRememberMe: Boolean
        get() = getBooleanValue(KEY_IS_REMEMBER_ME)

    fun setKeyIsLogin(keyIsLogin: Boolean) {
        setBooleanValue(KEY_IS_LOGIN, keyIsLogin)
    }

    val isLogin: Boolean
        get() = getBooleanValue(KEY_IS_LOGIN)
    val keyDeviceToken: String?
        get() = getStringValue(KEY_DEVICE_TOKEN)

    fun setKeyDeviceToken(token: String) {
        setStringValue(KEY_DEVICE_TOKEN, token)
    }

    val keyNotificationId: Int
        get() = getIntValue(KEY_NOTIFICATION_ID)

    fun setKeyNotifiactionId(keyNotifiaction: Int) {
        setIntValue(KEY_NOTIFICATION_ID, keyNotifiaction)
    }

    val latitude: String?
        get() = getStringValue(KEY_LATITUDE)

    fun setLatitude(keyLatitude: String) {
        setStringValue(KEY_LATITUDE, keyLatitude)
    }

    val longitude: String?
        get() = getStringValue(KEY_LONGITUDE)

    fun setLongitude(longitude: String) {
        setStringValue(KEY_LONGITUDE, longitude)
    }

    /**
     * Retrieving the value from the preference for the respective key.
     *
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as boolean.
     */
    private fun getBooleanValue(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    /**
     * Saving the preference
     *
     * @param key   : Key of the preference.
     * @param value : Value of the preference.
     */
    private fun setBooleanValue(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    /**
     * Retrieving the value from the preference for the respective key.
     *
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as string.
     */
    private fun getStringValue(key: String): String? {
        return preferences.getString(key, "")
    }

    /**
     * Saving the preference
     *
     * @param key   : Key of the preference.
     * @param value : Value of the preference.
     */
    private fun setStringValue(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    /**
     * Retrieving the value from the preference for the respective key.
     *
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as string.
     */
    private fun getIntValue(key: String): Int {
        return preferences.getInt(key, 0)
    }

    /**
     * Saving the preference
     *
     * @param key   : Key of the preference.
     * @param value : Value of the preference.
     */
    private fun setIntValue(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    /**
     * Retrieving the value from the preference for the respective key.
     *
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as string.
     */
    fun getLongValue(key: String?): Long {
        return preferences.getLong(key, 0L)
    }

    /**
     * Saving the preference
     *
     * @param key   : Key of the preference.
     * @param value : Value of the preference.
     */
    fun setLongValue(key: String?, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    /**
     * Remove the preference for the particular key
     *
     * @param key : Key for which the preference to be cleared.
     */
    fun removeFromPreference(key: String?) {
        editor.remove(key)
        editor.commit()
    }

    companion object {
        //preferences variables
        const val EMAIL = "email"
        const val PASSWORD = "password"

        const val AUTH_TOKEN = "auth_token"
        const val TOKEN = "token"
        const val USERID = "userid"
        const val COMMENT_ID = "commentId"
        const val userName = "userName"
         var _id = "_id"
         var otherUserId = "otherUserId"
        const val postid = "postid"
        private const val KEY_USER_DETAIL = "key_user_detail"
        private const val KEY_IS_REMEMBER_ME = "key_is_remember_me"
        const val KEY_IS_LOGIN = "key_is_login"
        private const val KEY_DEVICE_TOKEN = "key_device_token"
        private const val KEY_NOTIFICATION_ID = "notification_id"
        private const val KEY_LATITUDE = "key_lattitude"
        private const val KEY_LONGITUDE = "key_longitude"
        private const val KEY_CALL_TYPE = "key_call_type"
        private const val QB_USER_ID = "qb_user_id"
        private const val QB_USER_LOGIN = "qb_user_login"
        private const val QB_USER_PASSWORD = "qb_user_password"
        private const val QB_USER_FULL_NAME = "qb_user_full_name"
        private const val KEY_END_POINT_ARN = "end_point_arn"
        private var instance: SavedPrefManager? = null
        private var value : Double? = null
        const val IMAGE_ONE : String = "IMAGE_ONE"
        const val IMAGE_TWO : String = "IMAGE_TWO"
        const val IMAGE_THREE : String = "IMAGE_THREE"
        const val LATITUDE = "LAT"
        const val LONGITUDE = "LONG"
        private var lat : Double? = null
        private var long : Double? = null
        private const val PREF_HIGH_QUALITY = "pref_high_quality"


        fun getInstance(context: Context): SavedPrefManager? {
            if (instance == null) {
                synchronized(SavedPrefManager::class.java) {
                    if (instance == null) {
                        instance = SavedPrefManager(context)
                    }
                }
            }
            return instance
        }

        fun SaveString(key: String?, value: String) {
            Hawk.put(key, value)
        }

        fun GetString(key: String?): String {
            return Hawk.get(key)
        }

        fun saveStringPreferences(context: Context?, key: String, value: String?): String {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
            return key
        }

        fun saveIntPreferences(context: Context?, key: String?, value: Int) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun saveFloatPreferences(context: Context?, key: String?, value: Float) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putFloat(key, value)
            editor.apply()
        }

        /*
  This method is used to get string values from shared preferences.
   */
        fun getStringPreferences(context: Context?, key: String?): String? {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString(key, "")
        }

        /*
     This method is used to get string values from shared preferences.
      */
        fun getIntPreferences(context: Context?, key: String?): Int {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getInt(key, 0)
        }

        fun savePreferenceBoolean(context: Context?, key: String?, b: Boolean) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putBoolean(key, b)
            editor.commit()
        }

        /*
      This method is used to get string values from shared preferences.
       */
        fun getBooleanPreferences(context: Context?, key: String?): Boolean {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getBoolean(key, false)
        }


        /**
         * Removes all the fields from SharedPrefs
         */
        fun clearPrefs(context: Context?) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        }

        fun setPrefHighQuality(context: Context?, isEnabled: Boolean) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putBoolean(PREF_HIGH_QUALITY, isEnabled)
            editor.apply()
        }

        fun getPrefHighQuality(context: Context?): Boolean {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getBoolean(PREF_HIGH_QUALITY, false)
        }

        fun getCountryName(conteActivity: Context?): String? {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(conteActivity)
            return sharedPreferences.getString("songs", "")
        }

        fun saveLoginPreferences(context: Context?, key: String?, value: String?) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.commit()
        }

        fun setLatitudeLocation(value : Double) {
            this.lat = value
        }

        fun getLatitudeLocation() : Double? {
            return lat
        }

        fun setLongitudeLocation(value : Double) {
            this.long = value
        }

        fun getLongitudeLocation() : Double? {
            return long
        }
    }


    //    /*save Login Model*/
    //    public static void saveLogin(String key, SignInResponse request) {
    //        Hawk.delete(key);
    //        Hawk.put(key, request);
    //    }
    //    /*get Login Model*/
    //    public static SignInResponse getLogin(String key) {
    //        return Hawk.get(key);
    //    }
    //    /*delete Login Model*/
    //    public static void deleteLogin(String key) {
    //        Hawk.delete(key);
    //    }
    init {
        preferences =
            context.getSharedPreferences("stromstreet", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }
}