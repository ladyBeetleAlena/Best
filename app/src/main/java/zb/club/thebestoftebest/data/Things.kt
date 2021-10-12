package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
@Entity(indices = [     Index(value = ["thing"], unique = true) ])
data class Things(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val thing: String
)
