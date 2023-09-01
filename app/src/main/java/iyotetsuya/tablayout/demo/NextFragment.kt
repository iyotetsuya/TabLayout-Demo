package iyotetsuya.tablayout.demo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import iyotetsuya.tablayout.demo.databinding.FragmentNextBinding

class NextFragment : Fragment() {
    companion object {
        const val ARGS_INDEX = "index"

        fun newInstance(index: Int): NextFragment {
            val args = Bundle()

            args.putInt(ARGS_INDEX, index)
            val fragment = NextFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentNextBinding? = null
    private val binding get() = _binding!!

    private val index by lazy {
        arguments?.getInt(ARGS_INDEX) ?: 0
    }

    private val onBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (parentFragmentManager.backStackEntryCount > 0) {
                    popBackStack()
                } else {
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            onBackPressedCallback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback.isEnabled = true
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.isEnabled = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener {
            replaceFragment(newInstance(index + 1))
        }
        val desc = String.format(
            resources.getString(
                R.string.next_fragment_desc,
                index,
                System.identityHashCode(this).toString()
            )
        )
        binding.desc.text = desc
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.child_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun popBackStack() {
        parentFragmentManager.popBackStack()
    }
}