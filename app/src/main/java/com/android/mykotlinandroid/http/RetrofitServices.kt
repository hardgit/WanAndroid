package com.android.mykotlinandroid.http

import com.android.mykotlinandroid.mvp.response.*
import io.reactivex.Observable
import retrofit2.http.*
import java.net.HttpURLConnection
import java.net.IDN


/**
 * author : zf
 * date   : 2019/10/21
 * You are the best.
 */
interface RetrofitServices {

    /* 首页banner */
    @GET(HttpUrlConnect.GET_HOMEPAGE_BANNER_LIST)
    fun getHomeBannerList(): Observable<NetResult<List<BannerResponse>>>

    /* 首页文字列表 */
    @GET(HttpUrlConnect.GET_HOMEPAGE_ARTICLE_LIST)
    fun getHomeListData(@Path("pageNum")pageNum: Int): Observable<NetResult<HomeListResponse>>

    /* 登录 */
    @POST(HttpUrlConnect.LOGIN)
    fun getLogin(@QueryMap map:Map<String,String>): Observable<NetResult<UserResponse>>

    /* 注册 */
    @POST(HttpUrlConnect.REGISTER)
    fun getRegist(@QueryMap map:Map<String,String>): Observable<NetResult<UserResponse>>

    /* 退出登录 */
    @GET(HttpUrlConnect.LOGOUT)
    fun getExit(): Observable<NetResult<Any>>

    /* 收藏 -- 首页文字列表 */
    @POST(HttpUrlConnect.COLLECT_ARTICLE_INSIDE)
    fun addCollect(@Path("id")id: Int): Observable<NetResult<Any>>

    /* 取消收藏 -- 首页文字列表 */
    @POST(HttpUrlConnect.UN_COLLECT_ARTICLE_LIST)
    fun cancelCollect(@Path("id")id: Int): Observable<NetResult<Any>>

     /* 收藏列表 -- 我的收藏 */
    @GET(HttpUrlConnect.GET_COLLECTION_LIST)
    fun collectList(@Path("pageNum")pageNum: Int): Observable<NetResult<CollectResponse>>

    /* 取消收藏 -- 我的收藏 */
    @POST(HttpUrlConnect.UN_COLLECT_ARTICLE_COLLECTED)
    fun cancelMyCollect(@Path("id")id: Int,@Query("originId")originId:Int):Observable<NetResult<Any>>

    /* 导航类型列表 */
    @POST(HttpUrlConnect.GET_NAVIGATION_LIST)
    fun hotTypeData():Observable<NetResult<List<NavResponse>>>

    /* 公众号列表 */
    @GET(HttpUrlConnect.GET_BJNEWS_LIST)
    fun gzhList(): Observable<NetResult<List<GZHResponse>>>

    /* 公众号列表 */
    @GET(HttpUrlConnect.GET_BJNEWS_ARTICLES)
    fun gzhDetails(@Path("id")id: Int,@Path("pageNum")pageNum:Int): Observable<NetResult<GZHDetailsResponse>>

    /* 项目类型列表 */
    @GET(HttpUrlConnect.GET_PROJECT_CATEGORY)
    fun projectCategory(): Observable<NetResult<List<ProjectCategoryResponse>>>

    /* 项目类型列表详情 */
    @GET(HttpUrlConnect.GET_PROJECT_LIST)
    fun detailsCategory(@Path("pageNum")pageNum: Int,@Query("cid")cid:Int): Observable<NetResult<ProjectViewpageResponse>>
}

