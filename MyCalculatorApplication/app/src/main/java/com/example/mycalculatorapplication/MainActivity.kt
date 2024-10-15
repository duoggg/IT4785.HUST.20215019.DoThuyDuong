package com.example.mycalculatorapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){

    private lateinit var tvResult: TextView

    private var operand1: Int = 0
    private var operand2: Int = 0
    private var operation: String = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.textResult)

        val buttons = listOf(
            findViewById<Button>(R.id.btn0), findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2), findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4), findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6), findViewById<Button>(R.id.btn7),
            findViewById<Button>(R.id.btn8), findViewById<Button>(R.id.btn9)
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                if (isNewOperation) {
                    tvResult.text = ""
                    isNewOperation = false
                }
                tvResult.append((it as Button).text)
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperation("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { setOperation("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { setOperation("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { setOperation("/") }

        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnCE).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.btnBS).setOnClickListener { backspace() }
    }

    private fun setOperation(op: String) {
        operand1 = tvResult.text.toString().toInt()
        operation = op
        isNewOperation = true
    }

    private fun calculateResult() {
        operand2 = tvResult.text.toString().toInt()

        val result = when (operation) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> if (operand2 != 0) operand1 / operand2 else 0
            else -> 0
        }

        tvResult.text = result.toString()
        isNewOperation = true
    }

    private fun clearEntry() {
        operation = ""
        tvResult.text = "0"
    }

    private fun clearAll() {
        tvResult.text = "0"
        operand1 = 0
        operand2 = 0
        isNewOperation = true
    }

    private fun backspace() {
        val currentText = tvResult.text.toString()

        if (currentText.isNotEmpty()) {
            // Xóa ký tự cuối cùng
            tvResult.text = currentText.dropLast(1)
        }

        // Nếu text đã bị xóa hết, thiết lập lại mặc định "0"
        if (tvResult.text.isEmpty()) {
            tvResult.text = "0"
            isNewOperation = true
        }
    }

}