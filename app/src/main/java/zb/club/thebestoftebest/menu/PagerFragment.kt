package zb.club.thebestoftebest.menu

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.CurrentMenu
import zb.club.thebestoftebest.data.MenuTemp
import zb.club.thebestoftebest.data.Recipe
import zb.club.thebestoftebest.databinding.FragmentPagerBinding
import zb.club.thebestoftebest.onboarding.AdapterForCreateMenuDayMeal
import zb.club.thebestoftebest.onboarding.AdapterForRecipeInCreateMenu
import zb.club.thebestoftebest.onboarding.CheckedRecipeListener
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*


class PagerFragment(

) : Fragment(), CheckedRecipeListener,AdapterForCreateMenuDayMeal.OnItemClickListener{
    val day: Int = 1
    lateinit var adapter: AdapterForRecipeInCreateMenu
    lateinit var reciclerRecipeInCreate: RecyclerView
    var recipeList = mutableListOf<Recipe>()
    private lateinit var model: ViewModelMenu
    private val args by navArgs<PagerFragmentArgs>()
    lateinit var adapterForMenuItem: AdapterForCreateMenuDayMeal
    lateinit var recyclerViewItemMenu: RecyclerView
    var tempMenuForDay = mutableListOf<MenuTemp>()
    lateinit var currentMenuItem:CurrentMenu


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPagerBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_pager,
            container, false
        )

        model = ViewModelProvider(this).get(ViewModelMenu::class.java)
        binding.lifecycleOwner = this
        binding.viewModelMenu = model
        adapterForMenuItem = AdapterForCreateMenuDayMeal(this, tempMenuForDay)
        adapter = AdapterForRecipeInCreateMenu(this, requireContext(), recipeList)
        reciclerRecipeInCreate = binding.recyclercreatemenu
        recyclerViewItemMenu = binding.reciclerCreateMenuDayWithMeal
        if(args.mealThis == "all recipe"){model.allRecipe.observe(viewLifecycleOwner,{adapter.setData(it)})}


        model.getRecipeByMeal(args.mealThis).observe(viewLifecycleOwner, { adapter.setData(it) })


        recyclerViewItemMenu.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewItemMenu.adapter = adapterForMenuItem
        reciclerRecipeInCreate.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        if(args.calendarDay.toInt().equals(0)){
            binding.textViewDateInCreateDate.visibility = View.INVISIBLE
        }
        reciclerRecipeInCreate.adapter=adapter
       if(args.calendarDay >0){
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val showDate  = sdf.format(args.calendarDay)
        val weekDay = Instant.ofEpochMilli(args.calendarDay).atZone(ZoneId.of("UTC")).dayOfWeek.getDisplayName(
            TextStyle.SHORT,
            Locale.US
        )
        binding.textViewDateInCreateDate.setText("${weekDay}, ${showDate.toString()}")}
        binding.textViewDayInCreateDay.setText("Day ${args.day}")
        if(args.mealThis !=null)
        {binding.textViewMealInDayMeal.setText(args.mealThis)}






       model.getSelectedRecipe().observe(viewLifecycleOwner, {
           addRecipeToCurrentMenu(it)
       })



        model.getTempMenuForDayMeals(args.day, args.mealThis).observe(viewLifecycleOwner, {
            adapterForMenuItem.setData(it)
        })


        return binding.root
    }

    private fun addRecipeToCurrentMenu(it: ArrayList<Recipe>?) {
        if (it != null) {
            for(i in it){
                val tempMenuItem = MenuTemp(
                    0,
                    i.id,
                    i.title!!,
                    args.calendarDay,
                    args.menuTitle,
                    args.day,
                    args.mealThis,
                    args.childrenServing,
                    args.adultServing,
                    args.childAdultK.toDouble()


                )

              model.insertTempMenu(tempMenuItem)
                adapterForMenuItem.notifyDataSetChanged()
            }
        }}


    override fun onCheckedRecipes(checkedRecipe: ArrayList<Recipe>) {
        model.setSelectedRecipes(checkedRecipe)
        checkedRecipe.clear()

    }

    override fun onItemClick(position: Int) {
        val item = adapterForMenuItem.getCurrentMenuAtPosition(position)
        val builder = MaterialAlertDialogBuilder(requireContext())



            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.change_item_current_menu, null)
            builder.setView(dialogView)
            val adult = dialogView.findViewById<TextInputEditText>(R.id.adultPortionCurrent)
            val child = dialogView.findViewById<TextInputEditText>(R.id.childPortionCurrent)
            val titleRec = dialogView.findViewById<TextView>(R.id.textViewCurrentTitle)
            if (titleRec != null) {
                titleRec.text = item.titleRecipe
            }
            adult.setText(item.adultServing.toString())
            child.setText(item.childrenServings.toString())
            adult.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (s != null ) {
                        if(s.isNotEmpty()){
                            val tempMenuChanged = s.trim().toString().toIntOrNull()?.let {
                                MenuTemp(
                                    item.id,
                                    item.idRecipe,
                                    item.titleRecipe,
                                    item.calendarDay,
                                    item.menuTitle,
                                    item.day,
                                    item.meal,
                                    child.text.toString().toInt(),
                                    it,
                                    item.childAdultK
                                )
                            }
                            if (tempMenuChanged!= null) {
                                model.updateTempMenu(tempMenuChanged)
                            }
                        }
                    }

                }
            })

           child.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (s != null ) {
                        if(s.isNotEmpty()){
                            val tempMenuChanged = s.trim().toString().toIntOrNull()?.let {
                               MenuTemp(
                                    item.id,
                                    item.idRecipe,
                                    item.titleRecipe,
                                    item.calendarDay,
                                    item.menuTitle,
                                    item.day,
                                    item.meal,
                                    it,
                                    adult.text.toString().toInt(),
                                    item.childAdultK
                                )
                            }
                            if (tempMenuChanged != null) {
                                model.updateTempMenu(tempMenuChanged)
                            }
                        }
                    }

                }
            })
                    builder.setNegativeButton("Go to create "){ dialog, which->}
            builder.setPositiveButton(""){dialog, which->}


            val dialog = builder.create()
            dialog.show()
          }





    override fun delete(position: Int) {
        val item = adapterForMenuItem.getCurrentMenuAtPosition(position)
        model.deleteOneOfTempMenu(item)
    }


}



