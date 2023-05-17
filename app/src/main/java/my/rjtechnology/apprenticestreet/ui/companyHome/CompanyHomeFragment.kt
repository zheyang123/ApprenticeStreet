package my.rjtechnology.apprenticestreet.ui.companyHome

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import my.rjtechnology.apprenticestreet.LoginActivity
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyHomeBinding
import my.rjtechnology.apprenticestreet.models.CompanyProfile
import java.io.ByteArrayOutputStream

class CompanyHomeFragment : Fragment() {
    var companyProfile = CompanyProfile()

    private var _binding: FragmentCompanyHomeBinding? = null
    private val binding get() = _binding!!
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private var bit: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private val PICK_IMAGE_REQUEST_CODE = 1

    private lateinit var companyPfp: ImageView
    private lateinit var editProfileButton: Button
    private lateinit var logoutButton: Button
    private lateinit var name: EditText
    private lateinit var contact: EditText
    private lateinit var email: EditText
    private lateinit var managerName: EditText
    private lateinit var address: EditText
    private lateinit var companyID: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompanyHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        companyPfp = binding.companyProfileImageEdit
        name = binding.companyNameEdit
        contact = binding.companyContactEdit
        email = binding.companyEmailEdit
        managerName = binding.managerNameEdit
        address = binding.companyLocationEdit
        companyID = binding.companyIDGet
        editProfileButton = binding.companyEditProfButton
        logoutButton = binding.companyLogoutButton

        val database = Firebase.database
        val companyProfileRef = database.reference.child("Company Profiles").child("12345")

        val imageRef = storageRef.child("CompanyLogoPics").child("12345")

        // Download the image as a byte array
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            // Image download successful, handle the byte array
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            companyPfp.setImageBitmap(bitmap)
            bit = bitmap
            // Use the bitmap as needed
        }.addOnFailureListener { exception ->
            // Handle any errors that occurred during image download
            Log.e(TAG, "Failed to download image: ${exception.message}")
        }

        companyProfileRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val companyProfile = snapshot.getValue(CompanyProfile::class.java)

                    // Update the UI with the retrieved user profile data
                    companyProfile?.let {
                        name.setText(companyProfile.companyName)
                        email.setText(companyProfile.companyEmail)
                        managerName.setText(companyProfile.managerName)
                        contact.setText(companyProfile.companyContact)
                        address.setText(companyProfile.companyAddress)
                        companyID.text = companyProfile.companyID

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur during data retrieval
                Toast.makeText(requireContext(), "Failed to retrieve company profile data", Toast.LENGTH_SHORT).show()
            }
            })

        companyPfp.isEnabled = false
        name.isEnabled = false
        email.isEnabled = false
        managerName.isEnabled = false
        contact.isEnabled = false
        address.isEnabled = false

        name.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        email.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        companyID.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        managerName.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        address.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        editProfileButton.setOnClickListener {
            if (editProfileButton.text == "Edit Profile") {
                // Switch to edit mode
                editProfileButton.text = "Save Profile"
                companyPfp.isEnabled = true
                name.isEnabled = true
                email.isEnabled = true
                managerName.isEnabled = true
                contact.isEnabled = true
                address.isEnabled = true

                companyPfp.imageAlpha = 128
                name.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                email.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                managerName.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                address.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            } else {
                // Save profile changes and switch back to view mode
                editProfileButton.text = "Edit Profile"

                companyPfp.isEnabled = false
                name.isEnabled = false
                email.isEnabled = false
                managerName.isEnabled = false
                contact.isEnabled = false
                address.isEnabled = false
                companyPfp.imageAlpha = 128
                name.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                email.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                managerName.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                address.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

                // Perform validation checks
                val updatedUsername = name.text.toString().trim()
                val updatedEmail = email.text.toString().trim()
                val updatedManager = managerName.text.toString().trim()
                val updatedContact = contact.text.toString().trim()
                val updatedAddress = address.text.toString().trim()

                if (updatedUsername.isEmpty() || updatedEmail.isEmpty() || updatedManager.isEmpty() ||
                    updatedContact.isEmpty() || updatedAddress.isEmpty()) {
                    Toast.makeText(requireContext(), "Please make sure that all the required fields are not empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                companyProfile.companyName = updatedUsername
                companyProfile.companyEmail = updatedEmail
                companyProfile.managerName = updatedManager
                companyProfile.companyContact = updatedContact
                companyProfile.companyAddress = updatedAddress

                var database = Firebase.database.reference
                database.child("Company Profiles").child("12345").setValue(companyProfile)
                    .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Data write operation successful
                        Toast.makeText(requireContext(), "Profile saved successfully", Toast.LENGTH_SHORT)
                            .show()
                        // Perform further actions if needed
                    } else {
                        // Data write operation failed
                        Toast.makeText(requireContext(), "Profile save failed", Toast.LENGTH_SHORT).show()
                    }
                    uploadImage()
                }
            }
        }

        logoutButton.setOnClickListener {
            // Perform logout actions here
            // For example, you can clear user session or navigate to the login screen

            // Clear user session and navigate to login screen
            // Replace LoginActivity::class.java with your actual login activity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data

            if (selectedImageUri != null) {
                loadImageFromUri(selectedImageUri)
            } else {
                Toast.makeText(requireContext(), "Failed to retrieve selected image", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun loadImageFromUri(uri: Uri) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.companyProfileImageEdit.setImageBitmap(bitmap)
            bit = bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImage() {
        val imageId = "12345" // Replace with your desired ID or use a dynamic ID
        val imageRef = storageRef.child("CompanyLogoPics").child(imageId)

        try {
            //val inputStream = contentResolver.openInputStream(uri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bit.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val data = byteArrayOutputStream.toByteArray()

            val uploadTask = imageRef.putBytes(data)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        // Image upload successful
                        val imageUrl = downloadUrl.toString()
                        // Save the imageUrl to the user profile or perform further actions
                        Toast.makeText(requireContext(), "Image Upload Successful", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        // Handle download URL retrieval failure
                        Toast.makeText(requireContext(), "Failed to retrieve download URL", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle upload failure
                    Toast.makeText(requireContext(), "Image Upload Failed", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}