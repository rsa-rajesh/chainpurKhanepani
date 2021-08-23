package com.heartsun.pithuwakhanipani.ui.noticeBoard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.databinding.ActivityNoticeBoardBinding
import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import com.heartsun.pithuwakhanipani.ui.sameetee.MemberTypeAdapter
import com.heartsun.pithuwakhanipani.ui.sameetee.SameeteeListActivity
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        with(binding) {
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@NoticeBoardActivity.finish()
            }
            toolbar.tvToolbarTitle.text = "सूचना पाटी"
        }
        getNoticesFromDb()

    }

    private fun getNoticesFromDb() {

        // TODO: 8/23/2021 change the observer no notices observer
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
                        startActivity(NoticeDetailsActivity.newIntent(context = this,title = it.NoticeHeadline.orEmpty(),details = it.NoticeDesc.orEmpty(),image = it.NoticeFile.orEmpty(),date = it.DateNep.orEmpty()))
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
            for (notice in it.tblNotice) {
                homeViewModel.insert(notice)
            }
        })
    }
}