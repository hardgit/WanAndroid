package com.android.mykotlinandroid.ui.activity


import android.content.Intent
import android.text.TextUtils
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.base.BaseActivity
import com.android.mykotlinandroid.base.BaseMvpActivity
import com.android.mykotlinandroid.mvp.main.MyPresenter
import com.android.mykotlinandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_login.user_phone
import kotlinx.android.synthetic.main.activity_login.user_pwd
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseMvpActivity<MyPresenter>(){
    override fun initLoad() {

    }

    override fun initData() {

    }

    var map:HashMap<String,String> = hashMapOf()

    override fun initPresenter(): MyPresenter {
        return MyPresenter(this)
    }

    override fun initView() {
        register_btn.setOnClickListener {
            var userName = user_phone.text.toString().trim()
            var user_pwd = user_pwd.text.toString().trim()
            var user_pwd_again = user_pwd_again.text.toString().trim()
            if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(user_pwd)||TextUtils.isEmpty(user_pwd_again)){
               ToastUtil.show(this,"请您填写完整信息")
                return@setOnClickListener
            }
              map["username"] = userName
              map["password"] = user_pwd
              map["repassword"] = user_pwd_again
           presenter.userRegist(map)

        }

    }

    override fun getIayoutId(): Int {
        return R.layout.activity_register
    }

    override fun setTitleName(): String {
        return getString(R.string.register_title)
    }
    companion object{
        fun start(context: BaseActivity) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }
}
