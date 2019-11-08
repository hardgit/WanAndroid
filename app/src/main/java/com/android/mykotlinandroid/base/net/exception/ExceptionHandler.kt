package com.android.mykotlinandroid.base.net.exception
import android.net.ParseException
import com.android.mykotlinandroid.base.net.exception.Code.Companion.BAD_GATEWAY
import com.android.mykotlinandroid.base.net.exception.Code.Companion.FORBIDDEN
import com.android.mykotlinandroid.base.net.exception.Code.Companion.GATEWAY_TIMEOUT
import com.android.mykotlinandroid.base.net.exception.Code.Companion.HTTP_ERROR
import com.android.mykotlinandroid.base.net.exception.Code.Companion.INTERNAL_SERVER_ERROR
import com.android.mykotlinandroid.base.net.exception.Code.Companion.NET_ERROR
import com.android.mykotlinandroid.base.net.exception.Code.Companion.NOT_FOUND
import com.android.mykotlinandroid.base.net.exception.Code.Companion.PARSE_ERROR
import com.android.mykotlinandroid.base.net.exception.Code.Companion.REQUEST_TIMEOUT
import com.android.mykotlinandroid.base.net.exception.Code.Companion.SERVICE_UNAVAILABLE
import com.android.mykotlinandroid.base.net.exception.Code.Companion.SSL_ERROR
import com.android.mykotlinandroid.base.net.exception.Code.Companion.TIMEOUT_ERROR
import com.android.mykotlinandroid.base.net.exception.Code.Companion.UNAUTHORIZED
import com.android.mykotlinandroid.base.net.exception.Code.Companion.UNKNOWN_ERROR
import com.android.mykotlinandroid.base.net.exception.ResponseException
import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class ExceptionHandler {
    companion object {
        fun handle(e: Throwable): ResponseException {
            val responseException: ResponseException
            if (e is ApiException) {
                responseException = ResponseException(e, Integer.valueOf(e.errorCode), e.message)
            } else if (e is HttpException) {
                responseException = when (e.code()) {
                    UNAUTHORIZED, FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE -> ResponseException(e, "$HTTP_ERROR:${e.code()}", "网络连接错误")
                    else -> ResponseException(e, "$HTTP_ERROR:${e.code()}", "网络连接错误（${e.code()}）")
                }
            } else if (e is JsonParseException
                    || e is JSONException
                    || e is ParseException) {
                responseException = ResponseException(e, PARSE_ERROR, "解析错误")
            } else if (e is ConnectException) {
                responseException = ResponseException(e, NET_ERROR, "连接失败")
            }else if (e is UnknownHostException) {
                responseException = ResponseException(e, NET_ERROR, "请您打开网络")
            } else if (e is ConnectTimeoutException || e is java.net.SocketTimeoutException) {
                responseException = ResponseException(e, TIMEOUT_ERROR, "网络连接超时")
            } else if (e is javax.net.ssl.SSLHandshakeException) {
                responseException = ResponseException(e, SSL_ERROR, "证书验证失败")
            } else {
                responseException = ResponseException(e, UNKNOWN_ERROR, "网络错误")
            }
            return responseException
        }
    }
}