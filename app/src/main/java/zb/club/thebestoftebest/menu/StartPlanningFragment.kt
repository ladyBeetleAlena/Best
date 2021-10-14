package zb.club.thebestoftebest.menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Meal
import zb.club.thebestoftebest.data.Recipe
import zb.club.thebestoftebest.databinding.FragmentStartPlanningBinding
import zb.club.thebestoftebest.onboarding.AdapterForMeal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class StartPlanningFragment : Fragment(), AdapterForMeal.OnItemMealClickListener {
    private lateinit var model: ViewModelMenu
    lateinit var adultEdit: TextInputEditText
    lateinit var childEdit: TextInputEditText
    lateinit var childAdultEdit: TextInputEditText
    lateinit var titleMenu: TextInputEditText
    lateinit var linearLayout: LinearLayout
    lateinit var buttonYes:Button
    lateinit var mealEdit: TextInputEditText
    lateinit var mealAdd: ImageButton
    lateinit var reciclerMeal: RecyclerView
    lateinit var adapterMeal:AdapterForMeal
    val mealList = mutableListOf<Meal>()
    lateinit var correctMealButton:Button
    lateinit var linearMeal:LinearLayout


    lateinit var buttonCreate: Button
    var adultNumber by Delegates.notNull<Int>()
    var childNumber by Delegates.notNull<Int>()
    lateinit var childadult:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStartPlanningBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_start_planning,
            container, false
        )
        model = ViewModelProvider(this).get(ViewModelMenu::class.java)
        binding.lifecycleOwner = this
        binding.viewModelMenu = model

        correctMealButton = binding.buttonCorrectMeal
        linearMeal = binding.LinearMeal


        linearLayout = binding.linearLayoutCorrectServing
        buttonYes = binding.button
        model.getAllServing.observe(viewLifecycleOwner, {
            childNumber = it[0].childServing
            adultNumber = it[0].adultServitg
            childadult = it[0].k.toString()
        })

        correctMealButton.setOnClickListener {
            val inflater = LayoutInflater.from(requireContext())
            val row: View = inflater.inflate(R.layout.add_meal, null)

            reciclerMeal = row.findViewById(R.id.recyclermenu)
            mealAdd = row.findViewById(R.id.imageButton)
            mealEdit = row.findViewById(R.id.mealedit)
            mealAdd.setOnClickListener { val mealToEnter = Meal(0,mealEdit.text.toString())
                model.insertMeal(mealToEnter)
                mealEdit.text?.clear()
            }
            mealEdit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (s != null) {
                        if (s.length > 0) {
                            mealAdd.visibility = View.VISIBLE
                        } else {
                            mealAdd.visibility = View.INVISIBLE
                        }
                    }
                }
            })
            adapterMeal = AdapterForMeal(mealList, this )
            reciclerMeal.adapter = adapterMeal
            reciclerMeal.layoutManager = LinearLayoutManager(requireContext())
            model.getAllMeal.observe(viewLifecycleOwner, {adapterMeal.setData(it)})
            linearMeal.addView(row)
            correctMealButton.visibility = View.INVISIBLE
        }




        buttonYes.setOnClickListener {
            val inflater = LayoutInflater.from(requireContext())
            val row: View = inflater.inflate(R.layout.item_correct_serving, null)
            adultEdit = row.findViewById(R.id.adultPort)
            childEdit = row.findViewById(R.id.childrenPortion)
            childAdultEdit = row.findViewById(R.id.AdCH)
            model.getAllServing.observe(viewLifecycleOwner, {if(it.isNotEmpty()){
                adultEdit.setText(it[0].adultServitg.toString())
                childEdit.setText(it[0].childServing.toString())
                childAdultEdit.setText(it[0].k.toString())}
            })
            linearLayout.addView(row)
            buttonYes.visibility =View.INVISIBLE

        }



        titleMenu = binding.menuTitle
        var titleMenuInput: String = ""

        buttonCreate = binding.buttonCreateMenu







        buttonCreate.setOnClickListener {
            var recipeList = ArrayList<Recipe>()
            model.setSelectedRecipes(recipeList)
            model.clearTempMenu()
            if(linearLayout.childCount > 0) {
                val row = linearLayout.getChildAt(0)
                adultEdit = row.findViewById(R.id.adultPort)
                childEdit = row.findViewById(R.id.childrenPortion)
                childAdultEdit = row.findViewById(R.id.AdCH)
             childNumber = childEdit.text.toString().toInt()
            adultNumber = adultEdit.text.toString().toInt()
            childadult= childAdultEdit.text.toString()}
            if (titleMenu.text.toString().length != 0 ) {
                titleMenuInput = titleMenu.text.toString().trim()
            } else {
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())
                titleMenuInput = "Untitle from ${currentDate}"
            }


            val createMenu =
                StartPlanningFragmentDirections.actionStartPlanningFragmentToCreateMenuFragment(

                    adultNumber,
                    childNumber,
                    childadult,
                    titleMenuInput,

                )
            model.date.clear()
            findNavController().navigate(createMenu)

        }

        return binding.root
    }

    override fun deleteMealClick(position: Int) {
       val meal = adapterMeal.getMealByPosition(position)
        model.deletMeal(meal)
    }


}



