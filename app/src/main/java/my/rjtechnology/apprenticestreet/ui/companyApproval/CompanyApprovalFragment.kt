package my.rjtechnology.apprenticestreet.ui.companyApproval

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import my.rjtechnology.apprenticestreet.CompanyMainActivityViewModel
import my.rjtechnology.apprenticestreet.MainViewModel
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyApprovalBinding
import my.rjtechnology.apprenticestreet.models.*
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

        progressAdapter.onApproveClick={
            var database = Firebase.database.reference
            database.child("companyTraineeList").child(viewModel.id).setValue(it.traineeID)
            var traineeJob = TraineeJob()
            traineeJob.jobName = it.job
            traineeJob.learningOutcome = it.learningOutcome
            var database1 = Firebase.database.reference
            database1.child("traineeJob").child(it.traineeID).setValue(traineeJob)
            viewModel.approvalList.removeAt(progressAdapter.pos)
            progressAdapter.notifyDataSetChanged()
            val ref = FirebaseDatabase.getInstance().getReference("jobApplications").child(viewModel.id).child(it.jobID)
            ref.removeValue()
            val ref1 = FirebaseDatabase.getInstance().getReference("jobApplications").child(it.traineeID).child(it.jobID)
            ref1.removeValue()
        }
        progressAdapter.onDelineClick={

            var database = Firebase.database.reference
            database.child("jobApplications").child(viewModel.id).child(it.jobID).child("status").setValue( "Rejected")
            var database1 = Firebase.database.reference
            database1.child("jobApplications").child(it.traineeID).child(it.jobID).child("status").setValue( "Rejected")
            viewModel.approvalList.removeAt(progressAdapter.pos)
            progressAdapter.notifyDataSetChanged()
        }
        mViewModel.id.observe(viewLifecycleOwner) { id ->

            // 在此处使用获取到的 ID 值进行后续操作
            viewModel.id = id
           // read(progressAdapter)
          // var a = readJobName("fe65c4c4-226e-47b8-b548-e7ef775fc14e")
            read(progressAdapter)
            //Toast.makeText(requireContext(), a, Toast.LENGTH_SHORT).show()

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
                val approvalList = snapshot.children.map { child -> child.getValue<JobApplication>()!! }
                for(apList in approvalList )
                {
                    var a = CompanyApproval()

                    a.traineeID = apList.userId
                    a.jobID=apList.jobId
                   readTraineeName(apList.userId,progressAdapter)
                    readJobName(apList.jobId,progressAdapter)
                   a.learningOutcome = readlearningOutcome(apList.jobId)
                    if(apList.status!="Rejected")
                    viewModel.approvalList.add(a)
                }
                progressAdapter.notifyDataSetChanged()
            }
    }

    fun readJobName(jobID:String,progressAdapter:CompanyApprovalAdapter) {
        var jobName = ""
       // viewModel.approvalList.clear()
        Firebase.database.reference.child("jobs").child(jobID).child("job")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val job = snapshot.getValue(Job::class.java)
                    // 在这里处理获取到的作业对象
                    if (job != null) {
                        var index = 0
                        jobName = job.title
                        for(job in viewModel.approvalList)
                        {
                            if(jobID==job.jobID)
                            {
                                viewModel.approvalList[index].job = jobName
                            }
                                index++
                        }
                        progressAdapter.notifyDataSetChanged() // 其他处理逻辑
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // 在这里处理取消事件的逻辑（如果需要）
                }
            })

    }
    fun readTraineeName(userID:String,progressAdapter:CompanyApprovalAdapter) {
        var traineeName = ""
        viewModel.approvalList.clear()
        Firebase.database.reference
            .child("users").child(userID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    // 在这里处理获取到的作业对象
                    if (user != null) {
                        var index = 0
                        traineeName = user.fullName
                        for(u in viewModel.approvalList)
                        {
                            if(userID==u.traineeID)
                            {
                                viewModel.approvalList[index].traineeName = traineeName
                            }
                            index++
                        }
                        progressAdapter.notifyDataSetChanged() // 其他处理逻辑
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // 在这里处理取消事件的逻辑（如果需要）
                }
            })

    }
    fun readlearningOutcome(jobID:String): ArrayList<LearningOutcome> {
        var learningOutcome = ArrayList<LearningOutcome>()
        // viewModel.approvalList.clear()
        Firebase.database.reference.child("jobs").child(jobID).child("learningOutcomes")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val learnOutcom = snapshot.getValue<ArrayList<LearningOutcome>>()
                    // 在这里处理获取到的作业对象
                    if (learnOutcom != null) {
                        var index = 0
                        learningOutcome = learnOutcom
                        for(u in viewModel.approvalList)
                        {
                            if(jobID==u.jobID)
                            {
                                viewModel.approvalList[index].learningOutcome = learningOutcome
                            }
                            index++
                        }

                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // 在这里处理取消事件的逻辑（如果需要）
                }
            })
        return learningOutcome
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
