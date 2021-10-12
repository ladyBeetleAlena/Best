package zb.club.thebestoftebest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Setting(
    @PrimaryKey(autoGenerate = true)
    val idSetting: Long,
    val adultPortion: Int,
    val childPortion: Int,
    val k: Double

)
