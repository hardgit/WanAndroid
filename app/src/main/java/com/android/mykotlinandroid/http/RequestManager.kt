import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.android.mykotlinandroid.base.observer.BaseObserver
import com.android.mykotlinandroid.base.net.convert.ExceptionConvert
import com.android.mykotlinandroid.http.NetResult
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object RequestManager {
    /**
     * 通用网络请求方法
     */
    fun <E> execute(activity: FragmentActivity?, observable: Observable<NetResult<E>>, observer: BaseObserver<E>){
        observable
            .map(ResponseConvert())
            .onErrorResumeNext(ExceptionConvert<E>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity, Lifecycle.Event.ON_DESTROY)))
            .subscribe(observer)
    }


}

