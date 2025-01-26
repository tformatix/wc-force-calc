package at.hagenberg.fh.wc.viewmodel

import at.hagenberg.fh.wc.data.EuroRescueRepository
import at.hagenberg.fh.wc.data.FeuerwehrAppRepository
import at.hagenberg.fh.wc.model.rescue.sheet.FeuerwehrAppCar
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

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _feuerwehrAppCar = MutableStateFlow<FeuerwehrAppCar?>(null)
    val feuerwehrAppCar: StateFlow<FeuerwehrAppCar?> get() = _feuerwehrAppCar

    private val _euroRescueCar = MutableStateFlow<EuroRescueCar?>(null)
    val euroRescueCar: StateFlow<EuroRescueCar?> get() = _euroRescueCar

    fun findRescueSheet(authority: String, number: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _feuerwehrAppCar.value =
                    feuerwehrAppRepository.searchLicencePlate(authority, number)

                // TODO: Display multiple cars (e.g. in case of different body types)
                _euroRescueCar.value = _feuerwehrAppCar.value?.let {
                    euroRescueRepository.findCar(it)
                }
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _isLoading.value = false
            }
        }
    }
}