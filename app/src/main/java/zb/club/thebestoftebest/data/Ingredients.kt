package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity( foreignKeys = [
    ForeignKey(
        entity = Recipe::class,
        parentColumns = ["id"],
        childColumns = ["idrecipe"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )

])
data class Ingredients(
    @PrimaryKey(autoGenerate = true)
    var idingredients: Long = 0,
    var idrecipe: Long = 0,
    var product: String? = "",
    var quantity: Int = 0

)