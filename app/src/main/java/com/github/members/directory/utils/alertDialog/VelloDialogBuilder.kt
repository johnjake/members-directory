package com.github.members.directory.utils.alertDialog

import android.content.Context
import android.graphics.Typeface

class VelloDialogBuilder(private var context: Context) {
    private var titleTf: Typeface? = null
    private var subTitleTf: Typeface? = null
    private var isCancelable = false
    private lateinit var title: String
    private lateinit var subtitle: String
    private lateinit var okButtonLable: String
    private lateinit var cancelButtonLable: String
    private lateinit var okListener: ListenerCallBack
    private lateinit var cancelListener: ListenerCallBack
    private var isNegativeBtnHide = false

    fun setTitle(title: String): VelloDialogBuilder {
        this.title = title
        return this
    }

    fun setSubTitle(subTitle: String): VelloDialogBuilder {
        this.subtitle = subTitle
        return this
    }

    fun setTitleFont(titleFont: Typeface): VelloDialogBuilder {
        titleTf = titleFont
        return this
    }

    fun setSubTitleFont(subTFont: Typeface): VelloDialogBuilder {
        this.subTitleTf = subTFont
        return this
    }

    fun setPositiveButton(lable: String, listener: ListenerCallBack): VelloDialogBuilder {
        okListener = listener
        this.okButtonLable = lable
        return this
    }

    fun setNegativeButton(label: String, listener: ListenerCallBack): VelloDialogBuilder {
        cancelListener = listener
        this.cancelButtonLable = label
        return this
    }

    fun setCancellable(isCancelable: Boolean): VelloDialogBuilder {
        this.isCancelable = isCancelable
        return this
    }

    fun setNegativeButtonHide(isHide: Boolean): VelloDialogBuilder {
        isNegativeBtnHide = isHide
        return this
    }

    fun build(): VelloAlertDialog {
        val dialog = VelloAlertDialog()
        dialog.alertInitialize(context, title, subtitle, titleTf, subTitleTf, isCancelable, isNegativeBtnHide)
        cancelButtonLable.let { dialog.setNegative(it, cancelListener) }
        okButtonLable.let { dialog.setPositive(it, okListener) }
        return dialog
    }
}