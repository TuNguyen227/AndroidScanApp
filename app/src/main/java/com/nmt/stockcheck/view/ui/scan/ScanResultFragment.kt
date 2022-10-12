package com.nmt.stockcheck.view.ui.scan


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.nmt.stockcheck.R
import com.nmt.stockcheck.databinding.ScanResultFragmentBinding
import org.koin.android.ext.android.inject
import com.nmt.stockcheck.model.OCRModel
import com.nmt.stockcheck.viewmodel.ScanViewModel
import kotlinx.android.synthetic.main.scan_result_fragment.view.*


import java.util.*


class ScanResultFragment: DialogFragment(){

    lateinit var binding:ScanResultFragmentBinding
     val scanViewModel: ScanViewModel by inject()
    var calendarExpiry = Calendar.getInstance()
    var calendarDOB = Calendar.getInstance()

    companion object {

        fun newInstance(driverLicencesData: OCRModel.OCRDriverLicencesResponse.DriverLicencesData ,marginTop: Int): ScanResultFragment {
            val fragment = ScanResultFragment()
            val args = Bundle()
            args.putParcelable("object", driverLicencesData)
            args.putInt("marginTop", marginTop)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.scan_result_fragment, null, false);
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            scanViewModel.driverLicencesDataObject= it.getParcelable("object")!!

        }
        setListener()
        initInfo()
        loadInfo()
    }
    override fun onStart() {
        super.onStart()
        dialog?.let {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val window = dialog!!.window
            val windowParams = window!!.attributes
            windowParams.dimAmount = 0.0f
            windowParams.flags = windowParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            window.attributes = windowParams
        }

    }


    fun setListener(){

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    fun initInfo(){
        (binding.layoutRoot.layoutParams as LinearLayout.LayoutParams).setMargins(0, arguments?.getInt("marginTop",0)?:0,0,0)
    }

    fun loadInfo() {
        binding.apply {
            ediFirstname.edi_firstname.setText(scanViewModel.driverLicencesDataObject?.firstName.toString())
            ediLastname.edi_lastname.setText(scanViewModel.driverLicencesDataObject?.lastName.toString())
            ediStreet.edi_street.setText(scanViewModel.driverLicencesDataObject?.streetLine1.toString())
            ediPostcode.edi_postcode.setText(scanViewModel.driverLicencesDataObject?.postCode.toString())
            ediState.edi_state.setText(scanViewModel.driverLicencesDataObject?.state.toString())
            ediSuburb.edi_suburb.setText(scanViewModel.driverLicencesDataObject?.suburb.toString())
            ediLicenseNo.edi_license_no.setText(scanViewModel.driverLicencesDataObject?.driverLicenceNumber.toString())
            txtLicenseExpired.setText(scanViewModel.driverLicencesDataObject?.driverLicenceExpiryDate.toString())
            scanViewModel.driverLicencesDataObject?.version?.let {it-> ediVersion.edi_version.setText(it.toString()) }
            txtDob.setText(scanViewModel.driverLicencesDataObject?.dob.toString())
        }

    }




}

