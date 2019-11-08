package com.android.mykotlinandroid.utils.loading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.mykotlinandroid.R;
import com.google.gson.JsonParseException;
import org.json.JSONException;
import retrofit2.HttpException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * *********************************************
 *
 * @项目名称 AndroidX
 * @文件用途 JavaDoc
 * @创建时间 2019/8/7
 * *********************************************
 */
public class WEmptyView extends FrameLayout {


    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    public static final int NETWORK_ERROR = 100001;
    public static final int DATA_EMPTY = 100002;


    private WLoadingView mLoadingView;
    private TextView mTitleTextView;

    private LinearLayout loading_bg;
    private WEmptyView wempty_view;

    public WEmptyView(Context context) {
        this(context, null);
    }

    public WEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.WEmptyView);
        Boolean attrShowLoading = arr.getBoolean(R.styleable.WEmptyView_libx_show_loading, false);
        String attrTitleText = arr.getString(R.styleable.WEmptyView_libx_title_text);
        String attrDetailText = arr.getString(R.styleable.WEmptyView_libx_detail_text);
        String attrBtnText = arr.getString(R.styleable.WEmptyView_libx_btn_text);
        arr.recycle();
        //show(attrShowLoading,attrTitleText,attrDetailText,attrBtnText,null);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, this, true);
        loading_bg = findViewById(R.id.loading_bg);
        mLoadingView = findViewById(R.id.empty_view_loading);
        wempty_view = findViewById(R.id.wempty_view);
    }
    /**
     * 用于显示emptyView并且只显示loading的情况，此时title、detail、button都被隐藏
     * @param loading 是否显示loading
     */
    public void show(boolean loading) {
        setLoadingShowing(loading);
        show();
    }

    /**
     * 用于显示纯文本的简单调用方法，此时loading、button均被隐藏
     * //     * @param titleText 标题的文字，不需要则传null
     * //     * @param detailText 详情文字，不需要则传null
     */
    public void show(Throwable e, OnClickListener onButtonClickListener) {
        int state = 0;
        if (e instanceof HttpException) {
            //HTTP错误
            //onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            //连接错误
            state = NETWORK_ERROR;
            //onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //连接超时
            state = NETWORK_ERROR;
            // onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            //解析错误
            // onException(ExceptionReason.PARSE_ERROR);
            Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
        } else {
            //其他错误
            // onException(ExceptionReason.UNKNOWN_ERROR);
            Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();

        }

        show(state, onButtonClickListener);
    }

    /**
     * 显示异常布局
     *
     * @param state
     * @param onButtonClickListener
     */
    public void show(int state, OnClickListener onButtonClickListener) {
        switch (state) {
            case NETWORK_ERROR:
                setLoadingShowing(false);
                break;
            case DATA_EMPTY:
                setLoadingShowing(false);
                break;
        }
    }


    /**
     * 显示emptyView，不建议直接使用，建议调用带参数的show()方法，方便控制所有子View的显示/隐藏
     */
    public void show() {
        setVisibility(VISIBLE);
    }

    /**
     * 隐藏emptyView
     */
    public void hide() {
        setVisibility(GONE);
        setLoadingShowing(false);
    }

    /**
     * 隐藏emptyView   提示异常信息
     */
    public void hide(Throwable throwable) {
        if (throwable instanceof HttpException) {
            //HTTP错误
        } else if (throwable instanceof ConnectException || throwable instanceof UnknownHostException) {
            //连接错误
            Toast.makeText(getContext(), "请检查网络是否可用", Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof InterruptedIOException) {
            //连接超时
            Toast.makeText(getContext(), "请检查网络是否可用", Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof JsonParseException || throwable instanceof JSONException || throwable instanceof ParseException) {
            Toast.makeText(getContext(), "解析异常", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
        }
        hide();
    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    public boolean isLoading() {
        return mLoadingView.getVisibility() == VISIBLE;
    }

    public void setLoadingShowing(boolean show) {
        if (!show) {
            wempty_view.setBackgroundColor(Color.WHITE);
        } else {
            wempty_view.setBackgroundColor(Color.argb(0, 255, 255, 255));
        }

        loading_bg.setVisibility(show ? VISIBLE : GONE);
        mLoadingView.setVisibility(show ? VISIBLE : GONE);
    }

}
