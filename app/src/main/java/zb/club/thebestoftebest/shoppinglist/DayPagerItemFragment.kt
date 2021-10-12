package zb.club.thebestoftebest.shoppinglist

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.DateForCurrentMenu
import zb.club.thebestoftebest.data.ShoppingListWithDay
import zb.club.thebestoftebest.databinding.FragmentDayPagerItemBinding
import zb.club.thebestoftebest.onboarding.AdapterForDayCurrentMenu
import zb.club.thebestoftebest.onboarding.ChekedProductWithDay
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*


class DayPagerItemFragment(val dayDate: DateForCurrentMenu) : Fragment(), ChekedProductWithDay {



    var listDayShopping = mutableListOf<ShoppingListWithDay>()
    private lateinit var model:ShoppingListViewModel
    private lateinit var adapter: AdapterForDayCurrentMenu

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val binding: FragmentDayPagerItemBinding = DataBindingUtil.inflate(
          layoutInflater,
          R.layout.fragment_day_pager_item,
          container,
          false
      )
        model = ViewModelProvider(this).get(ShoppingListViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModelShoppingList= model
        adapter = AdapterForDayCurrentMenu(listDayShopping, this)
        val recyclerDate = binding.recyclerForShoppingDay
        recyclerDate.adapter = adapter
        val layoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        binding.textViewDay.setText("Day ${dayDate.day.toString()}")
        recyclerDate.layoutManager = layoutManager
        if (dayDate.calendarDay.equals(0L)){binding.textViewDate.visibility = View.INVISIBLE} else {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val currentDate = sdf.format(Date(dayDate.calendarDay))
        val weekDay = Instant.ofEpochMilli(dayDate.calendarDay).atZone(ZoneId.of("UTC")).dayOfWeek.getDisplayName(
            TextStyle.SHORT,
            Locale.US
        )


        binding.textViewDate.setText(" ${weekDay}, ${currentDate.toString()}")}


        model.getShoppingListWithDay.observe(viewLifecycleOwner, {if (it.isEmpty()){
            model.dayCurrentMenu.observe(viewLifecycleOwner, { day ->  for (d in day){
                model.getShoppingListForDay(d.day).observe(viewLifecycleOwner,{shoppingItemList ->for(s in shoppingItemList){
                    val shoppingToInsert = ShoppingListWithDay(
                        0,
                        s.product,
                        s.quantity,
                        false,
                        d.day
                    )
                    model.insertShoppingListWithDay(shoppingToInsert)
                } })
            }})

            } else{model.getFromShoppListWithDayByDay(dayDate.day).observe(viewLifecycleOwner, {adapter.setData(it)})}})

        model.getSelectedProductWithDay().observe(viewLifecycleOwner, {
            val productWithDay = ShoppingListWithDay(
                it.id,
                it.product,
                it.quantity,
                true,
                it.day
            )
            model.updateShoppingListWithDay(productWithDay)
        })
        model.getUnSelectedProductWithDay().observe(viewLifecycleOwner, {
            val productWithDay = ShoppingListWithDay(
                it.id,
                it.product,
                it.quantity,
                false,
                it.day
            )
            model.updateShoppingListWithDay(productWithDay)
        })

        return binding.root
    }

    override fun onCheckedProduct(checkedProduct: ShoppingListWithDay) {
        model.setSelectedProductWithDay(checkedProduct)
    }

    override fun unChekedProduct(unchekedProduct: ShoppingListWithDay) {
        model.setunselectedProductWithDay(unchekedProduct)
    }


}