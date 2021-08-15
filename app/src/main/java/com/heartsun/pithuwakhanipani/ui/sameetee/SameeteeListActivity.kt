package com.heartsun.pithuwakhanipani.ui.sameetee

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.databinding.ActivitySameeteeListBinding
import com.heartsun.pithuwakhanipani.databinding.ActivitySameeteeSelectionBinding
import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import com.heartsun.pithuwakhanipani.ui.waterRate.WaterRateAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SameeteeListActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySameeteeListBinding.inflate(layoutInflater)
    }
    private lateinit var memberListAdapter: MemberListAdapter

    private val memberTypeId by lazy { intent.getIntExtra(memberId, -1) }
    private val memberType by lazy { intent.getStringExtra(memberTypee) }
    private val isOldMember by lazy { intent.getIntExtra(isOldMembers,0) }

    companion object {
        private const val memberId = "memberTypeId"
        private const val memberTypee = "memberType"
        private const val isOldMembers = "isOldMember"

        fun newIntent(context: Context, memberTypeID: Int, memberType: String, isOldMember: Int): Intent {
            return Intent(context, SameeteeListActivity::class.java).apply {
                putExtra(isOldMembers, isOldMember)
                putExtra(memberId, memberTypeID)
                putExtra(memberTypee, memberType)
            }
        }
    }

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        if (isOldMember==0){
            homeViewModel.getMembers(memberTypeId)
        }else{
            binding.tvSelectYear.isVisible=true
            homeViewModel.getOldMembers(memberTypeId)
        }

        binding.toolbar.tvToolbarTitle.text = memberType
        binding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

        listObserver()
    }

    private fun listObserver() {
        homeViewModel.membersFromLocal?.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
            } else {
                binding.btMakePDF.isVisible=true
                binding.clEmptyList.isVisible = false
                memberListAdapter = MemberListAdapter(this)
                memberListAdapter.items = it
                binding.rvMembers.layoutManager = LinearLayoutManager(this)
                binding.rvMembers.adapter = memberListAdapter
                hideProgress()
            }
        })
    }


}