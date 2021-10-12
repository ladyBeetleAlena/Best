package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingListWithDay( @PrimaryKey(autoGenerate = true)
                                val id: Long,
                                val product: String,
                                val quantity: Double,
                                val check: Boolean,
                                val day: Int)
