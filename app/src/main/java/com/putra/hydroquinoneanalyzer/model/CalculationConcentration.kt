package com.putra.hydroquinoneanalyzer.model

import kotlin.math.log10

class CalculationConcentration(private val rgb: Double) {
    private fun absorbency(): Double {
        return -log10(rgb / 255)
    }

    fun concentrationCalculation(): Double {
        val a = 0.07
        val b = 0.1538
        return (absorbency() - b) / a
    }

    fun concentrationPercentage(): Double {
        val p = 100 / 0.01 * concentrationCalculation()
        val q = p / 1000
        return q / 100 * 100
    }

    fun checkStatus(): String? {
        return if (concentrationCalculation() < 0.02) {
            STATUS_GOOD
        } else {
           STATUS_BAD
        }
    }

    companion object{
        private const val STATUS_GOOD = "Layak Pakai"
        private const val STATUS_BAD = "Tidak Layak Pakai"
    }
}