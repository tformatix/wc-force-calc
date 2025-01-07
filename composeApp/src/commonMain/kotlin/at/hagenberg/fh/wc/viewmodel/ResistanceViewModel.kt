package at.hagenberg.fh.wc.viewmodel

import at.hagenberg.fh.wc.model.Surface
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlin.math.PI
import kotlin.math.sin

class ResistanceViewModel: ViewModel() {
    fun getRollingResistance(weightTons: Int, surface: Surface): Double {
        return surface.getRollingResistanceCoefficient() * weightTons * 10.0
    }

    fun getInclineResidence(weightTons: Int, inclineGrad: Int): Double {
        return weightTons * sin(inclineGrad * PI / 180.0) * 10.0
    }
}