package my.rjtechnology.apprenticestreet


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var emailOrUsername: EditText
    private lateinit var password: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fun getAll(scope: CoroutineScope, nextKey: String = "", onDone: (String) -> Unit = {}) {
            Firebase.database.reference
                .child("users")
                .orderByKey()
                .get()
                .addOnSuccessListener { snapshot ->
                    scope.launch {
                        val users = snapshot.children.map { child -> child.getValue<User>()!! }
                        loginButton.setOnClickListener{
                            lifecycleScope.launch { AppDatabase.get(this@LoginActivity).loginDao().clearUser()}
                            lifecycleScope.launch { AppDatabase.get(this@LoginActivity).loginDao().clearCompany()}
                            val emailOrUsernameText = emailOrUsername.text.toString().trim()
                            val passwordText = password.text.toString().trim()
                            var find:Boolean = false

                            for(user in users){
                                if (user.email == emailOrUsernameText && user.password == passwordText){
                                    Toast.makeText(this@LoginActivity, user.status, Toast.LENGTH_SHORT).show()
                                    find = true
                                    if (user.status == "User")
                                    {
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java).also {
                                            it.putExtra("id",user.id)
                                            startActivity(it)
                                        }

                                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                                        lifecycleScope.launch { AppDatabase.get(this@LoginActivity).loginDao().insert(user)}
                                        break
                                    }else
                                    {
                                        Toast.makeText(this@LoginActivity, "Login Invalid", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            if(!find) {
                                Firebase.database.reference
                                    .child("companies")
                                    .orderByKey()
                                    .get()
                                    .addOnSuccessListener { snapshot ->
                                        val companies = snapshot.children.map { child -> child.getValue<Company>()!! }
                                        for (company in companies) {
                                            if (company.companyEmail == emailOrUsernameText && company.companyPassword == passwordText) {
                                                if (company.status == "Company") {
                                                    val intent2 = Intent(this@LoginActivity, companyMainActivity::class.java).also {it.putExtra("id",company.id)
                                                        startActivity(it)
                                                    }

                                                    Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                                                    lifecycleScope.launch { AppDatabase.get(this@LoginActivity).loginDao().insert(company) }
                                                    break
                                                }else
                                                {
                                                    Toast.makeText(this@LoginActivity, "Login Invalid", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error fetching data", Toast.LENGTH_LONG)
                        .show()
                }
        }

        emailOrUsername = findViewById<EditText>(R.id.et_email)
        password = findViewById<EditText>(R.id.et_password)
        loginButton = findViewById<Button>(R.id.login_btn)
        registerButton = findViewById<Button>(R.id.register_button)

        getAll(CoroutineScope(coroutineContext))

        registerButton.setOnClickListener{
            val intent = Intent(this@LoginActivity, SelectUserActivity::class.java)
            startActivity(intent)

        }

    }

    @Entity(tableName = "user") data class User(
        @PrimaryKey val id: String,
        val status:String,
        val email: String,
        val password: String,
        val fullName: String,
        val username: String,
    )
    {
        constructor() : this("", "", "", "", "", "")
    }

    @Entity(tableName = "company") data class Company(
       @PrimaryKey val id: String,
        val status:String,
        val companyEmail: String,
        val companyPassword: String,
        val companyName: String,
        val managerName: String,
        val contactNo: String,
        val companyUsername: String,
    )
    {
        constructor() : this("", "", "", "", "", "", "", "")
    }


}
