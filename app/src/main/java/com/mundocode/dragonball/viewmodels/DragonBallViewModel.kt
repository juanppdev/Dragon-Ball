package com.mundocode.dragonball.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonball.models.DragonBallModel
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonball.network.ApiDragonBall
import com.mundocode.dragonball.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

interface PokemonListRepositoryInterface {
    suspend fun getPokemonList(): Response<DragonBallModel>
}

class PokemonListRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
): PokemonListRepositoryInterface {
    override suspend fun getPokemonList(): Response<DragonBallModel> {
        return apiService.obtenerPersonajes()
    }
}


class PokemonListViewModel(
    private val repository: PokemonListRepositoryInterface = PokemonListRepository()
): ViewModel() {
    private val _pokemonList = MutableStateFlow<DragonBallModel?>(null)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(true)

    val dragonList: StateFlow<DragonBallModel?> get() = _pokemonList.asStateFlow()
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    fun getPokemonList() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getPokemonList()
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    Log.d("Success", "$body?.size")
                    _isLoading.value = false
                    _pokemonList.value = body
                }
            } else {
                val error = response.errorBody()
                if(error != null) {
                    Log.d("Pokemon List Error", error.string())
                    _isLoading.value = false
                    _errorMessage.value = error.string()
                }
            }
        }
    }
}

interface DragonDetailsRepositoryInterface {
    suspend fun obtenerPersonaje(id: Int): Response<SingleDragonBallLista>
}

class DragonDetailsRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
): DragonDetailsRepositoryInterface {
    override suspend fun obtenerPersonaje(id: Int): Response<SingleDragonBallLista> {
        return apiService.obtenerPersonaje(id)
    }
}

class MyViewModel(
    id: Int,
    private val repository: DragonDetailsRepositoryInterface = DragonDetailsRepository()
) : ViewModel() {

    // Mutable States
    private val _dragonDetails = MutableStateFlow<SingleDragonBallLista?>(null)
    private val _isLoading = MutableStateFlow(true)
    private val _gotError = MutableStateFlow(false)

    // States
    val dragonDetails: StateFlow<SingleDragonBallLista?> get() = _dragonDetails.asStateFlow()
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()
    val gotError: StateFlow<Boolean> get() = _gotError.asStateFlow()

    init {
        fetchDetails(id)
    }


    fun fetchDetails(id: Int) {
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
}

class MyViewModelFactory(
    private val id: Int,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModel(id) as T
    }
}