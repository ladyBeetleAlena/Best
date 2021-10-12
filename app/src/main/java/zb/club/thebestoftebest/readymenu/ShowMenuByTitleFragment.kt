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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.DayWithDishMenu
import zb.club.thebestoftebest.data.ListForDate
import zb.club.thebestoftebest.data.Menu
import zb.club.thebestoftebest.databinding.FragmentShowMenuByTitleBinding
import zb.club.thebestoftebest.onboarding.AdapterForDialogBuilderInMenu
import zb.club.thebestoftebest.onboarding.AdaptorForMenuRecycler


class ShowMenuByTitleFragment : Fragment(), AdaptorForMenuRecycler.OnItemMenuClickListener, AdapterForDialogBuilderInMenu.ListenerInCurrentDialog {
    val args by navArgs<ShowMenuByTitleFragmentArgs>()
    private lateinit var model:ViewModelReadyMenu
    val dateCurrentDay = mutableListOf<DayWithDishMenu>()
    lateinit var recyclerMenu: RecyclerView
    val currentList= mutableListOf<Menu>()
    lateinit var mainAdapter: AdaptorForMenuRecycler
    val adapter = AdapterForDialogBuilderInMenu(this, currentList)
    lateinit var dayWithList:DayWithDishMenu
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentShowMenuByTitleBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_show_menu_by_title, container, false)

        model = ViewModelProvider(this).get(ViewModelReadyMenu::class.java)
        binding.lifecycleOwner = this
        binding.viewModelsShowMenu = model
        binding.textViewTitleInShowMenu.setText(args.titleMenu)
        mainAdapter = AdaptorForMenuRecycler(this, dateCurrentDay, requireContext())
        recyclerMenu = binding.recyclerShowMenu
        recyclerMenu.adapter=mainAdapter
        val layoutManager: GridLayoutManager =
            GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        recyclerMenu.layoutManager = layoutManager


        model.getMenuByTitle(args.titleMenu).observe(viewLifecycleOwner, {it ->
            layoutManager.removeAllViews()
            dateCurrentDay.clear()
            if(it.size > 0) {
                var cd = it[0].day
                var dishDate = mutableListOf<ListForDate>()


                for (i in it) {

                    if(cd == i.day)
                    {


                        var dishByDate = ListForDate(i.idRecipe,i.titleRecipe,i.menuTitle,i.day,i.meal,i.childrenServings,i.adultServing,i.childAdultK)
                        dishDate.add(dishByDate)

                    }
                    else
                    {

                        dayWithList = DayWithDishMenu(cd, dishDate)
                        dateCurrentDay.add(dayWithList)
                        cd = i.day

                        dishDate = mutableListOf<ListForDate>()
                        var dishByDate = ListForDate(i.idRecipe,i.titleRecipe,i.menuTitle,i.day,i.meal,i.childrenServings,i.adultServing,i.childAdultK)
                        dishDate.add(dishByDate)
                    }
                }

                dayWithList = DayWithDishMenu(cd, dishDate)
                dateCurrentDay.add(dayWithList)
                mainAdapter.setData(dateCurrentDay)

            }
        })
        binding.buttonApplyThis.setOnClickListener { val go = ShowMenuByTitleFragmentDirections.actionShowMenuByTitleFragmentToApplyMenuFragment(args.titleMenu)
        findNavController().navigate(go)}

        return binding.root
    }

    override fun onItemClick(position: Int) {
        val itemCurrentMenu = mainAdapter.getMenuAtPosition(position)
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("What do you whant to do with ${itemCurrentMenu.day} day?")
        builder.setNegativeButton("Delete ${itemCurrentMenu.day} day from menu"){ dialog, which->
            model.deleteMenuByDayTitle(itemCurrentMenu.day, args.titleMenu)
            mainAdapter.notifyDataSetChanged()
        }
        builder.setPositiveButton("Edit ${itemCurrentMenu.day} menu's day"){ dialog, which->
            val inflater = this.layoutInflater

            val dialogView: View = inflater.inflate(R.layout.just_recycler, null)
            builder.setView(dialogView)
            val recyclerMenuInDialog = dialogView.findViewById<RecyclerView>(R.id.jastrecycl)
            recyclerMenuInDialog.adapter = adapter
            recyclerMenuInDialog.layoutManager= LinearLayoutManager(requireContext())
            model.getMenuByDayTitle(itemCurrentMenu.day, args.titleMenu).observe(viewLifecycleOwner, {
                adapter.setData(
                    it
                )
            })
            builder.setTitle("Edit ${itemCurrentMenu.day} menu's day")
            builder.setNegativeButton("Go to menu "){ dialog, which->}
            builder.setPositiveButton(""){dialog, which->}

            val dialog = builder.create()
            dialog.show()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onItemClic(position: Int) {
        val itemCurrentMenu = adapter.getCurrentMenuItemAtPosition(position)
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("What do you whant to do with ${itemCurrentMenu.titleRecipe}?")
        builder.setNegativeButton("Delete ${itemCurrentMenu.titleRecipe} from menu"){ dialog, which-> model.deleteMenu(itemCurrentMenu)}
        builder.setPositiveButton("Edit ${itemCurrentMenu.titleRecipe}"){  dialog, which->
            builder.setTitle("Edit item")
            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.change_item_current_menu, null)
            builder.setView(dialogView)
            val adult = dialogView.findViewById<TextInputEditText>(R.id.adultPortionCurrent)
            val child = dialogView.findViewById<TextInputEditText>(R.id.childPortionCurrent)
            val titleRec = dialogView.findViewById<TextView>(R.id.textViewCurrentTitle)
            if (titleRec != null) {
                titleRec.text = itemCurrentMenu.titleRecipe
            }
            adult.setText(itemCurrentMenu.adultServing.toString())
            child.setText(itemCurrentMenu.childrenServings.toString())
            adult.addTextChangedListener(object : TextWatcher {
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
                            val currentMenuChanged = s.trim().toString().toIntOrNull()?.let {
                               Menu(
                                    itemCurrentMenu.id,
                                    itemCurrentMenu.idRecipe,
                                    itemCurrentMenu.titleRecipe,
                                    itemCurrentMenu.menuTitle,
                                    itemCurrentMenu.day,
                                    itemCurrentMenu.meal,
                                    child.text.toString().toInt(),
                                    it,
                                    itemCurrentMenu.childAdultK
                                )
                            }
                            if (currentMenuChanged != null) {
                                model.updateMenu(currentMenuChanged)



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

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (s != null ) {
                        if(s.isNotEmpty()){
                            val currentMenuChanged = s.trim().toString().toIntOrNull()?.let {
                               Menu(
                                    itemCurrentMenu.id,
                                    itemCurrentMenu.idRecipe,
                                    itemCurrentMenu.titleRecipe,
                                    itemCurrentMenu.menuTitle,
                                    itemCurrentMenu.day,
                                    itemCurrentMenu.meal,
                                    it,
                                    adult.text.toString().toInt(),
                                    itemCurrentMenu.childAdultK
                                )
                            }
                            if (currentMenuChanged != null) {
                                model.updateMenu(currentMenuChanged)

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
        val dialog = builder.create()
        dialog.show()

    }


}