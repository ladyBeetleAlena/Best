package zb.club.thebestoftebest.readymenu

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.DateForCurrentMenu
import zb.club.thebestoftebest.data.dishbyDate
import zb.club.thebestoftebest.databinding.FragmentItemDayMenuBinding
import zb.club.thebestoftebest.onboarding.AdapterForDayDish
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*


class ItemDayMenu(val dayDate: DateForCurrentMenu) : Fragment() {

    val model: ViewModelReadyMenu by activityViewModels()
   lateinit var reciclerDish: RecyclerView
   lateinit var adapter: AdapterForDayDish
   val dishList = mutableListOf<dishbyDate>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentItemDayMenuBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_item_day_menu,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModelReadyMenu= model
        reciclerDish = binding.recyclerForMenuDay
        adapter = AdapterForDayDish(dishList)
        val layoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        reciclerDish.layoutManager = layoutManager

        model.getDishByDay(dayDate.day).observe(viewLifecycleOwner, {if(it.isNotEmpty())
        {adapter.setData(it)}
        })
        reciclerDish.adapter = adapter

        binding.textViewDayMenu.setText("Day ${dayDate.day.toString()}")

        val sdf = SimpleDateFormat("dd.MM.yyyy")
        if (dayDate.calendarDay.equals(0L)){binding.textViewDateMenu.visibility = View.INVISIBLE} else{
        val currentDate = sdf.format(Date(dayDate.calendarDay))
        val weekDay = Instant.ofEpochMilli(dayDate.calendarDay).atZone(ZoneId.of("UTC")).dayOfWeek.getDisplayName(
            TextStyle.SHORT,
            Locale.US
        )



        binding.textViewDateMenu.setText(" ${weekDay}, ${currentDate.toString()}")}

        return binding.root
    }

}