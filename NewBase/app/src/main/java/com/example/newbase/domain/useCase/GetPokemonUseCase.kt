package com.example.newbase.domain.useCase

import android.content.Context
import com.example.newbase.data.repo.PokemonRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    fun getAllPokemons() = pokemonRepository.getPokemonsLocal()
    suspend fun getPokemonDetail(id: Int) = pokemonRepository.getPokemonDetail(id)
}