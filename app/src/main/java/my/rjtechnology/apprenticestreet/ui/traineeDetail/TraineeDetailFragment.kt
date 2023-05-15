package my.rjtechnology.apprenticestreet.ui.traineeDetail

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
import my.rjtechnology.apprenticestreet.databinding.FragmentTraineeDetailBinding
import my.rjtechnology.apprenticestreet.ui.adapters.CompanyApprovalAdapter
import my.rjtechnology.apprenticestreet.ui.adapters.TraineeDetailAdapter
import my.rjtechnology.apprenticestreet.ui.adapters.traineeListAdapter
import my.rjtechnology.apprenticestreet.ui.companyApproval.CompanyApprovalViewModel


class TraineeDetailFragment : Fragment() {
    private lateinit var viewModel: TraineeDetailViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTraineeDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireParentFragment())[TraineeDetailViewModel::class.java]
        binding.viewModel=viewModel

        val layoutManager = LinearLayoutManager(activity)

        var progressAdapter: TraineeDetailAdapter = TraineeDetailAdapter(viewLifecycleOwner,viewModel.traneeDetailList)

        binding.traineeDetailRecycle.layoutManager = layoutManager
        binding.traineeDetailRecycle.setHasFixedSize(true)
        binding.traineeDetailRecycle.adapter = progressAdapter






        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}