package iyotetsuya.tablayout.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import iyotetsuya.tablayout.demo.databinding.FragmentChildContainerBinding

class ChildContainerFragment : Fragment() {
    private var _binding: FragmentChildContainerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val desc =
            getString(R.string.child_container_fragment) + ": " + System.identityHashCode(this)
                .toString()

        binding.textChildFragment.text = desc
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}