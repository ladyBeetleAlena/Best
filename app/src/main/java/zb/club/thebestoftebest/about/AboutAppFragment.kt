package zb.club.thebestoftebest.about

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Serving
import zb.club.thebestoftebest.databinding.FragmentAboutAppBinding
import zb.club.thebestoftebest.readymenu.ViewModelReadyMenu

class AboutAppFragment : Fragment() {
    val model: ViewModelReadyMenu by activityViewModels()
    lateinit var edittextAdult: EditText
    lateinit var edittextChild: EditText
    lateinit var k:EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAboutAppBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_about_app,
            container,
            false
        )
        binding.readyRecipeViewModel = model
        edittextAdult = binding.editTextNumber2
        edittextChild = binding.editTextNumber3
        k = binding.editTextNumber4




        model.getAllServing.observe(viewLifecycleOwner, {
            if(it.isEmpty()){
                    val serving = Serving(
                        0,
                        edittextAdult.text.toString().toInt(),
                        edittextChild.text.toString().toInt(),
                        k.text.toString().toDouble()
                    )
                model.insertServing(serving)}
                  else{
                      if(edittextAdult.text.toString() !=it[0].adultServitg.toString()){
                    edittextAdult.setText(it[0].adultServitg.toString())}
                if(edittextChild.text.toString() !=it[0].childServing.toString()){
              edittextChild.setText(it[0].childServing.toString())}
                if (k.text.toString().toDouble() !=it[0].k){
                k.setText(it[0].k.toString())}

                  }
                })

        edittextAdult.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null ) {
                    if(s.isNotEmpty()){
                        val serving = Serving(
                            1,
                            s.toString().toInt(),
                            edittextChild.text.toString().toInt(),
                            k.text.toString().toDouble())

                        if (serving != null) {
                            model.updateServing(serving)
                        }

                    }}}

        })
        edittextChild.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null ) {
                    if(s.isNotEmpty()){
                        val serving =
                            Serving(
                                1,
                                edittextAdult.text.toString().toInt(),
                                s.toString().toInt(),
                                k.text.toString().toDouble())

                        if (serving != null) {
                            model.updateServing(serving)
                        }

                    }}}

        })
        k.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null ) {
                    if(s.isNotEmpty()){
                        val serving =
                            Serving(
                                1,
                                edittextAdult.text.toString().toInt(),
                                edittextChild.text.toString().toInt(),
                                s.toString().toDouble())

                        if (serving != null) {
                            model.updateServing(serving)
                        }

                    }}}

        })

        return binding.root
    }




}