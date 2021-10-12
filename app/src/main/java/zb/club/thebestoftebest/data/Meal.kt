package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val meal: String,
)
