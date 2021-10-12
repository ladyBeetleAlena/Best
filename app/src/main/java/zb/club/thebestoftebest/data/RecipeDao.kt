package zb.club.thebestoftebest.data

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface RecipeDao {

    @Query("SELECT * FROM serving ")
    fun getAllServing(): LiveData<List<Serving>>
    @Insert
    suspend fun insertServing(serving: Serving)
    @Update
    suspend fun updateServing(serving: Serving)

    @Insert
    suspend fun insertMenuString(menu: Menu)
    @Query("SELECT * FROM menu ")
    fun getAllMenu(): LiveData<List<Menu>>
    @Query("DELETE FROM menu WHERE menuTitle LIKE :menuTitle")
    suspend fun deleteMenuByTitle(menuTitle:String)

    @Query("SELECT menuTitle FROM menu GROUP BY menuTitle")
    fun getTitlemenu(): LiveData<List<String>>


    @Query("SELECT DISTINCT tag FROM recipebytag ")
    fun getTagUnic():LiveData<List<String>>



    @Query("SELECT DISTINCT product FROM ingredients ")
    fun getProductUnic():LiveData<List<String>>


    @Query("SELECT * FROM menu WHERE menuTitle LIKE :menuTitle ORDER BY id ")
    fun getMenuByTitle(menuTitle: String): List<Menu>
    @Query("SELECT * FROM menu WHERE menuTitle LIKE :menuTitle ORDER BY id ")
    fun getMenuByTitleFoShow(menuTitle: String): LiveData<List<Menu>>


    @Query("SELECT * FROM menu WHERE day = :day AND menuTitle LIKE :menuTitle" )
    fun getMenuForDayTitle(day: Int, menuTitle: String ): LiveData<List<Menu>>
    @Delete
    suspend fun deleteMenu(menu: Menu)
    @Update
    suspend fun updatePMenu(menu: Menu)
    @Query("DELETE  FROM menu WHERE day = :day AND menuTitle LIKE :menuTitle" )
    suspend fun deletMenuForDayTitle(day: Int, menuTitle: String )


    @Insert
    suspend fun insertSetting(setting: Setting)

    @Query("DELETE FROM setting")
    suspend fun clearSetting()

    @Query("SELECT * FROM setting")
    fun getsetting(): LiveData<List<Setting>>



    @Insert
    suspend fun insertCurrentMenu(currentMenu: CurrentMenu)

    @Query("SELECT * FROM currentmenu WHERE day = :day" )
    fun getCurrentMenuForDay(day: Int ): LiveData<List<CurrentMenu>>

    @Query("DELETE FROM currentmenu")
    suspend fun clearCurrentMenu()

    @Query("SELECT * FROM currentmenu" )
    fun getAllCurrentMenu(): LiveData<List<CurrentMenu>>
    @Delete
    suspend fun deleteCurrentMenu(currentMenu: CurrentMenu)
    @Update
    suspend fun updateCurrentMenu(currentMenu: CurrentMenu)
    @Query("DELETE FROM currentmenu WHERE day = :day")
    suspend fun deleteByCurrentMenuByDate(day:Int)

    @Query("DELETE FROM currentmenu WHERE id = :id")
    suspend fun deleteByCurrentMenuByID(id:Long)




    @Insert
    suspend fun insertTempMenu(temp: MenuTemp)

    @Query("SELECT * FROM menutemp ORDER BY meal")
    fun getTempMenu(): LiveData<List<MenuTemp>>

    @Query("DELETE FROM menutemp")
    suspend fun clearTempMenu()




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRec(recipe: Recipe): Long



    @Query ("UPDATE recipe SET title = :title WHERE id = :id")
    suspend fun updateRecipeTitle(id:Long, title:String)





    @Query ("UPDATE recipe SET link = :link WHERE id = :id")
    suspend fun updateRecipeLink(id:Long, link:String)


    @Query ("UPDATE recipe SET picture = :picture WHERE id = :id")
    suspend fun updateRecipePicture(id:Long, picture:String)

    @Query ("UPDATE recipe SET input = :input WHERE id = :id")
    suspend fun updateRecipeInput(id:Long, input:Boolean)


    @Update
    suspend fun updaterec(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe")
    fun getAllRecipe(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe ORDER BY id DESC LIMIT 1")
    fun getLastRecipe(): LiveData<Recipe>

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipebyId(id: Long): LiveData<Recipe>





    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserIngredient(ingredients: Ingredients)

    @Transaction
    suspend fun insertIngredients(ingredients: Ingredients) {
        try {
            inserIngredient(ingredients)
        } catch (exception: SQLiteConstraintException) {

        }
    }

    @Delete
    suspend fun deleteIngredient(ingredients: Ingredients)


    @Query("SELECT * FROM ingredients WHERE idrecipe = :id")
    fun getIngridientForRecipe(id: Long): LiveData<List<Ingredients>>


    @Query("SELECT * FROM recipebytag WHERE idRecipes = :id")
    fun getMealForRecipe(id: Long): LiveData<List<RecipeByTag>>




    @Query("DELETE FROM ingredients WHERE idrecipe = :id")
    fun deleteIngridientForRecipe(id: Long)

    @Query("DELETE  FROM recipebytag WHERE idRecipes = :id")
    fun deleteMealForRecipe(id: Long)




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeByTag(recipeByTag: RecipeByTag)


    @Transaction
    suspend fun insertRecipeByTags(recipeByTag: RecipeByTag) {
        try {
           insertRecipeByTag(recipeByTag)
        } catch (exception: SQLiteConstraintException) {

        }
    }


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeBySeason(recipeToSeason: RecipeToSeason)






    @Query("SELECT ingredients.product as product, SUM(currentmenu.childrenServings*currentmenu.childAdultK*ingredients.quantity + currentmenu.adultServing*ingredients.quantity) as quantity FROM ingredients JOIN currentmenu on  ingredients.idrecipe = currentmenu.idRecipe GROUP BY product")
    fun getShoppingListFromCurrent(): LiveData<List<SoppingTemp>>


    @Query("SELECT ingredients.product as product, SUM(currentmenu.childrenServings*currentmenu.childAdultK*ingredients.quantity + currentmenu.adultServing*ingredients.quantity) as quantity FROM ingredients JOIN currentmenu on  ingredients.idrecipe = currentmenu.idRecipe WHERE currentmenu.day =:day GROUP BY product")
    fun getShoppingListfromCurrentByDay(day:Int): LiveData<List<SoppingTemp>>



    @Insert
    suspend fun insertInShoppingList(shoppList: ShoppList)
    @Update
    suspend fun updateShoppingList(shoppList: ShoppList)

    @Insert
    suspend fun insertShoppingListWithDay(shoppingListWithDay: ShoppingListWithDay)
    @Update
    suspend fun updateShoppingListWithDay(shoppingListWithDay: ShoppingListWithDay)






    @Query("DELETE FROM shopplist")
    suspend fun clearShoppingList()
    @Query("DELETE FROM shoppinglistwithday")
    suspend fun clearShoppingWithDay()

    @Query("SELECT * FROM shopplist")
    fun getShoppingList(): LiveData<List<ShoppList>>

    @Query("SELECT * FROM shoppinglistwithday")
    fun getShoppingListWithDay(): LiveData<List<ShoppingListWithDay>>


    @Query("SELECT * FROM shoppinglistwithday WHERE day = :day")
    fun getFromShoppListWithDayByDay(day:Int):LiveData<List<ShoppingListWithDay>>


    @Query("SELECT * FROM shoppinglistwithday WHERE product LIKE :product ")
    fun getShoppingListWithDayByTitle(product: String): List<ShoppingListWithDay>



    @Query("SELECT day, calendarDay FROM currentmenu GROUP BY day ORDER BY day ASC")
    fun getDayCurrentMenu(): LiveData<List<DateForCurrentMenu>>


    @Query("SELECT DISTINCT menuTitle from menu ORDER BY 'ASC'")
    fun getMenuTitle():LiveData<List<String>>

    @Query("SELECT recipe.id as recipeId, currentmenu.titleRecipe, currentmenu.meal, currentmenu.childrenServings, currentmenu.adultServing, currentmenu.childAdultK, recipe.picture  FROM currentmenu JOIN recipe on recipe.id = currentmenu.idRecipe WHERE currentmenu.day = :day ORDER BY day ASC")
    fun getDishFromDay(day:Int): LiveData<List<dishbyDate>>

    @Query("SELECT currentmenu.day, currentmenu.calendarDay, recipe.id as recipeId, currentmenu.titleRecipe, currentmenu.meal, currentmenu.childrenServings, currentmenu.adultServing, currentmenu.childAdultK, recipe.picture  FROM currentmenu JOIN recipe on recipe.id = currentmenu.idRecipe ORDER BY day ASC")
    fun getDishWithDays(): LiveData<List<dishwithDate>>


    @Query("SELECT * from currentmenu WHERE menuTitle LIKE :menuTitle ")
    fun getMenuForDay(menuTitle: String): LiveData<List<CurrentMenu>>

    @Query("SELECT * from currentmenu WHERE day =:day AND meal LIKE :meal")
    fun getMenuForDayMeals(day: Int, meal: String): LiveData<List<CurrentMenu>>


    @Query("SELECT * from menutemp WHERE day =:day AND meal LIKE :meal")
    fun getTempMenuForDayMeals(day: Int, meal: String): LiveData<List<MenuTemp>>

    @Query("SELECT * from menutemp WHERE day =:day ")
    fun getTempMenuForDay(day: Int): List<MenuTemp>

    @Query("SELECT * from recipe WHERE id = :id")
    fun getRecipeForAdd(id:Long): Recipe

    @Update
    suspend fun updateTempMenu(temp: MenuTemp)

    @Delete
    suspend fun deleteTempMenu(temp: MenuTemp)



    @Insert(onConflict =OnConflictStrategy.IGNORE)
    suspend fun insertThings(things: Things)

    @Transaction
    suspend fun insertThing(things: Things) {
        try {
            insertThings(things)
        } catch (exception: SQLiteConstraintException) {

        }
    }

    @Insert
    suspend fun insertAddingShop(shoppingAdding: ShoppingAdding)

    @Update
    suspend fun updateThing(things: Things)
    @Update
    suspend fun updateAddingShop(adding: ShoppingAdding)

    @Delete
    suspend fun daletThing(things: Things)
    @Delete
    suspend fun deleteAdding(adding: ShoppingAdding)

    @Query("SELECT * FROM things GROUP BY thing ORDER BY id DESC")
    fun getAllThing(): LiveData<List<Things>>

    @Query("SELECT thing FROM things GROUP BY thing ORDER BY id DESC")
    fun getAllThingString(): LiveData<List<String>>


    @Query("SELECT * FROM ShoppingAdding ORDER BY id DESC")
    fun getAllAdding():LiveData<List<ShoppingAdding>>

    @Query("DELETE FROM things")
    suspend fun clearThings()

    @Query("DELETE FROM shoppingadding")
    suspend fun clearShoppingAdding()




    @Query("DELETE FROM recipebytag WHERE  idRecipeByTag = :id")
    suspend fun deleteTagbyId( id:Long)


    @Query("DELETE FROM recipebytag WHERE tag = :tag")
    suspend fun deleteTagByTag(tag:String)




}