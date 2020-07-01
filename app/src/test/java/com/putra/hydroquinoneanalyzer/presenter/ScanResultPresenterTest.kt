package com.putra.hydroquinoneanalyzer.presenter

import com.putra.hydroquinoneanalyzer.view.ScanResultView
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import kotlin.math.log10

class ScanResultPresenterTest {
    companion object{
        private const val STATUS_GOOD = "Layak Pakai"
        private const val STATUS_BAD = "Tidak Layak Pakai"
    }
    private var rgb1: IntArray = intArrayOf(
        204,206,203
    )

    private var rgb2: IntArray = intArrayOf(
        79,49,47
    )

    @Test
    fun calculationConcentrationWithGoodStatus() {
        val a = 0.07
        val b = 0.1538
        val absorbency : Double = -log10(rgb1[2].toDouble() / 255)
        val concentrationCalculation : Double = (absorbency - b) / a
        val p = 100 / 0.01 * concentrationCalculation
        val q = p / 1000
        val concentrationPercentage : Double = q / 100 * 100
        if (concentrationCalculation < 0.02) {
            Assert.assertEquals(-0.7822265354179672, concentrationCalculation, 0.0)
            Assert.assertEquals(-7.822265354179672, concentrationPercentage, 0.0)
            Assert.assertEquals(STATUS_GOOD, "Layak Pakai")
        }
    }

    @Test
    fun calculationConcentrationWithBadStatus() {
        val a = 0.07
        val b = 0.1538
        val absorbency : Double = -log10(rgb2[2].toDouble() / 255)
        val concentrationCalculation : Double = (absorbency - b) / a
        val p = 100 / 0.01 * concentrationCalculation
        val q = p / 1000
        val concentrationPercentage : Double = q / 100 * 100
        if (concentrationCalculation > 0.02) {
            Assert.assertEquals(8.294890321403395, concentrationCalculation, 0.0)
            Assert.assertEquals(82.94890321403396, concentrationPercentage, 0.0)
            Assert.assertEquals(STATUS_BAD, "Tidak Layak Pakai")
        }
    }


}