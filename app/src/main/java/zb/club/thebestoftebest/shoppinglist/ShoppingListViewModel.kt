package zb.club.thebestoftebest.shoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zb.club.thebestoftebest.data.*

class ShoppingListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository

    var dayCurrentMenu: LiveData<List<DateForCurrentMenu>>
    var shoppingListfromCurrentMenu: LiveData<List<SoppingTemp>>
    val getShoppingList: LiveData<List<ShoppList>>
    val getShoppingListWithDay: LiveData<List<ShoppingListWithDay>>
    val getThings:LiveData<List<Things>>
    val getAdding:LiveData<List<ShoppingAdding>>
    val getStringThing:LiveData<List<String>>

    init {
        val recipeDao = RecipeDataBase.getDatabase(
            application
        ).recipeDao()
        repository = RecipeRepository(recipeDao)

        dayCurrentMenu = repository.getDayCurrentMenu
        shoppingListfromCurrentMenu = repository.shoppingListFromCurrent
        getShoppingList =  repository.getShoppingList
        getShoppingListWithDay= repository.getShoppingListWithDay
        getThings = repository.getThings
        getAdding= repository.getAdding
        getStringThing = repository.getStringThing
    }






     fun insertThing(things: Things){
        viewModelScope.launch(Dispatchers.IO) {
        repository.insertThing(things)}
    }
     fun insertAddingShop(shoppingAdding: ShoppingAdding){
        viewModelScope.launch(Dispatchers.IO) {
        repository.insertAddingShop(shoppingAdding)}
    }

     fun updateThing(things: Things){
        viewModelScope.launch(Dispatchers.IO) {
        repository.updateThing(things)}
    }

     fun updateAddingShop(adding: ShoppingAdding){
        viewModelScope.launch(Dispatchers.IO) {
        repository.updateAddingShop(adding)}
    }


     fun daletThing(things: Things){
         viewModelScope.launch(Dispatchers.IO) {
        repository.daletThing(things)}
    }

   fun deleteAdding(adding: ShoppingAdding){
       viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAdding(adding)}
    }



   fun clearThings(){
       viewModelScope.launch(Dispatchers.IO) {
        repository.clearThings()}
    }


  fun clearShoppingAdding(){
      viewModelScope.launch(Dispatchers.IO) {
        repository.clearShoppingAdding()}
    }

    fun getFromShoppListWithDayByDay(day: Int): LiveData<List<ShoppingListWithDay>>{
        return repository.getFromShoppListWithDayByDay(day)
    }

    fun insertShoppingList(shoppList: ShoppList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertShoppingList(shoppList)
        }
    }

    fun updateShoppingList(shoppList: ShoppList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateShoppingList(shoppList)
        }
    }
    fun insertShoppingListWithDay(shoppingListWithDay: ShoppingListWithDay) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertShoppingListWithDay(shoppingListWithDay)
        }
    }
    fun updateShoppingListWithDay(shoppingListWithDay: ShoppingListWithDay) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateShoppingListWithDay(shoppingListWithDay)
        }
    }

    fun getShoppingListForDay(day: Int): LiveData<List<SoppingTemp>>{
        return repository.getcurrentshoppingListDyDay(day)
    }


    private val selectedProduct =  MutableLiveData<ShoppList>()
    fun getSelectedProduct(): MutableLiveData<ShoppList> {
        return selectedProduct
    }

    fun setSelectedProduct(product: ShoppList) {
        selectedProduct.value = product
    }

    private val unselectedProduct =  MutableLiveData<ShoppList>()
    fun getUnSelectedProduct(): MutableLiveData<ShoppList> {
        return unselectedProduct
    }

    fun setunselectedProduct(product:ShoppList) {
        unselectedProduct.value = product
    }





    private val selectedProductWithDay =  MutableLiveData<ShoppingListWithDay>()
    fun getSelectedProductWithDay(): MutableLiveData<ShoppingListWithDay> {
        return selectedProductWithDay
    }

    fun setSelectedProductWithDay(product: ShoppingListWithDay) {
        selectedProductWithDay.value = product
    }

    private val unselectedProductWithDay =  MutableLiveData<ShoppingListWithDay>()
    fun getUnSelectedProductWithDay(): MutableLiveData<ShoppingListWithDay> {
        return unselectedProductWithDay
    }

    fun setunselectedProductWithDay(product:ShoppingListWithDay) {
        unselectedProductWithDay.value = product
    }


    val liveShoppingListWithDay: LiveData<List<ShoppingListWithDay>> = repository.mutableShoppingListWithDay

    fun getShoppingListWithDayAsync(product: String) {
        repository.getShoppListWithDayAsync(product)
    }


    private val selectedThing =  MutableLiveData<ShoppingAdding>()
    fun getSelectedThing(): MutableLiveData<ShoppingAdding> {
        return selectedThing
    }

    fun setSelectedThing(thing: ShoppingAdding) {
        selectedThing.value = thing
    }

    private val unselectedThing =  MutableLiveData<ShoppingAdding>()
    fun getUnSelectedThing(): MutableLiveData<ShoppingAdding> {
        return unselectedThing
    }

    fun setunselectedThing(thing:ShoppingAdding) {
        unselectedThing.value = thing
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