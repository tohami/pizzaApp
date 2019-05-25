package com.etisalat.sampletask.ui.pickimage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import com.etisalat.sampletask.R
import com.etisalat.sampletask.bases.BaseActivity
import com.etisalat.sampletask.bases.BasePresenter
import com.etisalat.sampletask.bases.BasePresenterListener
import kotlinx.android.synthetic.main.activity_pick_image.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PickImageActivity : BaseActivity<BasePresenter<BasePresenterListener>>() {
    private var REQUEST_IMAGE_CODE = 1993
    private val PERMS_REQUEST_CAMERA = 122


    private lateinit var IMAGE_DIRECTORY: File
    internal lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_image)
        IMAGE_DIRECTORY = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        fab.setOnClickListener { takePhoto() }

    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun askForPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(this,
                arrayOf(permission),
                requestCode)
    }

    private fun takePhoto() {
        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showSnackbar("App need Permission to access files")
                takePhoto()
            } else {
                // No explanation needed; request the permission
                askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        PERMS_REQUEST_CAMERA)
            }
            return
        }
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                showSnackbar("Can not create file for image")
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(applicationContext,
                        "com.etisalat.sampletask.fileprovider",
                        photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMS_REQUEST_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                } else {
                    showSnackbar("permission not granted")
                }
                return
            }
        }
    }


    override fun setupPresenter(): BasePresenter<BasePresenterListener>? {
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            val f = File(currentPhotoPath)
            val contentUri = Uri.fromFile(f)
            ivPickImg.setImageURI(contentUri)
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = IMAGE_DIRECTORY
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        currentPhotoPath = image.absolutePath
        return image
    }

}
