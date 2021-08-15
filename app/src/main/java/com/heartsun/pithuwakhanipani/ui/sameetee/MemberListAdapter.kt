package com.heartsun.pithuwakhanipani.ui.sameetee

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidcommon.RDrawable
import androidcommon.base.ImmutableRecyclerAdapter
import androidcommon.base.VBViewHolder
import androidcommon.extension.load
import com.heartsun.pithuwakhanipani.PlaceHolder
import com.heartsun.pithuwakhanipani.databinding.ItemContactListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemMemberListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemMemberTypeListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemUnitRateListBinding
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblBoardMemberType
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblContact
import com.ouattararomuald.slider.loaders.glide.GlideApp
import java.io.File
import kotlin.properties.Delegates

class MemberListAdapter(private val context:Context) :
    ImmutableRecyclerAdapter<TblContact, ItemMemberListBinding>() {
    override var items: List<TblContact> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemMemberListBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemMemberListBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {
            tvName.text = item.ContactName.toString().orEmpty()
            tvContact.text = item.ContactNumber.toString().orEmpty()
            tvPosition.text = item.Post.toString().orEmpty()
            tvAddress.text = item.Address.toString().orEmpty()
            if(item.Image!=null){

//                val image = File(
//                    context.getExternalFilesDir(null),
//                    "memberImage" + item.ContID.toString() + ".png"
//                )
                ivProfile.load( item.Image)

//                GlideApp.with(ivProfile).load(image).placeholder(RDrawable.loading_anim).error(RDrawable.error_placeholder)
            }
            // TODO: 8/13/2021  profile image is remaining
        }
    }
}