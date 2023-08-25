package iyotetsuya.tablayout.demo

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import iyotetsuya.tablayout.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val SELECTED_TAB_POSITION = "selectedTabPosition"
    }

    private lateinit var binding: ActivityMainBinding

    private val onBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                popBackStack()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            val index = savedInstanceState.getInt(SELECTED_TAB_POSITION)
            binding.tabLayout.getTabAt(index)?.select()
        }

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                val fragmentPosition = FragmentPosition.getByIndex(tab.position)
                if (fragmentPosition != null) {
                    val currentFragment = binding.fragmentContainer.getFragment<Fragment>()
                    if (currentFragment::class.java == fragmentPosition.kClass.java) {
                        // pop action, no-op
                    } else {
                        replaceFragment(fragmentPosition)
                    }
                }
            }

            override fun onTabUnselected(tab: Tab) {
            }

            override fun onTabReselected(tab: Tab) {
                popAllChildBackStack()
            }
        })
        onBackPressedDispatcher.addCallback(
            this@MainActivity,
            onBackPressedCallback
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_TAB_POSITION, binding.tabLayout.selectedTabPosition)
    }

    private fun replaceFragment(fragmentPosition: FragmentPosition) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                fragmentPosition.getFragment(supportFragmentManager),
                fragmentPosition.tag
            )
            .addToBackStack(null)
            .commit()
    }

    private fun popBackStack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            val popped = supportFragmentManager.popBackStackImmediate()
            if (popped) {
                val tagName = binding.fragmentContainer.getFragment<Fragment>().tag ?: return
                val index = FragmentPosition.getByTag(tagName)?.index ?: return
                binding.tabLayout.getTabAt(index)?.select()
            }
        }
    }

    private fun popAllChildBackStack() {
        val fragment = binding.fragmentContainer.getFragment<Fragment>()
        fragment.childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}