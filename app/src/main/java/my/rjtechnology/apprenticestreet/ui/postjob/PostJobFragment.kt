package my.rjtechnology.apprenticestreet.ui.postjob

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.rjtechnology.apprenticestreet.Constants
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentPostJobBinding
import my.rjtechnology.apprenticestreet.ui.adapters.FilterlessArrayAdapter

class PostJobFragment : Fragment() {
    private var _binding: FragmentPostJobBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostJobBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[PostJobViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.industryFilledExposedDropdown.setAdapter(
            FilterlessArrayAdapter(
                requireContext(),
                R.layout.item_dropdown_menu_popup,
                arrayOf(
                    "Art & Media",
                    "Construction",
                    "Engineering",
                    "Healthcare",
                    "Hotel",
                    "Information Technology",
                    "Manufacturing",
                    "Restaurant",
                    "Services",
                )
            )
        )

        binding.locationFilledExposedDropdown.setAdapter(
            FilterlessArrayAdapter(
                requireContext(),
                R.layout.item_dropdown_menu_popup,
                arrayOf(
                    resources.getString(R.string.johor),
                    resources.getString(R.string.kedah),
                    resources.getString(R.string.kelantan),
                    resources.getString(R.string.kuala_lumpur),
                    resources.getString(R.string.melaka),
                    resources.getString(R.string.negeri_sembilan),
                    resources.getString(R.string.pahang),
                    resources.getString(R.string.penang),
                    resources.getString(R.string.perak),
                    resources.getString(R.string.perlis),
                    resources.getString(R.string.sabah),
                    resources.getString(R.string.sarawak),
                    resources.getString(R.string.selangor),
                    resources.getString(R.string.terangganu),
                )
            )
        )

        binding.jobDesc.setOnClickListener {
            findNavController().navigate(R.id.action_nav_postJob_to_nav_job_desc)
        }

        binding.learningOutcome.setOnClickListener {
            findNavController().navigate(R.id.action_nav_postJob_to_nav_learning_outcome)
        }

        binding.submit.setOnClickListener {
            // Validations
            if (viewModel.jobTitle.trim().isEmpty()) {
                binding.jobTitle.error = getString(R.string.empty_field_err_msg)
                return@setOnClickListener
            } else binding.jobTitle.error = null

            if (viewModel.industry.isEmpty()) {
                binding.industry.error = getString(R.string.empty_field_err_msg)
                return@setOnClickListener
            } else binding.industry.error = null

            if (viewModel.location.isEmpty()) {
                binding.location.error = getString(R.string.empty_field_err_msg)
                return@setOnClickListener
            } else binding.location.error = null

            if (viewModel.showSalaries.value == true) {
                val minSalary = viewModel.minSalary.toIntOrNull()
                val maxSalary = viewModel.maxSalary.toIntOrNull()

                if (minSalary == null) {
                    binding.minSalary.error = getString(R.string.empty_field_err_msg)
                    return@setOnClickListener
                } else binding.minSalary.error = null

                if (maxSalary == null) {
                    binding.maxSalary.error = getString(R.string.empty_field_err_msg)
                    return@setOnClickListener
                } else binding.maxSalary.error = null

                if (minSalary > maxSalary) {
                    binding.minSalary.error = getString(R.string.invalid_salaries_err_msg)
                    binding.maxSalary.error = getString(R.string.invalid_salaries_err_msg)
                    return@setOnClickListener
                } else {
                    binding.minSalary.error = null
                    binding.maxSalary.error = null
                }
            }

            if (viewModel.jobDesc.value!!.trim().isEmpty()) {
                binding.jobDescErrorIcon.visibility = View.VISIBLE

                Toast.makeText(
                    requireContext(),
                    getString(R.string.empty_job_desc_err_msg),
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            } else binding.jobDescErrorIcon.visibility = View.GONE

            if (viewModel.learningOutcome.value!!.isEmpty()) {
                binding.learningOutcomeErrorIcon.visibility = View.VISIBLE

                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_learning_outcome_err_msg),
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            } else binding.learningOutcomeErrorIcon.visibility = View.GONE

            // Commit

            val a = viewModel.jobTitle
            val b = viewModel.location
            val c = viewModel.showSalaries.value
            val d = viewModel.minSalary
            val e = viewModel.maxSalary
            val f = viewModel.jobDesc.value
        }

        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(Constants.JOB_DESC_KEY)
            ?.observe(viewLifecycleOwner) {
                viewModel.jobDesc.value = it
            }

        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<List<String>>(Constants.LEARNING_OUTCOME_KEY)
            ?.observe(viewLifecycleOwner) {
                viewModel.learningOutcome.value = it
            }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
