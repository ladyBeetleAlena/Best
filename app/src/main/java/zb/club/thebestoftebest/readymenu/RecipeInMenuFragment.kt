package zb.club.thebestoftebest.readymenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Ingredients
import zb.club.thebestoftebest.data.Recipe
import zb.club.thebestoftebest.databinding.FragmentRecipeInMenuBinding
import zb.club.thebestoftebest.onboarding.AdapterForRecipeInMenu


class RecipeInMenuFragment : Fragment() {
    private val args by navArgs<RecipeInMenuFragmentArgs>()
    private lateinit var model:ViewModelReadyMenu
    lateinit var  textInstruction:TextView
    lateinit var textLink: TextView
    lateinit var textTitle: TextView
    lateinit var imageRecipe: ImageView
     var listIngredient = mutableListOf<Ingredients>()
    lateinit var adapter: AdapterForRecipeInMenu
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRecipeInMenuBinding=
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_recipe_in_menu, container, false)

        model = ViewModelProvider(this).get(ViewModelReadyMenu::class.java)
        binding.lifecycleOwner = this
        binding.viewModelReadyMenu= model
        var recycler:RecyclerView = binding.reciclerForProductinRecipeMenu
        textInstruction = binding.textViewInstructionRecipeMenu
        textLink = binding.textViewLincRecipeMenu
        textTitle = binding.textViewTitleRecipeMenu
        imageRecipe = binding.imageViewRecipeMenu

        adapter = AdapterForRecipeInMenu(listIngredient, args.dishbydate.adultServing, args.dishbydate.childrenServings, args.dishbydate.childAdultK)
        recycler.adapter=adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())
        model.getIngredientByRecipeId(args.dishbydate.recipeId).observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
        model.getRecipebyId(args.dishbydate.recipeId).observe(viewLifecycleOwner,{
            showRecipe(it)
        })


        return binding.root
}

    private fun showRecipe(it: Recipe?) {
        if (it != null) {
            textTitle.setText(it.title)
            textLink.setText(it.link)

            imageRecipe.setImageURI(it.picture?.toUri())

        }


    }

}