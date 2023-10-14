package com.example.newbase.data.entities

import com.google.gson.annotations.SerializedName

data class PokemonDetail (

    @SerializedName("name" ) var name : String? = null,
    @SerializedName("order"  ) var order  : String? = null,
    @SerializedName("weight"  ) var weight  : String? = null,
    @SerializedName("sprite"  ) var sprite  : String? = null

)