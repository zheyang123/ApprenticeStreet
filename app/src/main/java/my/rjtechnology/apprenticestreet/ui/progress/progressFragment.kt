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
import my.rjtechnology.apprenticestreet.databinding.FragmentProgressBinding
import my.rjtechnology.apprenticestreet.models.AppiledProgress
import my.rjtechnology.apprenticestreet.models.JobApplication

import my.rjtechnology.apprenticestreet.ui.adapters.AppiledCompanyProgressAdapter
import my.rjtechnology.apprenticestreet.ui.searchjob.SearchJobViewModelFactory


class progressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private lateinit var viewModel: ProgressViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val apyJob = ArrayList<AppiledProgress>()
    var accepted = false;
    //var progressAdapter= AppiledCompanyProgressAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var progressAdapter: AppiledCompanyProgressAdapter= AppiledCompanyProgressAdapter(viewLifecycleOwner,apyJob)
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        val root: View = binding.root
      // viewModel = ViewModelProvider(requireParentFragment())[ProgressViewModel::class.java]
//       ii

        viewModel = ViewModelProvider(
            requireParentFragment(),
            ProgressViewModelFactory(
                requireActivity().application,
                onDone = {
                    Firebase.database.reference
                        .child("jobApplications").child(it)
                        .get()
                        .addOnSuccessListener { snapshot ->
                            val jobApplication = snapshot.children.map { child -> child.getValue<JobApplication>()!! }
                        for(jobApp in jobApplication )
                        {
                            var a = AppiledProgress()
                            a.companyName = jobApp.jobId
                            a.companyId = jobApp.userId
                            a.status = jobApp.status
                            if(a.status=="Accepted")
                                accepted=true;
                            apyJob.add(a)
                        }
                            progressAdapter.notifyDataSetChanged()
                        }
                    if(!accepted)
                    {

                        val companyRecycler: RecyclerView = binding.comapanyRecycle
                        val layoutManager = LinearLayoutManager(activity)
                        // Toast.makeText(context,"123", Toast.LENGTH_SHORT).show()



                        companyRecycler.layoutManager = layoutManager
                        companyRecycler.setHasFixedSize(true)
                        companyRecycler.adapter = progressAdapter
                        progressAdapter.onItemClick = {
                            Toast.makeText(context,"asd", Toast.LENGTH_SHORT).show()
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

                            }
                            builder.show()



                            //companyRecycler.adapter=progressAdapter
                        }

                    }
                    else
                    {

                    }


                }

            ))[ProgressViewModel::class.java]


        //viewModel.id.value

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}