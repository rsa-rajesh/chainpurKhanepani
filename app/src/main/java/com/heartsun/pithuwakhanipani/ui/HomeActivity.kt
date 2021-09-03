package com.heartsun.pithuwakhanipani.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivityHomeBinding
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.glide.GlideImageLoaderFactory
import androidx.core.text.parseAsHtml
import com.bumptech.glide.request.RequestOptions
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.ui.about.AboutAppActivity
import com.heartsun.pithuwakhanipani.ui.about.AboutOrganizationActivity
import com.heartsun.pithuwakhanipani.ui.billDetails.BillDetailsActivity
import com.heartsun.pithuwakhanipani.ui.contact.ContactActivity
import com.heartsun.pithuwakhanipani.ui.memberRegisterRequest.MemberRegisterActivity
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.MeroKhaniPaniActivity
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeBoardActivity
import com.heartsun.pithuwakhanipani.ui.sameetee.SameeteeSelectionActivity
import com.heartsun.pithuwakhanipani.ui.waterRate.WaterRateActivity
import com.ouattararomuald.slider.ImageLoader
import org.koin.android.ext.android.inject


class HomeActivity : BaseActivity() {

    private val prefs by inject<Prefs>()

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private lateinit var imageSliderNew: ImageSlider
    private val imageUrls = arrayListOf(
        "https://th.bing.com/th/id/R.2e1c8a67d406ebe4026a2f3db875be38?rik=vyLzAAxHKxxQDQ&pid=ImgRaw&r=0",
        "https://th.bing.com/th/id/R.73dafe0505a5d557ecd84c1d255fb642?rik=Z37v69j9%2bCNw8Q&riu=http%3a%2f%2fgordonac.com%2fwp-content%2fuploads%2f2020%2f09%2fendless-water-supply.jpg&ehk=RVV4quLcIJ8djeOhZh%2b2rI6ghxIbG0Xu74CBMUJqaBY%3d&risl=&pid=ImgRaw&r=0",
        "https://jooinn.com/images/water-supply-2.jpg"
    )

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        prefs.isFirstTime=false
        initView()
    }

    private fun initView() {
        with(binding) {
            imageSliderNew = imageSlider
            imageSlider.adapter = SliderAdapter(
                this@HomeActivity,
                getImageLoader(),
//                GlideImageLoaderFactory(),
                imageUrls = imageUrls
            )
            imageSlider.setIndicatorsBottomMargin(8)

            val powered: String = "powered by:- <font color=#223AF1>Heartsun Technology</font>"
            tvPoweredBy.text = powered.parseAsHtml()

            val version = getString(R.string.app_version)
            val versions: String = "version:  <font color=#223AF1>$version</font>"
            tvVersion.text = versions.parseAsHtml()

            listOf(
                cvMeroKahiPani,
                cvNayaDhara,
                cvPanikoDar,
                cvSamitee,
                cvSamparka,
                cvSastakoBarama,
                cvSuchanaPati,
                tvPoweredBy,
                tvVersion,
                cvBillDetails
            ).forEach {
                it.setOnClickListener { view ->
                    when (view) {
                        cvPanikoDar -> {
                            activateViews(false)
                            startActivity(WaterRateActivity.newIntent(this@HomeActivity))
                        }
                        cvMeroKahiPani -> {
                            activateViews(false)
                            startActivity(MeroKhaniPaniActivity.newIntent(this@HomeActivity))
                        }
                        cvNayaDhara -> {
                            activateViews(false)
                            startActivity(MemberRegisterActivity.newIntent(this@HomeActivity))
                        }
                        cvSamitee -> {
                            activateViews(false)
                            startActivity(SameeteeSelectionActivity.newIntent(this@HomeActivity))
                        }
                        cvSamparka -> {
                            activateViews(false)
                            startActivity(ContactActivity.newIntent(this@HomeActivity))                        }
                        cvSastakoBarama -> {
                            activateViews(false)
                            startActivity(AboutOrganizationActivity.newIntent(this@HomeActivity))
                        }
                        cvSuchanaPati -> {
                            activateViews(false)
                            startActivity(NoticeBoardActivity.newIntent(this@HomeActivity))
                        }
                        cvBillDetails -> {
                            activateViews(false)
                            startActivity(BillDetailsActivity.newIntent(this@HomeActivity))
                        }
                        tvVersion -> {
                            activateViews(false)
                            startActivity(AboutAppActivity.newIntent(this@HomeActivity))
                        }
                        tvPoweredBy -> {
                            val url = "https://www.heartsun.com.np/"
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            startActivity(i)
                        }
                    }
                }
            }

        }
    }

    private fun activateViews(status: Boolean) {
        with(binding) {
            cvSamitee.isClickable = status
            cvImageSlider.isClickable = status
            cvSuchanaPati.isClickable = status
            cvSastakoBarama.isClickable = status
            cvSamparka.isClickable = status
            cvPanikoDar.isClickable = status
            cvNayaDhara.isClickable = status
            cvMeroKahiPani.isClickable = status
            tvPoweredBy.isClickable = status

        }
    }


    override fun onResume() {
        super.onResume()

        activateViews(true)
    }

    private fun getImageLoader(): ImageLoader.Factory<out ImageLoader> {
                val requestOptions = RequestOptions.errorOf(R.drawable.error_placeholder)
                    .placeholder(R.drawable.loading_anim)
            return    GlideImageLoaderFactory(requestOptions = requestOptions)
            }
}

