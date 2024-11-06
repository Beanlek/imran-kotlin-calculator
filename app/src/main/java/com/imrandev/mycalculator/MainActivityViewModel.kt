package com.imrandev.mycalculator

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imrandev.mycalculator.context.App

class MainActivityViewModel(num: Double) : ViewModel() {
    private var numInput = MutableLiveData<Double>()
    val result: LiveData<Double> get() = numInput
    private var equationInput = MutableLiveData<String>()
    val equationFinal: LiveData<String> get() = equationInput
    private var historyInput = MutableLiveData<String>()
    val history: LiveData<String> get() = historyInput

    private var equation = ""
    private var tempEquation = ""
    private var numberInput = "0"
    private var operatorIsPressed = false
    private var isNewEquation = false

    val btn_zero: String = App.applicationContext().getString(R.string.btn_zero)
    val btn_one: String = App.applicationContext().getString(R.string.btn_one)
    val btn_two: String = App.applicationContext().getString(R.string.btn_two)
    val btn_three: String = App.applicationContext().getString(R.string.btn_three)
    val btn_four: String = App.applicationContext().getString(R.string.btn_four)
    val btn_five: String = App.applicationContext().getString(R.string.btn_five)
    val btn_six: String = App.applicationContext().getString(R.string.btn_six)
    val btn_seven: String = App.applicationContext().getString(R.string.btn_seven)
    val btn_eight: String = App.applicationContext().getString(R.string.btn_eight)
    val btn_nine: String = App.applicationContext().getString(R.string.btn_nine)
    val btn_decimal: String = App.applicationContext().getString(R.string.btn_decimal)

    val btn_plus: String = App.applicationContext().getString(R.string.btn_plus)
    val btn_minus: String = App.applicationContext().getString(R.string.btn_minus)
    val btn_multiply: String = App.applicationContext().getString(R.string.btn_multiply)
    val btn_divide: String = App.applicationContext().getString(R.string.btn_divide)
    val btn_equal: String = App.applicationContext().getString(R.string.btn_equal)

    init { numInput.value = num }

    private fun doPLus(input: Double) {
        numInput.value = (numInput.value)?.plus(input)
    }
    private fun doMinus(input: Double) {
        numInput.value = (numInput.value)?.minus(input)
    }
    private fun doMultiply(input: Double) {
        numInput.value = (numInput.value)?.times(input)
    }
    private fun doDivide(input: Double) {
        numInput.value = (numInput.value)?.div(input)
    }

    private fun calculateResult() {
        val splitEquation: MutableList<String> = equation.split(" ").toMutableList()

        var tempResult: Double = 0.0
        var tempOperator: String = "+"

        var i: Int = 1
        for (singleEntity in splitEquation) {
            if (i % 2 != 0) {
                val entityDouble: Double = singleEntity.toDouble()
                tempResult = entityDouble
            } else {
                if (i > 2) {
                    when (tempOperator) {
                        "+" -> { doPLus(tempResult) }
                        "-" -> { doMinus(tempResult) }
                        "*" -> { doMultiply(tempResult) }
                        "/" -> { doDivide(tempResult) }
                    }
                } else {
                    numInput.value = tempResult
                }

                tempOperator = singleEntity
            }

            i++
        }

        println(splitEquation)
    }

    private fun upsertHistory() {

    }

    fun onOperatorPressed(operator: String): Operation {
        if (operator == "=" && equation == "") {
            isNewEquation = false
            equationInput.value = equation
            return Operation(numberInput, equation)
        } else if (operator != "=") {
            if (equation == "") {
                equation = numberInput
            } else if (!operatorIsPressed){
                equation = "$equation $numberInput"
            }

            if (operatorIsPressed) {
                val index: Int = equation.lastIndexOf(" ")
                equation = equation.dropLast(equation.length - index)
                equation = "$equation $operator"
            } else {
                equation = "$equation $operator"
            }
        } else {
            equation = "$equation $numberInput $operator"
            isNewEquation = true
        }

        numberInput = "0"
        calculateResult()
        val operate = Operation(numberInput, equation)
        equationInput.value = equation

        if (operator == "=") {
            upsertHistory()
            tempEquation = equation
            equation = ""
        }
        operatorIsPressed = true
        return operate
    }

    fun getIsNewEquation(): Boolean {
        return isNewEquation
    }
    fun getEquation(historyInterface: String): String {
        when (historyInterface) {
            tempEquation -> historyInput.value = historyInterface
            "null" -> historyInput.value = tempEquation+" "+result.value.toString()
            else -> historyInput.value = historyInterface+"\n"+tempEquation+" "+result.value.toString()
        }
        return history.value.toString()
    }

    fun upsertOutput(input: String): String {
        operatorIsPressed = false
        isNewEquation = false

        if (numberInput == "0" && numberInput.length == 1) {
            numberInput = input
        }
        else if (input == "." && numberInput.contains(".")) { null }
        else { numberInput = numberInput + input }

        numInput.value = numberInput.toDouble()
        equationInput.value = equation
        return numberInput
    }

    fun deleteOutput(): String {
        operatorIsPressed = false
        isNewEquation = false

        if (numberInput.length == 1) { numberInput = "0" }
        else { numberInput = numberInput.removeRange(numberInput.lastIndex,numberInput.lastIndex + 1) }

        numInput.value = numberInput.toDouble()
        equationInput.value = equation
        return numberInput
    }

    fun clearOutput(): Operation {
        operatorIsPressed = false
        isNewEquation = false

        equation = ""
        numberInput = "0"

        val operate = Operation(numberInput, equation)
        numInput.value = numberInput.toDouble()
        equationInput.value = equation
        return operate
    }
}