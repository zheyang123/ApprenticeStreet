package my.rjtechnology.apprenticestreet.ui.jobView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import my.rjtechnology.apprenticestreet.databinding.FragmentJobViewBinding


class JobViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentJobViewBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[JobViewViewModel::class.java]
        return binding.root
    }
}