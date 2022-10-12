package com.nmt.stockcheck.view.ui.scan

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.nmt.stockcheck.R
import com.nmt.stockcheck.config.Config
import com.nmt.stockcheck.databinding.ScanActivityBinding
import com.nmt.stockcheck.model.OCRAuthRequest
import com.nmt.stockcheck.model.OCRModel
import com.nmt.stockcheck.model.OCRRequest
import com.nmt.stockcheck.view.helper.PreferenceHelper
import com.nmt.stockcheck.view.ui.base.BaseActivity
import com.nmt.stockcheck.view.utility.Utility
import com.nmt.stockcheck.viewmodel.ScanViewModel
import com.tbruyelle.rxpermissions3.RxPermissions
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
import es.dmoral.toasty.Toasty
import org.koin.android.ext.android.inject
import kotlin.properties.Delegates

class ScanActivity : BaseActivity() {
    private lateinit var binding: ScanActivityBinding
    private lateinit var rxPermissions: RxPermissions
    private val viewModel: ScanViewModel by inject()
    private var resultLauncher: ActivityResultLauncher<Intent>? =null
    private val intentData by lazy {
        intent.getIntExtra("scanEnumType",-1)
    }
    private var imgBitmap:Bitmap?=null
    val REQUEST_CODE_GALLERY=100
    val REQUEST_BAR_CODE=101
    val REQUEST_QR_CODE=102
    enum class SCANTENUM{
        VIN,REGO,DRIVERLICENCE,CAR,QRCODE,BARCODE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=DataBindingUtil.setContentView(this@ScanActivity, R.layout.scan_activity)
        super.onCreate(savedInstanceState)
        observeGeneral(viewModel.loadError,viewModel.isLoading)

    }

    override fun onBackPressed() {
        binding.btnCancel.performClick()
        imgBitmap?.recycle()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_OK)
        {
            when(requestCode)
            {
                CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result= CropImage.getActivityResult(data)
                    imgBitmap=Utility.scaleBitmapDown(MediaStore.Images.Media.getBitmap(contentResolver,result.uri),840)
                    binding.imageView.setImageBitmap(imgBitmap)
                    authorizeOCRService()
                }
                REQUEST_BAR_CODE -> {
                    data?.getStringExtra("result")?.let {
                        binding.ediScanResult.setText(it)
                    }
                }

                REQUEST_QR_CODE -> {
                    data?.getStringExtra("result")?.let {
                        binding.ediScanResult.setText(it)
                    }
                }
            }

        }
    }


    override fun init() {
        rxPermissions= RxPermissions(this)

    }

    override fun setListener() {
        binding.btnCamera.setOnClickListener {
            openActivityForScanType()
        }
        resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            when(result.resultCode)
            {
                Activity.RESULT_OK -> {
                    result.data?.let {
                        when(it.getIntExtra("request_code",100))
                        {

                            REQUEST_CODE_GALLERY -> {
                                val uri=it.data
                                CropImage.activity(uri).start(this)
                            }
                            else -> {
                                result.data?.getStringExtra("result")?.let {
                                    Log.d("url",it.toString())
                                    imgBitmap=Utility.getBitmapFromURL(it.toString())
                                    binding.imageView.setImageBitmap(imgBitmap)
                                    authorizeOCRService()
                                }
                            }
                        }
                    }


                }

            }

        }
        binding.btnGallery.setOnClickListener {
            val galleryIntent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher?.launch(galleryIntent)

        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    override fun setObserver() {
        viewModel.OCRAuthenResponse.observe(this){
            it?.let { it1 -> PreferenceHelper.setAccessToken(it1.data.asString)
                imgBitmap?.let { it2 -> scanForIntent(it2) }
            }

        }
        viewModel.imgScanVinResponseObserver.observe(this){

            it?.let { it1 -> binding.ediScanResult.setText(it1.data!!.vin)
            }
        }
        viewModel.imgScanRegoResponseObserver.observe(this){

            it?.let { it1 -> binding.ediScanResult.setText(it1.data!!.rego)
            }
        }
        viewModel.imgScanDriverLicenseObserver.observe(this){
            it?.let { it1->
                it1.data?.copy()?.let { it2 ->
                    showDriverLicences(it2)
                }
            }
        }

    }

    override fun load() {
    }

    private fun openActivityForScanType()
    {
        rxPermissions.request(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe{
                if (it)
                {
                    when(intentData)
                    {
                        SCANTENUM.BARCODE.ordinal -> {
                            startActivityForResult(Intent(this@ScanActivity,ZXingScannerActivity::class.java),REQUEST_BAR_CODE)
                        }
                        SCANTENUM.QRCODE.ordinal -> {
                            startActivityForResult(Intent(this@ScanActivity,ZXingScannerActivity::class.java),REQUEST_QR_CODE)
                        }
                        else -> resultLauncher?.launch(Intent(this@ScanActivity,CameraActivity::class.java).putExtra("request_code",intentData))
                    }

                }
            }


    }
    private fun authorizeOCRService()
    {
        val ocrAuthRequest=OCRAuthRequest(Config.OCR_SYSTEM_CODE,"Android OS:"+Build.VERSION.RELEASE +" v"+Utility.getAppVersionName(),Utility.randomString(),Config.OCR_API_KEY)
        viewModel.authenOCRService(ocrAuthRequest)
    }
    private fun scanForIntent(bitmap: Bitmap)
    {
        val ocrRequest=OCRRequest(Config.OCR_COUNTRY_CODE,Utility.convertBitmapToBase64(bitmap))
        when(intentData)
        {
            SCANTENUM.VIN.ordinal -> viewModel.scanVin(ocrRequest)
            SCANTENUM.REGO.ordinal -> viewModel.scanRego(ocrRequest)
            SCANTENUM.DRIVERLICENCE.ordinal -> viewModel.scanDriverLicense(ocrRequest)
            SCANTENUM.BARCODE.ordinal -> viewModel.scanBarCode(ocrRequest)
            SCANTENUM.QRCODE.ordinal -> viewModel.scanQRCode(ocrRequest)
            else -> viewModel.scanCar(ocrRequest)
        }
    }

    private fun showDriverLicences(driverLicencesData: OCRModel.OCRDriverLicencesResponse.DriverLicencesData)
    {

        driverLicencesData?.let {
            val scanResultFragment=ScanResultFragment.newInstance(driverLicencesData,binding.toolbar.height+binding.layoutImage.height)
            scanResultFragment.show(supportFragmentManager,ScanResultFragment::class.simpleName)
        }

    }




}