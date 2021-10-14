package zb.club.thebestoftebest.cookbook.viewmodelcookbook

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import zb.club.thebestoftebest.data.*

class ViewModelAddRecipe(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository

    var ingridientList: ArrayList<Ingredients> = ArrayList()
    var mealId: ArrayList<String> = ArrayList()
    var category: ArrayList<String> = ArrayList()
    var picture: Bitmap? = null
    var allRecipe: LiveData<List<Recipe>>

    var tagUnic: LiveData<List<String>>
    var productUnic: LiveData<List<String>>
    var getLastRecipe: LiveData<Recipe>
    init {
        val recipeDao = RecipeDataBase.getDatabase(
            application
        ).recipeDao()
        repository = RecipeRepository(recipeDao)
        allRecipe = repository.allRecipe

        tagUnic = repository.tagUnic
        productUnic = repository.productUnic
        getLastRecipe = repository.getLastRecipe


    }

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    fun insertRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO) {repository.insertRecipe(recipe)}}


     fun insertInstruction(inst:Instructions){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertInstruction(inst)
        }

    }


    fun deleteInstruction(inst: Instructions){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteInstruction(inst)
        }

    }


    fun getInstructionForRecipe(idRecipe:Long):LiveData<List<Instructions>>{
        return repository.getInstructionForRecipe(idRecipe)
    }







    fun deleteIngredient(ingredients: Ingredients){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteIngredient(ingredients)
        }

    }


    fun insertIngridient(ingredients: Ingredients) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertIngredient(ingredients)
        }
    }

    fun deleteIngrByRecipe(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delIngrForRecipe(id)
        }
    }

    fun getIngridientForRecipe(id: Long): LiveData<List<Ingredients>>{
        return repository.ingredientByID(id)
    }


    fun getRecipebyId(id:Long):LiveData<Recipe>{
        return repository.getRecipebyId(id)
    }


    var status = MutableLiveData<Boolean?>()



    private var _recipeNumber = MutableLiveData<Long>()


    val recipeNumber: LiveData<Long>
        get() = _recipeNumber


    fun saveRe(recipeNeew: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            var value: Long = 0
            value = repository.insertRecipe(recipeNeew)
            _recipeNumber.postValue(value)
            status.postValue(true)
        }


    }

    val liveRec: LiveData<Recipe> = repository.mutableRecipe

    fun getNumberAsync(id: Long) {
        repository.getRecypeAsync(id)
    }



    fun ingredientByRec(id: Long): LiveData<List<Ingredients>> {

        return repository.ingredientByID(id)
    }
    fun mealByRecipe(id: Long): LiveData<List<RecipeByTag>>{
        return repository.getTagForRecipe(id)
    }


    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRec(recipe)
        }
    }







    fun updateRecipeLink(id:Long, link:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecipeLink(id, link)
        }
    }



    fun updateRecipePicture(id:Long, picture:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecipePicture(id, picture)
        }
    }

    fun updateRecipeInput(id:Long, input:Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecipeInput(id, input)
        }
    }






    fun  updateRecipeTitle(id: Long, title:String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecipeTitle(id, title)
        }
    }


    fun deleteTagForRecipe(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTag(id)
        }
    }









    fun addRecipeByTag(recipeByTag: RecipeByTag) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecipeByTag(recipeByTag)
        }
    }




    fun delRecipe(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delrecipe(recipe)
        }
    }
    fun delTag(id:Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTag(id)
        }

    }





}