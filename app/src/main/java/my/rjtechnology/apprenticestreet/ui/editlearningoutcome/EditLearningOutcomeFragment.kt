package my.rjtechnology.apprenticestreet.ui.editlearningoutcome

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import my.rjtechnology.apprenticestreet.databinding.FragmentEditLearningOutcomeBinding
import my.rjtechnology.apprenticestreet.ui.adapters.AppiledCompanyProgressAdapter
import my.rjtechnology.apprenticestreet.ui.adapters.newProgressAdapter
import my.rjtechnology.apprenticestreet.ui.progress.ProgressViewModel

class EditLearningOutcomeFragment : Fragment() {
    private lateinit var viewModel: EditLearningOutcomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditLearningOutcomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment())[EditLearningOutcomeViewModel::class.java]
        binding.viewModel=viewModel
        val jobEditText =binding.jobEditText
        val addNewProgressButton = binding.addNewProgress

        val layoutManager = LinearLayoutManager(activity)

        var progressAdapter:newProgressAdapter = newProgressAdapter(viewLifecycleOwner,viewModel.progressList)

        binding.addProgressRecycler.layoutManager = layoutManager
        binding.addProgressRecycler.setHasFixedSize(true)
        binding.addProgressRecycler.adapter = progressAdapter
        progressAdapter.onItemClick = {
            Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show()
        }
            addNewProgressButton.setOnClickListener {
                val job = jobEditText.text.toString().trim()

                if (job.isEmpty()) {
                    jobEditText.error = "This field cannot be empty"
                    return@setOnClickListener
                }

                viewModel.progressList.add(job)
                progressAdapter.notifyDataSetChanged()
            }
        progressAdapter.onDeleteClick={
            val a = progressAdapter.pos
            val builder = AlertDialog.Builder(requireActivity() as Context)
            builder.setTitle("Confirmation")
            builder.setMessage("Are you sure you want to perform this action?")
            builder.setPositiveButton("Yes") { dialog, which ->
                Toast.makeText(context, a.toString(), Toast.LENGTH_SHORT).show()
                viewModel.progressList.removeAt(a)
                progressAdapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("No") { dialog, which ->
                // 用户选择了 No，什么也不做
            }
            builder.show()
        }

        return binding.root
    }
}
