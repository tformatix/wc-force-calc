package at.hagenberg.fh.wc.viewmodel

import at.hagenberg.fh.wc.data.EuroRescueRepository
import at.hagenberg.fh.wc.data.FeuerwehrAppRepository
import at.hagenberg.fh.wc.model.rescue.sheet.dto.EuroRescueCar
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RescueSheetViewModel : ViewModel() {
    private val euroRescueRepository = EuroRescueRepository()
    private val feuerwehrAppRepository = FeuerwehrAppRepository()

    private val _euroRescueCar = MutableStateFlow<EuroRescueCar?>(null)
    val euroRescueCar: StateFlow<EuroRescueCar?> get() = _euroRescueCar

    fun findRescueSheet(authority: String, number: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val feuerwehrAppCar = feuerwehrAppRepository.searchLicencePlate(authority, number)
                _euroRescueCar.value = euroRescueRepository.findCar(feuerwehrAppCar)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}