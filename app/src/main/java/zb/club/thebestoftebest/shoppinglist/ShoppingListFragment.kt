package zb.club.thebestoftebest.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.databinding.FragmentShoppingListBinding
import zb.club.thebestoftebest.onboarding.AdapterForShoppingList
import zb.club.thebestoftebest.onboarding.ZoomOutPageTransformer



class ShoppingListFragment : Fragment() {
    lateinit var viewPagerShoppingList: ViewPager2
    lateinit var tabLayotShoppingList: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {    val binding: FragmentShoppingListBinding = DataBindingUtil.inflate(
        layoutInflater, R.layout.fragment_shopping_list,
        container, false
    )
        viewPagerShoppingList = binding.pagerShoppingList
        viewPagerShoppingList.isSaveEnabled = false
        viewPagerShoppingList.isUserInputEnabled = false
        tabLayotShoppingList = binding.tabLayoutShoppingList
        val adapter = AdapterForShoppingList(childFragmentManager, lifecycle)
        adapter.addFragment(AllShopFragment(), "All shopping list")
        adapter.addFragment(DayShoppingListFragment(), "Day's shopping list")
        adapter.addFragment(AddingThingsShoppingFragment(), "Adding item")
        viewPagerShoppingList.adapter = adapter
        TabLayoutMediator(tabLayotShoppingList, viewPagerShoppingList) { tab, position ->
            tab.text = adapter.getPageTitle(position)
            viewPagerShoppingList.setCurrentItem(tab.position, true)

        }.attach()
        viewPagerShoppingList.setPageTransformer(ZoomOutPageTransformer())

       return  binding.root
    }


}