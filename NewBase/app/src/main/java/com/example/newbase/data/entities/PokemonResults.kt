package com.example.newbase.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Pokemons")
data class PokemonResults (
    @PrimaryKey
    @SerializedName("name")
    var name: String,  // Made it non-nullable
    @ColumnInfo(name = "url")
    @SerializedName("url")
    var url: String? = null
)