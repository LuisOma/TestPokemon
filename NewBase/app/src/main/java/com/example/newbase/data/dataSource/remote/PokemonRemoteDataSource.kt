package com.example.newbase.data.dataSource.remote

import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
        private val pokemonService: PokemonService
    ): BaseRemoteDataSource() {
        suspend fun getPokemons() = getResult { pokemonService.getPokemonList() }
        suspend fun getPokemonDetail(id: Int) = getResult { pokemonService.getPokemonDetail(id) }

}