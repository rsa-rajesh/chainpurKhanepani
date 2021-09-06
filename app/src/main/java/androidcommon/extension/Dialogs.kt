package androidcommon.extension

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import androidx.annotation.DrawableRes
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.databinding.DialogErrorBinding
import com.heartsun.pithuwakhanipani.databinding.DialogPosNegOptionBinding
import com.heartsun.pithuwakhanipani.databinding.DilogAddNewBinding
import com.heartsun.pithuwakhanipani.databinding.DilogRequestPinBinding

fun Context.getBaseDialog(): Dialog {
    return Dialog(this, R.style.WideDialog).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
        }
    }
}

fun Context.showErrorDialog(
    message: String,
    label: String = "Ok",
    title: String = "Water Supply",
    @DrawableRes icon: Int = R.drawable.ic_server_error,
    isCancellable: Boolean = true,
    onBtnClick: () -> Unit = {},
) {
    val dialog = getBaseDialog()
    val dialogView = DialogErrorBinding.inflate(
        LayoutInflater.from(this),
        null,
        false
    )
    dialog.apply {
        setContentView(dialogView.root)
        setCancelable(isCancellable)
        show()
        with(dialogView) {
            tvTitle.text = title
            btnRetry.text = label
            tvMessage.text = message
            ivErrorImage.setImageResource(icon)
            btnRetry.setOnClickListener {
                dialog.dismiss()
                onBtnClick()
            }
        }
    }
}

fun Context.showCustomDialog(
    message: String,
    hideNegOption: Boolean = false,
    negLabel: String = "Cancel",
    posLabel: String = "Ok",
    onPosClick: () -> Unit = {},
    onNegClick: () -> Unit = {},
    icon: Int = R.drawable.logo
) {
    val dialog = getBaseDialog()
    val dialogView = DialogPosNegOptionBinding.inflate(LayoutInflater.from(this), null, false)
    dialog.apply {
        setContentView(dialogView.root)
        setCancelable(false)
        show()
        with(dialogView) {
            tvPosAction.text = posLabel
            tvNegAction.text = negLabel
            tvMessage.text = message
            ivLogo.setImageResource(icon)
            if (hideNegOption) {
                tvNegAction.gone()
                viewTwo.invisible()
            }

            tvNegAction.setOnClickListener {
                onNegClick()
                dismiss()
            }
            tvPosAction.setOnClickListener {
                onPosClick()
                dismiss()
            }
        }
    }
}

fun Context.showAddTapDialog(

    onAddClick: (phoneNo:String,pin:String) -> Unit ,
    onRequestClick: () -> Unit ,
    icon: Int = R.drawable.ic_notification
) {
    val dialog = getBaseDialog()
    val dialogView = DilogAddNewBinding.inflate(LayoutInflater.from(this), null, false)
    dialog.apply {
        setContentView(dialogView.root)
        setCancelable(false)
        show()
        with(dialogView) {

            btAddNew.setOnClickListener {
                when {
                    phoneNumber.text.isNullOrBlank() -> {
                        tiPhoneNumber.error="Phone number is required"
                    }
                    pin.text.isNullOrBlank() -> {
                        tiPin.error="Validation Pin is required"
                        tiPhoneNumber.error=null
                    }
                    else -> {
                        tiPin.error=null
                        tiPhoneNumber.error=null
                        onAddClick(phoneNumber.text.toString(),pin.text.toString())
                        dismiss()
                    }
                }
            }
            btRequestPin.setOnClickListener {
                onRequestClick()
                dismiss()
            }
            btClose.setOnClickListener {
                dismiss()
            }
        }
    }
}

fun Context.showRequestPinDialog(
    onAddClick: () -> Unit={},
    onRequestClick: (phoneNo:String,memberId:String) -> Unit={ s: String, s1: String -> }
) {
    val dialog = getBaseDialog()
    val dialogView = DilogRequestPinBinding.inflate(LayoutInflater.from(this), null, false)
    dialog.apply {
        setContentView(dialogView.root)
        setCancelable(false)
        show()
        with(dialogView) {


            btAddNew.setOnClickListener {
                onAddClick()
                dismiss()
            }
            btRequestPin.setOnClickListener {

                when {
                    phoneNumber.text.isNullOrBlank() -> {
                        tiPhoneNumber.error="Phone number is required"
                    }
                    memberId.text.isNullOrBlank() -> {
                        tiMemberId.error="Member id is required"
                        tiPhoneNumber.error=null
                    }

                    else -> {
                        tiMemberId.error=null
                        tiPhoneNumber.error=null

                        onRequestClick(phoneNumber.text.toString(),memberId.text.toString())
                        dismiss()

                    }
                }



            }

            btClose.setOnClickListener {
                dismiss()
            }
        }
    }
}
