package com.example.newbase.data.repo

import androidx.lifecycle.liveData
import com.example.newbase.core.entity.Resource
import com.example.newbase.data.dataSource.local.DatabaseBuilder
import com.example.newbase.data.dataSource.remote.PokemonRemoteDataSource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
) {
    fun getPokemons() = liveData(Dispatchers.IO) {
        val responseStatus = pokemonRemoteDataSource.getPokemons()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            responseStatus.data?.let { pokemons ->
                DatabaseBuilder.getInstance().pokemonDao().insertAll(pokemons.results)
            }
            emit(Resource(Resource.Status.SUCCESS,responseStatus.data?.results,""))
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }
    suspend fun getPokemonDetail(id: Int) = pokemonRemoteDataSource.getPokemonDetail(id)

    fun getPokemonsLocal() = liveData(Dispatchers.IO) {
        val responseStatus = DatabaseBuilder.getInstance().pokemonDao().getAllPokemons()
        if (responseStatus.isNullOrEmpty()) {
            emitSource(getPokemons())
        } else {
            emit(Resource(Resource.Status.SUCCESS,responseStatus, ""))
        }
    }

}