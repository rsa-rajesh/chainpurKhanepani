package com.heartsun.pithuwakhanipani.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidcommon.RDrawable
import androidcommon.base.BaseActivity
import androidcommon.extension.showAddTapDialog
import androidcommon.extension.showCustomDialog
import androidcommon.extension.showErrorDialog
import androidcommon.extension.showRequestPinDialog
import com.heartsun.pithuwakhanipani.databinding.ActivityHomeBinding
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.glide.GlideImageLoaderFactory
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblMember
import com.heartsun.pithuwakhanipani.ui.about.AboutAppActivity
import com.heartsun.pithuwakhanipani.ui.about.AboutOrganizationActivity
import com.heartsun.pithuwakhanipani.ui.activityes.ActivitiesActivity
import com.heartsun.pithuwakhanipani.ui.billDetails.BillDetailsActivity
import com.heartsun.pithuwakhanipani.ui.contact.ContactActivity
import com.heartsun.pithuwakhanipani.ui.memberRegisterRequest.MemberRegisterActivity
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.MeroKhaniPaniActivity
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.MyTapViewModel
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.TapListAdapter
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.personalMenu.PersonalMenu
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
    private val myTapViewModel by viewModel<MyTapViewModel>()
    private lateinit var imageSliderNew: ImageSlider

    lateinit var member: TblMember

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
            addTapFromServerObserver()
            requestPinFromServerObserver()
            GlobalScope.launch {
                homeViewModel.getSlidersFromServer()
            }
            getTapsFromDb()

            tvNoOfTaps.text = "धारा स्ख्या:- " + prefs.noOfTaps.orEmpty()

            val powered: String =
                "<font color=#ECE5F0>powered by:- </font><font color=#59114D>Heartsun Technology</font>"
            tvPoweredBy.text = powered.parseAsHtml()

            val version = getString(R.string.app_version)
            val versions: String =
                "<font color=#ECE5F0>version: </font><font color=#59114D>$version</font>"
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
                cvActivity, cvLogin, cvUser
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
                        cvLogin -> {
                            openAddDialog()
                        }
                        cvUser -> {
                            startActivity(
                                PersonalMenu.newIntent(
                                    this@HomeActivity,
                                    address = member.Address.toString(),
                                    memberId = member.MemberID.toString(),
                                    registrationDate = member.RegDateTime.toString().split(" ")[0],
                                    name = member.MemName.toString(),
                                    phoneNo = member.ContactNo.toString(),
                                    member.PinCode.toString().toInt()
                                )
                            )
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


    private fun openAddDialog() {

        showAddTapDialog(onAddClick = { phoneNo, pin ->
            addTap(phoneNo, pin)
        }, onRequestClick = {
            openRequestDialog()
        })
    }

    private fun addTap(phoneNo: String, pin: String) {
        showProgress()
        myTapViewModel.addTap(phoneNo, pin)
    }

    private fun requestNewPin(phoneNo: String, memberId: String) {
        showProgress()
        myTapViewModel.requestPin(phoneNo, memberId)
    }

    private fun openRequestDialog() {
        showRequestPinDialog(onAddClick = {
            openAddDialog()
        }, onRequestClick = { phoneNo, memberId ->
            requestNewPin(phoneNo, memberId)
        })
    }

    private fun addTapFromServerObserver() {
        myTapViewModel.addTap.observe(this, {
            it ?: return@observe
            hideProgress()

            if (it.status.equals("error", true)) {
                showErrorDialog(
                    message = it.message,
                    "बन्द गर्नुहोस्",
                    "त्रुटि",
                    RDrawable.ic_error_for_dilog,
                    color = Color.RED
                )
            } else {
                for (member in it.tblMember!!) {
                    myTapViewModel.insert(members = member)
                }
            }


        })
    }

    private fun requestPinFromServerObserver() {
        myTapViewModel.pinRequest.observe(this, {
            it ?: return@observe
            hideProgress()

            if (it.equals("Access Code is sent to your mobile", true)) {
                showErrorDialog(
                    message = it,
                    "बन्द गर्नुहोस्",
                    "सफलता",
                    RDrawable.ic_success_for_dilog,
                    color = Color.GREEN
                )
            } else {
                showErrorDialog(
                    message = it,
                    "बन्द गर्नुहोस्",
                    "त्रुटि",
                    RDrawable.ic_error_for_dilog,
                    color = Color.RED
                )
            }
        })

    }

    @DelicateCoroutinesApi
    private fun getTapsFromDb() {
        showProgress()
        myTapViewModel.tapsListFromLocalDb.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
                binding.cvLogin.isVisible = true
                binding.cvUser.isVisible = false

//                prefs.noOfTaps = "0"
                hideProgress()

            } else {
                binding.cvLogin.isVisible = false
                binding.cvUser.isVisible = true
                binding.tvName.text =  "नाम :- " +it[0].MemName.orEmpty()
                binding.tvMemberId.text ="दर्ता न. :- "+ it[0].MemberID.toString()
                member = it[0]
                hideProgress()
            }
        })
    }
}

