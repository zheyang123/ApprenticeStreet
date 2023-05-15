package my.rjtechnology.apprenticestreet.ui.companyApproval

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyApprovalBinding
import my.rjtechnology.apprenticestreet.databinding.FragmentDashboardBinding
import my.rjtechnology.apprenticestreet.ui.adapters.CompanyApprovalAdapter
import my.rjtechnology.apprenticestreet.ui.adapters.newProgressAdapter
import my.rjtechnology.apprenticestreet.ui.companyTrainee.CompanyTraineeViewModel


class CompanyApprovalFragment : Fragment() {
    private lateinit var viewModel:CompanyApprovalViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCompanyApprovalBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireParentFragment())[CompanyApprovalViewModel::class.java]
        binding.viewModel=viewModel


        val layoutManager = LinearLayoutManager(activity)

        var progressAdapter: CompanyApprovalAdapter = CompanyApprovalAdapter(viewLifecycleOwner,viewModel.approvalList)

        binding.companyApproveReclcyer.layoutManager = layoutManager
        binding.companyApproveReclcyer.setHasFixedSize(true)
        binding.companyApproveReclcyer.adapter = progressAdapter



        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}