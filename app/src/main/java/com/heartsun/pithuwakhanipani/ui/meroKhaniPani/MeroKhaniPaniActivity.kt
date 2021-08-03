package com.heartsun.pithuwakhanipani.ui.meroKhaniPani

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivityHomeBinding
import com.heartsun.pithuwakhanipani.databinding.ActivityMeroKhaniPaniBinding
import com.heartsun.pithuwakhanipani.ui.HomeActivity

class MeroKhaniPaniActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMeroKhaniPaniBinding.inflate(layoutInflater)
    }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MeroKhaniPaniActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            toolbar.tvToolbarTitle.text = "मेरो खानेपानी"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@MeroKhaniPaniActivity.finish()
            }
        }
    }
}