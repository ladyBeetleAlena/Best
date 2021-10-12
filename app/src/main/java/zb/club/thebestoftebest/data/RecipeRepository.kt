package zb.club.thebestoftebest.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executors

class RecipeRepository(private val recipeDao: RecipeDao) {

    val mealUnic:LiveData<List<String>> = recipeDao.getMealsUnic()
    val categoryUnic:LiveData<List<String>> = recipeDao.getCategoryUnic()
    val productUnic:LiveData<List<String>> = recipeDao.getProductUnic()

    val allMenu: LiveData<List<Menu>> = recipeDao.getAllMenu()
    val allRecipe: LiveData<List<Recipe>> = recipeDao.getAllRecipe()
    val allMenuTemp: LiveData<List<MenuTemp>> = recipeDao.getTempMenu()
    val getTitleMenu:LiveData<List<String>> = recipeDao.getTitlemenu()
    val getDayCurrentMenu:LiveData<List<DateForCurrentMenu>> = recipeDao.getDayCurrentMenu()
    val getMenuTitle: LiveData<List<String>> = recipeDao.getMenuTitle()
    val getDishWithDays : LiveData<List<dishwithDate>> = recipeDao.getDishWithDays()
    val getAllCurrentMenu: LiveData<List<CurrentMenu>> = recipeDao.getAllCurrentMenu()
    val getAllServing:LiveData<List<Serving>> = recipeDao.getAllServing()
    val shoppingListFromCurrent:LiveData<List<SoppingTemp>> = recipeDao.getShoppingListFromCurrent()
    val getShoppingList: LiveData<List<ShoppList>> = recipeDao.getShoppingList()
    val getShoppingListWithDay: LiveData<List<ShoppingListWithDay>> = recipeDao.getShoppingListWithDay()
    val getThings:LiveData<List<Things>> = recipeDao.getAllThing()
    val getStringThing:LiveData<List<String>> =recipeDao.getAllThingString()
    val getAdding:LiveData<List<ShoppingAdding>> = recipeDao.getAllAdding()
    val getLastRecipe: LiveData<Recipe> = recipeDao.getLastRecipe()

    suspend fun insertThing(things: Things){
        recipeDao.insertThing(things)
    }
    suspend fun insertAddingShop(shoppingAdding: ShoppingAdding){
        recipeDao.insertAddingShop(shoppingAdding)
    }

    suspend fun updateThing(things: Things){
        recipeDao.updateThing(things)
    }

    suspend fun updateAddingShop(adding: ShoppingAdding){
        recipeDao.updateAddingShop(adding)
    }


    suspend fun daletThing(things: Things){
        recipeDao.daletThing(things)
    }

    suspend fun deleteAdding(adding: ShoppingAdding){
        recipeDao.deleteAdding(adding)
    }



    suspend fun clearThings(){
        recipeDao.clearThings()
    }


    suspend fun clearShoppingAdding(){
        recipeDao.clearShoppingAdding()
    }









    suspend fun insertShoppingList(shoppList: ShoppList){
        recipeDao.insertInShoppingList(shoppList)
    }
    suspend fun insertShoppingListWithDay(shoppingListWithDay: ShoppingListWithDay){
        recipeDao.insertShoppingListWithDay(shoppingListWithDay)
    }
    suspend fun updateShoppingList(shoppList: ShoppList){
        recipeDao.updateShoppingList(shoppList)
    }
    suspend fun updateShoppingListWithDay(shoppingListWithDay: ShoppingListWithDay){
        recipeDao.updateShoppingListWithDay(shoppingListWithDay)
    }

    suspend fun clearShoppingList(){
        recipeDao.clearShoppingList()
    }
    suspend fun clearShoppingWithDay(){
        recipeDao.clearShoppingWithDay()
    }

    fun getFromShoppListWithDayByDay(day:Int):LiveData<List<ShoppingListWithDay>>{
        return recipeDao.getFromShoppListWithDayByDay(day)
    }
   fun  getcurrentshoppingListDyDay(day:Int): LiveData<List<SoppingTemp>>{
      return recipeDao.getShoppingListfromCurrentByDay(day)

   }
    suspend fun insertServing(serving: Serving){
        recipeDao.insertServing(serving)

    }

