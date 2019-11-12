package com.android.mykotlinandroid.ui.fragment


import android.view.View
import com.android.mykotlinandroid.MyApplication
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.base.BaseActivity
import com.android.mykotlinandroid.base.BaseMvpFragment
import com.android.mykotlinandroid.mvp.main.MyPresenter
import com.android.mykotlinandroid.ui.activity.LoginActivity
import com.android.mykotlinandroid.ui.activity.MyCollectActivity
import com.android.mykotlinandroid.ui.view.Contract
import com.android.mykotlinandroid.utils.ToastUtil
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnConfirmListener
import kotlinx.android.synthetic.main.fragment_layout_my.*

/**
 * author : zf
 * date   : 2019/10/18
 * You are the best.
 */
class MyFragment : BaseMvpFragment<MyPresenter>(), Contract.View {


    override fun initLoad() {

    }

    override fun initData() {
        my_collect.setOnClickListener {
            if(!SpUtil.getCookies().isEmpty())
                activity?.let { it1 -> MyCollectActivity.actionStart(it1,"","") }
            else   activity?.let { it1 -> LoginActivity.actionStart(it1 as BaseActivity) }

        }
    }

    override fun initPresenter(): MyPresenter {
        return MyPresenter(activity)
    }

    override fun initView() {
        if (SpUtil.getUserId() != 0) {
            MyApplication.ISLOGIN = true
            user_head_text.text = SpUtil.getUsername()
        }

        LiveEventBus.get("userName").observe(this, androidx.lifecycle.Observer {
            ToastUtil.show(activity,"登录成功")
            user_head_text.text = it.toString()
            MyApplication.ISLOGIN = true
            my_logo_layout.isEnabled = false
            exit_img.visibility = View.VISIBLE
            click()
        })
        click()
        LiveEventBus.get("userExit").observe(this,androidx.lifecycle.Observer {
            ToastUtil.show(activity,"安全退出")
            user_head_text.text = getString(R.string.notlogin)
            MyApplication.ISLOGIN = false
            my_logo_layout.isEnabled = true
            exit_img.visibility = View.GONE
            click()
        })
    }

    fun click() {
       if(!MyApplication.ISLOGIN){
           /* 点击跳转 */
           my_logo_layout.setOnClickListener {
               LoginActivity.actionStart(activity as BaseActivity)
           }
       }else{
           exit_img.visibility = View.VISIBLE
           /* 退出登录 */
           exit_img.setOnClickListener {
               XPopup.Builder(activity).run {
                   asConfirm("安全退出",
                       "您确定要退出登录吗？",
                       "取消",
                       "确定",
                       OnConfirmListener {
                           presenter.userExit()
                       },null,false).show()

               }

           }
       }
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_layout_my
    }

    companion object {
        fun newInstance(): MyFragment {
            return MyFragment()
        }
    }

}