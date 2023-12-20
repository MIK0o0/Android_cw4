package com.example.l4_andro

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.FileProvider
import com.example.l4_andro.databinding.FragmentTakePhotoBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TakePhotoFragment : Fragment() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var currentPhotoPath: String
    private lateinit var viewBinding: FragmentTakePhotoBinding
    private lateinit var lastFileUri: Uri

    val photoPreviewLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                result: Bitmap? ->
            if (result != null) {
                Toast.makeText(requireContext(),"PREVIEW RECEIVED",Toast.LENGTH_LONG).show()
                viewBinding.imageView.setImageBitmap(result)
            }
            else {
                Toast.makeText(requireContext(),"PREVIEW NOT RECEIVED",Toast.LENGTH_LONG).show()
            }
        }

    var lastFile: File = File("")
    val photoLauncher=registerForActivityResult(ActivityResultContracts.TakePicture()) {
            result : Boolean ->
        if (result) {
            // consume result - see later remarks
            Toast.makeText(requireContext(),"Photo TAKEN",Toast.LENGTH_LONG).show()
            saveImageToExternalStorage()
//            saveImageToInternalStorage()
        }else
        // make some action – warning
        Toast.makeText(requireContext(), "Photo NOT taken!", Toast.LENGTH_LONG).show()
        lastFile.delete()
    }

    private fun saveImageToExternalStorage() {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val externalStorageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera")
            val newFile = File(externalStorageDir, lastFile.name)


            var fis: FileInputStream? = null
            var fos: FileOutputStream? = null

            try {
                fis = FileInputStream(lastFile)
                fos = FileOutputStream(newFile)

                val buffer = ByteArray(1024)
                var length: Int
                while (fis.read(buffer).also { length = it } > 0) {
                    fos.write(buffer, 0, length)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                fis?.close()
                fos?.close()
            }
            MediaStore.Images.Media.insertImage(requireActivity().contentResolver, newFile.path, newFile.name, newFile.name)


            lastFile.delete()
        } else{
            Toast.makeText(requireContext(), "External storage not available!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun saveImageToInternalStorage() {
        val internalStorageDir = requireActivity().filesDir
        val newFile = File(internalStorageDir, lastFile.name)

        var fis: FileInputStream? = null
        var fos: FileOutputStream? = null

        try {
            fis = FileInputStream(lastFile)
            fos = FileOutputStream(newFile)

            val buffer = ByteArray(1024)
            var length: Int
            while (fis.read(buffer).also { length = it } > 0) {
                fos.write(buffer, 0, length)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fis?.close()
            fos?.close()
        }

        lastFile.delete()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTakePhotoBinding.inflate(inflater, container, false)
        viewBinding.previewButton.setOnClickListener {
            try {
                photoPreviewLauncher.launch()
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(),"PREVIEW DOESN'T WORK!", Toast.LENGTH_LONG).show()
            }
        }
        viewBinding.takePhotoButton.setOnClickListener {
            lastFileUri = getNewFileUri()
            try {
                photoLauncher.launch(lastFileUri)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(),"CAMERA DOESN'T WORK!", Toast.LENGTH_LONG).show()
            }
        }
        return viewBinding.root
    }

    private fun getNewFileUri(): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM + "/Camera")
//        val storageDir: File? = requireActivity().filesDir
        val imageFile = File.createTempFile(
            "IMG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        lastFile = imageFile //save File for future use
        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            imageFile
        )
    }
}