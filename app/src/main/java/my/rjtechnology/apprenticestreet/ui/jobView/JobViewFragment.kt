package my.rjtechnology.apprenticestreet.ui.jobView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import my.rjtechnology.apprenticestreet.databinding.FragmentJobViewBinding


class JobViewFragment : Fragment() {
    private var _binding: FragmentJobViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobViewBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[JobViewViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        val args: JobViewFragmentArgs by navArgs()
        viewModel.job.value = args.job

        binding.jobViewJobDesc.setOnClickListener {
            findNavController()
                .navigate(
                    JobViewFragmentDirections.actionNavigationJobViewToNavigationViewJobDesc(
                        args.job.job.desc
                    )
                )
        }

        binding.viewJobApplyJob.setOnClickListener {

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
