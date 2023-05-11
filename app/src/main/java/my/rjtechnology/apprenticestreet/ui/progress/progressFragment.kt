package my.rjtechnology.apprenticestreet.ui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.databinding.FragmentProgressBinding
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
            var progressAdapter: AppiledCompanyProgressAdapter= AppiledCompanyProgressAdapter(viewLifecycleOwner, viewModel.arrayList)
            viewModel.arrayList.add("item0")
            viewModel.arrayList.add("item1")
            viewModel.arrayList.add("item2")
            companyRecycler.layoutManager = layoutManager
            companyRecycler.setHasFixedSize(true)
            companyRecycler.adapter = progressAdapter
            progressAdapter.onItemClick = {
                Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show()
            }
            progressAdapter.onDeleteClick = {
                var a = progressAdapter.pos
                Toast.makeText(context, a.toString(), Toast.LENGTH_SHORT).show()
                viewModel.arrayList.removeAt(a)
                progressAdapter.notifyDataSetChanged()
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