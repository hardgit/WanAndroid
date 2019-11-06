package com.android.mykotlinandroid.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.webkit.*
import androidx.annotation.RequiresApi
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.base.BaseActivity
import com.android.mykotlinandroid.http.ACTION_TITLE
import com.android.mykotlinandroid.http.ACTION_WEB_URL
import kotlinx.android.synthetic.main.activity_article_web.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

class ArticleWebActivity : BaseActivity() {
    override fun initLoad() {

    }

    override fun initData() {

    }

    override fun initView() {

       var webSetting = brideg_webview.settings
        webSetting.domStorageEnabled = true
        webSetting.loadWithOverviewMode = true
        webSetting.useWideViewPort = true
        @SuppressLint("SetJavaScriptEnabled")
        webSetting.javaScriptEnabled = true
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE


        brideg_webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        brideg_webview.webChromeClient = object : WebChromeClient(){
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                var titleName = intent.getStringExtra(ACTION_TITLE)
                if(TextUtils.isEmpty(titleName))
                    toolbar.title_text.text = title
                else toolbar.title_text.text = titleName
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if(newProgress == 100){
                    webview_progress.visibility = View.GONE
                }else{
                    webview_progress.visibility = View.VISIBLE
                    webview_progress.progress = newProgress
                }

            }
        }
        if(intent.getStringExtra(ACTION_WEB_URL) != null){
            brideg_webview.loadUrl(intent.getStringExtra(ACTION_WEB_URL))
        }

    }

    override fun getIayoutId(): Int {
        return R.layout.activity_article_web
    }



    companion object {
        /**
         * 界面入口
         *
         * @param context Context 对象
         * @param title 标题
         * @param url Web Url
         */
        fun actionStart(context: Activity, title: String, url: String) {
            context.startActivity(Intent(context, ArticleWebActivity::class.java).apply {
                putExtra(ACTION_TITLE, title)
                putExtra(ACTION_WEB_URL, url)
                if (context !is Activity) {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            })
        }
    }

}
