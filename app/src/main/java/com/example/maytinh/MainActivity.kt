package com.example.maytinh

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentNumber = ""
    private var previousNumber = ""
    private var operator = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MayTinh)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        val buttons = listOf(R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9)

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { numberClicked(it) }
        }

        findViewById<Button>(R.id.buttonAdd).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.buttonSub).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.buttonMul).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.buttonDiv).setOnClickListener { operatorClicked("/") }
        findViewById<Button>(R.id.buttonEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.buttonC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.buttonBS).setOnClickListener { backspace() }
        findViewById<Button>(R.id.buttonNeg).setOnClickListener { toggleNegative() }
    }

    private fun toggleNegative() {
        if (currentNumber.isNotEmpty() && currentNumber != "0") {
            currentNumber = if (currentNumber.startsWith("-")) {
                currentNumber.substring(1)
            } else {
                "-$currentNumber"
            }
            tvResult.text = currentNumber
        }
    }

    private fun backspace() {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
            tvResult.text = if (currentNumber.isEmpty()) "0" else currentNumber
        }
    }

    private fun clearAll() {
        currentNumber = ""
        previousNumber = ""
        operator = ""
        tvResult.text = "0"
        isNewOperation = true
    }

    private fun calculateResult() {
        if (previousNumber.isNotEmpty() && currentNumber.isNotEmpty()) {
            val number1 = previousNumber.toIntOrNull() ?: return
            val number2 = currentNumber.toIntOrNull() ?: return
            var result = 0

            when (operator) {
                "+" -> result = number1 + number2
                "-" -> result = number1 - number2
                "*" -> result = number1 * number2
                "/" -> result = if (number2 != 0) number1 / number2 else {
                    tvResult.text = "Lỗi!"
                    return
                }
            }
            tvResult.text = result.toString()
            currentNumber = result.toString()
            previousNumber = ""
            isNewOperation = true
        }
    }

    private fun operatorClicked(op: String) {
        if (currentNumber.isNotEmpty()) {
            previousNumber = currentNumber
            currentNumber = ""
            operator = op
            isNewOperation = true
            tvResult.text = "$previousNumber $operator"
        }
    }

    private fun numberClicked(view: View) {
        if (view is Button) {
            if (isNewOperation) {
                currentNumber = ""
                isNewOperation = false
            }
            currentNumber += view.text.toString()
            tvResult.text = currentNumber
        }
    }
}