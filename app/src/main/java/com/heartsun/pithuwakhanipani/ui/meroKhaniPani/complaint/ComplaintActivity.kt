package com.heartsun.pithuwakhanipani.ui.meroKhaniPani.complaint

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidcommon.extension.toastS
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.databinding.ActivityComplaintBinding
import com.heartsun.pithuwakhanipani.domain.BillDetails
import com.heartsun.pithuwakhanipani.domain.ComplaintResponse
import com.heartsun.pithuwakhanipani.ui.billDetails.BillDetailsAdapter
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.MyTapViewModel
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeDetailsActivity
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ComplaintActivity : BaseActivity() {

    private val myTapViewModel by viewModel<MyTapViewModel>()

    private val binding by lazy {
        ActivityComplaintBinding.inflate(layoutInflater)
    }

    private lateinit var complaintListAdapter: ComplaintListAdapter


    private val memberID1 by lazy {
        intent.getStringExtra(memberId)
    }
    private val phoneNo1 by lazy {
        intent.getStringExtra(phoneNO)
    }

    var lastMessage: String = ""

    companion object {
        private const val memberId = "MemberID"
        private const val phoneNO = "PhoneNO"

        fun newIntent(
            context: Context, memberID: String, phoneNo: String,
        ): Intent {
            return Intent(context, ComplaintActivity::class.java).apply {
                putExtra(memberId, memberID)
                putExtra(phoneNO, phoneNo)

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

        with(binding) {
            toolbar.tvToolbarTitle.text = "गुनासो"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@ComplaintActivity.finish()
            }
            showProgress()
            myTapViewModel.getComplaintListServer(memberID1, phoneNo1)

            ivSend.setOnClickListener {
                complaintAddObserver()
                if (!tvMessage.text.isNullOrBlank()) {
                    showProgress()
                    lastMessage = tvMessage.text.toString()
                    myTapViewModel.postComplaint(
                        tvMessage.text.toString(),
                        memberID1,
                        phoneNo1
                    )
                }
            }
        }
        complaintListObserver()
    }

    @SuppressLint("SetTextI18n")
    private fun complaintAddObserver() {
        myTapViewModel.addComplaint.observe(this, {

            it ?: return@observe
            if (it.equals("success", true)) {
                showProgress()
                myTapViewModel.getComplaintListServer(memberID1, phoneNo1)
                binding.tvMessage.setText("")
//                var date:Date= Date()
//                list.add(ComplaintResponse(null,memberID1.toString().toInt(),lastMessage,date.toString(),null))
//                complaintListAdapter.items = list
            } else {
                toastS("Unable to send \n Please try again later")
            }
            hideProgress()

        })
    }


    @SuppressLint("SetTextI18n")
    private fun complaintListObserver() {
        myTapViewModel.complaintList.observe(this, {

            it ?: return@observe
            if (!it.isNullOrEmpty()) {
                complaintListAdapter = ComplaintListAdapter()
                complaintListAdapter.items = it
                binding.rvMessages.layoutManager = LinearLayoutManager(this)
                binding.rvMessages.adapter = complaintListAdapter
                binding.rvMessages.scrollToPosition(it.size - 1)
                hideProgress()
            } else {
                toastS("List is empty")
            }
            hideProgress()

        })
    }
}