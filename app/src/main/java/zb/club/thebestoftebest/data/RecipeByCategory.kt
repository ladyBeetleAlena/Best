package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [(ForeignKey(
    entity = Recipe::class,
    parentColumns = ["id"],
    childColumns = ["idrecips"],
    onDelete = CASCADE,
    onUpdate = CASCADE)),

]
)
data class RecipeByCategory (
    @PrimaryKey(autoGenerate = true)
    val idRecipeByCategory:Long,
    val idrecips: Long = 0,
    val categorytorecipe: String = "")
