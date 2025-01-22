package at.hagenberg.fh.wc.viewmodel

import at.hagenberg.fh.wc.model.resistance.Surface
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlin.math.PI
import kotlin.math.sin

class ResistanceViewModel: ViewModel() {
    fun getRollingResistance(weight: Int, surface: Surface): Double {
        return surface.rollingResidenceCoefficient * weight
    }

    fun getInclineResidence(weight: Int, inclineGrad: Int): Double {
        return weight * sin(inclineGrad * PI / 180.0)
    }
}