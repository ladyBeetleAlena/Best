package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Serving(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val adultServitg: Int,
    val childServing: Int,
    val  k : Double
)
