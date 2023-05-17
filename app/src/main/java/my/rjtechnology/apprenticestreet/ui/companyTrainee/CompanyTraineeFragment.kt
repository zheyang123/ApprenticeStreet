package my.rjtechnology.apprenticestreet.ui.companyTrainee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.rjtechnology.apprenticestreet.CompanyMainActivityViewModel
import my.rjtechnology.apprenticestreet.MainViewModel
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyTraineeBinding
import my.rjtechnology.apprenticestreet.ui.adapters.traineeListAdapter

class CompanyTraineeFragment : Fragment() {
    private var _binding: FragmentCompanyTraineeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CompanyTraineeViewModel
    private lateinit var mViewModel: CompanyMainActivityViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyTraineeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment())[CompanyTraineeViewModel::class.java]
        binding.viewModel = viewModel
        mViewModel = ViewModelProvider(requireActivity()).get(CompanyMainActivityViewModel::class.java)
        //val addNewProgressButton = binding.addNewProgress
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
        mViewModel.id.observe(viewLifecycleOwner) { id ->
            // 在此处使用获取到的 ID 值进行后续操作
            viewModel.id = id

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
