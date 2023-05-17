package my.rjtechnology.apprenticestreet.ui.companyApproval

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import my.rjtechnology.apprenticestreet.CompanyMainActivityViewModel
import my.rjtechnology.apprenticestreet.MainViewModel
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyApprovalBinding
import my.rjtechnology.apprenticestreet.models.AppiledProgress
import my.rjtechnology.apprenticestreet.models.CompanyApproval
import my.rjtechnology.apprenticestreet.models.JobApplication
import my.rjtechnology.apprenticestreet.ui.adapters.AppiledCompanyProgressAdapter
import my.rjtechnology.apprenticestreet.ui.adapters.CompanyApprovalAdapter

class CompanyApprovalFragment : Fragment() {
    private var _binding: FragmentCompanyApprovalBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CompanyApprovalViewModel
    private lateinit var mViewModel: CompanyMainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyApprovalBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment())[CompanyApprovalViewModel::class.java]
        binding.viewModel = viewModel
        mViewModel = ViewModelProvider(requireActivity()).get(CompanyMainActivityViewModel::class.java)

        val layoutManager = LinearLayoutManager(activity)

        var progressAdapter: CompanyApprovalAdapter = CompanyApprovalAdapter(viewLifecycleOwner,viewModel.approvalList)

        binding.companyApproveReclcyer.layoutManager = layoutManager
        binding.companyApproveReclcyer.setHasFixedSize(true)
        binding.companyApproveReclcyer.adapter = progressAdapter

        mViewModel.id.observe(viewLifecycleOwner) { id ->
            // 在此处使用获取到的 ID 值进行后续操作
            viewModel.id = id
            //read(progressAdapter)
            Toast.makeText(requireContext(), viewModel.id, Toast.LENGTH_SHORT).show()

        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun read(progressAdapter: CompanyApprovalAdapter)
    {
        viewModel.approvalList.clear()
        Firebase.database.reference
            .child("jobApplications").child(viewModel.id)
            .get()
            .addOnSuccessListener { snapshot ->
                val jobApplication = snapshot.children.map { child -> child.getValue<JobApplication>()!! }
                for(jobApp in jobApplication )
                {
                    var a = CompanyApproval()

                    a.job = jobApp.jobId
                    a.traineeID = jobApp.userId
                    a.traineeName = jobApp.status

                    viewModel.approvalList.add(a)
                }
                progressAdapter.notifyDataSetChanged()
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
