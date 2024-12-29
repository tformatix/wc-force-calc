package at.hagenberg.fh.wc.viewmodel

import at.hagenberg.fh.wc.model.Surface
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlin.math.PI
import kotlin.math.sin

class ResistanceViewModel: ViewModel() {
    fun getRollingResistance(weight: Double, surface: Surface): Double {
        return surface.getRollingResistanceCoefficient() * weight
    }

    fun getGradientResistance(weight: Double, gradient: Double): Double {
        return weight * sin(gradient * PI / 180)
    }
}