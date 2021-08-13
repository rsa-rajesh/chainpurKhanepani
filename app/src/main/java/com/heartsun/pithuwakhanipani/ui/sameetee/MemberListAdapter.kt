package com.heartsun.pithuwakhanipani.ui.sameetee

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidcommon.base.ImmutableRecyclerAdapter
import androidcommon.base.VBViewHolder
import com.heartsun.pithuwakhanipani.databinding.ItemContactListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemMemberListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemMemberTypeListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemUnitRateListBinding
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblBoardMemberType
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblContact
import kotlin.properties.Delegates

class MemberListAdapter() :
    ImmutableRecyclerAdapter<TblContact, ItemMemberListBinding>() {
    override var items: List<TblContact> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemMemberListBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemMemberListBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {
            tvName.text = item.ContactName.toString().orEmpty()
            tvContact.text=item.ContactNumber.toString().orEmpty()
            tvPosition.text=item.Post.toString().orEmpty()
            tvAddress.text=item.ContID.toString().orEmpty()

            // TODO: 8/13/2021  address not available for now
            // also set  profile image is remaining
        }
    }
}