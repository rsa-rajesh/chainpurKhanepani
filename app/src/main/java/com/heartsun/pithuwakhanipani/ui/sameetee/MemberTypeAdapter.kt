package com.heartsun.pithuwakhanipani.ui.sameetee

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidcommon.base.ImmutableRecyclerAdapter
import androidcommon.base.VBViewHolder
import com.heartsun.pithuwakhanipani.databinding.ItemMemberTypeListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemUnitRateListBinding
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblBoardMemberType
import kotlin.properties.Delegates

class MemberTypeAdapter(
    private val onItemClick: (item: TblBoardMemberType) -> Unit = {}
) :
    ImmutableRecyclerAdapter<TblBoardMemberType, ItemMemberTypeListBinding>() {
    override var items: List<TblBoardMemberType> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemMemberTypeListBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemMemberTypeListBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {
            tv1.text = item.MemberType.toString().orEmpty()
        }


        holder.itemView.setOnClickListener {
            onItemClick(items[position])

        }
    }
}