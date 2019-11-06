package com.android.mykotlinandroid.ui.activity

import android.content.Intent
import android.text.TextUtils
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.base.BaseActivity
import com.android.mykotlinandroid.base.BaseMvpActivity
import com.android.mykotlinandroid.mvp.main.MyPresenter
import com.android.mykotlinandroid.ui.view.Contract
import com.android.mykotlinandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.user_phone
import kotlinx.android.synthetic.main.activity_login.user_pwd

class LoginActivity : BaseMvpActivity<MyPresenter>(),Contract.View{
    override fun initLoad() {

    }

    override fun initData() {

    }

    var map:HashMap<String,String> = hashMapOf()
    override fun initPresenter(): MyPresenter {
        return MyPresenter(this)
    }

    override fun initView() {

        login_btn.setOnClickListener {
            var userName = user_phone.text.toString().trim()
            var user_pwd = user_pwd.text.toString().trim()
            if(TextUtils.isEmpty(userName)|| TextUtils.isEmpty(user_pwd)){
                ToastUtil.show(this,"请您填写完整信息")
                return@setOnClickListener
            }
            map["username"] = userName
            map["password"] = user_pwd
            presenter.userLogin(map)
        }
        to_regist_btn.setOnClickListener {
            RegisterActivity.start(this)
        }
    }

    override fun getIayoutId(): Int {
       return R.layout.activity_login
    }

    override fun setTitleName(): String {
        return getString(R.string.login_title)
    }

    companion object{
        fun actionStart(context: BaseActivity) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}
