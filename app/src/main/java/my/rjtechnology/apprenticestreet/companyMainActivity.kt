package my.rjtechnology.apprenticestreet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.rjtechnology.apprenticestreet.databinding.ActivityCompanyMainBinding

class companyMainActivity : AppCompatActivity() {
    private lateinit var viewModel: CompanyMainActivityViewModel

    private lateinit var binding: ActivityCompanyMainBinding
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding = ActivityCompanyMainBinding.inflate(layoutInflater)
                setContentView(binding.root)
                viewModel = ViewModelProvider(this).get(CompanyMainActivityViewModel::class.java)
                val id = intent.getStringExtra("id")
                viewModel.id.value = id

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