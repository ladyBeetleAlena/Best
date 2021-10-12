package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [(ForeignKey(
    entity = Recipe::class,
    parentColumns = ["id"],
    childColumns = ["idRecipe"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE
))]   )
data class CurrentMenu(@PrimaryKey(autoGenerate = true)
                        val id: Long = 0,
                       val idRecipe: Long = 0,
                       val titleRecipe: String = "",
                       val calendarDay: Long = 0,
                       val menuTitle: String? = null,
                       val day: Int = 0,
                       val meal: String? =null,
                       val childrenServings: Int = 0,
                       var adultServing: Int = 0,
                       val childAdultK: Double = 0.0)
