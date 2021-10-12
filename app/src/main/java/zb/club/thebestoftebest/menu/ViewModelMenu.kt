package zb.club.thebestoftebest.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import zb.club.thebestoftebest.data.*

class ViewModelMenu (application: Application) : AndroidViewModel(application){
    private val repository: RecipeRepository

    var getTitleMenu: LiveData<List<String>>
    var tempMenu: LiveData<List<MenuTemp>>

    var allMeal: LiveData<List<String>>
    var allRecipe: LiveData<List<Recipe>>
    var getAllCurrentMenu:LiveData<List<CurrentMenu>>
    var getAllServing: LiveData<List<Serving>>
    var date: ArrayList<DateForCurrentMenu> = ArrayList()

    var shoppingListfromCurrentMenu: LiveData<List<SoppingTemp>>
    var dayCurrentMenu: LiveData<List<DateForCurrentMenu>>
    val getShoppingList: LiveData<List<ShoppList>>


    init {
        val recipeDao = RecipeDataBase.getDatabase(
            application
        ).recipeDao()
        repository = RecipeRepository(recipeDao)
        getTitleMenu = repository.getTitleMenu
        allMeal = repository.tagUnic

        tempMenu = repository.allMenuTemp
        allRecipe = repository.allRecipe
        getAllCurrentMenu = repository.getAllCurrentMenu
        getAllServing = repository.getAllServing
        dayCurrentMenu = repository.getDayCurrentMenu
        shoppingListfromCurrentMenu = repository.shoppingListFromCurrent
        getShoppingList =  repository.getShoppingList

    }



    fun getShoppingListForDay(day: Int): LiveData<List<SoppingTemp>>{
        return repository.getcurrentshoppingListDyDay(day)
    }
  fun getCurrentMenuForDay(day: Int ): LiveData<List<CurrentMenu>>{
      return repository.getCurrentMenuForDay(day)
  }


    fun getMenuForDayMeals( day:Int, meal: String): LiveData<List<CurrentMenu>>{
       return repository.getMenuForDayMeals(day, meal)
    }

    fun getMenuForDay(menuTitle: String): LiveData<List<CurrentMenu>>{
        return repository.getMenuForDay(menuTitle)
    }

    fun insertShoppingList(shoppList: ShoppList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertShoppingList(shoppList)
        }
    }
    fun insertShoppingListWithDay(shoppingListWithDay: ShoppingListWithDay) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertShoppingListWithDay(shoppingListWithDay)
        }
    }

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val selectedRecipes =  MutableLiveData<ArrayList<Recipe>>()
    fun getSelectedRecipe(): MutableLiveData<ArrayList<Recipe>> {
        return selectedRecipes
    }

    fun setSelectedRecipes(recipes: ArrayList<Recipe>) {
        selectedRecipes.value = recipes
    }


    private val updateCurrentItem =  MutableLiveData<CurrentMenu>()
    fun getCurrentItem(): MutableLiveData<CurrentMenu>{
        return updateCurrentItem
    }

    fun setCurrentItem(currentItem: CurrentMenu) {
        updateCurrentItem.value = currentItem
    }






    val liveMenu: LiveData<List<Menu>> = repository.mutableMenu

    fun getWordsAsync(title: String) {
        repository.getMenuAsync(title)
    }


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)






    fun insertTempMenu(temp: MenuTemp) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTempMenu(temp)
        }
    }

    fun getTempMenuForDayMeals(day: Int, meal: String): LiveData<List<MenuTemp>>{
        return repository.getTempMenuForDayMeals(day, meal)
    }



    val liveTempMenu: LiveData<List<MenuTemp>> = repository.mutableTempMenuByDay

    fun getdayFoTempAsync(day: Int) {
        repository.getTempMenuAsync(day)
    }





    fun insertMenu(menu: Menu) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMenu(menu)
        }
    }

    fun insertCurrentMenu(currentMenu: CurrentMenu) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCurrentMenu(currentMenu)
        }
    }

    fun updatetCurrentMenu(currentMenu: CurrentMenu) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCurrentMenu(currentMenu)
        }
    }

    fun clearcurrentMenu() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearCurrentMenu()
        }
    }
    fun dalcurrentMenu(currentMenu: CurrentMenu) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delCurrentMenu(currentMenu)
        }
    }
    fun updateTempMenu(temp: MenuTemp){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTempMenu(temp)
        }
    }

     fun deleteOneOfTempMenu(temp: MenuTemp){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTempMenu(temp)
        }
    }

     fun clearTempMenu(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delTempMenu()
        }
    }

    fun clearShoppList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearShoppingList()
        }
    }
    fun clearShoppWithDay() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearShoppingWithDay()
        }
    }





}