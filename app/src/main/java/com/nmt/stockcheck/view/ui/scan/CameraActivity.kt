package com.nmt.stockcheck.view.ui.scan

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.Surface
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.nmt.stockcheck.R
import com.nmt.stockcheck.databinding.CameraActivityBinding
import com.nmt.stockcheck.view.control.CameraOverlayViewGroup
import com.nmt.stockcheck.view.ui.base.BaseActivity
import com.nmt.stockcheck.view.utility.Utility
import com.nmt.stockcheck.viewmodel.CameraViewModel
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.gesture.GestureAction
import com.tbruyelle.rxpermissions3.RxPermissions
import org.koin.android.ext.android.inject

class CameraActivity : BaseActivity() {
    private lateinit var binding:CameraActivityBinding

    private lateinit var animatorSet: AnimatorSet
    private var frameDimension= arrayListOf<Int>()
    private val viewModel:CameraViewModel by inject()
    private val requestCode by lazy {
        intent.getIntExtra("request_code",-1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=DataBindingUtil.setContentView(this@CameraActivity, R.layout.camera_activity)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        binding.camera.open()
    }

    override fun onPause() {
        super.onPause()
        binding.camera.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.camera.destroy()
        animatorSet.removeAllListeners()
        animatorSet.cancel()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun init() {

        binding.camera.apply {
            setLifecycleOwner(this@CameraActivity)
            //startAutoFocus((binding.camera.width/2).toFloat(), (binding.camera.height/2).toFloat())
        }
        binding.capturedImage.viewTreeObserver.addOnGlobalLayoutListener (object:
            ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                binding.overlayView.visibility= View.VISIBLE
                binding.capturedImage.viewTreeObserver.removeOnGlobalLayoutListener (this)
            }
        })
        val fadeOut = ObjectAnimator.ofFloat(binding.capturedImage, "alpha",  1f, .1f);
        fadeOut.duration = 500;
        val fadeIn = ObjectAnimator.ofFloat(binding.capturedImage, "alpha", .1f, 1f);
        fadeIn.duration = 500;
        animatorSet = AnimatorSet();
        var count=0
        animatorSet.play(fadeOut).after(fadeIn)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation);
                count++
                if(count<10)
                    animatorSet.start();
                else{
                    Handler().postDelayed({
                        count=0
                        animatorSet.start();
                    },5000)

                }
            }
        })
        animatorSet.start()
        binding.capturedImage.postDelayed({

            frameDimension.add(binding.capturedImage.width)
            frameDimension.add(binding.capturedImage.height)
            binding.overlayView.myWidth= binding.capturedImage.width.toFloat()
            binding.overlayView.myHeight= binding.capturedImage.height.toFloat()
            binding.overlayView.requestLayout()

        },500)

        binding.camera.mapGesture(com.otaliastudios.cameraview.gesture.Gesture.TAP, GestureAction.AUTO_FOCUS)
        binding.camera.mapGesture(com.otaliastudios.cameraview.gesture.Gesture.PINCH, GestureAction.ZOOM)
    }

    override fun setListener() {
        binding.overlayView.cameraOverlayViewGroupListener=object :CameraOverlayViewGroup.CameraOverlayViewGroupListener{
            override fun onTouch(motionEvent: MotionEvent) {
                binding.camera.dispatchTouchEvent(motionEvent)
            }
        }
        binding.camera.apply {
            addCameraListener(object : CameraListener(){
                override fun onCameraOpened(options: CameraOptions) {
                    super.onCameraOpened(options)
                }

                override fun onCameraClosed() {
                    super.onCameraClosed()
                }

                override fun onPictureTaken(result: PictureResult) {
                    super.onPictureTaken(result)
                    val width: Int = binding.camera.width
                    val height: Int = binding.camera.height
                    val options = BitmapFactory.Options()
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888
                    options.inMutable = true
                    options.inScaled = false
                    options.inSampleSize = 2

                    var bitmap = BitmapFactory.decodeByteArray(result.data, 0, result?.data.size, options)
                    val matrix = Matrix()
                    val rotation = this@CameraActivity.windowManager.defaultDisplay.rotation
                    var degrees = 0f
                    when (rotation) {
                        Surface.ROTATION_0 -> degrees = 90f
                        Surface.ROTATION_90 -> degrees = 0f
                        Surface.ROTATION_180 -> degrees = 270f
                        Surface.ROTATION_270 -> degrees = 180f
                    }
                    matrix.postRotate(degrees)


                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                    val bh = bitmap!!.height
                    val bw = bitmap.width
                    val l = binding.capturedImage.x.toInt() * bw / width
                    val t = binding.capturedImage.y.toInt() * bh / height
                    val w = binding.capturedImage.width * bw / width
                    val h = binding.capturedImage.height * bh / height
                    var resizedBitmap = Bitmap.createBitmap(bitmap, l, t, if(l+w>bw) bw else l+w , h)
                    resizedBitmap = Utility.scaleBitmapDown(resizedBitmap,800)
                    resizedBitmap?.let {
                        val path=Utility.saveBitmapToFile(resizedBitmap).toString()
                        val returnIntent=Intent().putExtra("result",path).putExtra("request_code",requestCode)
                        setResult(Activity.RESULT_OK,returnIntent)
                        resizedBitmap.recycle()
                        finish()
                    }
                }



            })

        }
        binding.btnTake.setOnClickListener {
            binding.camera.takePicture()
        }

    }

    override fun setObserver() {

    }

    override fun load() {

    }
}