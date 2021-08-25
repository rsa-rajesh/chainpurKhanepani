package com.heartsun.pithuwakhanipani.ui.contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.databinding.ActivityContactBinding
import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.util.Log
import android.net.Uri
import androidcommon.extension.loggerE

class ContactActivity : BaseActivity() {

    private val binding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    private lateinit var contactListAdapter: ContactListAdapter

    private val homeViewModel by viewModel<HomeViewModel>()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ContactActivity::class.java)
        }
    }

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    @DelicateCoroutinesApi
    private fun initView() {
        with(binding) {
            toolbar.tvToolbarTitle.text = "सम्पर्क"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@ContactActivity.finish()
            }
        }
        getContactsFromDb()
    }

    @DelicateCoroutinesApi
    private fun getContactsFromDb() {
        showProgress()
        homeViewModel.contactsListFromLocalDb.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
                contactsFromServerObserver()
                GlobalScope.launch {
                    homeViewModel.getContactsFromServer()
                }
            } else {
                binding.clEmptyList.isVisible = false
                contactListAdapter = ContactListAdapter(
                    onCallClick = {
                        try {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:" + it.Dept_contact.toString().orEmpty())
                            startActivity(intent)
                        } catch (e: Exception) {
                            loggerE("Failed to invoke call")
                        }
                    },
                    onMailClick = {
                        val intent = Intent(Intent.ACTION_SENDTO)
                        intent.data = Uri.parse("mailto:") // only email apps should handle this
                        intent.putExtra(Intent.EXTRA_EMAIL, it.Dept_mail.toString().orEmpty())
                        Log.d("test mail", it.Dept_mail.toString())
                        intent.putExtra(Intent.EXTRA_SUBJECT, "")
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                        }
                    }
                )
                contactListAdapter.items = it
                binding.rvList.layoutManager = LinearLayoutManager(this)
                binding.rvList.adapter = contactListAdapter
                hideProgress()
            }
        })
    }

    private fun contactsFromServerObserver() {
        homeViewModel.contactsFromServer.observe(this, {
            it ?: return@observe
            for (memberType in it.tblDepartmentContact) {
                homeViewModel.insert(contacts = memberType)
            }

        })
    }
}