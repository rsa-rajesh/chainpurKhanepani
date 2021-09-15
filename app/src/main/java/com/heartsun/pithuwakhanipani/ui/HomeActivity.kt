package com.heartsun.pithuwakhanipani.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidcommon.RDrawable
import androidcommon.base.BaseActivity
import androidcommon.extension.showErrorDialog
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
import com.heartsun.pithuwakhanipani.ui.activityes.ActivitiesActivity
import com.heartsun.pithuwakhanipani.ui.billDetails.BillDetailsActivity
import com.heartsun.pithuwakhanipani.ui.contact.ContactActivity
import com.heartsun.pithuwakhanipani.ui.memberRegisterRequest.MemberRegisterActivity
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.MeroKhaniPaniActivity
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeBoardActivity
import com.heartsun.pithuwakhanipani.ui.sameetee.SameeteeSelectionActivity
import com.heartsun.pithuwakhanipani.ui.waterRate.WaterRateActivity
import com.ouattararomuald.slider.ImageLoader
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity() {

    private val prefs by inject<Prefs>()

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val homeViewModel by viewModel<HomeViewModel>()

    private lateinit var imageSliderNew: ImageSlider

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        prefs.isFirstTime = false
        initView()
    }

    @DelicateCoroutinesApi
    private fun initView() {
        with(binding) {

            showProgress()
            slidersFromServerObserver()

            GlobalScope.launch {
                homeViewModel.getSlidersFromServer()
            }
//            getSliderFromDb()

            tvNoOfTaps.text = "धारा स्ख्या:- " + prefs.noOfTaps.orEmpty()

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
                cvBillDetails,
                cvActivity
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
                            startActivity(ContactActivity.newIntent(this@HomeActivity))
                        }
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
                        cvActivity -> {
                            activateViews(false)
                            startActivity(ActivitiesActivity.newIntent(this@HomeActivity))
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

    private fun getSliderFromDb() {
        homeViewModel.sliderImagesFromLocalDb.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
            } else {
                val imageUrls = arrayListOf<String>()
                val descriptions = arrayListOf<String>()
                for (a in it) {
                    descriptions.add(a.SliderTitle.orEmpty())
                    if (a.SliderImageUrl.isNullOrEmpty()) {
                        imageUrls.add(a.SliderImageFile.toString())
                    } else {
                        imageUrls.add(a.SliderImageUrl.toString())
                    }
                }
                imageSliderNew = binding.imageSlider
                binding.imageSlider.setIndicatorsBottomMargin(8)
                binding.imageSlider.adapter = SliderAdapter(
                    this@HomeActivity,
                    getImageLoader(),
//                GlideImageLoaderFactory(),
                    imageUrls = imageUrls,
                    descriptions = descriptions,
                )
                hideProgress()
            }
        })
    }

    private fun slidersFromServerObserver() {
        homeViewModel.slidersFromServer.observe(this, {
            it ?: return@observe

            if (it.status.equals("success", true)) {
                getSliderFromDb()


                if (!homeViewModel.sliderImagesFromLocalDb.value.isNullOrEmpty()) {
                    homeViewModel.deleteAllSlider(it.tblSliderImages[0])
                }

                for (slider in it.tblSliderImages) {
                    homeViewModel.insert(slider)
                }
            } else {

                hideProgress()
                showErrorDialog(
                    message = "माफ गर्नुहोस्!!! सर्भरमा जडान गर्न सकेन",
                    "पुन: प्रयास गर्नुहोस्",
                    "त्रुटि",
                    RDrawable.ic_error_for_dilog,
                    color = Color.RED
                )
            }


        })
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
            cvActivity.isCheckable = status
            tvPoweredBy.isClickable = status
            cvBillDetails.isCheckable = status
        }
    }


    override fun onResume() {
        super.onResume()
        binding.tvNoOfTaps.text = "धारा स्ख्या:- " + prefs.noOfTaps.orEmpty()
        activateViews(true)
    }

    private fun getImageLoader(): ImageLoader.Factory<out ImageLoader> {
        val requestOptions = RequestOptions.errorOf(R.drawable.error_placeholder)
            .placeholder(R.drawable.loading_anim)
        return GlideImageLoaderFactory(requestOptions = requestOptions)
    }
}

