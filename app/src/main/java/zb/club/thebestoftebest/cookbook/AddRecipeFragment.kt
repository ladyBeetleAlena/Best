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
import android.widget.*
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
import zb.club.thebestoftebest.data.Instructions
import zb.club.thebestoftebest.data.Recipe
import zb.club.thebestoftebest.data.RecipeByTag
import zb.club.thebestoftebest.databinding.FragmentAddRecipeBinding
import zb.club.thebestoftebest.onboarding.AdapterAddTag
import zb.club.thebestoftebest.onboarding.AdapterInstructions
import zb.club.thebestoftebest.onboarding.AdaptorAddIngridient
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class AddRecipeFragment : Fragment(), AdaptorAddIngridient.OnItemIngrClickListener,
    AdapterAddTag.OnItemMealClickListener, AdapterInstructions.InstructinListwener {

    private val args by navArgs<AddRecipeFragmentArgs>()
    private lateinit var model: ViewModelAddRecipe
    var numberRecipe: Long = 0
    lateinit var titleRecipe: TextInputEditText
    lateinit var instruction: TextInputEditText
    lateinit var link: TextInputEditText
    lateinit var pictureFood: ImageView
    lateinit var takepicture: ImageView
    lateinit var takeinstrPic: ImageView
    lateinit var instructionImage: ImageView
    lateinit var instructBinPick: ImageView
    lateinit var arrayAdapterForMeal: ArrayAdapter<String>
    lateinit var arrayAdapterForProduct: ArrayAdapter<String>
    lateinit var reciclerInst: RecyclerView
    lateinit var adapterInst: AdapterInstructions

    private var imageNo: Int? = null

    var ingredientList = mutableListOf<Ingredients>()
    var mealList = mutableListOf<RecipeByTag>()
    var instList = mutableListOf<Instructions>()
    lateinit var adapterTag: AdapterAddTag
    lateinit var recyclerTag: RecyclerView

    lateinit var adaptorForIngredientRecyclerView: AdaptorAddIngridient
    lateinit var ingredientRecycler: RecyclerView
    lateinit var insertIng: AutoCompleteTextView
    lateinit var weightIng: TextInputEditText
    lateinit var saveIng: ImageView

    lateinit var insertTag: AutoCompleteTextView
    lateinit var saveTag: ImageView
    lateinit var saveInstruction: ImageButton
    lateinit var linearImageInstr: LinearLayout


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
        saveInstruction = binding.imageButtonInstruction


        recyclerTag = binding.recyclerTag
        reciclerInst = binding.recyclerViewInstructionRow


        insertTag = binding.inputTag

        saveTag = binding.imageButtonSaveTag


        ingredientRecycler = binding.reciclerIngredients
        adapterInst = AdapterInstructions(instList, this)

        adapterTag = AdapterAddTag(mealList, this)
        adaptorForIngredientRecyclerView = AdaptorAddIngridient(ingredientList, this)
        titleRecipe = binding.inputtitleEditText
        pictureFood = binding.imageViewPictureFood
        takepicture = binding.imageViewTakePicture
        takeinstrPic = binding.imageViewAddInstructionPic
        instructBinPick = binding.imageViewBinInstrPic
        linearImageInstr = binding.linearLayoutImageInst
        link = binding.linkText
        instruction = binding.inputInstruction

        setRecipe(args.recipeFoEdit)
        setAdapter(args.recipeFoEdit.id)
        numberRecipe = args.recipeFoEdit.id


        insertTag.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.length > 0) {
                        saveTag.visibility = View.VISIBLE
                    } else {
                        saveTag.visibility = View.INVISIBLE
                    }
                }
            }
        })

        instruction.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

                if (s!!.length > 0) {
                    saveInstruction.visibility = View.VISIBLE
                } else if (linearImageInstr.childCount == 0 && s.length == 0) {
                    saveInstruction.visibility = View.INVISIBLE
                }


            }
        })

        insertIng.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.length > 0) {
                        saveIng.visibility = View.VISIBLE
                    } else {
                        saveIng.visibility = View.INVISIBLE
                    }
                }
            }
        })
        saveTag.setOnClickListener {
            val newTagByRecipe = RecipeByTag(
                0, numberRecipe, insertTag.text.toString()
            )
            model.addRecipeByTag(newTagByRecipe)
            insertTag.setText("")
        }

        saveIng.setOnClickListener {
            val newIng = Ingredients(
                0,
                numberRecipe,
                insertIng.text.toString(),
                weightIng.text.toString().toInt()
            )
            model.insertIngridient(newIng)
            insertIng.setText("")
            weightIng.setText("0")
        }
        recyclerTag.adapter = adapterTag
        reciclerInst.adapter = adapterInst
        reciclerInst.layoutManager = LinearLayoutManager(requireContext())

        recyclerTag.layoutManager = LinearLayoutManager(requireContext())

        ingredientRecycler.adapter = adaptorForIngredientRecyclerView
        ingredientRecycler.layoutManager = LinearLayoutManager(requireContext())

        takepicture.setOnClickListener {

            imageNo = 1
            getImageClick()


        }
        takeinstrPic.setOnClickListener {
            imageNo = 2
            getImageClick()
        }





        model.tagUnic.observe(viewLifecycleOwner, { listTag ->
            arrayAdapterForMeal = ArrayAdapter(
                requireContext(),
                R.layout.drop_down_product,
                listTag
            )
            arrayAdapterForMeal.setNotifyOnChange(true)
            insertTag.setAdapter(arrayAdapterForMeal)
        })


        model.productUnic.observe(viewLifecycleOwner, { listProd ->
            arrayAdapterForProduct = ArrayAdapter(
                requireContext(),
                R.layout.drop_down_product,
                listProd
            )
            arrayAdapterForProduct.setNotifyOnChange(true)
            insertIng.setAdapter(arrayAdapterForProduct)
        })
        instructBinPick.setOnClickListener {
            linearImageInstr.removeAllViews()

            instructBinPick.visibility = View.INVISIBLE
            if (instruction.text!!.length == 0) {
                saveInstruction.visibility = View.INVISIBLE
            }
        }


        saveInstruction.setOnClickListener {
            var picInstr: String? = null
            if (linearImageInstr.childCount == 0) {
                picInstr = null
            } else {
                val row = linearImageInstr.getChildAt(0)
                val image = row.findViewById<ImageView>(R.id.imageInstruction)


                val pictureInstrBitmap = image.drawable.toBitmap()
                picInstr = bitmapToFile(pictureInstrBitmap).toString()
            }
            val instructionInsert =
                Instructions(
                    0,
                    numberRecipe,
                    instruction.text.toString(),
                    picInstr
                )
            if (instructionInsert != null) {
                model.insertInstruction(instructionInsert)
            }
            if (linearImageInstr.childCount > 0) {
                linearImageInstr.removeAllViews()
                instructBinPick.visibility = View.INVISIBLE
            }
            instruction.text?.clear()
            saveInstruction.visibility = View.INVISIBLE


        }







        return binding.root
    }


    fun setAdapter(it: Long) {
        numberRecipe = it
        model.getIngridientForRecipe(numberRecipe).observe(viewLifecycleOwner, {
            adaptorForIngredientRecyclerView.setData(it)
        })
        model.mealByRecipe(numberRecipe).observe(viewLifecycleOwner, { adapterTag.setData(it) })
        model.getInstructionForRecipe(numberRecipe).observe(viewLifecycleOwner, {
            adapterInst.setData(
                it
            )
        })

    }


    fun getImageClick() {
        CropImage.activity().setAspectRatio(4, 3)
            .start(requireContext(), this)
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
        }
    }


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
                when (imageNo) {
                    1 -> {
                        pictureFood.setImageURI(resultUri)
                        val pictureRec = pictureFood.drawable.toBitmap()
                        val uri = bitmapToFile(pictureRec).toString()
                        model.updateRecipePicture(numberRecipe, uri)
                    }
                    2 -> {

                        val inflater = LayoutInflater.from(requireContext())
                        val row: View = inflater.inflate(R.layout.image_instruction, null)
                        instructionImage = row.findViewById(R.id.imageInstruction)
                        instructionImage.setImageURI(resultUri)
                        linearImageInstr.addView(instructionImage)
                        instructBinPick.visibility = View.VISIBLE
                        saveInstruction.visibility = View.VISIBLE


                    }

                }


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
        model.delTag(itemClick.idRecipeByTag)
    }

    override fun deleteInstructionClick(position: Int) {
        val instForDel = adapterInst.getInstByPosition(position)
        model.deleteInstruction(instForDel)
    }


}
