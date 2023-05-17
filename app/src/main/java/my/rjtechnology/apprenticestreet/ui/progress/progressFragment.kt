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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import my.rjtechnology.apprenticestreet.Constants
import my.rjtechnology.apprenticestreet.LoginActivity
import my.rjtechnology.apprenticestreet.MainViewModel
import my.rjtechnology.apprenticestreet.databinding.FragmentProgressBinding
import my.rjtechnology.apprenticestreet.models.AppiledProgress
import my.rjtechnology.apprenticestreet.models.JobApplication
import my.rjtechnology.apprenticestreet.models.LearningOutcome

import my.rjtechnology.apprenticestreet.ui.adapters.AppiledCompanyProgressAdapter
import my.rjtechnology.apprenticestreet.ui.adapters.HaveJobAdapter
import my.rjtechnology.apprenticestreet.ui.adapters.traineeListAdapter
import my.rjtechnology.apprenticestreet.ui.searchjob.SearchJobViewModelFactory


class progressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private lateinit var viewModel: ProgressViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val apyJob = ArrayList<AppiledProgress>()
    var accepted = false;
   // var id:String =""
    private lateinit var mViewModel: MainViewModel


    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        viewModel = ViewModelProvider(requireParentFragment())[ProgressViewModel::class.java]
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val root: View = binding.root
        //Toast.makeText(requireContext(), viewModel.id, Toast.LENGTH_SHORT).show()

        var progressAdapter:AppiledCompanyProgressAdapter  = AppiledCompanyProgressAdapter(viewLifecycleOwner,viewModel.appliedJobList)
        var progressAdapter1:HaveJobAdapter  = HaveJobAdapter(viewLifecycleOwner,viewModel.learningOutcome,requireContext())
        if(!viewModel.haveJob)
        {

            val companyRecycler: RecyclerView = binding.comapanyRecycle
            val layoutManager = LinearLayoutManager(activity)

           // Toast.makeText(context,id, Toast.LENGTH_SHORT).show()
//

            companyRecycler.layoutManager = layoutManager
            companyRecycler.setHasFixedSize(true)
            companyRecycler.adapter = progressAdapter
            progressAdapter.onItemClick = {
                Toast.makeText(context,"asd", Toast.LENGTH_SHORT).show()
            }
//
//
            progressAdapter.onDeleteClick = {
                var a = progressAdapter.pos
                val builder = AlertDialog.Builder(requireActivity() as Context)
                builder.setTitle("Confirmation")
                builder.setMessage("Are you sure you want to perform this action?")
                builder.setPositiveButton("Yes") { dialog, which ->

                    Toast.makeText(context, a.toString(), Toast.LENGTH_SHORT).show()
                    viewModel.appliedJobList.removeAt(a)
                    val ref = FirebaseDatabase.getInstance().getReference("jobApplications").child(viewModel.id).child(it.companyId)

                    ref.removeValue()
                    progressAdapter.notifyDataSetChanged()
                }
                builder.setNegativeButton("No") { dialog, which ->

                }
                builder.show()
                //companyRecycler.adapter=progressAdapter
            }

        }
        else
        {
            val companyRecycler: RecyclerView = binding.comapanyRecycle
            val layoutManager = LinearLayoutManager(activity)
            val learningOutcome= LearningOutcome()
            learningOutcome.desc = "asdasd"
            viewModel.learningOutcome.add(learningOutcome)
            learningOutcome.desc ="asddddsss"
            viewModel.learningOutcome.add(learningOutcome)
            // Toast.makeText(context,id, Toast.LENGTH_SHORT).show()
//

            companyRecycler.layoutManager = layoutManager
            companyRecycler.setHasFixedSize(true)
            companyRecycler.adapter = progressAdapter1


        }
        mViewModel.id.observe(viewLifecycleOwner) { id ->
            // 在此处使用获取到的 ID 值进行后续操作
            viewModel.id = id
            read(progressAdapter)

        }
        //viewModel.id.value

        return root
    }

    fun read(progressAdapter:AppiledCompanyProgressAdapter)
    {
        viewModel.appliedJobList.clear()
        Firebase.database.reference
            .child("jobApplications").child(viewModel.id)
            .get()
            .addOnSuccessListener { snapshot ->
                val jobApplication = snapshot.children.map { child -> child.getValue<JobApplication>()!! }
                for(jobApp in jobApplication )
                {
                    var a = AppiledProgress()

                    a.companyName = jobApp.userId
                    a.companyId = jobApp.jobId
                    a.status = jobApp.status

                    viewModel.appliedJobList.add(a)
                }
                progressAdapter.notifyDataSetChanged()
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}