package zb.club.thebestoftebest.readymenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.DateForCurrentMenu
import zb.club.thebestoftebest.databinding.FragmentDayMenuBinding
import zb.club.thebestoftebest.onboarding.ViewPagerAdapter
import zb.club.thebestoftebest.onboarding.ZoomOutPageTransformer
import java.text.SimpleDateFormat
import java.util.*


class DayMenuFragment : Fragment() {
    lateinit var imageForvard: ImageView
    lateinit var imageBack: ImageView
    lateinit var adapter: ViewPagerAdapter
    lateinit var viePagerDayMenu: ViewPager2
    val model: ViewModelReadyMenu by activityViewModels()
    val args by navArgs<DayMenuFragmentArgs>()
    var dayNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDayMenuBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_day_menu,
            container,
            false
        )

        viePagerDayMenu = binding.pagerDayMenu
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        model.dayCurrentMenu.observe(viewLifecycleOwner, {if(it.size>0)
        {adapter.remove()
            creatDayPager(it)}
        })
        imageForvard = binding.imageViewForward
        imageForvard.visibility = View.INVISIBLE
        imageBack = binding.imageViewBack
        imageBack.visibility = View.INVISIBLE
        imageForvard.setOnClickListener {
            viePagerDayMenu.setCurrentItem(
                viePagerDayMenu.currentItem + 1,
                true
            )
        }


        viePagerDayMenu.setPageTransformer(ZoomOutPageTransformer())
        imageBack.setOnClickListener {
            viePagerDayMenu.setCurrentItem(
                viePagerDayMenu.currentItem - 1,
                true
            )
        }

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
        viePagerDayMenu.registerOnPageChangeCallback(myPageChangeCallback)
    }

    private fun creatDayPager(it: List<DateForCurrentMenu>?) {

        var position: Int = 1

        if (it != null) {
            for (i in 0 until   it.size) {
                adapter.addFragment(ItemDayMenu(it[i]), it[i].calendarDay.toString())
                TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"))
                val sdf = SimpleDateFormat("dd.MM.yyyy")

                if(sdf.format(it[i].calendarDay) == sdf.format(Date())){
                    position = i
                } else {position = 0}

            }

        }
        viePagerDayMenu.adapter = adapter
        viePagerDayMenu.setCurrentItem(position, true)
        viePagerDayMenu.setPageTransformer(ZoomOutPageTransformer())
        when( adapter.itemCount){
            0, 1 -> {imageBack.visibility = View.INVISIBLE
            imageForvard.visibility = View.INVISIBLE}


            else ->{ pagerChange()}

    }
}}

