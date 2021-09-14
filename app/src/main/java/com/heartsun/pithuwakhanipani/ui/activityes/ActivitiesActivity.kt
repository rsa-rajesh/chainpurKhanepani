package com.heartsun.pithuwakhanipani.ui.activityes

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidcommon.RDrawable
import androidcommon.base.BaseActivity
import androidcommon.extension.showErrorDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.databinding.ActivityNoticeBoardBinding
import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeDetailsActivity
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeListAdapter
import com.heartsun.pithuwakhanipani.ui.sameetee.MemberTypeAdapter
import com.heartsun.pithuwakhanipani.ui.sameetee.SameeteeListActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActivitiesActivity : BaseActivity() {

    private val binding by lazy {
        ActivityNoticeBoardBinding.inflate(layoutInflater)
    }
    private val homeViewModel by viewModel<HomeViewModel>()

    private lateinit var noticeListAdapter: ActivitiesListAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ActivitiesActivity::class.java)
        }
    }

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    @DelicateCoroutinesApi
    private fun initView() {
        with(binding) {
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@ActivitiesActivity.finish()
            }
            toolbar.tvToolbarTitle.text = "क्रियाकलाप"
        }
        showProgress()
        activityFromServerObserver()
        getNoticesFromDb()
    }

    @DelicateCoroutinesApi
    private fun getNoticesFromDb() {
        homeViewModel.activityFromLocalDb.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
                activityFromServerObserver()
                GlobalScope.launch {
                    homeViewModel.getActivitiesFromServer()
                }
            } else {
                binding.clEmptyList.isVisible = false
                noticeListAdapter = ActivitiesListAdapter(
                    onItemClick = {
                        startActivity(
                            NoticeDetailsActivity.newIntent(
                                context = this,
                                title = it.ActivityHeadline.orEmpty(),
                                details = it.ActivityDesc.orEmpty(),
                                image = it.ActivityFile.orEmpty(),
                                date = it.DateNep.orEmpty(),
                                pageTitle = "क्रियाकलाप"
                            )
                        )
                    }
                )
                noticeListAdapter.items = it
                binding.rvList.layoutManager = LinearLayoutManager(this)
                binding.rvList.adapter = noticeListAdapter
                hideProgress()
            }
        })
    }

    private fun activityFromServerObserver() {
        homeViewModel.activitiesFromServer.observe(this, {
            it ?: return@observe

            if(it.status.equals("success",true)){
                for (notice in it.tblActivity) {
                    homeViewModel.insert(notice)
                }
            }else{
                hideProgress()
                showErrorDialog(
                    message = "माफ गर्नुहोस्!!! सर्भरमा जडान गर्न सकेन \n" +
                            " कृपया पछि फेरि प्रयास गर्नुहोस्",
                    "पुन: प्रयास गर्नुहोस्",
                    "त्रुटि",
                    RDrawable.ic_error_for_dilog,
                    color = Color.RED
                )
            }



        })
    }
}