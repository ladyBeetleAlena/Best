package zb.club.thebestoftebest.readymenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.onboarding.ViewPagerAdapter
import zb.club.thebestoftebest.onboarding.ZoomOutPageTransformer


class TodayFragment : Fragment() {
    val model: ViewModelReadyMenu by activityViewModels()
    lateinit var viewpagger: ViewPager2
    lateinit var adapter: ViewPagerAdapter
    lateinit var tabMenuAll: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_today, container, false)
        viewpagger = view.findViewById<ViewPager2>(R.id.pagerMenuAll)
        tabMenuAll = view.findViewById(R.id.tabLayoutForAllMenu)
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        adapter.addFragment(DayMenuFragment(), "Today")
        adapter.addFragment(CurrentMenuFragment(), "Current Menu")
        adapter.addFragment(AllMenuFragment(), "All Menu")
        viewpagger.adapter = adapter
        viewpagger.isUserInputEnabled = false
        TabLayoutMediator(tabMenuAll, viewpagger) { tab, position ->
            tab.text = adapter.getPageTitle(position)


            viewpagger.setCurrentItem(tab.position, true)

        }.attach()
        viewpagger.setPageTransformer(ZoomOutPageTransformer())

        return view
    }



}