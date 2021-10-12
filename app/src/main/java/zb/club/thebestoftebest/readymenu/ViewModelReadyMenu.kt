package zb.club.thebestoftebest.readymenu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zb.club.thebestoftebest.data.*

class ViewModelReadyMenu (application: Application) : AndroidViewModel(application){
    private val repository: RecipeRepository
    var getMenuTitle: LiveData<List<String>>
    var dayCurrentMenu: LiveData<List<DateForCurrentMenu>>
    var getDishWithDays: LiveData<List<dishwithDate>>
    var getAllCurrentMenu: LiveData<List<CurrentMenu>>
    var getAllServing: LiveData<List<Serving>>
    var allRecipe: LiveData<List<Recipe>>

    fun getDishByDay(day: Int): LiveData<List<dishbyDate>>{
         return repository.getDishByDay(day)

    }
    init {
        val recipeDao = RecipeDataBase.getDatabase(
            application
        ).recipeDao()
        repository = RecipeRepository(recipeDao)
        getAllServing = repository.getAllServing
        getMenuTitle = repository.getTitleMenu
        dayCurrentMenu = repository.getDayCurrentMenu
        getDishWithDays = repository.getDishWithDays
        getAllCurrentMenu = repository.getAllCurrentMenu
        allRecipe = repository.allRecipe
    }

    fun getMenuByTitle(title:String): LiveData<List<Menu>> { return repository.getMenuByTitleFoShow(title)}



    fun deleteMenu(menu: Menu){
        viewModelScope.launch(Dispatchers.IO){
        repository.deleteMenu(menu)}
    }

    fun deleteMenuByDayTitle(day: Int, title: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.deletMenuForDayTitle(day, title)}
    }
  fun updateMenu(menu: Menu){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateMenu(menu)}
    }
    fun getMenuByDayTitle(day: Int, title:String):LiveData<List<Menu>>{
        return  repository.getMenuByDayTitle(day, title)

    }


    fun getRecipebyId(id: Long): LiveData<Recipe>{
        return repository.getRecipebyId(id)
    }
    fun getIngredientByRecipeId(id:Long): LiveData<List<Ingredients>>{
        return repository.ingredientByID(id)
    }

    fun insertMenu(menu: Menu) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMenu(menu)
        }
    }
    fun deleteMenuByTitle(title:String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletMenuByTitle(title)
        }
    }

    fun deleteCurrentMenuByDay(day: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletCurrentMenuByDay(day)
        }
    }

    fun getCurrentMenuByDay(day: Int):LiveData<List<CurrentMenu>> {

           return repository. getCurrentMenuForDay(day)

    }
    fun deleteCurrentMenuByID(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteByCurrentMenuByID(id)
        }
    }
    fun updatetCurrentMenu(currentMenu: CurrentMenu) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCurrentMenu(currentMenu)
        }
    }

    fun insertServing(serving: Serving){
        viewModelScope.launch(Dispatchers.IO) {
       repository.insertServing(serving)}

    }

  fun updateServing(serving: Serving){
      viewModelScope.launch(Dispatchers.IO) {
        repository.updateServing(serving)}
    }


}
