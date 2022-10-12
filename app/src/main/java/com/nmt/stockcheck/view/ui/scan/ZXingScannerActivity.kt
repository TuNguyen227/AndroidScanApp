package com.nmt.stockcheck.view.ui.scan

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.Result
import com.nmt.stockcheck.view.ui.base.BaseActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ZXingScannerActivity : BaseActivity(), ZXingScannerView.ResultHandler {
    private var zXingScannerView : ZXingScannerView ? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zXingScannerView= ZXingScannerView(this)
        setContentView(zXingScannerView)
    }

    override fun onResume() {
        super.onResume()
        zXingScannerView?.setResultHandler(this)
        zXingScannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zXingScannerView?.stopCamera()
    }

    override fun init() {

    }

    override fun setListener() {

    }

    override fun setObserver() {

    }

    override fun load() {

    }

    override fun handleResult(p0: Result?) {
        val returnIntent=Intent().putExtra("result",p0.toString())
        setResult(Activity.RESULT_OK,returnIntent)
        finish()
    }
}