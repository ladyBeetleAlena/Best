package zb.club.thebestoftebest.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.DateForCurrentMenu
import zb.club.thebestoftebest.databinding.FragmentDayShoppingListBinding
import zb.club.thebestoftebest.onboarding.ViewPagerAdapter
import zb.club.thebestoftebest.onboarding.ZoomOutPageTransformer

import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match


class DayShoppingListFragment : Fragment() {

    lateinit var imageForvard: ImageView
    lateinit var imageBack: ImageView
    lateinit var adapter: ViewPagerAdapter
    lateinit var viePagerDay: ViewPager2
    val model: ShoppingListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentDayShoppingListBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_day_shopping_list,
            container,
            false
        )
        viePagerDay = binding.pagerDayDate
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        model.dayCurrentMenu.observe(viewLifecycleOwner, {if(it.isNotEmpty())
        {creatDayPager(it)}
        })
        imageForvard = binding.imageViewForward
        imageForvard.visibility = View.INVISIBLE
        imageBack = binding.imageViewBack
        imageBack.visibility = View.INVISIBLE
        imageForvard.setOnClickListener {
            viePagerDay.setCurrentItem(
                viePagerDay.currentItem + 1,
                true
            )
        }
        imageBack.setOnClickListener {
            viePagerDay.setCurrentItem(
                viePagerDay.currentItem - 1,
                true
            )
        }

        viePagerDay.setPageTransformer(ZoomOutPageTransformer())
        return binding.root
    }

    private fun pagerChange() {

        val myPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        imageBack.visibility = View.INVISIBLE
                        imageForvard.visibility = View.VISIBLE
                    }
                    adapter.itemCount - 1 -> {
                        imageForvard.visibility = View.INVISIBLE
                        imageBack.visibility = View.VISIBLE
                    }
                    else -> {
                        imageForvard.visibility = View.VISIBLE
                        imageBack.visibility = View.VISIBLE
                    }

                }

            }
        }
        viePagerDay.registerOnPageChangeCallback(myPageChangeCallback)
    }

    private fun creatDayPager(it: List<DateForCurrentMenu>?) {
        var position: Int = 1
        if (it != null) {for (i in 0 until   it.size) {
            adapter.addFragment(DayPagerItemFragment(it[i]), it[i].calendarDay.toString())
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            if(sdf.format(it[i].calendarDay) == sdf.format(Date())){
                position = i
            }else {position = 0}

        }

            }
        viePagerDay.adapter = adapter
        viePagerDay.setCurrentItem(position, true)
        when( adapter.itemCount){
            0, 1 -> {imageBack.visibility = View.INVISIBLE
                imageForvard.visibility = View.INVISIBLE}


            else ->{ pagerChange()}


    }}}

