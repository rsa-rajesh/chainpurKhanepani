package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidcommon.RDrawable
import androidcommon.base.BaseActivity
import androidcommon.extension.showErrorDialog
import androidx.core.view.isVisible
import com.heartsun.pithuwakhanipani.databinding.ActivityMemberRegisterBinding
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblDocumentType
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.*
import kotlin.concurrent.thread
import android.net.Uri
import android.content.ActivityNotFoundException
import android.os.Environment
import androidx.core.app.ActivityCompat.startActivityForResult
import android.app.Activity

import androidx.activity.result.ActivityResultCallback

import androidx.activity.result.contract.ActivityResultContracts

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult


class MemberRegisterActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMemberRegisterBinding.inflate(layoutInflater)
    }
    private val registerViewModel by viewModel<RegisterViewModel>()
    private val downloadID: Long = 0

    companion object {
        var registerRequest: RegistrationRequest? =
            RegistrationRequest(null, null, null, null, null, null, null, null, null, null)

        fun newIntent(context: Context): Intent {
            return Intent(context, MemberRegisterActivity::class.java)
        }
    }


    // Receiver
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = it.data?.data
                if (uri != null) {
                    downloadAndOpenPdf(
                        "https://drive.google.com/file/d/1tWQrxUSvyV1eF6VL3lNNXuxCgjzJUQfF/view?usp=sharing",
                        uri
                    )
                }
            }
        }

    // using broadcast method
    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            //Fetching the download id received with the broadcast
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID === id) {
                Toast.makeText(
                    this@MemberRegisterActivity,
                    "Download Completed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        // using broadcast method
        registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        );
    }

    @DelicateCoroutinesApi
    private fun initView() {
        with(binding) {

            btDownloadForm.setOnClickListener {


                val exportIntent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                exportIntent.addCategory(Intent.CATEGORY_OPENABLE)
                exportIntent.type = "*/*"
                exportIntent.putExtra(
                    Intent.EXTRA_TITLE,
                    "member_form.pdf"
                )
                getResult.launch(exportIntent)
            }

            btDownloadForm2.setOnClickListener {
                val exportIntent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                exportIntent.addCategory(Intent.CATEGORY_OPENABLE)
                exportIntent.type = "*/*"
                exportIntent.putExtra(
                    Intent.EXTRA_TITLE,
                    "member_form.pdf"
                )
                getResult.launch(exportIntent)
            }

            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@MemberRegisterActivity.finish()
            }
            toolbar.tvToolbarTitle.text = "सदस्य दर्ता"
        }
        showProgress()
        getFilesRequirementFromDb()
        requestObserver()
    }


    private fun getFilesRequirementFromDb() {

        registerViewModel.fileTypeFromLocalDb.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
                rateFromServerObserver()
                GlobalScope.launch {
                    registerViewModel.getFileRequirementFromServer()
                }
            } else {
                var docs: MutableList<RegistrationRequest.RequiredDocuments> = arrayListOf()
                for (fileType in it) {
                    var file: RegistrationRequest.RequiredDocuments =
                        RegistrationRequest.RequiredDocuments(fileType.DocTypeName, null)
                    docs.add(file)
                }
                registerRequest?.files = docs
                binding.clError.isVisible = false

                hideProgress()
            }
        })
    }

    private fun rateFromServerObserver() {
        registerViewModel.fileTypesFromServer.observe(this, {
            it ?: return@observe


            if (it.status.equals("success", true)) {
                var count: Int = 1
                for (fileType in it.documentTypes) {
                    val file: TblDocumentType = TblDocumentType(count, fileType.DocumentName)
                    count++
                    registerViewModel.insert(fileTypes = file)
                }
            } else {
                hideProgress()
                showErrorDialog(
                    message = "माफ गर्नुहोस्!!! सर्भरमा जडान गर्न सकेन \n" +
                            " कृपया पछि फेरि प्रयास गर्नुहोस्",
                    "पुन: प्रयास गर्नुहोस्",
                    "त्रुटि",
                    RDrawable.ic_error_for_dilog,
                    color = Color.RED
                )
            }
        })
    }

    fun requestRegistrationToServer() {
        showProgress()
        thread {
            Thread.sleep(100)
            registerViewModel.sendRegistrationRequestToServer(registerRequest, this)
        }


    }

    private fun requestObserver() {
        registerViewModel.registrationResponse.observe(this, {
            it ?: return@observe
            hideProgress()
            if (it.equals("Success", true)) {
                showErrorDialog(
                    message = "नयाँ धारा अनुरोध सफलतापूर्वक पेश गरियो",
                    "बन्द गर्नुहोस्",
                    "सफलता",
                    RDrawable.ic_success_for_dilog,
                    color = Color.GREEN,
                    onBtnClick = {
                        onBackPressed()
                        this.finish()
                    }
                )

            } else {
                hideProgress()
                showErrorDialog(
                    message = "माफ गर्नुहोस्!!! तपाईँको अनुरोध अहिले पूरा गर्न सकेन कृपया पछि फेरि प्रयास गर्नुहोस् ",
                    "पुन: प्रयास गर्नुहोस्",
                    "त्रुटि",
                    RDrawable.ic_error_for_dilog,
                    color = Color.RED
                )
            }
        })
    }

    fun downloadAndOpenPdf(url: String?, uri: Uri) {

        val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val req = DownloadManager.Request(Uri.parse(url))
        req.setDestinationUri(uri)
        req.setTitle("Downloading new member form")
        val receiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                unregisterReceiver(this)
                openPdfDocument(uri)
            }
        }
        registerReceiver(
            receiver, IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE
            )
        )
        dm.enqueue(req)
        Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show()

    }

    fun openPdfDocument(uri: Uri?): Boolean {
        val target = Intent(Intent.ACTION_VIEW)
        target.setDataAndType(uri, "application/pdf")
        target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        return try {
            startActivity(target)
            true
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No PDF reader found", Toast.LENGTH_LONG).show()
            false
        }
    }

}