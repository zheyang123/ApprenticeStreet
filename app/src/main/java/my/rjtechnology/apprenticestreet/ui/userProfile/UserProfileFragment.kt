package my.rjtechnology.apprenticestreet.ui.userProfile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import my.rjtechnology.apprenticestreet.LoginActivity
import my.rjtechnology.apprenticestreet.MainViewModel
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentUserProfileBinding
import my.rjtechnology.apprenticestreet.models.UserProfile
import my.rjtechnology.apprenticestreet.ui.progress.ProgressViewModel
import java.io.ByteArrayOutputStream

class UserProfileFragment : Fragment() {
    private var userProfile = UserProfile()

    private var _binding: FragmentUserProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private var bit: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private val PICK_IMAGE_REQUEST_CODE = 1

    private lateinit var userPfp: ImageView
    private lateinit var age: EditText
    private lateinit var contact: EditText
    private lateinit var email: EditText
    private lateinit var loe: Spinner
    private lateinit var workExp: EditText
    private lateinit var bmRating: RatingBar
    private lateinit var enRating: RatingBar
    private lateinit var chRating: RatingBar
    private lateinit var username: EditText
    private lateinit var userID: TextView
    private lateinit var editProfileButton: Button
    private lateinit var logoutButton: Button

    private lateinit var viewModel: UserProfileViewModel
    private lateinit var mViewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireParentFragment())[UserProfileViewModel::class.java]
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.id.observe(viewLifecycleOwner) { id ->
            viewModel.id = id
            callUI()
        }
    }

    private fun callUI() {
        userPfp = binding.userProfileImageEdit
        age = binding.userAgeEdit
        contact = binding.userContactEdit
        email = binding.userEmailEdit
        loe = binding.spinnerLOEEdit
        workExp = binding.userWorkExpEdit
        bmRating = binding.bmRatingBarEdit
        enRating = binding.englishRatingBarEdit
        chRating = binding.chineseRatingBarEdit
        username = binding.userNameEdit
        userID = binding.userIDGet
        editProfileButton = binding.userEditProfButton
        logoutButton = binding.userLogoutButton

        // Fetch the user profile data from the database
        val database = Firebase.database
        val userProfileRef = database.reference.child("User Profiles").child(viewModel.id)

        val imageRef = storageRef.child("UserProfilePics").child(viewModel.id)

        // Download the image as a byte array
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            // Image download successful, handle the byte array
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            userPfp.setImageBitmap(bitmap)
            bit = bitmap
            // Use the bitmap as needed
        }.addOnFailureListener { exception ->
            // Handle any errors that occurred during image download
            Log.e(TAG, "Failed to download image: ${exception.message}")
        }

        userProfileRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userProfile = snapshot.getValue(UserProfile::class.java)

                    // Update the UI with the retrieved user profile data
                    userProfile?.let {
                        username.setText(userProfile.userName)
                        email.setText(userProfile.userEmail)
                        age.setText(userProfile.userAge.toString())
                        loe.setSelection(getIndexOfLOE(userProfile.userLevelOfEducation))
                        contact.setText(userProfile.userContact)
                        workExp.setText(userProfile.userWorkingExp)
                        bmRating.rating = userProfile.userBMSkill.toFloat()
                        enRating.rating = userProfile.userENSkill.toFloat()
                        chRating.rating = userProfile.userCHSkill.toFloat()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur during data retrieval
                Toast.makeText(requireContext(), "Failed to retrieve user profile data", Toast.LENGTH_SHORT).show()
            }
        })

        userPfp.isEnabled = false
        username.isEnabled = false
        email.isEnabled = false
        age.isEnabled = false
        loe.isEnabled = false
        contact.isEnabled = false
        workExp.isEnabled = false

        username.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        email.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        userID.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        age.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        workExp.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        userPfp.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        editProfileButton.setOnClickListener {
            if (editProfileButton.text == "Edit Profile") {
                // Switch to edit mode
                editProfileButton.text = "Save Profile"
                userPfp.isEnabled = true
                username.isEnabled = true
                email.isEnabled = true
                age.isEnabled = true
                loe.isEnabled = true
                contact.isEnabled = true
                workExp.isEnabled = true
                bmRating.setIsIndicator(false)
                enRating.setIsIndicator(false)
                chRating.setIsIndicator(false)

                userPfp.imageAlpha = 128
                username.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                email.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                age.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                workExp.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            } else {
                // Save profile changes and switch back to view mode
                editProfileButton.text = "Edit Profile"

                userPfp.isEnabled = false
                username.isEnabled = false
                email.isEnabled = false
                age.isEnabled = false
                loe.isEnabled = false
                contact.isEnabled = false
                workExp.isEnabled = false
                bmRating.setIsIndicator(true)
                enRating.setIsIndicator(true)
                chRating.setIsIndicator(true)

                userPfp.imageAlpha = 255
                username.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                email.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                age.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                workExp.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

                // Perform validation checks
                val updatedUsername = username.text.toString().trim()
                val updatedEmail = email.text.toString().trim()
                val updatedAge = age.text.toString().trim()
                val updateLOE = loe.selectedItem.toString().trim()
                val updatedContact = contact.text.toString().trim()
                val updatedWorkExp = workExp.text.toString().trim()

                if (updatedUsername.isEmpty() || updatedEmail.isEmpty() || updatedAge.isEmpty() ||
                    updateLOE.isEmpty() || updatedContact.isEmpty() || updatedWorkExp.isEmpty()) {
                    Toast.makeText(requireContext(), "Please make sure that all the required fields are not empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val updatedBMRating = bmRating.rating.toInt()
                val updatedENRating = enRating.rating.toInt()
                val updatedCHRating = chRating.rating.toInt()

                userProfile.userName = updatedUsername
                userProfile.userEmail = updatedEmail
                userProfile.userAge = updatedAge.toInt()
                userProfile.userContact = updatedContact
                userProfile.userLevelOfEducation = updateLOE
                userProfile.userWorkingExp = updatedWorkExp
                userProfile.userBMSkill = updatedBMRating
                userProfile.userENSkill = updatedENRating
                userProfile.userCHSkill = updatedCHRating

                var database = Firebase.database.reference
                database.child("User Profiles").child(viewModel.id).setValue(userProfile)
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

    private fun getIndexOfLOE(loe: String): Int {
        val loeArray = resources.getStringArray(R.array.Level_of_Education)
        return loeArray.indexOf(loe)
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
            binding.userProfileImageEdit.setImageBitmap(bitmap)
            bit = bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImage() {
        val imageRef = storageRef.child("UserProfilePics").child(viewModel.id)

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