package zb.club.thebestoftebest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Meal::class, ShoppingAdding::class, ShoppingListWithDay::class, Things::class, ShoppList::class, Recipe::class,  Serving::class, Ingredients::class, RecipeByCategory::class, RecipeByTag::class, RecipeToSeason::class,  Menu::class, MenuTemp::class, Setting::class, CurrentMenu::class, Instruction::class), version = 1, exportSchema = false)
public abstract class RecipeDataBase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RecipeDataBase? = null

        fun getDatabase(context: Context): RecipeDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDataBase::class.java,
                    "repsdata.db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}