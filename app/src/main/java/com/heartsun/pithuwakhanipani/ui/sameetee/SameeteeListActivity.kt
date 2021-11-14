package com.heartsun.pithuwakhanipani.ui.sameetee

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.print.PrintAttributes
import android.view.View
import android.widget.AdapterView
import androidcommon.base.BaseActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.databinding.ActivitySameeteeListBinding
import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.widget.ArrayAdapter
import android.widget.Toast
import androidcommon.RLayout
import androidx.core.content.FileProvider
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblContact
import com.uttampanchasara.pdfgenerator.CreatePdf
import java.io.File
import java.util.*


class SameeteeListActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySameeteeListBinding.inflate(layoutInflater)
    }
    private lateinit var memberListAdapter: MemberListAdapter

    private val memberTypeId by lazy { intent.getIntExtra(memberId, -1) }
    private val memberType by lazy { intent.getStringExtra(memberTypee) }
    private val isOldMember by lazy { intent.getIntExtra(isOldMembers, 0) }
    var maxYear: Int = 2000
    var minYear: Int = 3000
    lateinit var originalItems: List<TblContact>
    lateinit var listItems: List<TblContact>

    companion object {
        private const val memberId = "memberTypeId"
        private const val memberTypee = "memberType"
        private const val isOldMembers = "isOldMember"

        fun newIntent(
            context: Context,
            memberTypeID: Int,
            memberType: String,
            isOldMember: Int
        ): Intent {
            return Intent(context, SameeteeListActivity::class.java).apply {
                putExtra(isOldMembers, isOldMember)
                putExtra(memberId, memberTypeID)
                putExtra(memberTypee, memberType)
            }
        }
    }

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        if (isOldMember == 0) {
            homeViewModel.getMembers(memberTypeId)
        } else {
            binding.tvSelectYear.isVisible = true
            homeViewModel.getOldMembers(memberTypeId)
        }
        binding.toolbar.tvToolbarTitle.text = memberType
        binding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btMakePDF.setOnClickListener {
            showProgress("Generating PDF ....")
            var mType=memberType

//            mType = if(isOldMember==0){
//                memberType
//            }else{
//                memberType+" (${binding.tvSelectYear.text.toString().orEmpty()} ${binding.spYear.selectedItem.toString().orEmpty()})"
//            }

            var htmlString: String =
                "<html><head><style>table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}</style>" +
                        "</head><body><h1 style=text-align:center>पिठुवा खानेपानी तथा सरसफाई उपभोक्ता संस्था</h1><h4 style=text-align:center>रत्ननगर-१५ माधवपुर,चितवन</h4><h2 style=text-align:center>$mType</h2><br><br><table><tr><th>नाम</th><th>पद</th><th>ठेगाना</th><th>सम्पर्क</th></tr>"


            for (data in listItems) {
                htmlString =
                    "$htmlString<tr><td>${data.ContactName}</td><td>${data.Post}</td><td>${data.Address}</td><td>${data.ContactNumber}</td></tr>"
            }
            htmlString = "$htmlString</table></body></html>"

            val date = Date()
            CreatePdf(this)
                .setPdfName("PK-Member_list$date")
                .openPrintDialog(false)
                .setContentBaseUrl(null)
                .setPageSize(PrintAttributes.MediaSize.ISO_A4)
                .setContent(htmlString)
                .setFilePath(getExternalFilesDir(null)!!.absolutePath + "/MyPdf")
                .setCallbackListener(object : CreatePdf.PdfCallbackListener {
                    override fun onFailure(s: String) {
                        hideProgress()
                        Toast.makeText(
                            this@SameeteeListActivity,
                            "Error while generating PDF",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onSuccess(s: String) {
                        hideProgress()
//                        val mActivity: Activity = this@SameeteeListActivity
//                        try {
//                            val pdfFile = File(s)
//                            val intent = Intent(Intent.ACTION_VIEW)
//                            val uri = FileProvider.getUriForFile(
//                                applicationContext,
//                                "com.bihanitech.dahalpratisthan.fileprovider",
//                                pdfFile
//                            )
//                            val resInfoList = application.packageManager.queryIntentActivities(
//                                intent,
//                                PackageManager.MATCH_DEFAULT_ONLY
//                            )
//                            for (resolveInfo in resInfoList) {
//                                val packageName = resolveInfo.activityInfo.packageName
//                                application.grantUriPermission(
//                                    packageName,
//                                    uri,
//                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
//                                )
//                            }
//                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                            intent.setDataAndType(uri, "application/pdf")
//                            startActivity(intent)
                            Toast.makeText(this@SameeteeListActivity, "Pdf Saved", Toast.LENGTH_SHORT)
                                .show()
//                        } catch (e: ActivityNotFoundException) {
//                            Toast.makeText(mActivity, "No PDF Viewer Installed", Toast.LENGTH_LONG)
//                                .show()
//                        }
                    }
                })
                .create()

        }

        binding.spYear.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

                var year: String = binding.spYear.selectedItem.toString()
                if (year.equals("all", true)) {
                    listItems = originalItems
                    memberListAdapter.items = listItems

                } else {
                    if (year.isNotEmpty()) {
                        var tempItems: MutableList<TblContact> = ArrayList()

                        for (data in originalItems) {
                            if (year.toInt() >= data.Tenure.orEmpty()
                                    .split("-")[0].toInt() && year.toInt() <= data.Tenure.orEmpty()
                                    .split("-")[1].toInt()
                            ) {
                                tempItems.add(data)
                            }
                        }
                        listItems = tempItems
                        memberListAdapter.items = listItems
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


        listObserver()
    }

    private fun listObserver() {
        homeViewModel.membersFromLocal?.observe(this, { it ->
            it ?: return@observe
            if (!it.isNullOrEmpty()) {
                binding.btMakePDF.isVisible = true
                binding.clEmptyList.isVisible = false
                binding.rvMembers.isVisible = true
                memberListAdapter = MemberListAdapter(this)
                originalItems = it
                listItems = originalItems
                memberListAdapter.items = listItems
                binding.rvMembers.layoutManager = LinearLayoutManager(this)
                binding.rvMembers.adapter = memberListAdapter
                if (isOldMember == 1) {
                    binding.llSelectYear.isVisible = true
                    for (member in it) {
                        if (member.Tenure.orEmpty().split("-")[0].toInt() < minYear) {
                            minYear = member.Tenure.orEmpty().split("-")[0].toInt()
                        }
                        if (member.Tenure.orEmpty().split("-")[1].toInt() > maxYear) {
                            maxYear = member.Tenure.orEmpty().split("-")[1].toInt()
                        }
                    }

                    var listData = getNumbersInRange()
                    val adapter = ArrayAdapter(
                        this,
                        RLayout.item_years_dropdown,
                        listData
                    )
                    binding.spYear.adapter = adapter
                    binding.spYear.dropDownVerticalOffset = binding.spYear.height
                }
                hideProgress()
            }
        })
    }

    private fun getNumbersInRange(): List<String> {
        var array2: ArrayList<String>? = ArrayList()
        array2?.add("all")
        for (i in minYear until maxYear + 1 step 1) {
            array2?.add(i.toString())
        }
        return array2?.toList().orEmpty()
    }
}