package com.android.mykotlinandroid.utils.error

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.android.mykotlinandroid.BuildConfig
import com.shehuan.wanandroid.utils.sp.SharedPreferencesHelper
import com.shehuan.wanandroid.utils.sp.SpUtil
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * author : zf
 * date   : 2019/11/5
 * You are the best.
 */
class CrashHandler :Thread.UncaughtExceptionHandler {


    /**
     * 系统默认UncaughtExceptionHandler
     */
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    /**
     * context
     */
    private var mContext: Context? = null

    /**
     * 存储异常和参数信息
     */
    private val paramsMap = HashMap<String, String>()

    /**
     * 格式化时间
     */
    private val format = SimpleDateFormat("yyyyMMdd_HHmmss")

    private val TAG = this.javaClass.simpleName



    private val savePath = Environment.getExternalStorageDirectory().toString() + "/qinjia/cache/crash/"



    /**
     * 获取CrashHandler实例
     */
 companion object{
        private var mInstance: CrashHandler? = null
        @Synchronized
        fun getInstance(): CrashHandler {
            if (null == mInstance) {
                mInstance = CrashHandler()
            }
            return mInstance as CrashHandler
        }
    }


    fun init(context: Context) {
        mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandler为系统默认的
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * uncaughtException 回调函数
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {//如果自己没处理交给系统处理
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {//自己处理
            try {//延迟3秒杀进程
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                Log.e("error", e.message)
            }

            System.exit(0)
        }
    }

    /**
     * 收集错误信息.发送到服务器
     *
     * @return 处理了该异常返回true, 否则false
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        //收集设备参数信息
        collectDeviceInfo(mContext)
        //添加自定义信息
        addCustomInfo()
        //使用Toast来显示异常信息
        object : Thread() {
            override fun run() {
                Looper.prepare()
                Toast.makeText(mContext, "亲,应用开小差了,请您稍后重启一下哦!", Toast.LENGTH_SHORT).show()

                Looper.loop()
            }
        }.start()
        //保存日志文件
        val filePath = savePath + saveCrashInfo2File(ex)!!
//        SharedPreferencesUtil.putShareprefer("error_file", filePath)
        return true
    }


    /**
     * 收集设备参数信息
     * @param ctx
     */
    fun collectDeviceInfo(ctx: Context?) {
        //获取versionName,versionCode
        try {
            val pm = ctx!!.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                paramsMap["versionName"] = versionName
                paramsMap["versionCode"] = versionCode
            }
            val cookie = SpUtil.getCookies()
            if (!cookie.isEmpty()) {//用户登录过了
                paramsMap["user_Name"] = SpUtil.getUsername()
            } else {
                paramsMap["user_Name"] = "此用户没有登录!"
            }


        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("error", e.message)
        }

        //获取所有系统信息
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                paramsMap[field.name] = field.get(null).toString()
            } catch (e: Exception) {
                Log.e("error", e.message)
            }

        }
    }

    /**
     * 添加自定义参数
     */
    private fun addCustomInfo() {

    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String? {

        val sb = StringBuffer()
        for ((key, value) in paramsMap) {
            sb.append("$key=$value\n")
        }

        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        if (BuildConfig.DEBUG) {
            Log.e("TAG", "debug")
            return null
        } else {
            Log.e("TAG", "release")
        }
        try {
            val timestamp = System.currentTimeMillis()
            val time = format.format(Date())
            val fileName = "crash-$time.log"
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val path = Environment.getExternalStorageDirectory().toString() + "/wanAndroid/cache/crash/"
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val fos = FileOutputStream(path + fileName)
                fos.write(sb.toString().toByteArray())
                fos.close()
            }
            return fileName
        } catch (e: Exception) {
            Log.e("error", e.message)
        }

        return null
    }

}