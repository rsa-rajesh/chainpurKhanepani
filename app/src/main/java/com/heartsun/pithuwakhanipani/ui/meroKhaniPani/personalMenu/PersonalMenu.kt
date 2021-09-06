package com.heartsun.pithuwakhanipani.ui.meroKhaniPani.personalMenu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidcommon.extension.toastS
import com.heartsun.pithuwakhanipani.databinding.ActivityPersionalMenuBinding
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.MeroKhaniPaniActivity
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.ledger.LedgerActivity
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeDetailsActivity
import com.heartsun.pithuwakhanipani.ui.waterRate.WaterRateActivity

class PersonalMenu : BaseActivity() {


    private val binding by lazy {
        ActivityPersionalMenuBinding.inflate(layoutInflater)
    }


    private val name by lazy {
        intent.getStringExtra(PersonalMenu.name)
    }
    private val address by lazy {
        intent.getStringExtra(PersonalMenu.address)
    }
    private val memberId by lazy {
        intent.getStringExtra(PersonalMenu.memberId)
    }
    private val registrationDate by lazy {
        intent.getStringExtra(PersonalMenu.registrationDate)
    }

    companion object {
        private const val name = "NoticePublishedDate"
        private const val address = "NoticeImageUrl"
        private const val memberId = "NoticeTitle"
        private const val registrationDate = "NoticeDetails"
        fun newIntent(
            context: Context,
            address: String,
            memberId: String,
            registrationDate: String,
            name: String
        ): Intent {
            return Intent(context, PersonalMenu::class.java).apply {
                putExtra(PersonalMenu.name, name)
                putExtra(PersonalMenu.address, address)
                putExtra(PersonalMenu.memberId, memberId)
                putExtra(PersonalMenu.registrationDate, registrationDate)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

        with(binding) {
            tvName.text ="ग्राहकको नाम :- "+ name.orEmpty()
            tvAddress.text ="ठेगाना :- " + address.orEmpty()
            tvDharaNo.text ="धारा न. :- " + memberId.orEmpty()
            tvRegistrationDate.text ="दर्ता भएको मिति :- " + registrationDate.orEmpty()

            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@PersonalMenu.finish()
            }
            toolbar.tvToolbarTitle.text = "मेरो खानेपानी"

            listOf(cvLedger, cvComplain,cvChangePin).forEach {
                it.setOnClickListener { view ->
                    when (view) {
                        cvComplain -> {
                            toastS("coming soon...")
                        }
                        cvLedger -> {
                            startActivity(LedgerActivity.newIntent(this@PersonalMenu,memberId.toString()))
                        }
                        cvChangePin -> {
                            toastS("coming soon...")
                        }
                    }
                }
            }
        }
    }
}