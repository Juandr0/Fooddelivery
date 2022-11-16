package com.example.fooddeliveryproject

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class RestaurantUploadImageActivity : AppCompatActivity() {

    lateinit var uploadImageButton: Button
    lateinit var selectImageButton: Button
    lateinit var uploadPreviewImageView: ImageView
    lateinit var imageUri: Uri
    lateinit var returnImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_upload_image)


        uploadImageButton = findViewById(R.id.uploadImageButton)
        selectImageButton = findViewById(R.id.selectImageButton)
        uploadPreviewImageView = findViewById(R.id.uploadPreviewImageVIew)
        returnImageButton = findViewById(R.id.returnImageButton)

        //gets image from phone and shows it in the ImageVIew
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {

                uploadPreviewImageView.setImageURI(it)

                imageUri = it!!

            }
        )

        returnImageButton.setOnClickListener {
            val intent = Intent(this, RestaurantInterfaceActivity::class.java)
                startActivity(intent)
        }

        selectImageButton.setOnClickListener {

            //the folder it searches images in
            getImage.launch("image/*")
        }

        uploadImageButton.setOnClickListener {

            uploadImage()
        }

    }

    private fun uploadImage() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)
        val storageRef = FirebaseStorage.getInstance().getReference("images/$filename")

        storageRef.putFile(imageUri).addOnSuccessListener {

            uploadPreviewImageView.setImageURI(null)
            Toast.makeText(
                this,
                "successfully uploaded",
                Toast.LENGTH_SHORT
            ).show()
            if (progressDialog.isShowing) progressDialog.dismiss()

        }.addOnFailureListener {

            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

        }

    }

}