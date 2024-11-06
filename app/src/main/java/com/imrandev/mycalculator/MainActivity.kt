package com.imrandev.mycalculator

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.imrandev.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // Binding
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private lateinit var viewModel: MainActivityViewModel

    // Factory
    private lateinit var factory: MainActivityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        factory = MainActivityViewModelFactory(0.0)
        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.apply { binding.apply {
            tvHistory.movementMethod = ScrollingMovementMethod()

            btnClear.setOnClickListener {onClick(btnClear);
                val operation = clearOutput()
                resultInterface = operation.numberInput
                equationInterface = operation.equation
            }
            btnDelete.setOnClickListener { onClick(btnDelete); resultInterface = deleteOutput() }
            btnPercentage.setOnClickListener {  }

            btnPlus.setOnClickListener {onClick(btnPlus);
                val operation = onOperatorPressed(btn_plus)
                equationInterface = operation.equation
            }
            btnMinus.setOnClickListener {onClick(btnMinus);
                val operation = onOperatorPressed(btn_minus)
                equationInterface = operation.equation
            }
            btnMultiply.setOnClickListener {onClick(btnMultiply);
                val operation = onOperatorPressed(btn_multiply)
                equationInterface = operation.equation
            }
            btnDivide.setOnClickListener {onClick(btnDivide);
                val operation = onOperatorPressed(btn_divide)
                equationInterface = operation.equation
            }

            btnEqual.setOnClickListener {onClick(btnEqual);
                val operation = onOperatorPressed(btn_equal)
                equationInterface = operation.equation
            }

            viewModel.result.observe(this@MainActivity, Observer {
                resultInterface = it.toString()
            })
            viewModel.equationFinal.observe(this@MainActivity, Observer {
                equationInterface = it.toString()
            })
            viewModel.history.observe(this@MainActivity, Observer {
                historyInterface = it.toString()
            })

            btnZero.setOnClickListener { onClick(btnZero); resultInterface = upsertOutput(btn_zero) }
            btnOne.setOnClickListener { onClick(btnOne); resultInterface = upsertOutput(btn_one) }
            btnTwo.setOnClickListener { onClick(btnTwo); resultInterface = upsertOutput(btn_two) }
            btnThree.setOnClickListener { onClick(btnThree); resultInterface = upsertOutput(btn_three) }
            btnFour.setOnClickListener { onClick(btnFour); resultInterface = upsertOutput(btn_four) }
            btnFive.setOnClickListener { onClick(btnFive); resultInterface = upsertOutput(btn_five) }
            btnSix.setOnClickListener { onClick(btnSix); resultInterface = upsertOutput(btn_six) }
            btnSeven.setOnClickListener { onClick(btnSeven); resultInterface = upsertOutput(btn_seven) }
            btnEight.setOnClickListener { onClick(btnEight); resultInterface = upsertOutput(btn_eight) }
            btnNine.setOnClickListener { onClick(btnNine); resultInterface = upsertOutput(btn_nine) }
            btnDecimal.setOnClickListener { onClick(btnDecimal); resultInterface = upsertOutput(btn_decimal) }
        } }
    }

    override fun onClick(v: View?) {
        binding.apply {
            val isNewEquation: Boolean = viewModel.getIsNewEquation()

            if (isNewEquation) {
                equationInterface = ""
                historyInterface = viewModel.getEquation(historyInterface.toString())
            }
        }

    }
}