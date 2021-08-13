package com.heartsun.pithuwakhanipani.ui.sameetee

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.databinding.ActivitySameeteeSelectionBinding
import com.heartsun.pithuwakhanipani.databinding.ActivityWaterRateBinding
import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import com.heartsun.pithuwakhanipani.ui.about.AboutAppActivity
import com.heartsun.pithuwakhanipani.ui.waterRate.WaterRateActivity
import com.heartsun.pithuwakhanipani.ui.waterRate.WaterRateAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SameeteeSelectionActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySameeteeSelectionBinding.inflate(layoutInflater)
    }
    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var memberTypeAdapter: MemberTypeAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SameeteeSelectionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            toolbar.tvToolbarTitle.text = "समिति"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@SameeteeSelectionActivity.finish()
            }

            getMemberTypesFromDb()


        }
    }

    private fun getMemberTypesFromDb() {
        homeViewModel.membersTypeFromLocalDb.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
                membersFromServerObserver()
                GlobalScope.launch {
                    homeViewModel.getMembersFromServer()
                }
            } else {
                binding.clEmptyList.isVisible=false
                memberTypeAdapter = MemberTypeAdapter(
                    onItemClick = {
                        startActivity(SameeteeListActivity.newIntent(this,it.MemTypeID))
                    }
                )
                memberTypeAdapter.items = it
                binding.rvMemberList.layoutManager = LinearLayoutManager(this)
                binding.rvMemberList.adapter= memberTypeAdapter
                hideProgress()
            }
        })
    }

    private fun membersFromServerObserver() {
        homeViewModel.membersFromServer.observe(this, {
            it ?: return@observe
            for (memberType in it.tblBoardMemberType){
                homeViewModel.insert(memberType)
            }
            for (members in it.tblContact){
                homeViewModel.insert(members)
            }
        })
    }

}