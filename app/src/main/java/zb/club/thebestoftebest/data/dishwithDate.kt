package zb.club.thebestoftebest.data

data class dishwithDate(
    val day: Int,
    val calendarDay:Long,
    val recipeId: Long,
    val titleRecipe: String,
    val meal: String,
    val childrenServings: Int,
    val adultServing:Int,
    val childAdultK: Double = 0.0,
    val picture:String
)
