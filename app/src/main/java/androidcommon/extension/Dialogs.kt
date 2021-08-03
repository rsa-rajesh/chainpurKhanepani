package androidcommon.extension

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import androidx.annotation.DrawableRes
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.databinding.DialogErrorBinding
import com.heartsun.pithuwakhanipani.databinding.DialogPosNegOptionBinding

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
    icon: Int = R.drawable.ic_notification
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


