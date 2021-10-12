package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingAdding(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val thing: String,
    val quantity: Int,
    val check: Boolean
)
