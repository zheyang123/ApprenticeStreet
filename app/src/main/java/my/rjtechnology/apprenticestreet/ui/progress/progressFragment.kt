package my.rjtechnology.apprenticestreet.ui.progress

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.databinding.FragmentProgressBinding
import my.rjtechnology.apprenticestreet.models.AppiledProgress
import my.rjtechnology.apprenticestreet.ui.adapters.AppiledCompanyProgressAdapter

class progressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private lateinit var viewModel: ProgressViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val progressViewModel =
            ViewModelProvider(this).get(ProgressViewModel::class.java)
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(requireParentFragment())[ProgressViewModel::class.java]

        if(!viewModel.haveJob)
        {

            val companyRecycler: RecyclerView = binding.comapanyRecycle
            val layoutManager = LinearLayoutManager(activity)
            val trainee1 = AppiledProgress()
            val trainee2 = AppiledProgress()
            trainee1.companyName="asd"
            trainee2.companyName="sssssss"
            trainee2.status="rejected"
            viewModel.appliedJobList.add(trainee1)
            viewModel.appliedJobList.add(trainee2)
            var progressAdapter: AppiledCompanyProgressAdapter= AppiledCompanyProgressAdapter(viewLifecycleOwner, viewModel.appliedJobList)

            companyRecycler.layoutManager = layoutManager
            companyRecycler.setHasFixedSize(true)
            companyRecycler.adapter = progressAdapter
            progressAdapter.onItemClick = {
                Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show()
            }
            progressAdapter.onDeleteClick = {
                var a = progressAdapter.pos
                val builder = AlertDialog.Builder(requireActivity() as Context)
                builder.setTitle("Confirmation")
                builder.setMessage("Are you sure you want to perform this action?")
                builder.setPositiveButton("Yes") { dialog, which ->

                    Toast.makeText(context, a.toString(), Toast.LENGTH_SHORT).show()
                    viewModel.appliedJobList.removeAt(a)
                    progressAdapter.notifyDataSetChanged()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    // 用户选择了 No，什么也不做
                }
                builder.show()


                //companyRecycler.adapter=progressAdapter
            }

        }
        else
        {

        }
        // val textView: TextView = binding.textHome

        progressViewModel.text.observe(viewLifecycleOwner) {
            // textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}