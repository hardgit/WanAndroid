

import com.android.mykotlinandroid.base.net.exception.ApiException
import com.android.mykotlinandroid.http.NetResult
import io.reactivex.functions.Function

class ResponseConvert<E> : Function<NetResult<E>, E> {
    override fun apply(baseResponse: NetResult<E>): E? {
        if (0 != baseResponse.errorCode) {
            throw ApiException(baseResponse.errorCode, baseResponse.errorMsg)
        }
        if (baseResponse.data == null) {
            baseResponse.data = "" as E
        }
        return baseResponse.data
    }
}