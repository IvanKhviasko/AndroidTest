package com.example.androidtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtest.databinding.FragmentFirstBinding
import popVal
import recCalculate

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = requireNotNull(_binding) { "view was destroyed" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFirstBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            fun setTextFields(str: String) {
                mathOperation.append(str)
            }
            button0.setOnClickListener {
                setTextFields("0")
            }
            button1.setOnClickListener {
                setTextFields("1")
            }
            button2.setOnClickListener {
                setTextFields("2")
            }
            button3.setOnClickListener {
                setTextFields("3")
            }
            button4.setOnClickListener {
                setTextFields("4")
            }
            button5.setOnClickListener {
                setTextFields("5")
            }
            button6.setOnClickListener {
                setTextFields("6")
            }
            button7.setOnClickListener {
                setTextFields("7")
            }
            button8.setOnClickListener {
                setTextFields("8")
            }
            button9.setOnClickListener {
                setTextFields("9")
            }
            buttonPlus.setOnClickListener {
                setTextFields("+")
            }
            buttonMinus.setOnClickListener {
                setTextFields("-")
            }
            buttonDivide.setOnClickListener {
                setTextFields("/")
            }
            buttonMultiply.setOnClickListener {
                setTextFields("*")
            }
            buttonOpen.setOnClickListener {
                setTextFields("(")
            }
            buttonClose.setOnClickListener {
                setTextFields(")")
            }
            buttonPoint.setOnClickListener {
                setTextFields(".")
            }
            buttonBack.setOnClickListener {
                val strTemp = mathOperation.text.toString()
                if (strTemp.isNotEmpty())
                    mathOperation.text = strTemp.substring(0, strTemp.length - 1)
            }
            buttonClean.setOnClickListener {
                mathOperation.text = ""
            }
            buttonEquals.setOnClickListener {
                try {
                    val actualLine = mathOperation.text.toString()
                    recCalculate(actualLine)
                    mathOperation.text = popVal()
                } catch (e: Exception) {
                    android.widget.Toast.makeText(
                        root.context,
                        "Something terrible has happened",
                        android.widget.Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}