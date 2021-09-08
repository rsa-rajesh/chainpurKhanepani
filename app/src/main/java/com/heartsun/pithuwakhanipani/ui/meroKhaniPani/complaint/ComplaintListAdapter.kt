package com.heartsun.pithuwakhanipani.ui.meroKhaniPani.complaint

import android.annotation.SuppressLint
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
import com.heartsun.pithuwakhanipani.databinding.ItemMessageSentBinding
import com.heartsun.pithuwakhanipani.databinding.ItemNoticeListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemUnitRateListBinding
import com.heartsun.pithuwakhanipani.domain.ComplaintResponse
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblBoardMemberType
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblNotice
import kotlin.properties.Delegates
import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ComplaintListAdapter(
    private val onItemClick: (item: TblNotice) -> Unit = {}
) :
    ImmutableRecyclerAdapter<ComplaintResponse, ItemMessageSentBinding>() {
    override var items: List<ComplaintResponse> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemMessageSentBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemMessageSentBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {
            tvMessage.text = item.ComplaintMsg.toString().orEmpty()
            tvDate.text = convertDate(item.ComptDate.toString())
        }
    }

    private fun convertDate(sdate: String?): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        var nDate = Date()
        try {
            nDate = sdf.parse(sdate)
        } catch (e: ParseException) {
            return sdate
        }
        val newDateTimeLong: Long = nDate.getTime()
        val timeAgo = DateUtils.getRelativeTimeSpanString(
            newDateTimeLong,
            System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE
        )
        return timeAgo.toString()
    }

}