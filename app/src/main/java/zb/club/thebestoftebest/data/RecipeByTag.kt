package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [(ForeignKey(
    entity = Recipe::class,
    parentColumns = ["id"],
    childColumns = ["idRecipes"],
onDelete = CASCADE,
onUpdate = CASCADE))
  ]
)
data class RecipeByTag (
    @PrimaryKey(autoGenerate = true)
    val idRecipeByTag: Long,
    val idRecipes: Long = 0,
    val tag: String = "")




