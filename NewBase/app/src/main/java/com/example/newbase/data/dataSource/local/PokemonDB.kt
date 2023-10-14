package com.example.newbase.data.dataSource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newbase.data.dataSource.local.dao.PokemonDao
import com.example.newbase.data.entities.PokemonResults

@Database(entities = [PokemonResults::class], version = 1)
abstract class PokemonDB : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}

object DatabaseBuilder {
    private var INSTANCE: PokemonDB? = null

    fun getInstance(): PokemonDB {
        return INSTANCE ?: throw IllegalStateException("Database not initialized!")
    }

    fun initDB(context: Context){
        synchronized(PokemonDB::class) {
            if (INSTANCE == null) {
                INSTANCE = buildRoomDB(context)
            }
        }
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            PokemonDB::class.java,
            "my-database"
        ).build()
}