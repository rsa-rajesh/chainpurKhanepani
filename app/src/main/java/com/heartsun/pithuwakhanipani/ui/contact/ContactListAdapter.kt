package com.heartsun.pithuwakhanipani.ui.contact

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidcommon.base.ImmutableRecyclerAdapter
import androidcommon.base.VBViewHolder
import androidx.core.view.isVisible
import com.heartsun.pithuwakhanipani.databinding.ItemContactListBinding
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblDepartmentContact
import kotlin.properties.Delegates

class ContactListAdapter(
    private val onCallClick: (item: TblDepartmentContact) -> Unit = {},
    private val onMailClick: (item: TblDepartmentContact) -> Unit = {}
) :
    ImmutableRecyclerAdapter<TblDepartmentContact, ItemContactListBinding>() {
    override var items: List<TblDepartmentContact> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemContactListBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemContactListBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {
            tvTitle.text = item.Dept_name.toString().trim().orEmpty()
            tvPhone.text = "फोन नम्बर :- "+item.Dept_contact.toString().trim().orEmpty()
            if (item.Dept_mail.toString().isEmpty()){
                tvMail.isVisible=false
                btMail.isVisible=false

            }
            if (item.Dept_contact.toString().isEmpty()){
                tvPhone.isVisible=false
                btCall.isVisible=false
            }

            tvMail.text = "गईमेल :- "+item.Dept_mail.toString().trim().orEmpty()

            btCall.setOnClickListener {
                onCallClick(items[position])
            }
            btMail.setOnClickListener {
                onMailClick(items[position])
            }
        }



    }
}