    suspend fun updateServing(serving: Serving){
        recipeDao.updateServing(serving)
    }

    fun getRecipebyId(id: Long): LiveData<Recipe>{
        return recipeDao.getRecipebyId(id)
    }
    fun getMenuForDayMeals( day:Int, meal: String): LiveData<List<CurrentMenu>>{
        return recipeDao.getMenuForDayMeals( day, meal)
    }

    fun getCurrentMenuForDay(day: Int ): LiveData<List<CurrentMenu>>{
        return recipeDao.getCurrentMenuForDay(day)
    }
    suspend fun deleteByCurrentMenuByID(id:Long) {
        recipeDao.deleteByCurrentMenuByID(id)
    }
    fun getMenuForDay(menuTitle: String ): LiveData<List<CurrentMenu>>{
        return recipeDao.getMenuForDay(menuTitle)
    }



    suspend fun deletMenuByTitle(title:String){
        recipeDao.deleteMenuByTitle(title)
    }

    suspend fun deleteMenu(menu: Menu){
        recipeDao.deleteMenu(menu)
    }
    suspend fun updateMenu(menu: Menu){
        recipeDao.updatePMenu(menu)
    }
    fun getMenuByDayTitle(day: Int, title:String):LiveData<List<Menu>>{
        return recipeDao.getMenuForDayTitle(day, title)

    }

    suspend fun deletCurrentMenuByDay(day:Int){
        recipeDao.deleteByCurrentMenuByDate(day)
    }

    var mutableRecipe: MutableLiveData<Recipe> = MutableLiveData()
    fun getRecipeAdd(id:Long): Recipe{
        return recipeDao.getRecipeForAdd(id)
    }
    fun getRecypeAsync (id:Long){
        Executors.newSingleThreadExecutor().execute {
            val rec = getRecipeAdd(id)
            mutableRecipe.postValue(rec)   // <-- just doing this will trigger the observer and do next thing, such as, updating ui
        }
    }
    var mutableShoppingListWithDay: MutableLiveData<List<ShoppingListWithDay>> = MutableLiveData()
    fun getShoppingListWithDayByProduct(product:String):List<ShoppingListWithDay>{
        return recipeDao.getShoppingListWithDayByTitle(product)
    }
    fun getShoppListWithDayAsync(product: String) {
        Executors.newSingleThreadExecutor().execute {
            val shoppListWithDay = getShoppingListWithDayByProduct(product)
            mutableShoppingListWithDay.postValue(shoppListWithDay)   // <-- just doing this will trigger the observer and do next thing, such as, updating ui
        }
    }


    var mutableMenu: MutableLiveData<List<Menu>> = MutableLiveData()
    fun getMenu(title: String): List<Menu> {  // WARNING! run this in background thread else it will crash
        return recipeDao.getMenuByTitle(title)
    }

    fun getMenuAsync(title: String) {
        Executors.newSingleThreadExecutor().execute {
            val menus = getMenu(title)
            mutableMenu.postValue(menus)   // <-- just doing this will trigger the observer and do next thing, such as, updating ui
        }
    }




    fun getMenuByTitleFoShow(menuTitle: String): LiveData<List<Menu>>{
        return recipeDao.getMenuByTitleFoShow(menuTitle)
    }

    fun ingredientByID(id: Long): LiveData<List<Ingredients>> {
        return recipeDao.getIngridientForRecipe(id)
    }


    fun getRecipeByMeal(meal: String): LiveData<List<Recipe>> {
        return recipeDao.getRecipeByMeal(meal)
    }




    fun getMealForRecipe(id: Long): LiveData<List<RecipeByTag>> {

        return recipeDao.getMealForRecipe(id)
    }

    fun getCategoryForRecipe(id: Long): LiveData<List<RecipeByCategory>> {
        return recipeDao.getCategoryForRecipe(id)
    }

    suspend fun delIngrForRecipe(id: Long) {
        recipeDao.deleteIngridientForRecipe(id)
    }

