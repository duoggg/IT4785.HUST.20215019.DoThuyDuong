package com.example.currencyexchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private lateinit var inputAmount: EditText
    private lateinit var outputAmount: EditText
    private lateinit var inputCurrency: Spinner
    private lateinit var outputCurrency: Spinner

    // Conversion rates relative to USD
    private val rates = mapOf(
        "USD" to 1.0,
        "VND" to 25355.0,
        "SGD" to 1.32,
        "EUR" to 0.92,
        "JPY" to 153.26
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        inputAmount = findViewById(R.id.sourceAmount)
        outputAmount = findViewById(R.id.destinationAmount)
        inputCurrency = findViewById(R.id.sourceCurrency)
        outputCurrency = findViewById(R.id.destinationCurrency)

        // Set up spinner adapter
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.currency_array,
            android.R.layout.simple_spinner_dropdown_item
        )
        inputCurrency.adapter = adapter
        outputCurrency.adapter = adapter

        inputCurrency.setSelection(adapter.getPosition("VND"))
        outputCurrency.setSelection(adapter.getPosition("USD"))

        // Text change listener
        inputAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = convertCurrency()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val currencyChangeListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                if (inputCurrency.selectedItem == outputCurrency.selectedItem) {
                    outputCurrency.setSelection(getDifferentCurrencyPosition())
                }
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        inputCurrency.onItemSelectedListener = currencyChangeListener
        outputCurrency.onItemSelectedListener = currencyChangeListener
    }

    private fun convertCurrency() {
        val inputValue = inputAmount.text.toString().toDoubleOrNull() ?: 0.0
        val inputRate = rates[inputCurrency.selectedItem.toString()] ?: 1.0
        val outputRate = rates[outputCurrency.selectedItem.toString()] ?: 1.0
        val rawConvertedValue = (inputValue / inputRate) * outputRate

        // Format value
        var formattedValue = BigDecimal(rawConvertedValue)
            .setScale(10, RoundingMode.HALF_UP)
            .stripTrailingZeros()

        if (formattedValue.precision() > 16) {
            formattedValue = formattedValue.round(MathContext(16, RoundingMode.HALF_UP))
        }

        outputAmount.setText(formattedValue.toPlainString())
    }

    private fun getDifferentCurrencyPosition(): Int {
        return (0 until inputCurrency.adapter.count).firstOrNull {
            inputCurrency.adapter.getItem(it) != inputCurrency.selectedItem
        } ?: 0
    }
}
