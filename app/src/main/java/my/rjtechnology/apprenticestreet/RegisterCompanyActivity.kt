package my.rjtechnology.apprenticestreet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import my.rjtechnology.apprenticestreet.LoginActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterCompanyActivity : AppCompatActivity() {

    private lateinit var companyNameInput: TextInputLayout
    private lateinit var managerNameInput: TextInputLayout
    private lateinit var contactNoInput: TextInputLayout
    private lateinit var emailInput: TextInputLayout
    private lateinit var usernameInput: TextInputLayout
    private lateinit var passwordInput: TextInputLayout
    private lateinit var confirmPasswordInput: TextInputLayout

    private lateinit var companyNameEditText: TextInputEditText
    private lateinit var managerNameEditText: TextInputEditText
    private lateinit var contactNoEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText

    private lateinit var submitButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_company)

        companyNameInput = findViewById(R.id.til_company_name)
        managerNameInput = findViewById(R.id.til_manager_name)
        contactNoInput = findViewById(R.id.til_contact_no)
        emailInput = findViewById(R.id.til_company_email)
        usernameInput = findViewById(R.id.til_company_username)
        passwordInput = findViewById(R.id.til_company_password)
        confirmPasswordInput = findViewById(R.id.til_company_confirm_password)

        companyNameEditText = findViewById(R.id.et_company_name)
        managerNameEditText = findViewById(R.id.et_manager_name)
        contactNoEditText = findViewById(R.id.et_contact_no)
        emailEditText = findViewById(R.id.et_company_email)
        usernameEditText = findViewById(R.id.et_company_username)
        passwordEditText = findViewById(R.id.et_company_password)
        confirmPasswordEditText = findViewById(R.id.et_confirm_company_password)

        submitButton = findViewById(R.id.register_company_btn)

        submitButton.setOnClickListener {
            val company = Company(
                generateIdCompany(),
                "Company",
                companyNameEditText.text.toString(),
                managerNameEditText.text.toString(),
                contactNoEditText.text.toString(),
                emailEditText.text.toString(),
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
            var database = Firebase.database.reference
            database.child("companies").child(company.id).setValue(company
            )

            val intent = Intent(this@RegisterCompanyActivity, LoginActivity::class.java)
            startActivity(intent)

            // Save the company data to database or perform other actions
        }
    }

    private fun generateIdCompany(): String {
        // Generate a unique ID for the company, e.g. using UUID.randomUUID().toString()
        // You can customize the ID generation logic to suit your needs
        val prefix = "C"
        val randomDigits = (0..99999999).random().toString().padStart(8, '0')
        val generatedId = prefix + randomDigits

        return generatedId
    }
}

data class Company(
    val id: String,
    val status: String,
    val companyName: String,
    val managerName: String,
    val contactNo: String,
    val companyEmail: String,
    val companyUsername: String,
    val companyPassword: String
)
