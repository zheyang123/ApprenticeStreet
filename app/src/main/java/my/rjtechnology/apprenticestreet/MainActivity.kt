package my.rjtechnology.apprenticestreet

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.rjtechnology.apprenticestreet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//     val changePage = Intent(this, companyMainActivity::class.java)
//     startActivity(changePage)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val id = intent.getStringExtra("id")
        viewModel.id.value = id



        val navController = findNavController(R.id.nav_host_fragment_activity_main)

      //  navController.setGraph(navController.graph, bundle)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_progress,
                R.id.navigation_dashboard,
                R.id.navigation_search_job,
                R.id.navigation_notifications,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}

