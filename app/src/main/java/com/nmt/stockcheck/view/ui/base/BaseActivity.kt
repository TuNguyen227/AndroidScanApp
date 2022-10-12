package com.nmt.stockcheck.view.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.nmt.stockcheck.view.helper.DialogHelper
import es.dmoral.toasty.Toasty

abstract class BaseActivity : AppCompatActivity() {
    val progressDialog by lazy {
        DialogHelper.materialProgressDialog(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setListener()
        setObserver()
        load()
    }

    abstract fun init()
    abstract fun setListener()
    abstract fun setObserver()
    abstract fun load()

    private fun onLoading(isLoading: Boolean?) {
        isLoading?.let {
            if (it)
                progressDialog.show()
            else progressDialog.dismiss()
        }
    }

    @SuppressLint("CheckResult")
    protected fun observeGeneral(
        loadError: MutableLiveData<String?>?,
        loading: MutableLiveData<Boolean>?
    ) {
        loadError?.observe(this) { message ->
            Toasty.error(this,message.toString()).show()
        }
        loading?.observe(this) { isLoading ->
            onLoading(isLoading)
        }
    }

}