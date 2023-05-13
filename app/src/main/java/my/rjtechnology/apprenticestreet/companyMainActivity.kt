package my.rjtechnology.apprenticestreet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.rjtechnology.apprenticestreet.databinding.ActivityCompanyMainBinding
import my.rjtechnology.apprenticestreet.databinding.ActivityMainBinding

class companyMainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCompanyMainBinding
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding = ActivityCompanyMainBinding.inflate(layoutInflater)
                setContentView(binding.root)

                val navView: BottomNavigationView = binding.companyNavView

                val navController = findNavController(R.id.company_nav_host_fragment_activity_main)
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.navigation_company_home,
                        R.id.navigation_company_trainee,
                        R.id.navigation_company_help,
                        R.id.navigation_company_joblist
                    )
                )
                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)


    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            findNavController(R.id.company_nav_host_fragment_activity_main).navigateUp()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}