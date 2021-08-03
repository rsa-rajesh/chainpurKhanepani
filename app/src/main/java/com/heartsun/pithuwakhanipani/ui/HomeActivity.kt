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
import android.text.Html
import androidx.core.text.parseAsHtml
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.MeroKhaniPaniActivity
import com.heartsun.pithuwakhanipani.ui.sameetee.SameeteeSelectionActivity
import com.heartsun.pithuwakhanipani.ui.waterRate.WaterRateActivity


class HomeActivity : BaseActivity() {

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

        initView()


    }

    private fun initView() {
        with(binding) {
            imageSliderNew = imageSlider
            imageSlider.adapter = SliderAdapter(
                this@HomeActivity,
                GlideImageLoaderFactory(),
                imageUrls = imageUrls
//            descriptions = Data.generateDescriptions(imageUrls.size)
            )
            imageSlider.setIndicatorsBottomMargin(8)

            val powered: String = "powered by:- <font color=#223AF1>Heartsun Technology</font>"
            tvPoweredBy.text = powered.parseAsHtml()

            val version = "1.1.1"
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
                tvPoweredBy
            ).forEach {
                it.setOnClickListener { view ->
                    when (view) {
                        cvPanikoDar -> {
                            startActivity(WaterRateActivity.newIntent(this@HomeActivity))
                        }
                        cvMeroKahiPani -> {
                            startActivity(MeroKhaniPaniActivity.newIntent(this@HomeActivity))
                        }
                        cvNayaDhara -> {
                            // TODO: 8/3/2021
                        }
                        cvSamitee -> {
                            activateViews(false)
                            startActivity(SameeteeSelectionActivity.newIntent(this@HomeActivity))
                        }
                        cvSamparka -> {
                            // TODO: 8/3/2021
                        }
                        cvSastakoBarama -> {
                            // TODO: 8/3/2021
                        }
                        cvSuchanaPati -> {
                            // TODO: 8/3/2021
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


}