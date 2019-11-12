
class SpUtil {
    companion object {
        fun getCookies(): String {
            return SharedPreferencesHelper.get("cookies", "")
        }

        fun setCookies(version: String) {
            SharedPreferencesHelper.put("cookies", version)
        }

        fun removeCookies() {
            SharedPreferencesHelper.remove("cookies")
        }

        fun getUsername(): String {
            return SharedPreferencesHelper.get("username", "登录")
        }

        fun setUsername(username: String) {
            SharedPreferencesHelper.put("username", username)
        }

        fun removeUsername() {
            SharedPreferencesHelper.remove("username")
        }
        fun setUserId(userId: Int) {
            SharedPreferencesHelper.put("userId", userId)
        }
        fun getUserId() : Int{
            return SharedPreferencesHelper.get("userId",0)
        }

        fun removeUserId() {
            SharedPreferencesHelper.remove("userId")
        }
    }
}