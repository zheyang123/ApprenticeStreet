package my.rjtechnology.apprenticestreet

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import my.rjtechnology.apprenticestreet.databinding.ActivityCompleteProfileBinding
import my.rjtechnology.apprenticestreet.models.UserProfile
import java.io.ByteArrayOutputStream

class CompleteProfile : AppCompatActivity() {
    private lateinit var binding: ActivityCompleteProfileBinding
    private var userProfile = UserProfile()
    var id = ""
    private var bit: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private val PICK_IMAGE_REQUEST_CODE = 1
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    var namePass = ""
    var emailPass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityCompleteProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = intent.getStringExtra("id").toString()
        val uploadImg = binding.userUploadImage
        val completeProfileButton = binding.completeProfButton
        getEmail()
        getName()

        uploadImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        completeProfileButton.setOnClickListener {
            //val image = binding.userUploadImage.drawable as BitmapDrawable
            //val bitmap = image.bitmap
            val age = binding.userAgeComplete
            val contact = binding.userContactComplete
            val loe = binding.spinnerLOEComplete
            val workExp = binding.userWorkExpComplete
            val bmRating = binding.bmRatingBarComplete
            val enRating = binding.englishRatingBarComplete
            val chRating = binding.chineseRatingBarComplete

            // Retrieve user input from the UI elements
            val ageValue = age.text.toString().toInt()
            val contactValue = contact.text.toString()
            val loeValue = loe.selectedItem.toString()
            val workExpValue = workExp.text.toString()
            val bmRatingValue = bmRating.rating.toInt()
            val enRatingValue = enRating.rating.toInt()
            val chRatingValue = chRating.rating.toInt()

            val validation1 = binding.userAgeComplete.text.toString().trim()
            val validation2 = contact.text.toString().trim()
            val validation3 = workExp.text.toString().trim()

            if (validation1.isEmpty()) {
                age.error = "Age is required"
                age.requestFocus()
                return@setOnClickListener
            }

            if (validation2.isEmpty()) {
                contact.error = "Contact is required"
                contact.requestFocus()
                return@setOnClickListener
            }

            if (validation3.isEmpty()) {
                workExp.error = "Working Experience is required"
                workExp.requestFocus()
                return@setOnClickListener
            }

            // Create a UserProfile object with the retrieved values
            userProfile.userName = namePass
            userProfile.userEmail = emailPass
            userProfile.userAge = ageValue
            userProfile.userContact = contactValue
            userProfile.userLevelOfEducation = loeValue
            userProfile.userWorkingExp = workExpValue
            userProfile.userBMSkill = bmRatingValue
            userProfile.userENSkill = enRatingValue
            userProfile.userCHSkill = chRatingValue

            var database2 = Firebase.database.reference
            database2.child("users").child(id).child("profileOk").setValue(true)

            // Call a function to upload the user profile to Firebase
            var database = Firebase.database.reference
            database.child("User Profiles").child(id).setValue(userProfile)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Data write operation successful
                        Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT)
                            .show()
                        // Perform further actions if needed
                    } else {
                        // Data write operation failed
                        Toast.makeText(this, "Profile save failed", Toast.LENGTH_SHORT).show()
                    }
                    uploadImage()
                }
            val intent = Intent(this, MainActivity::class.java
            ).also { it.putExtra("id", id)
                startActivity(it)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data

            if (selectedImageUri != null) {
                loadImageFromUri(selectedImageUri)
            } else {
                Toast.makeText(this, "Failed to retrieve selected image", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun loadImageFromUri(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.userUploadImage.setImageBitmap(bitmap)
            bit = bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImage() {
        val imageId = id // Replace with your desired ID or use a dynamic ID
        val imageRef = storageRef.child("UserProfilePics").child(imageId)

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
                        Toast.makeText(this, "Image Upload Successful", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        // Handle download URL retrieval failure
                        Toast.makeText(this, "Failed to retrieve download URL", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle upload failure
                    Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getName() {
        var database1 = Firebase.database.reference
        var ref = database1.child("users").child(id).child("fullName")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val stringValue = snapshot.getValue(String::class.java)
                if (stringValue != null) {
                    // Use the retrieved string value
                    namePass = stringValue
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur during data retrieval
                Log.e(TAG, "Failed to retrieve string value: ${error.message}")
            }
        })
    }

    private fun getEmail() {
        var database1 = Firebase.database.reference
        var ref = database1.child("users").child(id).child("email")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val stringValue = snapshot.getValue(String::class.java)
                if (stringValue != null) {
                    // Use the retrieved string value
                    emailPass = stringValue
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur during data retrieval
                Log.e(TAG, "Failed to retrieve string value: ${error.message}")
            }
        })
    }
}