package com.mundocode.dragonball.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonball.models.DragonBallModel
import com.mundocode.dragonball.models.DragonBallPlanets
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonball.models.singlePlanets
import com.mundocode.dragonball.network.ApiDragonBall
import com.mundocode.dragonball.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

interface DBZListRepositoryInterface {
    suspend fun getSaiyanList(): Response<DragonBallModel>
    suspend fun getPlanets(): Response<DragonBallPlanets>
}

class DBZListRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
): DBZListRepositoryInterface {
    override suspend fun getSaiyanList(): Response<DragonBallModel> {
        return apiService.obtenerPersonajes()
    }

    override suspend fun getPlanets(): Response<DragonBallPlanets> {
        return apiService.obtenerPlanetas()
    }
}

class DragonBallListViewModel(
    private val repository: DBZListRepositoryInterface = DBZListRepository()
): ViewModel() {
    private val _saiyanList = MutableStateFlow<DragonBallModel?>(null)
    private val _planetList = MutableStateFlow<DragonBallPlanets?>(null)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(true)

    val saiyanList: StateFlow<DragonBallModel?> get() = _saiyanList.asStateFlow()
    val planetList: StateFlow<DragonBallPlanets?> get() = _planetList.asStateFlow()
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    init {
        getSaiyanList()
    }

    fun getSaiyanList() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getSaiyanList()
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    Log.d("Success", "${body.ListItems.size}")
                    _saiyanList.value = body
                }
                _isLoading.value = false
            } else {
                val error = response.errorBody()?.string()
                Log.d("Saiyan List Error", error ?: "Unknown error")
                _errorMessage.value = error
                _isLoading.value = false
            }
        }
    }

    fun getPlanets() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getPlanets()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("Success", "${body.ListPlanets.size}")
                    _planetList.value = body
                }
                _isLoading.value = false
            } else {
                val error = response.errorBody()?.string()
                Log.d("Planet List Error", error ?: "Unknown error")
                _errorMessage.value = error
                _isLoading.value = false
            }
        }
    }
}




interface DragonDetailsRepositoryInterface {
    suspend fun obtenerPersonaje(id: Int): Response<SingleDragonBallLista>
    suspend fun obtenerPlaneta(id: Int): Response<singlePlanets>
}

class DragonDetailsRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
): DragonDetailsRepositoryInterface {
    override suspend fun obtenerPersonaje(id: Int): Response<SingleDragonBallLista> {
        return apiService.obtenerPersonaje(id)
    }

    override suspend fun obtenerPlaneta(id: Int): Response<singlePlanets> {
        return apiService.obtenerPlaneta(id)
    }
}



class MyViewModel(
    id: Int,
    private val repository: DragonDetailsRepositoryInterface = DragonDetailsRepository()
) : ViewModel() {

    // Mutable States
    private val _dragonDetails = MutableStateFlow<SingleDragonBallLista?>(null)
    private val _planetDetails = MutableStateFlow<singlePlanets?>(null)
    private val _isLoading = MutableStateFlow(true)
    private val _gotError = MutableStateFlow(false)

    // States
    val dragonDetails: StateFlow<SingleDragonBallLista?> get() = _dragonDetails.asStateFlow()
    val planetDetails: StateFlow<singlePlanets?> get() = _planetDetails.asStateFlow()
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()
    val gotError: StateFlow<Boolean> get() = _gotError.asStateFlow()

    init {
        fetchDetails(id)
        fetchDetailsPlanets(id)
    }


    private fun fetchDetails(id: Int) {
// Start in another thread
        viewModelScope.launch {
// Loading state
            _isLoading.value = true
            val result = repository.obtenerPersonaje(id)
            val error = result.errorBody()
            val data = result.body()

            if (error != null || !result.isSuccessful) {
// Handle error state
                Log.e("Got an error", "Got an error")
                _isLoading.value = false
                _gotError.value = true
                return@launch
            }
            if (data != null) {
// Handle success case
                Log.i("Got data", "Got data")
                _isLoading.value = false
                _dragonDetails.value = data
                //Log.i("Juan", data.toString())
            } else {
// Handle empty data
                Log.d("Got nothing", "Got data")
                _isLoading.value = false
            }
        }
    }

    private fun fetchDetailsPlanets(id: Int) {
// Start in another thread
        viewModelScope.launch {
// Loading state
            _isLoading.value = true
            val result = repository.obtenerPlaneta(id)
            val error = result.errorBody()
            val data = result.body()

            if (error != null || !result.isSuccessful) {
// Handle error state
                Log.e("Got an error", "Got an error")
                _isLoading.value = false
                _gotError.value = true
                return@launch
            }
            if (data != null) {
// Handle success case
                Log.i("Got data", "Got data")
                _isLoading.value = false
                _planetDetails.value = data
                //Log.i("Juan", data.toString())
            } else {
// Handle empty data
                Log.d("Got nothing", "Got data")
                _isLoading.value = false
            }
        }
    }
}

class MyViewModelFactory(
    private val id: Int,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModel(id) as T
    }
}