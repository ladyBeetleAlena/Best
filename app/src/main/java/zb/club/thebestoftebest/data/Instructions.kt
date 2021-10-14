package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity( foreignKeys = [
    ForeignKey(
        entity = Recipe::class,
        parentColumns = ["id"],
        childColumns = ["idRecipe"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
])
data class Instructions(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val idRecipe: Long,
    val text: String,
    val picture: String? = null
)
