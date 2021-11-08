package com.heartsun.pithuwakhanipani.ui.noticeBoard

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidcommon.RDrawable
import androidcommon.base.BaseActivity
import androidcommon.extension.showErrorDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.databinding.ActivityNoticeBoardBinding
import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import com.heartsun.pithuwakhanipani.ui.sameetee.MemberTypeAdapter
import com.heartsun.pithuwakhanipani.ui.sameetee.SameeteeListActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoticeBoardActivity : BaseActivity() {

    private val binding by lazy {
        ActivityNoticeBoardBinding.inflate(layoutInflater)
    }
    private val homeViewModel by viewModel<HomeViewModel>()

    private lateinit var noticeListAdapter: NoticeListAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NoticeBoardActivity::class.java)
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
                this@NoticeBoardActivity.finish()
            }
            toolbar.tvToolbarTitle.text = "सूचना पाटी"
        }
        showProgress()
        noticesFromServerObserver()
        getNoticesFromDb()
    }

    @DelicateCoroutinesApi
    private fun getNoticesFromDb() {
        homeViewModel.noticesFromLocalDb.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
                noticesFromServerObserver()
                GlobalScope.launch {
                    homeViewModel.getNoticesFromServer()
                }
            } else {
                binding.clEmptyList.isVisible = false
                noticeListAdapter = NoticeListAdapter(
                    onItemClick = {
                        startActivity(
                            NoticeDetailsActivity.newIntent(
                                context = this,
                                title = it.NoticeHeadline.orEmpty(),
                                details = it.NoticeDesc.orEmpty(),
                                image = it.NoticeFile.orEmpty(),
                                date = it.DateNep.orEmpty(),
                                pageTitle = "सूचना पाटी"
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

    private fun noticesFromServerObserver() {
        homeViewModel.noticesFromServer.observe(this, {
            it ?: return@observe

            if (it.status.equals("success", true)) {
                hideProgress()
                if (it.tblNotice.isNullOrEmpty()) {
                    binding.clEmptyList.isVisible = true
                } else {
                    binding.clEmptyList.isGone = true
                    for (notice in it.tblNotice) {
                        homeViewModel.insert(notice)
                    }
                }

            } else {
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