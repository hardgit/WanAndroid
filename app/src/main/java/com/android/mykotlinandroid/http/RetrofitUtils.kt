package com.android.mykotlinandroid.http

import com.android.mykotlinandroid.base.net.HttpBaseUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * author : zf
 * date   : 2019/10/21
 * You are the best.
 */
class RetrofitUtils {

    lateinit var mRetrofit: Retrofit

    companion object {
        val getInstance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitUtils()
        }
    }

    private constructor()

    fun getRetrofit(): Retrofit {
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)//10秒
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        mRetrofit = Retrofit.Builder()
            .baseUrl(HttpBaseUrl.HTTP_HOST) // 添加base url
            .addConverterFactory(ScalarsConverterFactory.create())//请求结果转换为基本类型,String
            .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
            .client(mOkHttpClient)
            .build()
        return mRetrofit
    }

    fun <T> getRetrofitService(mclass: Class<T>): T {
        return getRetrofit().create(mclass)
    }
}