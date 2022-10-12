package com.nmt.stockcheck.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.nmt.stockcheck.R
import com.nmt.stockcheck.databinding.MainActivityBinding
import com.nmt.stockcheck.view.ui.base.BaseActivity
import com.nmt.stockcheck.view.ui.scan.ScanActivity

class MainActivity : BaseActivity() {
    private lateinit var binding:MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=DataBindingUtil.setContentView(this@MainActivity,R.layout.main_activity)
        super.onCreate(savedInstanceState)

    }

    override fun init() {

    }

    override fun setListener() {
        binding.apply {
            btnScanVin.setOnClickListener {
                startActivity(Intent(this@MainActivity,ScanActivity::class.java).putExtra("scanEnumType",ScanActivity.SCANTENUM.VIN.ordinal))
            }
            btnScanRego.setOnClickListener {
                startActivity(Intent(this@MainActivity,ScanActivity::class.java).putExtra("scanEnumType",ScanActivity.SCANTENUM.REGO.ordinal))
            }
            btnScanDriver.setOnClickListener {
                startActivity(Intent(this@MainActivity,ScanActivity::class.java).putExtra("scanEnumType",ScanActivity.SCANTENUM.DRIVERLICENCE.ordinal))
            }
            btnScanBarCode.setOnClickListener {
                startActivity(Intent(this@MainActivity,ScanActivity::class.java).putExtra("scanEnumType",ScanActivity.SCANTENUM.BARCODE.ordinal))
            }
            btnScanQrCode.setOnClickListener {
                startActivity(Intent(this@MainActivity,ScanActivity::class.java).putExtra("scanEnumType",ScanActivity.SCANTENUM.QRCODE.ordinal))
            }
            btnScanCar.setOnClickListener {
                startActivity(Intent(this@MainActivity,ScanActivity::class.java).putExtra("scanEnumType",ScanActivity.SCANTENUM.CAR.ordinal))
            }
        }
    }

    override fun setObserver() {

    }

    override fun load() {

    }
}