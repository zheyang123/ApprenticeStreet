package my.rjtechnology.apprenticestreet.ui.postjob

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentPostJobBinding
import my.rjtechnology.apprenticestreet.ui.adapters.FilterlessArrayAdapter

class PostJobFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostJobBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[PostJobViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.locationFilledExposedDropdown.setAdapter(
            FilterlessArrayAdapter(
                requireContext(),
                R.layout.item_dropdown_menu_popup,
                arrayOf(
                    resources.getString(R.string.johor),
                    resources.getString(R.string.kedah),
                    resources.getString(R.string.kelantan),
                    resources.getString(R.string.kuala_lumpur),
                    resources.getString(R.string.melaka),
                    resources.getString(R.string.negeri_sembilan),
                    resources.getString(R.string.pahang),
                    resources.getString(R.string.penang),
                    resources.getString(R.string.perak),
                    resources.getString(R.string.perlis),
                    resources.getString(R.string.sabah),
                    resources.getString(R.string.sarawak),
                    resources.getString(R.string.selangor),
                    resources.getString(R.string.terangganu),
                )
            )
        )

        return binding.root
    }
}
