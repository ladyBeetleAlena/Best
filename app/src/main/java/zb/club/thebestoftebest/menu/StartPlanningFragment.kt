package zb.club.thebestoftebest.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Recipe
import zb.club.thebestoftebest.databinding.FragmentStartPlanningBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class StartPlanningFragment : Fragment() {
    private lateinit var model: ViewModelMenu
    lateinit var adultEdit: TextInputEditText
    lateinit var childEdit: TextInputEditText
    lateinit var childAdultEdit: TextInputEditText
    lateinit var titleMenu: TextInputEditText

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

        adultEdit = binding.adultPort
        childEdit = binding.childrenPortion
        childAdultEdit = binding.AdCH
        model.getAllServing.observe(viewLifecycleOwner, {if(it.isNotEmpty()){
            adultEdit.setText(it[0].adultServitg.toString())
            childEdit.setText(it[0].childServing.toString())
            childAdultEdit.setText(it[0].k.toString())}
        })
        titleMenu = binding.menuTitle
        var titleMenuInput: String = ""

        buttonCreate = binding.buttonCreate







        buttonCreate.setOnClickListener {
            var recipeList = ArrayList<Recipe>()
            model.setSelectedRecipes(recipeList)
            model.clearTempMenu()
             childNumber = childEdit.text.toString().toInt()
            adultNumber = adultEdit.text.toString().toInt()
            childadult= childAdultEdit.text.toString()
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




        }



