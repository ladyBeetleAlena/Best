package zb.club.thebestoftebest.menu

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.CurrentMenu
import zb.club.thebestoftebest.data.DateForCurrentMenu
import zb.club.thebestoftebest.data.Menu
import zb.club.thebestoftebest.data.MenuTemp
import zb.club.thebestoftebest.databinding.FragmentCreateMenuBinding
import zb.club.thebestoftebest.onboarding.AdapterForMealInCreateMenu
import zb.club.thebestoftebest.onboarding.AdaptorInCreate
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*


class CreateMenuFragment() : Fragment(), AdapterForMealInCreateMenu.OnItemMealClickListener, AdaptorInCreate.OnItemClickListener{

    lateinit var adapter: AdapterForMealInCreateMenu
    lateinit var adapterChoosenRecipe: AdaptorInCreate
    var day: Int = 1
    var tempMenuForDay = mutableListOf<MenuTemp>()
    lateinit var recyclerForChosenRecipe: RecyclerView
    val model:ViewModelMenu by activityViewModels()
    private val args by navArgs<CreateMenuFragmentArgs>()
     var meal = mutableListOf<String>()
    var calendarDay:Long = 0
    lateinit var date: TextView
    lateinit var allRecipe:TextView
    lateinit var menuForDay:TextView
    lateinit var dayView: TextView
    var pickerDate: Long = 0
    lateinit var dateChoose: ImageView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreateMenuBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_create_menu,
            container, false
        )
        binding.lifecycleOwner = this
        binding.viewModelMenu = model
         date = binding.textViewDateFoCreateMenu
        adapter = AdapterForMealInCreateMenu(this, meal)
        val recycler = binding.recyclerViewSelectMeal
        recyclerForChosenRecipe = binding.recyclercreatemenuAllDay
        adapterChoosenRecipe = AdaptorInCreate(this, tempMenuForDay)

        model.allMeal.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        recycler.layoutManager = layoutManager

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()
        datePicker.addOnPositiveButtonClickListener {
            this.pickerDate = datePicker.selection!!
            calendarDay = pickerDate
            val a = calendarDay.toInt()
            if (a.equals(0)) {
                date.visibility = View.INVISIBLE
            } else {

                val sdf = SimpleDateFormat("dd/M/yyyy")
                val showDate = sdf.format(calendarDay)
                val weekDay = Instant.ofEpochMilli(calendarDay)
                    .atZone(ZoneId.of("UTC")).dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.US
                )
                date.setText("${weekDay}, ${showDate.toString()}")


            }}

            dateChoose = binding.imageViewCalendar
        if (day == 1){dateChoose.visibility = View.VISIBLE} else{dateChoose.visibility = View.INVISIBLE}
            dateChoose.setOnClickListener {
                activity?.let { it1 -> datePicker.show(it1.supportFragmentManager, "DatePicker") }
            }

            allRecipe  = binding.textViewAllrecipe
        allRecipe.setOnClickListener {  val createMealInDayForRecycler =
            CreateMenuFragmentDirections.actionCreateMenuFragmentToPagerFragment(
                "all recipe",
                calendarDay,
                args.titlemenu,
                args.namberchild,
                args.namberadult,
                day,
                args.childadult
            )
            findNavController().navigate(createMealInDayForRecycler) }

            date = binding.textViewDateFoCreateMenu
            dayView = binding.textViewDayInCreateMenu

            dayView.setText(day.toString())


            menuForDay = binding.textViewCreqteMenuForDay
            menuForDay.setText("This is menu for ${day} day")


            model.getdayFoTempAsync(day)
            model.liveTempMenu.observe(viewLifecycleOwner, {
                adapterChoosenRecipe.setData(it)
            })

            recyclerForChosenRecipe.adapter = adapterChoosenRecipe
            recyclerForChosenRecipe.layoutManager = LinearLayoutManager(requireContext())
            binding.imageViewNextDay.setOnClickListener {
                createNewView()

            }
            binding.imageViewPreviewDay.setOnClickListener {
                previewDay()
            }
          date.addTextChangedListener(object :TextWatcher{
              override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

              }

              override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

              }

              override fun afterTextChanged(s: Editable?) {

                  model.getdayFoTempAsync(day)
                  model.liveTempMenu.observe(viewLifecycleOwner, {
                      changeDate(it)
                  })
              }
          })





        binding.buttonSaveToCuurent.setText("Save all to current and list of menu")
           binding.buttonSaveToCuurent.setOnClickListener {  val builder = MaterialAlertDialogBuilder(requireContext())
               builder.setTitle("What do you want to do?").setMessage("After applaing this menu you current menu will changed and will created new shopping list")
               builder.setNegativeButton("Apply"){ dialog, which ->
                   model.clearcurrentMenu()
                   model.clearShoppList()
                   model.clearShoppWithDay()
                   model.tempMenu.observe(viewLifecycleOwner, {setCurrent(it)}) }
               builder.setNeutralButton("cancel"){dialog, which -> }
               builder.setPositiveButton("Save to All Menu ") { dialog, which -> model.tempMenu.observe(viewLifecycleOwner, {saveToAll(it)})}
               val dialog = builder.create()
               dialog.show()
           }


            return binding.root
        }

    private fun changeDate(it: List<MenuTemp>?) {
        if (it != null ) {
            if (it.isNotEmpty()){

                for(i in it)
                if(i.day == day){
                    val menuTempItem = MenuTemp(
                        i.id,
                        i.idRecipe,
                        i.titleRecipe,
                        calendarDay,
                        i.menuTitle,
                        i.day,
                        i.meal,
                        i.childrenServings,
                        i.adultServing,
                        i.childAdultK
                    )
                    model.updateTempMenu(menuTempItem)
                   adapterChoosenRecipe.notifyDataSetChanged()

                }
            }
        }


    }


    @SuppressLint("SimpleDateFormat", "SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        private fun createNewView() {
         val checkDay = ArrayList<Int>()

        day = day + 1



            if (calendarDay > 0) {
                calendarDay += 24 * 60 * 60 * 1000
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val showDate = sdf.format(calendarDay)
                val weekDay = Instant.ofEpochMilli(calendarDay)
                    .atZone(ZoneId.of("UTC")).dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.US
                )
                date.setText("${weekDay}, ${showDate.toString()}")
            }
        if (day == 1){dateChoose.visibility = View.VISIBLE} else{dateChoose.visibility = View.INVISIBLE}
        dayView.setText(day.toString())
            menuForDay.setText("This is menu for ${day} day")
            model.getdayFoTempAsync(day)
            adapterChoosenRecipe.notifyDataSetChanged()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun previewDay() {
            if (day > 1) {
                day = day - 1
            }
            if (calendarDay > 24 * 60 * 60 * 1000) {
                if(calendarDay > pickerDate){calendarDay -= 24 * 60 * 60 * 1000}
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val showDate = sdf.format(calendarDay)
                val weekDay = Instant.ofEpochMilli(calendarDay)
                    .atZone(ZoneId.of("UTC")).dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.US
                )
                date.setText("${weekDay}, ${showDate.toString()}")

            }
            if (day == 1){dateChoose.visibility = View.VISIBLE} else{dateChoose.visibility = View.INVISIBLE}
            dayView.setText(day.toString())

            menuForDay.setText("This is menu for ${day} day")
            model.getdayFoTempAsync(day)
            adapterChoosenRecipe.notifyDataSetChanged()
        }


        override fun onPause() {

            var dateSave = DateForCurrentMenu(day, calendarDay)
            model.date.add(dateSave)
            super.onPause()
        }

        override fun onResume() {
            super.onResume()
            if (model.date.isNotEmpty()) {
                day = model.date[0].day
                if (model.date[0].calendarDay != 0L) {
                    calendarDay = model.date[0].calendarDay
                    date.setText(SimpleDateFormat("dd/M/yyyy").format(calendarDay).toString())
                }
                dayView.setText(day.toString())
                menuForDay.setText("This is menu for ${day} day")
                model.date.clear()
            }
        }


        override fun onItemMealClick(position: Int) {
            val mealForDay = adapter.getMealAtPosition(position)
            val createMealInDayForRecycler =
                CreateMenuFragmentDirections.actionCreateMenuFragmentToPagerFragment(
                    mealForDay,
                    calendarDay,
                    args.titlemenu,
                    args.namberchild,
                    args.namberadult,
                    day,
                    args.childadult
                )
            findNavController().navigate(createMealInDayForRecycler)
        }

        override fun onItemClick(position: Int) {
            val item = adapterChoosenRecipe.getCurrentMenuAtPosition(position)
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
                adult.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (s != null) {
                            if (s.isNotEmpty()) {
                                val currentTempMenuChanged = s.trim().toString().toIntOrNull()?.let {
                                    item.titleRecipe?.let { it1 ->
                                        MenuTemp(
                                            item.id,
                                            item.idRecipe,
                                            it1,
                                            item.calendarDay,
                                            item.menuTitle,
                                            item.day,
                                            item.meal,
                                            child.text.toString().toInt(),
                                            it,
                                            item.childAdultK
                                        )
                                    }
                                }
                                if (currentTempMenuChanged != null) {
                                    model.updateTempMenu(currentTempMenuChanged)

                                }
                            }
                        }

                    }
                })

                child.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (s != null) {
                            if (s.isNotEmpty()) {
                                val tempMenuChanged = s.trim().toString().toIntOrNull()?.let {
                                    item.titleRecipe?.let { it1 ->
                                        MenuTemp(
                                            item.id,
                                            item.idRecipe,
                                            it1,
                                            item.calendarDay,
                                            item.menuTitle,
                                            item.day,
                                            item.meal,
                                            it,
                                            adult.text.toString().toInt(),
                                            item.childAdultK
                                        )
                                    }
                                }
                                if (tempMenuChanged != null) {
                                    model.updateTempMenu(tempMenuChanged)

                                }
                            }
                        }

                    }
                })
                builder.setNegativeButton("Go to create ") { dialog, which -> }
                builder.setPositiveButton("") { dialog, which -> }


                val dialog = builder.create()
                dialog.show()



        }

    override fun delete(position: Int) {
        val item = adapterChoosenRecipe.getCurrentMenuAtPosition(position)
        model.deleteOneOfTempMenu(item)
        adapterChoosenRecipe.notifyDataSetChanged()

    }

    fun setCurrent(it: List<MenuTemp>){
        if(it.isNotEmpty()){
            for (i in it){
                val itemToInsertCurrentMenu = i.titleRecipe?.let { it1 ->
                    CurrentMenu(
                        0,
                        i.idRecipe,
                        it1,
                        i.calendarDay,
                        i.menuTitle,
                        i.day,
                        i.meal,
                        i.childrenServings,
                        i.adultServing,
                        i.childAdultK

                    )
                }
                if (itemToInsertCurrentMenu != null) {
                    model.insertCurrentMenu(itemToInsertCurrentMenu)
                }
            }


            for (i in it){
                val arrayTitle = ArrayList<String>()
                var newTitle = String()
                model.getTitleMenu.observe(viewLifecycleOwner, {
                    for (i in it){arrayTitle.add(i)}

                })
                if(arrayTitle.contains(i.menuTitle)){ newTitle = i.menuTitle +.1} else {newTitle = i.menuTitle.toString()
                }
                val itemToInsertCurrentMenu = i.titleRecipe?.let { it1 ->
                    Menu(
                        0,
                        i.idRecipe,
                        it1,

                        newTitle,
                        i.day,
                        i.meal,
                        i.childrenServings,
                        i.adultServing,
                        i.childAdultK

                    )
                }
                if (itemToInsertCurrentMenu != null) {
                    model.insertMenu(itemToInsertCurrentMenu)

                }}



        }
        model.getAllCurrentMenu.observe(viewLifecycleOwner, {Toast.makeText(requireContext(), "Looks like ${it[0].menuTitle} was created", Toast.LENGTH_SHORT).show()})
//        val goToShoppingList = CreateMenuFragmentDirections.actionCreateMenuFragmentToShoppingListFragment2()
//        findNavController().navigate(goToShoppingList)

    }
    fun saveToAll(it: List<MenuTemp>){
        if(it.isNotEmpty()){

        for (i in it){
            val arrayTitle = ArrayList<String>()
            var newTitle = String()
            model.getTitleMenu.observe(viewLifecycleOwner, {
                for (i in it){arrayTitle.add(i)}

            })
            if(arrayTitle.contains(i.menuTitle)){ newTitle = i.menuTitle +.1} else {newTitle = i.menuTitle.toString()
            }
            val itemToInsertCurrentMenu = i.titleRecipe?.let { it1 ->
                Menu(
                    0,
                    i.idRecipe,
                    it1,
                    newTitle,
                    i.day,
                    i.meal,
                    i.childrenServings,
                    i.adultServing,
                    i.childAdultK

                )
            }

            if (itemToInsertCurrentMenu != null) {
                model.insertMenu(itemToInsertCurrentMenu)
            }}
    }
 }



}












