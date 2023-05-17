package my.rjtechnology.apprenticestreet.ui.companyTrainee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyTraineeBinding
import my.rjtechnology.apprenticestreet.ui.adapters.traineeListAdapter

class CompanyTraineeFragment : Fragment() {
    private var _binding: FragmentCompanyTraineeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CompanyTraineeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyTraineeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment())[CompanyTraineeViewModel::class.java]
        binding.viewModel = viewModel
        //val addNewProgressButton = binding.addNewProgress
        viewModel.trainees.add(viewModel.trainee)
        viewModel.trainees.add(viewModel.trainee)
        val layoutManager = LinearLayoutManager(activity)
        var progressAdapter: traineeListAdapter = traineeListAdapter(viewLifecycleOwner,viewModel.trainees)

        binding.traineeRecycler.layoutManager = layoutManager
        binding.traineeRecycler.setHasFixedSize(true)
        binding.traineeRecycler.adapter = progressAdapter
        progressAdapter.onItemClick = {
            val navController = findNavController().navigate(R.id.action_navigation_company_trainee_to_traineeDetailFragment)
        }

        val fab = binding.traineeFloatingbutton

        fab.setOnClickListener {

            val navController = findNavController().navigate(R.id.action_navigation_company_trainee_to_nav_company_approval)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
