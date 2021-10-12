package zb.club.thebestoftebest.cookbook

import android.content.Context
import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.cookbook.viewmodelcookbook.ViewModelAddRecipe
import zb.club.thebestoftebest.data.Ingredients
import zb.club.thebestoftebest.databinding.FragmentResipeShowBinding


class ResipeShowFragment : Fragment() {

    lateinit var layoutForIngrShow:LinearLayout
    val model: ViewModelAddRecipe by viewModels()
    private val args by navArgs<ResipeShowFragmentArgs>()
    lateinit var ingridientList: List<Ingredients>
    lateinit var textLink: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentResipeShowBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_resipe_show,
            container, false)
        layoutForIngrShow = binding.linearShowIngr
        binding.lifecycleOwner = this
        textLink = binding.textViewForLink
        binding.textShow.setText(args.recid.title)
        val id = args.recid.id
        val uri =


       // val uri = Uri.parse(args.recid.picture)
      binding.imageSow.setImageURI(args.recid.picture!!.toUri())

        textLink.setText("${args.recid.link}")
        Linkify.addLinks(textLink, Linkify.WEB_URLS)


        model.ingredientByRec(id).observe(viewLifecycleOwner, Observer { a ->

            ingridientList = a
            val i = ingridientList.size
            addIngr(i, ingridientList)

        })





      return  binding.root
    }

   fun addIngr(i: Int, ingridientList: List<Ingredients>){
    for (i in 0 until i){
        val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.row_show_ingr, null)
        val ingrShow: String? = ingridientList[i].product
        val weghtShow: String = ingridientList[i].quantity.toString()
        rowView.findViewById<TextView>(R.id.textViewshowprod).setText(ingrShow)
        rowView.findViewById<TextView>(R.id.textViewweight).setText(weghtShow)
        layoutForIngrShow.addView(rowView, layoutForIngrShow.childCount)
    }}


}


