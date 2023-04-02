package my.rjtechnology.apprenticestreet.ui.searchjob

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.rjtechnology.apprenticestreet.databinding.FragmentSearchJobBinding
import my.rjtechnology.apprenticestreet.ui.adapters.JobAdapter

class SearchJobFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchJobBinding.inflate(inflater, container, false)
        binding.jobList.adapter = JobAdapter()
        return binding.root
    }
}
