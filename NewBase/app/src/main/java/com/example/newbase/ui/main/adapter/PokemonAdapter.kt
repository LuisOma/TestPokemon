package com.example.newbase.ui.main.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.newbase.R
import com.example.newbase.constants.Constants
import com.example.newbase.core.entity.Resource
import com.example.newbase.data.entities.PokemonResults
import com.example.newbase.databinding.NetworkStateItemBinding
import com.example.newbase.databinding.PokemonItemBinding
import java.util.*
import kotlin.collections.ArrayList

class PokemonAdapter(private val context: Context, private val pokeList: ArrayList<PokemonResults>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var networkState: Resource.Status? = null
    private var listener: itemClickListener? = null

    var pokeFilterList = ArrayList<PokemonResults>()

    init {
        pokeFilterList = pokeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == Constants.POKE_VIEW_TYPE) {
            val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PokemonItemViewHolder(
                binding
            )
        } else {
            val binding = NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NetworkStateItemViewHolder(
                binding
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == Constants.POKE_VIEW_TYPE) {
            (holder as PokemonItemViewHolder).bind(pokeFilterList[position],context)
            holder.itemView.setOnClickListener{
                pokeFilterList[position].let { it1 -> listener?.onItemClicked(it1) }
            }
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    fun setItemListener(listener: itemClickListener){
        this.listener = listener
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != Resource.Status.SUCCESS
    }

    override fun getItemCount() = pokeFilterList.size

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            Constants.NETWORK_VIEW_TYPE
        } else {
            Constants.POKE_VIEW_TYPE
        }
    }

    class PokemonItemViewHolder (private val binding: PokemonItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"


        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(pokemon: PokemonResults?, context: Context) {


            binding.cvPokeTitle.text = pokemon?.name
            var id= pokemon?.url?.let { extractPokemonId(it) }
            val pokeURL = IMAGE_BASE_URL + id + ".png"
            binding.cvIvPokePoster.loadImageOrText(pokeURL, pokemon?.name?:"", context.resources.getDrawable(R.drawable.ic_launcher_background))
        }

        fun extractPokemonId(url: String): Int? {
            val regex = """https://pokeapi.co/api/v2/pokemon/(\d+)/""".toRegex()
            val match = regex.find(url)
            return match?.groups?.get(1)?.value?.toInt()
        }
    }

    class NetworkStateItemViewHolder (private val binding: NetworkStateItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(networkState: Resource.Status?) {
            if (networkState != null && networkState == Resource.Status.LOADING) {
                binding.progressBarItem.visibility = View.VISIBLE
            }
            else  {
                binding.progressBarItem.visibility = View.GONE
            }
            if (networkState != null && networkState == Resource.Status.ERROR) {
                binding.errorMsgItem.visibility = View.VISIBLE
            }
            else {
                binding.errorMsgItem.visibility = View.GONE
            }
        }
    }

    fun addItems(list: List<PokemonResults>){
        pokeFilterList.clear()
        pokeFilterList.addAll(list)
        notifyDataSetChanged()
    }

    interface itemClickListener{
        fun onItemClicked(poke:PokemonResults)
    }

}
