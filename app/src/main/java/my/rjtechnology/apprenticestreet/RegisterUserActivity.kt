package my.rjtechnology.apprenticestreet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import my.rjtechnology.apprenticestreet.LoginActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.rjtechnology.apprenticestreet.R


class RegisterUserActivity : AppCompatActivity() {

    private lateinit var fullNameInput: TextInputLayout
    private lateinit var emailInput: TextInputLayout
    private lateinit var usernameInput: TextInputLayout
    private lateinit var passwordInput: TextInputLayout
    private lateinit var confirmPasswordInput: TextInputLayout

    private lateinit var fullNameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText

    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        fullNameInput = findViewById(R.id.til_fullname)
        emailInput = findViewById(R.id.til_email)
        usernameInput = findViewById(R.id.til_username)
        passwordInput = findViewById(R.id.til_password)
        confirmPasswordInput = findViewById(R.id.til_confirm_password)

        fullNameEditText = findViewById(R.id.et_fullname)
        emailEditText = findViewById(R.id.et_email)
        usernameEditText = findViewById(R.id.et_username)
        passwordEditText = findViewById(R.id.et_password)
        confirmPasswordEditText = findViewById(R.id.et_confirm_password)

        submitButton = findViewById(R.id.register_btn)
        submitButton.setOnClickListener {
            val user = User(
                generateIdUser(),
                "User",
                fullNameEditText.text.toString(),
                emailEditText.text.toString(),
                usernameEditText.text.toString(),
                passwordEditText.text.toString(),
                false
            )
            // save the user data to database or perform other actions
            var database = Firebase.database.reference
            database.child("users").child(user.id).setValue(user
            )
            val intent = Intent(this@RegisterUserActivity, LoginActivity::class.java)
            startActivity(intent)

        }
    }

    private fun generateIdUser(): String {
        // generate a unique ID for the user, e.g. using UUID.randomUUID().toString()
        // you can customize the ID generation logic to suit your needs
        val prefix = "U"
        val randomDigits = (0..99999999).random().toString().padStart(8, '0')
        val generatedId = prefix + randomDigits

        // check if the ID already exists
        // You will need to implement your own logic here, depending on how you're storing user data
        // For example, you could check if the ID already exists in a database table
        // If it does, generate a new ID until you find one that doesn't exist yet
        // Once you find a unique ID, return it

        return generatedId
    }
}

data class User(
    val id: String,
    val status: String,
    val fullName: String,
    val email: String,
    val username: String,
    val password: String,
    val profileOk: Boolean
)
