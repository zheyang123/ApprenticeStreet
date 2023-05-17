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
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import my.rjtechnology.apprenticestreet.CompanyMainActivityViewModel
import my.rjtechnology.apprenticestreet.LoginActivity
import my.rjtechnology.apprenticestreet.MainViewModel
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyHomeBinding
import my.rjtechnology.apprenticestreet.models.CompanyProfile
import my.rjtechnology.apprenticestreet.ui.userProfile.UserProfileViewModel
import java.io.ByteArrayOutputStream

class CompanyHomeFragment : Fragment() {
    private var companyProfile = CompanyProfile()

    private var _binding: FragmentCompanyHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var companyPfp: ImageView
    private lateinit var editProfileButton: Button
    private lateinit var logoutButton: Button
    private lateinit var name: EditText
    private lateinit var username: EditText
    private lateinit var contact: EditText
    private lateinit var email: EditText
    private lateinit var managerName: EditText
    private lateinit var companyID: TextView

    private lateinit var viewModel: CompanyHomeViewModel
    private lateinit var mViewModel: CompanyMainActivityViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireParentFragment())[CompanyHomeViewModel::class.java]
        _binding = FragmentCompanyHomeBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(requireActivity()).get(CompanyMainActivityViewModel::class.java)
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
        name = binding.companyNameEdit
        username = binding.companyUserNameEdit
        contact = binding.companyContactEdit
        email = binding.companyEmailEdit
        managerName = binding.managerNameEdit
        companyID = binding.companyIDGet
        editProfileButton = binding.companyEditProfButton
        logoutButton = binding.companyLogoutButton

        // Fetch the user profile data from the database
        val database = Firebase.database
        val companyProfileRef = database.reference.child("companies").child(viewModel.id)

        companyProfileRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val companyProfile = snapshot.getValue(CompanyProfile::class.java)

                    // Update the UI with the retrieved user profile data
                    companyProfile?.let {
                        name.setText(companyProfile.companyName)
                        email.setText(companyProfile.companyEmail)
                        managerName.setText(companyProfile.managerName)
                        contact.setText(companyProfile.contactNo)
                        companyID.text = companyProfile.id

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur during data retrieval
                Toast.makeText(requireContext(), "Failed to retrieve company profile data", Toast.LENGTH_SHORT).show()
            }
        })

        name.isEnabled = false
        //username.isEnabled = false
        email.isEnabled = false
        managerName.isEnabled = false
        contact.isEnabled = false

        name.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        email.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        companyID.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        managerName.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        username.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        editProfileButton.setOnClickListener {
            if (editProfileButton.text == "Edit Profile") {
                // Switch to edit mode
                editProfileButton.text = "Save Profile"
                companyPfp.isEnabled = true
                name.isEnabled = true
                email.isEnabled = true
                managerName.isEnabled = true
                contact.isEnabled = true
                username.isEnabled = true

                name.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                email.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                managerName.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                username.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            } else {
                // Save profile changes and switch back to view mode
                editProfileButton.text = "Edit Profile"

                name.isEnabled = false
                email.isEnabled = false
                managerName.isEnabled = false
                contact.isEnabled = false
                username.isEnabled = true
                name.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                email.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                managerName.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                username.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

                // Perform validation checks
                val updatedCompanyName = name.text.toString().trim()
                val updatedUserName = username.text.toString().trim()
                val updatedEmail = email.text.toString().trim()
                val updatedManager = managerName.text.toString().trim()
                val updatedContact = contact.text.toString().trim()

                if (updatedCompanyName.isEmpty() || updatedUserName.isEmpty() || updatedEmail.isEmpty()
                    || updatedManager.isEmpty() || updatedContact.isEmpty()) {
                    Toast.makeText(requireContext(), "Please make sure that all the required fields are not empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                companyProfile.companyName = updatedCompanyName
                companyProfile.companyUsername = updatedUserName
                companyProfile.companyEmail = updatedEmail
                companyProfile.managerName = updatedManager
                companyProfile.contactNo = updatedContact

                var database = Firebase.database.reference
                database.child("companies").child(viewModel.id).setValue(companyProfile)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}