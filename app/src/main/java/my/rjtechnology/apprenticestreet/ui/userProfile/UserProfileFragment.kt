package my.rjtechnology.apprenticestreet.ui.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentUserProfileBinding
import my.rjtechnology.apprenticestreet.models.UserProfile

class UserProfileFragment : Fragment() {
    var userProfile = UserProfile()

    private var _binding: FragmentUserProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val userProfileRef = database.reference.child("User Profiles").child("12345")

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

        editProfileButton.setOnClickListener {
            if (editProfileButton.text == "Edit Profile") {
                // Switch to edit mode
                editProfileButton.text = "Save Profile"
                username.isEnabled = true
                email.isEnabled = true
                age.isEnabled = true
                loe.isEnabled = true
                contact.isEnabled = true
                workExp.isEnabled = true
                bmRating.setIsIndicator(false)
                enRating.setIsIndicator(false)
                chRating.setIsIndicator(false)

                username.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                email.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                age.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                workExp.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            } else {
                // Save profile changes and switch back to view mode
                editProfileButton.text = "Edit Profile"
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
                database.child("User Profiles").child("12345").setValue(userProfile)
            }
        }

    }

    private fun getIndexOfLOE(loe: String): Int {
        val loeArray = resources.getStringArray(R.array.Level_of_Education)
        return loeArray.indexOf(loe)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}