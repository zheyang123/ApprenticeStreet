package my.rjtechnology.apprenticestreet.ui.searchjob

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.Constants
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentSearchJobBinding
import my.rjtechnology.apprenticestreet.ui.adapters.JobAdapter

class SearchJobFragment : Fragment() {
    private lateinit var viewModel: SearchJobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchJobBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            requireParentFragment(),
            SearchJobViewModelFactory(
                requireActivity().application,
                requireActivity()
                    .getPreferences(Context.MODE_PRIVATE)
                    .getString(Constants.NEXT_JOB_ID_KEY, "")
                    .toString(),
                onDoing = {
                    binding.jobListContainer.isRefreshing = true
                },
                onDone = {
                    binding.jobListContainer.isRefreshing = false
                }
            )
        )[SearchJobViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        val adapter = JobAdapter()
        adapter.setHasStableIds(true)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.jobList.layoutManager = layoutManager
        binding.jobList.adapter = adapter
        binding.jobList.setHasFixedSize(true)

        viewModel.query.observe(viewLifecycleOwner) {
            viewModel.syncFilteredJobs()
        }

        viewModel.industries.observe(viewLifecycleOwner) {
            viewModel.syncFilteredJobs()
        }

        viewModel.locations.observe(viewLifecycleOwner) {
            viewModel.syncFilteredJobs()
        }

        viewModel.minSalaryPerMonth.observe(viewLifecycleOwner) {
            viewModel.syncFilteredJobs()
        }

        viewModel.jobRepo.allJobs.observe(viewLifecycleOwner) {
            viewModel.syncFilteredJobs()
        }

        viewModel.filteredJobs.observe(viewLifecycleOwner) {
            val state = binding.jobList.layoutManager?.onSaveInstanceState()

            adapter.submitList(it) {
                binding.jobList.layoutManager?.onRestoreInstanceState(state)
            }
        }

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
        
        binding.jobListContainer.setOnRefreshListener {
            viewModel.refreshJobs {
                binding.jobListContainer.isRefreshing = false
            }
        }

        binding.jobList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val index = layoutManager.findLastVisibleItemPosition()

                if (index != RecyclerView.NO_POSITION && dy > 0 && index == adapter.itemCount - 1) {
                    binding.jobListContainer.isRefreshing = true

                    viewModel.getNextJobs {
                        binding.jobListContainer.isRefreshing = false
                    }
                }
            }
        })

        navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<List<String>>(Constants.INDUSTRIES_KEY)
            ?.observe(viewLifecycleOwner) {
                viewModel.industries.value = it
            }

        navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<List<String>>(Constants.LOCATIONS_KEY)
            ?.observe(viewLifecycleOwner) {
                viewModel.locations.value = it
            }

        navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Int>(Constants.MIN_SALARY_PER_MONTH_KEY)
            ?.observe(viewLifecycleOwner) {
                viewModel.minSalaryPerMonth.value = it
            }

        adapter.onItemClick= {
          // Toast.makeText(context,adapter.index.toString(), Toast.LENGTH_SHORT).show()

        }
        return binding.root
    }

    override fun onStop() {
        super.onStop()

        requireActivity().getPreferences(Context.MODE_PRIVATE)
            .edit()
            .putString(Constants.NEXT_JOB_ID_KEY, viewModel.nextJobKey)
            .apply()
    }
}
