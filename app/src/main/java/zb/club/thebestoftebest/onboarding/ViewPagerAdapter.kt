package zb.club.thebestoftebest.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    private val fragmentList : MutableList<Fragment> =ArrayList()
    private val titleList : MutableList<String> =ArrayList()

    override fun getItemCount(): Int {
       return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
    fun addFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        titleList.add(title)
    }
    fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
    fun remove(){
        fragmentList.clear()
        titleList.clear()
    }

}