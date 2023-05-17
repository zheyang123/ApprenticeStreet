package my.rjtechnology.apprenticestreet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.RegisterCompanyActivity
import my.rjtechnology.apprenticestreet.RegisterUserActivity



class SelectUserActivity : AppCompatActivity() {
    private var userTypeRadioGroup: RadioGroup? = null
    private var signUpButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user)

        userTypeRadioGroup = findViewById<RadioGroup>(R.id.user_type_radio_group)
        signUpButton = findViewById<Button>(R.id.signup_button)

        signUpButton?.setOnClickListener {
            val selectedRadioButtonId = userTypeRadioGroup!!.checkedRadioButtonId
            if (selectedRadioButtonId == R.id.worker_radio_button) {
                val intent = Intent(this@SelectUserActivity, RegisterUserActivity::class.java)
                startActivity(intent)
            } else if (selectedRadioButtonId == R.id.company_radio_button) {
                val intent = Intent(this@SelectUserActivity, RegisterCompanyActivity::class.java)
                startActivity(intent)
            }
        }
    }
}