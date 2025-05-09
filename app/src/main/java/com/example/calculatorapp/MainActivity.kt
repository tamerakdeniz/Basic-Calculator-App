package com.example.calculatorapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    
    private lateinit var tvResult: TextView
    private lateinit var tvOperation: TextView
    private var firstNumber = 0.0
    private var operation = ""
    private var isNewOperation = true
    private lateinit var btnAddCardView: CardView
    private lateinit var btnSubtractCardView: CardView
    private lateinit var btnMultiplyCardView: CardView
    private lateinit var btnDivideCardView: CardView
    private val defaultButtonColor = Color.parseColor("#303030")
    private val selectedButtonColor = Color.parseColor("#03DAC5")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        tvResult = findViewById(R.id.tvResult)
        tvOperation = findViewById(R.id.tvOperation)
        btnAddCardView = findViewById<Button>(R.id.btnAdd).parent as CardView
        btnSubtractCardView = findViewById<Button>(R.id.btnSubtract).parent as CardView
        btnMultiplyCardView = findViewById<Button>(R.id.btnMultiply).parent as CardView
        btnDivideCardView = findViewById<Button>(R.id.btnDivide).parent as CardView

        setupNumberButton(R.id.btn0, "0")
        setupNumberButton(R.id.btn1, "1")
        setupNumberButton(R.id.btn2, "2")
        setupNumberButton(R.id.btn3, "3")
        setupNumberButton(R.id.btn4, "4")
        setupNumberButton(R.id.btn5, "5")
        setupNumberButton(R.id.btn6, "6")
        setupNumberButton(R.id.btn7, "7")
        setupNumberButton(R.id.btn8, "8")
        setupNumberButton(R.id.btn9, "9")

        setupOperationButton(R.id.btnAdd, "+")
        setupOperationButton(R.id.btnSubtract, "-")
        setupOperationButton(R.id.btnMultiply, "×")
        setupOperationButton(R.id.btnDivide, "÷")

        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            calculateResult()
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            tvResult.text = "0"
            tvOperation.text = ""
            firstNumber = 0.0
            operation = ""
            isNewOperation = true
            resetOperationButtonColors()
        }
    }
    
    private fun setupNumberButton(buttonId: Int, number: String) {
        findViewById<Button>(buttonId).setOnClickListener {
            if (isNewOperation) {
                tvResult.text = number
                isNewOperation = false
            } else {
                if (tvResult.text == "0") {
                    tvResult.text = number
                } else {
                    tvResult.text = "${tvResult.text}$number"
                }
            }
        }
    }
    
    private fun setupOperationButton(buttonId: Int, op: String) {
        findViewById<Button>(buttonId).setOnClickListener {
            firstNumber = tvResult.text.toString().toDouble()
            operation = op

            val displayText = if (firstNumber == firstNumber.toInt().toDouble()) {
                "${firstNumber.toInt()} $operation"
            } else {
                "$firstNumber $operation"
            }
            tvOperation.text = displayText

            resetOperationButtonColors()
            when (buttonId) {
                R.id.btnAdd -> btnAddCardView.setCardBackgroundColor(selectedButtonColor)
                R.id.btnSubtract -> btnSubtractCardView.setCardBackgroundColor(selectedButtonColor)
                R.id.btnMultiply -> btnMultiplyCardView.setCardBackgroundColor(selectedButtonColor)
                R.id.btnDivide -> btnDivideCardView.setCardBackgroundColor(selectedButtonColor)
            }
            
            isNewOperation = true
        }
    }
    
    private fun resetOperationButtonColors() {
        btnAddCardView.setCardBackgroundColor(defaultButtonColor)
        btnSubtractCardView.setCardBackgroundColor(defaultButtonColor)
        btnMultiplyCardView.setCardBackgroundColor(defaultButtonColor)
        btnDivideCardView.setCardBackgroundColor(defaultButtonColor)
    }
    
    private fun calculateResult() {
        if (operation.isNotEmpty()) {
            val secondNumber = tvResult.text.toString().toDouble()

            val displayText = if (firstNumber == firstNumber.toInt().toDouble() && secondNumber == secondNumber.toInt().toDouble()) {
                "${firstNumber.toInt()} $operation ${secondNumber.toInt()} ="
            } else if (firstNumber == firstNumber.toInt().toDouble()) {
                "${firstNumber.toInt()} $operation $secondNumber ="
            } else if (secondNumber == secondNumber.toInt().toDouble()) {
                "$firstNumber $operation ${secondNumber.toInt()} ="
            } else {
                "$firstNumber $operation $secondNumber ="
            }
            tvOperation.text = displayText
            
            val result = when (operation) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "×" -> firstNumber * secondNumber
                "÷" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                else -> secondNumber
            }
            
            tvResult.text = if (result == result.toInt().toDouble()) {
                result.toInt().toString()
            } else {
                result.toString()
            }
            
            firstNumber = result
            operation = ""
            isNewOperation = true
            resetOperationButtonColors()
        }
    }
}