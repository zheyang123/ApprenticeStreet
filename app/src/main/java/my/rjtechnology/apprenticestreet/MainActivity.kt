package my.rjtechnology.apprenticestreet

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.rjtechnology.apprenticestreet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//test
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_progress,
                R.id.navigation_dashboard,
                R.id.navigation_search_job,
                R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        var database = Firebase.database.reference
        //var myRef = database.getReference("message")
        database.child("users").child("userId1").setValue("zxc")
        database.child("users").child("userId2").setValue("xc1")

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}