package com.example.newbase.ui.main.pokemons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.newbase.data.entities.PokemonDetail
import com.example.newbase.data.entities.PokemonResults
import com.example.newbase.domain.useCase.GetPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonUseCase: GetPokemonUseCase
): ViewModel() {
    var navController: NavController? = null
    val pokemons = getPokemonUseCase.getAllPokemons()
    var selectedPokemon: PokemonResults? = null
    var pokemonDetail: MutableLiveData<PokemonDetail?> = MutableLiveData()

    fun getPokemonDetail(id: Int){
        viewModelScope.launch {
            var res = getPokemonUseCase.getPokemonDetail(id)
            withContext(Dispatchers.Main) {
                pokemonDetail.postValue(res.data)
            }
        }
    }

}