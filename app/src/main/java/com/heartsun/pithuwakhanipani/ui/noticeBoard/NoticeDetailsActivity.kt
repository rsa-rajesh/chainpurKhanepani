package com.heartsun.pithuwakhanipani.ui.noticeBoard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidcommon.RDrawable
import androidcommon.base.BaseActivity
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.heartsun.pithuwakhanipani.databinding.ActivityNoticeBoardBinding
import com.heartsun.pithuwakhanipani.databinding.ActivityNoticeDetailsBinding

class NoticeDetailsActivity : BaseActivity() {

    private val binding by lazy {
        ActivityNoticeDetailsBinding.inflate(layoutInflater)
    }
    private val noticeDate by lazy {
        intent.getStringExtra(Date)
    }
    private val noticeImage by lazy {
        intent.getStringExtra(ImageUrl)
    }
    private val noticeTitle by lazy {
        intent.getStringExtra(Title)
    }
    private val noticeDetails by lazy {
        intent.getStringExtra(Details)
    }

    companion object {
        private const val Date = "NoticePublishedDate"
        private const val ImageUrl = "NoticeImageUrl"
        private const val Title = "NoticeTitle"
        private const val Details = "NoticeDetails"


        fun newIntent(
            context: Context,
            date: String,
            image: String,
            title: String,
            details: String
        ): Intent {
            return Intent(context, NoticeDetailsActivity::class.java).apply {
                putExtra(Date, date)
                putExtra(ImageUrl, image)
                putExtra(Title, title)
                putExtra(Details, details)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        with(binding) {

            toolbar.tvToolbarTitle.text = "सूचना पाटी"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@NoticeDetailsActivity.finish()
            }

            tvPublishedDate.text = "मिति :- $noticeDate"
            tvNoticeTitle.text = noticeTitle.orEmpty().parseAsHtml()
            tvNoticeDetails.text = noticeDetails.orEmpty().parseAsHtml()
            if (noticeImage.isNullOrEmpty() || noticeImage?.equals("noImage", true) == true) {
                cvImage.isGone = true
            } else {
                cvImage.isVisible = true

                Glide.with(this@NoticeDetailsActivity)
                    .load(noticeImage)
                    .placeholder(RDrawable.loading_anim)
                    .error(RDrawable.ic_image_error)
                    .into(ivImage);
            }


        }
    }
}