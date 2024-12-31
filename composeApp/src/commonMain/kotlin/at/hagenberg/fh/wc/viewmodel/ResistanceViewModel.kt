package at.hagenberg.fh.wc.viewmodel

import at.hagenberg.fh.wc.model.Surface
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlin.math.PI
import kotlin.math.sin

class ResistanceViewModel: ViewModel() {
    fun getRollingResistance(weightTons: Double, surface: Surface): Double {
        return surface.getRollingResistanceCoefficient() * weightTons * 10
    }

    fun getInclineResidence(weightTons: Double, inclineGrad: Double): Double {
        return weightTons * sin(inclineGrad * PI / 180) * 10
    }
}