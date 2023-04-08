package my.rjtechnology.apprenticestreet.ui.salaries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import my.rjtechnology.apprenticestreet.Constants
import my.rjtechnology.apprenticestreet.databinding.FragmentSalariesBinding
import my.rjtechnology.apprenticestreet.models.SimpleFilter
import my.rjtechnology.apprenticestreet.ui.adapters.SimpleFilterAdapter

class SalariesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSalariesBinding.inflate(inflater, container, false)

        binding.salaryList.adapter = SimpleFilterAdapter(
            viewLifecycleOwner,
            listOf(
                SimpleFilter("0"),
                SimpleFilter("250"),
                SimpleFilter("500"),
                SimpleFilter("750"),
                SimpleFilter("1000"),
                SimpleFilter("1250"),
                SimpleFilter("1500"),
                SimpleFilter("1750"),
                SimpleFilter("2000"),
                SimpleFilter("2250"),
                SimpleFilter("2500"),
            ),
            onClick = {
                val navController = findNavController()

                navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(Constants.MIN_SALARY_PER_MONTH_KEY, it.label.toInt())

                navController.navigateUp()
            }
        )

        return binding.root
    }
}
