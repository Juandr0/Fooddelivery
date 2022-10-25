package fragment.restaurant

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.fooddeliveryproject.R
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImagesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

// ta bort denna när jkag är klar
//    lateinit var uploadNewImageButton: Button


    lateinit var uploadImageButton: Button
    lateinit var selectImageButton: Button
    lateinit var uploadPreviewImageView: ImageView
    lateinit var imageUri: Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ta bort detta när jag är klar
//        uploadNewImageButton = view.findViewById(R.id.uploadNewImageButton)

//        uploadNewImageButton.setOnClickListener {
//            val intent = Intent(context, UploadImageActivity::class.java)
//            startActivity(intent)
//        }
        // sluta ta bort

        uploadImageButton = view.findViewById(R.id.uploadImageButton2)
        selectImageButton = view.findViewById(R.id.selectImageButton2)
        uploadPreviewImageView = view.findViewById(R.id.uploadPreviewImageVIew2)

        //gets image from phone and shows it in the ImageVIew
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {

                uploadPreviewImageView.setImageURI(it)

                imageUri = it!!

            }
        )



        selectImageButton.setOnClickListener {

            //the folder it searches images in
            getImage.launch("image/*")
        }

        uploadImageButton.setOnClickListener {

            uploadImage()
        }


    }

    private fun uploadImage() {

        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Uploading File...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)
        val storageRef = FirebaseStorage.getInstance().getReference("images/$filename")

        storageRef.putFile(imageUri).
        addOnSuccessListener {

            uploadPreviewImageView.setImageURI(null)
            Toast.makeText(context, "successfully uploaded", Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing) progressDialog.dismiss()

        }.addOnFailureListener{

            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImagesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImagesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}