package com.example.newbase.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newbase.data.entities.PokemonResults

@Dao
interface PokemonDao {

   @Query("Select * from Pokemons")
   fun getAllPokemons(): List<PokemonResults>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<PokemonResults>)

}