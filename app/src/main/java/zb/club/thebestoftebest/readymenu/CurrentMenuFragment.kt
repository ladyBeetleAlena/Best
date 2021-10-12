package zb.club.thebestoftebest.readymenu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.CurrentMenu
import zb.club.thebestoftebest.data.DataToCurrentMenu
import zb.club.thebestoftebest.data.DateForCurrentMenu
import zb.club.thebestoftebest.data.dishbyDate
import zb.club.thebestoftebest.databinding.FragmentCurrentMenuBinding
import zb.club.thebestoftebest.onboarding.AdapterForCurrentMenuRecycler
import zb.club.thebestoftebest.onboarding.AdapterForDialogBuilderInCurrentMenu


class CurrentMenuFragment : Fragment(), AdapterForCurrentMenuRecycler.OnItemCurrentClickListener, AdapterForDialogBuilderInCurrentMenu.ListenerInCurrentDialog{



    val dateCurrentDay = mutableListOf<DataToCurrentMenu>()
    val currentList= mutableListOf<CurrentMenu>()




    lateinit var recyclerMenu: RecyclerView
    lateinit var mainAdapter: AdapterForCurrentMenuRecycler
    private lateinit var model: ViewModelReadyMenu
    lateinit var ent: DataToCurrentMenu
    val adapter = AdapterForDialogBuilderInCurrentMenu(this, currentList)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCurrentMenuBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_current_menu,
                container,
                false
            )

        model = ViewModelProvider(this).get(ViewModelReadyMenu::class.java)
        binding.lifecycleOwner = this
        binding.viewModelReadyMenu = model
        recyclerMenu = binding.recyclerCurrentMenu
        mainAdapter = AdapterForCurrentMenuRecycler(this, dateCurrentDay, requireContext())
        recyclerMenu.adapter = mainAdapter
        val layoutManager: GridLayoutManager =
            GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        recyclerMenu.layoutManager = layoutManager


        model.getDishWithDays.observe(viewLifecycleOwner, { it ->
            layoutManager.removeAllViews()
            dateCurrentDay.clear()
            if (it.size > 0) {
                var cd = it[0].day
                var dateDay = it[0].calendarDay
                var dishDate = mutableListOf<dishbyDate>()


                for (i in it) {

                    if (cd == i.day) {


                        var dishByDate = dishbyDate(
                            i.recipeId,
                            i.titleRecipe,
                            i.meal,
                            i.childrenServings,
                            i.adultServing,
                            i.childAdultK,
                            i.picture
                        )
                        dishDate.add(dishByDate)

                    } else {
                        var day = DateForCurrentMenu(cd, dateDay)
                        ent = DataToCurrentMenu(day, dishDate)
                        dateCurrentDay.add(ent)
                        cd = i.day
                        dateDay = i.calendarDay
                        dishDate = mutableListOf<dishbyDate>()
                        var dishByDate = dishbyDate(
                            i.recipeId,
                            i.titleRecipe,
                            i.meal,
                            i.childrenServings,
                            i.adultServing,
                            i.childAdultK,
                            i.picture
                        )
                        dishDate.add(dishByDate)
                    }
                }
                var day = DateForCurrentMenu(cd, dateDay)
                ent = DataToCurrentMenu(day, dishDate)
                dateCurrentDay.add(ent)
                mainAdapter.setData(dateCurrentDay)

            }
        })



        return binding.root
    }






    override fun onItemClic(position: Int) {
        val itemCurrentMenu = adapter.getCurrentMenuItemAtPosition(position)
//        val builder = MaterialAlertDialogBuilder(requireContext())
//        builder.setTitle("What do you whant to do with ${itemCurrentMenu.titleRecipe}?")
//        builder.setNegativeButton("Delete ${itemCurrentMenu.titleRecipe} from menu"){ dialog, which-> model.deleteCurrentMenuByID(itemCurrentMenu.id)}
//        builder.setPositiveButton("Edit ${itemCurrentMenu.titleRecipe}"){  dialog, which->
//            builder.setTitle("Edit item")
//            val inflater = this.layoutInflater
//            val dialogView: View = inflater.inflate(R.layout.change_item_current_menu, null)
//            builder.setView(dialogView)
//            val adult = dialogView.findViewById<TextInputEditText>(R.id.adultPortionCurrent)
//            val child = dialogView.findViewById<TextInputEditText>(R.id.childPortionCurrent)
//            val titleRec = dialogView.findViewById<TextView>(R.id.textViewCurrentTitle)
//            if (titleRec != null) {
//                titleRec.text = itemCurrentMenu.titleRecipe
//            }
//            adult.setText(itemCurrentMenu.adultServing.toString())
//            child.setText(itemCurrentMenu.childrenServings.toString())
//            adult.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                    if (s != null ) {
//                        if(s.isNotEmpty()){
//                            val currentMenuChanged = s.trim().toString().toIntOrNull()?.let {
//                                CurrentMenu(
//                                    itemCurrentMenu.id,
//                                    itemCurrentMenu.idRecipe,
//                                    itemCurrentMenu.titleRecipe,
//                                    itemCurrentMenu.calendarDay,
//                                    itemCurrentMenu.menuTitle,
//                                    itemCurrentMenu.day,
//                                    itemCurrentMenu.meal,
//                                    child.text.toString().toInt(),
//                                    it,
//                                   itemCurrentMenu.childAdultK
//                                )
//                            }
//                            if (currentMenuChanged != null) {
//                                model.updatetCurrentMenu(currentMenuChanged)
//                            }
//                        }
//                    }
//
//                }
//            })
//
//            child.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                    if (s != null ) {
//                        if(s.isNotEmpty()){
//                            val currentMenuChanged = s.trim().toString().toIntOrNull()?.let {
//                                CurrentMenu(
//                                    itemCurrentMenu.id,
//                                    itemCurrentMenu.idRecipe,
//                                    itemCurrentMenu.titleRecipe,
//                                    itemCurrentMenu.calendarDay,
//                                    itemCurrentMenu.menuTitle,
//                                    itemCurrentMenu.day,
//                                    itemCurrentMenu.meal,
//                                    it,
//                                    adult.text.toString().toInt(),
//                                    itemCurrentMenu.childAdultK
//                                )
//                            }
//                            if (currentMenuChanged != null) {
//                                model.updatetCurrentMenu(currentMenuChanged)
//                            }
//                        }
//                    }
//
//                }
//            })
//            builder.setNegativeButton("Go to create "){ dialog, which->}
//            builder.setPositiveButton(""){dialog, which->}
//
//
//            val dialog = builder.create()
//            dialog.show()
//        }
//        val dialog = builder.create()
//        dialog.show()

}



    override fun onItemClick(position: Int) {
        val itemCurrentMenu = mainAdapter.getCurrentMenuAtPosition(position)
//        val builder = MaterialAlertDialogBuilder(requireContext())
//        builder.setTitle("What do you whant to do with ${itemCurrentMenu.day.day} day?")
//        builder.setNegativeButton("Delete ${itemCurrentMenu.day.day} day from menu"){ dialog, which->
//            model.deleteCurrentMenuByDay(itemCurrentMenu.day.day)
//            mainAdapter.notifyDataSetChanged()
//        }
//        builder.setPositiveButton("Edit ${itemCurrentMenu.day.day} menu's day"){ dialog, which->
//            val inflater = this.layoutInflater
//
//            val dialogView: View = inflater.inflate(R.layout.just_recycler, null)
//            builder.setView(dialogView)
//            val recyclerMenuInDialog = dialogView.findViewById<RecyclerView>(R.id.jastrecycl)
//            recyclerMenuInDialog.adapter = adapter
//            recyclerMenuInDialog.layoutManager= LinearLayoutManager(requireContext())
//            model.getCurrentMenuByDay(itemCurrentMenu.day.day).observe(viewLifecycleOwner, {
//                adapter.setData(
//                    it
//                )
//            })
//            builder.setTitle("Edit ${itemCurrentMenu.day.day} menu's day")
//            builder.setNegativeButton("Go to current menu "){ dialog, which->}
//            builder.setPositiveButton(""){dialog, which->}
//
//            val dialog = builder.create()
//            dialog.show()
//        }
//        val dialog = builder.create()
//        dialog.show()
    }


    }


