package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidcommon.RDrawable
import androidcommon.base.ImmutableRecyclerAdapter
import androidcommon.base.VBViewHolder
import androidcommon.extension.load
import androidx.core.text.parseAsHtml
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.heartsun.pithuwakhanipani.databinding.ItemMemberTypeListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemNoticeListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemRegisterFilesListBinding
import com.heartsun.pithuwakhanipani.databinding.ItemUnitRateListBinding
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblBoardMemberType
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblNotice
import kotlin.properties.Delegates

class FilesListAdapter(
    private val onAddFileClicked: (item: Int) -> Unit = {},
    private val onRemoveFileClicked: (item: Int) -> Unit = {}

) :
    ImmutableRecyclerAdapter<RegistrationRequest.RequiredDocuments, ItemRegisterFilesListBinding>() {
    override var items: List<RegistrationRequest.RequiredDocuments> by Delegates.observable(
        emptyList()
    ) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getViewBinding(parent: ViewGroup) = ItemRegisterFilesListBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VBViewHolder<ItemRegisterFilesListBinding>,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {
            tvFileName.text = item.DocumentName.toString().orEmpty()
            tvFileType.text = item.DocumentName.toString().orEmpty()
            if (item.DocImage==null){
                clAddFile.isVisible=true
                ivRemove.isVisible=false

            }else{
                clAddFile.isVisible=false
                ivRemove.isVisible=true
                ivFilePreview.load(item.DocImage.toString())
            }


            clAddFile.setOnClickListener{
                onAddFileClicked(position)
            }
            ivRemove.setOnClickListener{
                onRemoveFileClicked(position)
            }
        }
    }
}