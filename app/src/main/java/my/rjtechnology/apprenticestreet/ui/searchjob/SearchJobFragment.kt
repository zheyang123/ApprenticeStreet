package my.rjtechnology.apprenticestreet.ui.searchjob

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.rjtechnology.apprenticestreet.Constants
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentSearchJobBinding
import my.rjtechnology.apprenticestreet.ui.adapters.JobAdapter

class SearchJobFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchJobBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(requireParentFragment())[SearchJobViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.jobList.adapter = JobAdapter()

        val navController = findNavController()

        binding.industriesButton.setOnClickListener {
            navController.navigate(R.id.action_navigation_search_job_to_navigation_industries)
        }

        binding.locationsButton.setOnClickListener {
            navController.navigate(R.id.action_navigation_search_job_to_navigation_locations)
        }

        binding.salariesButton.setOnClickListener {
            navController.navigate(R.id.action_navigation_search_job_to_navigation_salaries)
        }

        navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Int>(Constants.INDUSTRY_COUNT_KEY)
            ?.observe(viewLifecycleOwner) {
                viewModel.industryCount.value = it
            }

        navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Int>(Constants.LOCATION_COUNT_KEY)
            ?.observe(viewLifecycleOwner) {
                viewModel.locationCount.value = it
            }

        navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Int>(Constants.MIN_SALARY_PER_MONTH_KEY)
            ?.observe(viewLifecycleOwner) {
                viewModel.minSalaryPerMonth.value = it
            }

        return binding.root
    }
}
