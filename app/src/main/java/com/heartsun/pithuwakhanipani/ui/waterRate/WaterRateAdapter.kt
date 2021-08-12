package com.heartsun.pithuwakhanipani.ui.waterRate

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidcommon.base.ImmutableRecyclerAdapter
import androidcommon.base.VBViewHolder
import com.heartsun.pithuwakhanipani.databinding.ItemUnitRateListBinding
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import kotlin.properties.Delegates

class WaterRateAdapter(
) :
    ImmutableRecyclerAdapter<TBLReadingSetupDtl, ItemUnitRateListBinding>() {
    override var items: List<TBLReadingSetupDtl> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemUnitRateListBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemUnitRateListBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {

            if (position.mod(2) == 0) {
                root.setBackgroundColor(Color.parseColor("#9EA7F1"))
            } else {
                root.setBackgroundColor(Color.parseColor("#F3F2F2"))
            }


            tvFrom.text = item.MnNo.toString().orEmpty()
            tvTo.text = item.MxNo.toString().orEmpty()

            if (item.Amt!! > 0) {
                tvRate.text = "रु. "+item.Amt.toString() + "*"
                tvRate.setTypeface(Typeface.DEFAULT,Typeface.BOLD)
            } else {
                tvRate.text = "रु. "+item.Rate.toString().orEmpty()

            }

        }
    }
}