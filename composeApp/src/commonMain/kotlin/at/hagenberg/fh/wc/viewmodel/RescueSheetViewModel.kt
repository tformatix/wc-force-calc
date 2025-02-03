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

    private val _euroRescueCars = MutableStateFlow<List<EuroRescueCar>?>(null)
    val euroRescueCars: StateFlow<List<EuroRescueCar>?> get() = _euroRescueCars

    fun findRescueSheet(authority: String, number: String) {
        _isLoading.value = true
        _errorMessage.value = null
        _feuerwehrAppCar.value = null
        _euroRescueCars.value = null

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _feuerwehrAppCar.value =
                    feuerwehrAppRepository.searchLicencePlate(authority, number)

                _euroRescueCars.value = _feuerwehrAppCar.value?.let {
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