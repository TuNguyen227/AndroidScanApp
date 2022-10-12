package com.nmt.stockcheck.view.helper

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.datepicker.*
import com.nmt.stockcheck.R
import org.joda.time.DateTime
import java.util.*

class DialogHelper {
    companion object{
        fun materialProgressDialog(context: Context): MaterialDialog {
            val materialDialog = MaterialDialog(context).customView(R.layout.fullscreen_progress_dialog)
                .cancelOnTouchOutside(false)
                .cancelable(false)
            (materialDialog.view as ViewGroup).setBackgroundColor(Color.TRANSPARENT)
            materialDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            materialDialog.window?.setDimAmount(0.3F)
            return materialDialog
        }

        fun materialDialog(context: Context, message: String, positiveText: String=context.resources.getString(R.string.ok), callback: Material1ButtonCallback, ): MaterialDialog =
            MaterialDialog(context).
            message(text = message)
                .cancelOnTouchOutside(false)
                .cancelable(false)
                .positiveButton(text = positiveText) { it: MaterialDialog ->
                    it.dismiss()
                    callback.onOk()
                }

        fun materialDialog(context: Context,message: String, positiveText: String=context.resources.getString(R.string.ok),
                           negativeText: String=context.resources.getString(R.string.cancel), callback: Material2ButtonCallback ): MaterialDialog =
            MaterialDialog(context).
            message(text = message)
                .cancelOnTouchOutside(false)
                .cancelable(false)
                .positiveButton(text = positiveText) { it: MaterialDialog ->
                    it.dismiss()
                    callback.onOk()
                }
                .negativeButton(text = negativeText) { it: MaterialDialog ->
                    it.dismiss()
                    callback.onCancel()
                }
        fun materialDialog(context: Context,title:String,message: String, positiveText: String=context.resources.getString(R.string.ok),
                           negativeText: String=context.resources.getString(R.string.cancel), callback: Material1ButtonCallback ): MaterialDialog =
            MaterialDialog(context).
            title(text = title).
            message(text = message)
                .cancelOnTouchOutside(false)
                .cancelable(false)
                .positiveButton(text = positiveText) { it: MaterialDialog ->
                    it.dismiss()
                    callback.onOk()
                }
                .negativeButton(text = negativeText) { it: MaterialDialog ->
                    it.dismiss()
                }

//        fun showDatePicker(selected:Long?=null, listener: MaterialPickerOnPositiveButtonClickListener<Long>,
//                           fragmentManager: FragmentManager, isFutureDisable:Boolean=false,
//                           isOnlyFuture:Boolean=false){
//            val builder = MaterialDatePicker.Builder.datePicker()
//                .setTheme(R.style.Widget_AppTheme_MaterialDatePicker)
//            if(isFutureDisable) {
//                val constraintsBuilder =
//                    CalendarConstraints.Builder()
//                        .setValidator(DateValidatorPointBackward.now())
//                builder.setCalendarConstraints(constraintsBuilder.build())
//            }
//            if(isOnlyFuture) {
//                val constraintsBuilder =
//                    CalendarConstraints.Builder()
//                        .setValidator(DateValidatorPointForward.from(DateTime.now().millis))
//                builder.setCalendarConstraints(constraintsBuilder.build())
//            }
//            selected?.let {
//                builder.setSelection(selected)
//            }
//            val picker = builder.build()
//            picker.addOnNegativeButtonClickListener {
//                picker.dismiss()
//            }
//            picker.addOnPositiveButtonClickListener(listener)
//            picker.show(fragmentManager, picker.toString())
//        }




    }
}
interface Material1ButtonCallback {
    fun onOk()
}
interface Material2ButtonCallback {
    fun onOk()
    fun onCancel()
}
interface MaterialOkCallback {
    fun onOk(any: Any?)
}