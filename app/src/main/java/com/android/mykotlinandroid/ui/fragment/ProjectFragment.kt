package com.android.mykotlinandroid.ui.fragment


import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.android.mykotlinandroid.R
import com.android.mykotlinandroid.adapter.ProjectFragmentAdapter
import com.android.mykotlinandroid.base.BaseFragment
import com.android.mykotlinandroid.base.BaseMvpFragment
import com.android.mykotlinandroid.mvp.main.ProjectPresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_layout_project.*
import kotlinx.android.synthetic.main.fragment_layout_project.view.*

/**
 * author : zf
 * date   : 2019/10/18
 * You are the best.
 */
class ProjectFragment : BaseMvpFragment<ProjectPresenter>() {

    override fun initPresenter(): ProjectPresenter {
        return ProjectPresenter(activity)
    }

    override fun initLoad() {
        presenter.projectCategory()
    }

    override fun initData() {
       presenter.projectCategoryResponse.observe(this, Observer {
           project_tablayout.visibility = View.VISIBLE
           var fragments = arrayListOf<Fragment>()
           var titles = arrayListOf<String>()
           var adapter = ProjectFragmentAdapter(childFragmentManager)
           for (data in it){         /*动态创建fragment*/
               fragments.add(ProjectViewPageFragment.getNewInstance(data.id))
               titles.add(Html.fromHtml(data.name).toString())
           }
           adapter.addData(fragments,titles)
           project_viewpage.adapter = adapter
           /* tabLayout与ViewPage绑定*/
           project_tablayout.setupWithViewPager(project_viewpage)
       })

    }

    override fun initView() {
        /*设置tabLayout滚动效果*/
      project_tablayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun getLayoutResId(): Int {
       return R.layout.fragment_layout_project
    }

    companion object{
        fun newInstance():ProjectFragment{
            return ProjectFragment()
        }
    }
}