package zb.club.thebestoftebest.data

data class ListForDate(
    val idRecipe: Long = 0,
    val titleRecipe: String = "",
    val menuTitle: String? = null,
    var day: Int = 0,
    val meal: String? =null,
    val childrenServings: Int = 0,
    val adultServing: Int = 0,
    val childAdultK: Double = 0.0
)
