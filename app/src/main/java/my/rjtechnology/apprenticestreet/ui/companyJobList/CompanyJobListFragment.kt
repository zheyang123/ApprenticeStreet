package my.rjtechnology.apprenticestreet.ui.companyJobList

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
import my.rjtechnology.apprenticestreet.ui.adapters.newProgressAdapter

class CompanyJobListFragment : Fragment() {


    private lateinit var viewModel:CompanyJobListViewModel
    private var _binding: FragmentCompanyJobListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyJobListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment())[CompanyJobListViewModel::class.java]
        binding.viewModel = viewModel
        //val addNewProgressButton = binding.addNewProgress

        val layoutManager = LinearLayoutManager(activity)

        var progressAdapter: newProgressAdapter = newProgressAdapter(viewLifecycleOwner,viewModel.joblist)

        binding.companyJobRecycler.layoutManager = layoutManager
        binding.companyJobRecycler.setHasFixedSize(true)
        binding.companyJobRecycler.adapter = progressAdapter

        progressAdapter.onDeleteClick={
            val a = progressAdapter.pos
            val builder = AlertDialog.Builder(requireActivity() as Context)
            builder.setTitle("Confirmation")
            builder.setMessage("Are you sure you want to perform this action?")
            builder.setPositiveButton("Yes") { dialog, which ->
               // Toast.makeText(context, a.toString(), Toast.LENGTH_SHORT).show()
                viewModel.joblist.removeAt(a)
                progressAdapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("No") { dialog, which ->
                // 用户选择了 No，什么也不做
            }
            builder.show()
        }
        val fab = binding.floatingActionButton

        fab.setOnClickListener {
            val navController = findNavController().navigate(R.id.action_navigation_company_joblist_to_nav_postJob)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
