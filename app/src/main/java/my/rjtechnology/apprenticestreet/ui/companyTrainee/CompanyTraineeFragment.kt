package my.rjtechnology.apprenticestreet.ui.companyTrainee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import my.rjtechnology.apprenticestreet.CompanyMainActivityViewModel
import my.rjtechnology.apprenticestreet.MainViewModel
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyTraineeBinding
import my.rjtechnology.apprenticestreet.models.*
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
            val bundle = Bundle().apply {
                putParcelable("trainee", it)
            }
            val navController = findNavController().navigate(R.id.action_navigation_company_trainee_to_traineeDetailFragment,bundle)
        }

        val fab = binding.traineeFloatingbutton

        fab.setOnClickListener {

            val navController = findNavController().navigate(R.id.action_navigation_company_trainee_to_nav_company_approval)
        }
        mViewModel.id.observe(viewLifecycleOwner) { id ->
            // 在此处使用获取到的 ID 值进行后续操作
            viewModel.id = id
            read(progressAdapter)

        }
        return binding.root
    }
fun read(progressAdapter:traineeListAdapter)
{
    viewModel.trainees.clear()
    Firebase.database.reference
        .child("companyTraineeList").child(viewModel.id)
        .get()
        .addOnSuccessListener { snapshot ->
            val usersID = snapshot.children.map { child -> child.getValue<String>()!! }
            for(userID in usersID )
            {
                var a = Trainee()

                a.id = userID
                readLearningOutcome(progressAdapter,userID)
                readUser(progressAdapter,userID)
                readAge(progressAdapter,userID)
                viewModel.trainees.add(a)
            }
            progressAdapter.notifyDataSetChanged()
        }
}
    fun readLearningOutcome(progressAdapter:traineeListAdapter,traineeID:String)
    {
        Firebase.database.reference
            .child("traineeJob").child(traineeID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val traineeJob = snapshot.getValue(TraineeJob::class.java)

                    if (traineeJob != null) {
                        var index = 0

                        for(u in viewModel.trainees)
                        {
                            if(u.id==traineeID)
                            {
                                viewModel.trainees[index].learningOutcome= traineeJob.learningOutcome
                                viewModel.trainees[index].job=traineeJob.jobName
                            }
                            index++
                        }
                        progressAdapter.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
    fun readUser(progressAdapter:traineeListAdapter,traineeID:String)
    {
        Firebase.database.reference
            .child("users").child(traineeID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val users = snapshot.getValue(User::class.java)
                    // 在这里处理获取到的作业对象
                    if (users != null) {
                        var index = 0

                        for(u in viewModel.trainees)
                        {
                            if(u.id==traineeID)
                            {
                                viewModel.trainees[index].name= users.fullName
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
    fun readAge(progressAdapter:traineeListAdapter,traineeID:String)
    {
        Firebase.database.reference
            .child("User Profiles").child(traineeID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userProfile = snapshot.getValue(UserProfile::class.java)
                    // 在这里处理获取到的作业对象
                    if (userProfile != null) {
                        var index = 0

                        for(u in viewModel.trainees)
                        {
                            if(u.id==traineeID)
                            {
                                viewModel.trainees[index].age = userProfile.userAge
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
