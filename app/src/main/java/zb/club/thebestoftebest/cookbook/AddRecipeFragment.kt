package zb.club.thebestoftebest.cookbook

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canhub.cropper.CropImage
import com.google.android.material.textfield.TextInputEditText
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.cookbook.viewmodelcookbook.ViewModelAddRecipe
import zb.club.thebestoftebest.data.Ingredients
import zb.club.thebestoftebest.data.Recipe
import zb.club.thebestoftebest.data.RecipeByTag
import zb.club.thebestoftebest.databinding.FragmentAddRecipeBinding
import zb.club.thebestoftebest.onboarding.AdapterAddTag
import zb.club.thebestoftebest.onboarding.AdaptorAddIngridient
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class AddRecipeFragment : Fragment(), AdaptorAddIngridient.OnItemIngrClickListener, AdapterAddTag.OnItemMealClickListener {

    private val args by navArgs<AddRecipeFragmentArgs>()
    private lateinit var model: ViewModelAddRecipe
    var numberRecipe: Long = 0
    lateinit var titleRecipe: TextInputEditText
    lateinit var instruction: TextInputEditText
    lateinit var link:TextInputEditText
    lateinit var pictureFood: ImageView
    lateinit var takepicture: ImageView
    lateinit var arrayAdapterForMeal: ArrayAdapter<String>
    lateinit var arrayAdapterForCategory: ArrayAdapter<String>
    lateinit var arrayAdapterForProduct: ArrayAdapter<String>



    var ingredientList = mutableListOf<Ingredients>()

    var mealList = mutableListOf<RecipeByTag>()
    lateinit var adapterTag: AdapterAddTag

    lateinit var recyclerTag:RecyclerView

    lateinit var adaptorForIngredientRecyclerView: AdaptorAddIngridient
    lateinit var ingredientRecycler: RecyclerView
    lateinit var insertIng: AutoCompleteTextView
    lateinit var weightIng: TextInputEditText
    lateinit var saveIng:ImageView

    lateinit var insertTag: AutoCompleteTextView
    lateinit var saveTag:ImageView







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentAddRecipeBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_add_recipe,
            container, false
        )

        model = ViewModelProvider(this).get(ViewModelAddRecipe::class.java)
        binding.lifecycleOwner = this
        binding.viewModelAddRecipe = model

        insertIng = binding.ingredientsAutuComplite
        weightIng = binding.edittextinputweight
        saveIng = binding.imageButtonSaveIng


        recyclerTag = binding.recyclerTag


        insertTag =binding.inputTag

        saveTag = binding.imageButtonSaveTag


        ingredientRecycler = binding.reciclerIngredients

        adapterTag = AdapterAddTag(mealList, this)
        adaptorForIngredientRecyclerView = AdaptorAddIngridient(ingredientList, this)
        titleRecipe = binding.inputtitleEditText
        pictureFood = binding.imageViewPictureFood
        takepicture = binding.imageViewTakePicture

        link = binding.linkText
        instruction = binding.inputInstruction

        setRecipe(args.recipeFoEdit)
        setAdapter(args.recipeFoEdit.id)
        numberRecipe = args.recipeFoEdit.id


        insertTag.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if(s.length > 0){saveTag.visibility =View.VISIBLE} else {saveTag.visibility =View.INVISIBLE}
                }
            }
        })

        insertIng.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if(s.length > 0){saveIng.visibility =View.VISIBLE} else {saveIng.visibility =View.INVISIBLE}
                }
            }
        })
        saveTag.setOnClickListener { val newMealByRecipe = RecipeByTag(
            0, numberRecipe, insertTag.text.toString())
        model.addRecipeByMeal(newMealByRecipe)
        insertTag.setText("")}

        saveIng.setOnClickListener { val newIng = Ingredients(
            0,
            numberRecipe,
            insertIng.text.toString(),
            weightIng.text.toString().toInt()
        )
        model.insertIngridient(newIng)
        insertIng.setText("" )
        weightIng.setText("0")}
        recyclerTag.adapter = adapterTag

        recyclerTag.layoutManager = LinearLayoutManager(requireContext())

        ingredientRecycler.adapter = adaptorForIngredientRecyclerView
        ingredientRecycler.layoutManager = LinearLayoutManager(requireContext())

        takepicture.setOnClickListener {
            CropImage.activity().setAspectRatio(4, 3)
                .start(requireContext(), this)

        }





        model.mealUnic.observe(viewLifecycleOwner, {listMeal ->  arrayAdapterForMeal = ArrayAdapter(requireContext(), R.layout.drop_down_product, listMeal)
            arrayAdapterForMeal.setNotifyOnChange(true)
            insertTag.setAdapter(arrayAdapterForMeal)})


        model.productUnic.observe(viewLifecycleOwner, {listProd -> arrayAdapterForProduct = ArrayAdapter(requireContext(),R.layout.drop_down_product,listProd)
        arrayAdapterForProduct.setNotifyOnChange(true)
        insertIng.setAdapter(arrayAdapterForProduct)})






        return binding.root
    }
    fun setAdapter(it:Long){
        numberRecipe = it
        model.getIngridientForRecipe(numberRecipe).observe(viewLifecycleOwner, {
            adaptorForIngredientRecyclerView.setData(it)})
        model.mealByRecipe(numberRecipe).observe(viewLifecycleOwner, {adapterTag.setData(it)})

    }










    private fun setRecipe(it: Recipe) {
        numberRecipe = it.id
        if (it.title?.isNotEmpty() == true && titleRecipe.text.isNullOrEmpty()) {
            titleRecipe.setText(it.title)
        }



        if (it.link?.isNotEmpty() == true && link.text.isNullOrEmpty()) {
            link.setText(it.link)
        }



        link.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    model.updateRecipeLink(numberRecipe, s.toString())

                }

            }

        })

        instruction.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

            }
        })
        titleRecipe.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {

                    model.updateRecipeTitle(numberRecipe, s.toString())

                }
            }
        })

        if (it.picture == null) {
            pictureFood.setImageResource(R.drawable.sandwich)
            val pictureRec = pictureFood.drawable.toBitmap()
            val uri = bitmapToFile(pictureRec).toString()
            model.updateRecipePicture(numberRecipe, uri)
        } else {
            pictureFood.setImageURI(it.picture.toUri())
        }}




        private fun bitmapToFile(bitmap: Bitmap): Uri {
            // Get the context wrapper
            val wrapper = ContextWrapper(requireContext())

            // Initialize a new file instance to save bitmap object
            var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
            file = File(file, "${UUID.randomUUID()}.jpg")

            try {
                // Compress the bitmap and save in jpg format
                val stream: OutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                stream.flush()
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // Return the saved bitmap uri
            return Uri.parse(file.absolutePath)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    val resultUri: Uri? = result?.uriContent
                    val resultFilePath: String? = result?.getUriFilePath(requireContext())
                    pictureFood.setImageURI(resultUri)
                    val pictureRec = pictureFood.drawable.toBitmap()
                    val uri = bitmapToFile(pictureRec).toString()
                    model.updateRecipePicture(numberRecipe, uri)

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result!!.error
                }
            }
        }

    override fun deleteIngClick(position: Int) {
        val item = adaptorForIngredientRecyclerView.getIngrByPosition(position)
        model.deleteIngredient(item)
    }

    override fun deleteMealClick(position: Int) {
        val itemClick = adapterTag.getMealByPosition(position)
       model.delMeal(itemClick.idRecipeByTag)
    }




}
