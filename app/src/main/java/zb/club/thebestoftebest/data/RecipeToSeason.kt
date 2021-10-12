package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
class RecipeToSeason(
@PrimaryKey(autoGenerate = true)
val idRecipeBySeason: Long,
val idRecipes: Long = 0 ,
val recipeBySeason: String = "")