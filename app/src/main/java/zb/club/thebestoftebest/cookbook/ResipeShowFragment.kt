package zb.club.thebestoftebest.cookbook

import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.cookbook.viewmodelcookbook.ViewModelAddRecipe
import zb.club.thebestoftebest.data.Ingredients
import zb.club.thebestoftebest.data.Instructions
import zb.club.thebestoftebest.databinding.FragmentResipeShowBinding
import zb.club.thebestoftebest.onboarding.AdapterShowIngr
import zb.club.thebestoftebest.onboarding.AdapterShowInstruction


class ResipeShowFragment : Fragment() {

    lateinit var reciclerIngr: RecyclerView
    lateinit var adapter: AdapterShowIngr
    val model: ViewModelAddRecipe by viewModels()
    private val args by navArgs<ResipeShowFragmentArgs>()
    var ingridientList = mutableListOf<Ingredients>()
    lateinit var textLink: TextView
    lateinit var reciclerInstruction:RecyclerView
    lateinit var adapterInstruction:AdapterShowInstruction
    var instList = mutableListOf<Instructions>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentResipeShowBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_resipe_show,
            container, false)

        binding.lifecycleOwner = this
        textLink = binding.textViewForLink
        binding.textShow.setText(args.recid.title)
        val id = args.recid.id
        reciclerInstruction = binding.recyclerInstruk
        adapterInstruction = AdapterShowInstruction(instList)
        reciclerInstruction.adapter = adapterInstruction
        reciclerInstruction.layoutManager = LinearLayoutManager(requireContext())



        reciclerIngr = binding.recyclerShowIngr
        adapter = AdapterShowIngr(ingridientList)
        reciclerIngr.adapter = adapter
        reciclerIngr.layoutManager = LinearLayoutManager(requireContext())
      binding.imageSow.setImageURI(args.recid.picture!!.toUri())

        if(args.recid.link.isNullOrEmpty()){textLink.visibility = View.INVISIBLE} else {textLink.setText("${args.recid.link}")
        Linkify.addLinks(textLink, Linkify.WEB_URLS)}

        model.getInstructionForRecipe(id).observe(viewLifecycleOwner, {adapterInstruction.setData(it)})
        model.ingredientByRec(id).observe(viewLifecycleOwner, Observer { a ->
            adapter.setData(a)
        })





      return  binding.root
    }




}


