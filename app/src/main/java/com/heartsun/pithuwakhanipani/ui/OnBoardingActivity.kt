package com.heartsun.pithuwakhanipani.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeBoardActivity
import org.koin.android.ext.android.inject

class OnBoardingActivity : BaseActivity() {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, OnBoardingActivity::class.java)
        }
    }

    private val prefs by inject<Prefs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
    }

    public fun startActivity() {

        startActivity(HomeActivity.newIntent(this))
        this.finish()


    }
}