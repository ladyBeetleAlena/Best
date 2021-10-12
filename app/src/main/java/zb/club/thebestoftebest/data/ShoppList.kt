package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppList(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val product: String,
    val quantity: Double,
    val check: Boolean
)
