package zb.club.thebestoftebest.onboarding

import zb.club.thebestoftebest.data.Recipe

interface CheckedRecipeListener {

    fun onCheckedRecipes(checkedRecipe: ArrayList<Recipe>)
}