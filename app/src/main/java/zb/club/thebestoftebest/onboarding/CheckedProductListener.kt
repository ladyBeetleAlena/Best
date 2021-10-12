package zb.club.thebestoftebest.onboarding

import zb.club.thebestoftebest.data.ShoppList

interface CheckedProductListener {

    fun onCheckedProduct(checkedProduct: ShoppList)
    fun unChekedProduct(unchekedProduct:ShoppList)
}