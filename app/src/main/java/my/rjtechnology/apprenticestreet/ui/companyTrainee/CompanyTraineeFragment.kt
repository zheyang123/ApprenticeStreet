package my.rjtechnology.apprenticestreet.ui.companyTrainee

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyJobListBinding
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyTraineeBinding
import my.rjtechnology.apprenticestreet.models.companyTrainee
import my.rjtechnology.apprenticestreet.ui.adapters.newProgressAdapter
import my.rjtechnology.apprenticestreet.ui.adapters.traineeListAdapter
import my.rjtechnology.apprenticestreet.ui.companyJobList.CompanyJobListViewModel


class CompanyTraineeFragment : Fragment() {
    private lateinit var viewModel:CompanyTraineeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCompanyTraineeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireParentFragment())[CompanyTraineeViewModel::class.java]
        binding.viewModel=viewModel
        //val addNewProgressButton = binding.addNewProgress
        viewModel.trainees.add(viewModel.trainee)
        viewModel.trainees.add(viewModel.trainee)
        val layoutManager = LinearLayoutManager(activity)
        var progressAdapter: traineeListAdapter = traineeListAdapter(viewLifecycleOwner,viewModel.trainees)

        binding.traineeRecycler.layoutManager = layoutManager
        binding.traineeRecycler.setHasFixedSize(true)
        binding.traineeRecycler.adapter = progressAdapter


        val fab = binding.traineeFloatingbutton

        fab.setOnClickListener {
            val navController = findNavController().navigate(R.id.action_navigation_company_joblist_to_nav_postJob)
        }
        return binding.root
        // Inflate the layout for this fragment

    }


}