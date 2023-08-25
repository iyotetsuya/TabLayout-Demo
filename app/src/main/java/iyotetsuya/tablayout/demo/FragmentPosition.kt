package iyotetsuya.tablayout.demo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlin.reflect.KClass

enum class FragmentPosition(val index: Int, val kClass: KClass<out Fragment>, val tag: String) {
    EMPTY(0, EmptyFragment::class, "EmptyFragment"),
    HOME(1, ChildContainerFragment::class, "ChildContainerFragment"),
    NOTIFICATIONS(2, KeepFragment::class, "KeepFragment");

    companion object {
        fun getByTag(tagName: String): FragmentPosition? {
            return values().find { tagName == it.tag }
        }

        fun getByIndex(index: Int): FragmentPosition? {
            return values().find { index == it.index }
        }
    }

    fun getFragment(supportFragmentManager: FragmentManager): Fragment {
        return supportFragmentManager.findFragmentByTag(tag)
            ?: kClass.java.newInstance()
    }
}