    suspend fun delMealForRecipe(id: Long) {
        recipeDao.deleteMealForRecipe(id)
    }

    suspend fun delCatForRecipe(id: Long) {
        recipeDao.deleteCategoryForRecipe(id)
    }
    suspend fun delCurrentMenu(currentMenu: CurrentMenu) {
        recipeDao.deleteCurrentMenu(currentMenu)
    }

    suspend fun insertRecipe(recipe: Recipe): Long {

        return recipeDao.insertRec(recipe)


    }

    suspend fun insertCurrentMenu(currentMenu: CurrentMenu) {

        return recipeDao.insertCurrentMenu(currentMenu)


    }
    suspend fun updateCurrentMenu(currentMenu: CurrentMenu) {

        return recipeDao.updateCurrentMenu(currentMenu)


    }

    suspend fun insertMenu(menu: Menu) {

        return recipeDao.insertMenuString(menu)


    }
    suspend fun deletMenuForDayTitle(day: Int, menuTitle: String ){
        recipeDao.deletMenuForDayTitle(day, menuTitle)
    }
    suspend fun insertTempMenu(temp: MenuTemp) {

        return recipeDao.insertTempMenu(temp)


    }
    suspend fun delTempMenu() {

        return recipeDao.clearTempMenu()


    }


    suspend fun updateRecipeInstruction(id:Long, instruction:String){
        recipeDao.updateRecipeInstruction(id, instruction)
    }



    suspend fun updateRecipeLink(id:Long, link:String){
        recipeDao.updateRecipeLink(id, link)
    }



    suspend fun updateRecipePicture(id:Long, picture:String){
        recipeDao.updateRecipePicture(id, picture)
    }

    suspend fun updateRecipeInput(id:Long, input:Boolean){
        recipeDao.updateRecipeInput(id, input)
    }




    suspend fun updateRecipeTitle(id:Long, title:String){
        recipeDao.updateRecipeTitle(id, title)
    }

    suspend fun updateRec(recipe: Recipe) {
        recipeDao.updaterec(recipe)
    }

    suspend fun insertIngredient(ingredients: Ingredients) {
        recipeDao.insertIngredients(ingredients)
    }





    suspend fun addRecipeByMeal(recipeByTag: RecipeByTag) {
        recipeDao.insertRecipeByMeal(recipeByTag)
    }

    suspend fun addRecipeByCategory(recipeByCategory: RecipeByCategory) {
        recipeDao.insertRecipeByCategory(recipeByCategory)
    }

    suspend fun delrecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    suspend fun clearCurrentMenu() {
        recipeDao.clearCurrentMenu()
    }

    fun getDishByDay(day:Int): LiveData<List<dishbyDate>>{
        return recipeDao.getDishFromDay(day)

    }


    fun getTempMenuForDayMeals(day: Int, meal: String): LiveData<List<MenuTemp>>{
        return recipeDao.getTempMenuForDayMeals(day, meal)
    }


     var mutableTempMenuByDay: MutableLiveData<List<MenuTemp>> = MutableLiveData()

     fun getTempMenuByDays(day: Int): List<MenuTemp>{
         return recipeDao.getTempMenuForDay(day)
     }
    fun getTempMenuAsync(day: Int) {
        Executors.newSingleThreadExecutor().execute {
            val menusTemp = getTempMenuByDays(day)
           mutableTempMenuByDay.postValue(menusTemp)   // <-- just doing this will trigger the observer and do next thing, such as, updating ui
        }
    }







    suspend fun updateTempMenu(temp: MenuTemp){
        recipeDao.updateTempMenu(temp)
    }

    suspend fun deleteTempMenu(temp: MenuTemp){
        recipeDao.deleteTempMenu(temp)
    }



    suspend fun deleteIngredient(ingredients: Ingredients){
        recipeDao.deleteIngredient(ingredients)
    }



    suspend fun deleteCategory(id:Long){recipeDao.deleteCategory(id)}

    suspend fun deleteMeal(id:Long) {
        recipeDao.deleteMeal(id)
    }

}
