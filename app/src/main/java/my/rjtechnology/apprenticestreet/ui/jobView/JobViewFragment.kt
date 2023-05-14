package my.rjtechnology.apprenticestreet.ui.jobView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import my.rjtechnology.apprenticestreet.databinding.FragmentJobViewBinding


class JobViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentJobViewBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[JobViewViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        val args: JobViewFragmentArgs by navArgs()
        viewModel.job.value = args.job
        return binding.root
    }
}
