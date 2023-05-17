package my.rjtechnology.apprenticestreet.ui.companyHome

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyHomeBinding
import my.rjtechnology.apprenticestreet.models.CompanyProfile

class CompanyHomeFragment : Fragment() {
    var companyProfile = CompanyProfile()

    private var _binding: FragmentCompanyHomeBinding? = null
    private val binding get() = _binding!!

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
                name.isEnabled = true
                email.isEnabled = true
                managerName.isEnabled = true
                contact.isEnabled = true
                address.isEnabled = true

                name.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                email.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                managerName.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                contact.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                address.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            } else {
                // Save profile changes and switch back to view mode
                editProfileButton.text = "Edit Profile"

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
            }
        }

        logoutButton.setOnClickListener {
            // Perform logout actions here
            // For example, you can clear user session or navigate to the login screen

            // Clear user session and navigate to login screen
            // Replace LoginActivity::class.java with your actual login activity
            /*val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            requireActivity().finish()*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}