package zb.club.thebestoftebest.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String? = null,
    val picture: String? = null,
    val link: String? = null,
    val input: Boolean = false

): Parcelable

