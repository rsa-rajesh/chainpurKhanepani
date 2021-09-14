package com.heartsun.pithuwakhanipani.ui.noticeBoard

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidcommon.base.ImmutableRecyclerAdapter
import androidcommon.base.VBViewHolder
import androidcommon.extension.load
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import com.heartsun.pithuwakhanipani.databinding.ItemMemberTypeListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemNoticeListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemUnitRateListBinding
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblBoardMemberType
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblNotice
import java.util.*
import kotlin.properties.Delegates

class NoticeListAdapter(
    private val onItemClick: (item: TblNotice) -> Unit = {}
) :
    ImmutableRecyclerAdapter<TblNotice, ItemNoticeListBinding>() {
    override var items: List<TblNotice> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemNoticeListBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemNoticeListBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {
            tvPublishedDate.text = "मिति :- " + item.DateNep.toString().orEmpty()
            tvNoticeTitle.text = item.NoticeHeadline.toString().orEmpty().parseAsHtml()
            tvNoticeDetails.text = item.NoticeDesc.toString().orEmpty().parseAsHtml()
            val properNoun = arrayOf("#FE0000", "#0090B5", "#4E9D67", "#DB41E1", "#F83B00", "#515B3A","#7AC74F")
            val random = Random()
            val index: Int = random.nextInt(properNoun.size)
            cvMeroKahiPani.setStrokeColor(ColorStateList.valueOf(Color.parseColor(properNoun[index])))
            if (item.NoticeFile.isNullOrEmpty()) {
                cvImage.isVisible = false
            } else {
                ivImage.load( item.NoticeFile)
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(items[position])

        }
    }
}