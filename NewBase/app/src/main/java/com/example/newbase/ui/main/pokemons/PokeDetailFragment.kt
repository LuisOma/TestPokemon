package com.example.newbase.ui.main.pokemons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.newbase.databinding.FragmentPokeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokeDetailFragment : Fragment() {

    private val viewModel: PokemonViewModel by activityViewModels()
    private var binding: FragmentPokeDetailBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokeDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        viewModel.selectedPokemon?.let {
            viewModel.getPokemonDetail(extractPokemonId(it.url ?: "") ?: 0)
        }

        viewModel.pokemonDetail.observe(viewLifecycleOwner, Observer {
            val poke = it?.apply {
                sprite =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
                        viewModel.selectedPokemon?.url?.let { it1 ->
                            extractPokemonId(
                                it1
                            )
                        }
                    }.png"
            }
            binding?.name?.setText("Nombre: " + poke?.name)
            binding?.count?.setText("Número de pokemon: "+poke?.order)
            binding?.weight?.setText("Peso: "+ poke?.weight)

            binding?.image?.let { it1 ->
                Glide.with(requireContext())
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
                        viewModel.selectedPokemon?.url?.let { it1 ->
                            extractPokemonId(
                                it1
                            )
                        }
                    }.png")
                    .into(it1)
            }

        })
    }

    fun extractPokemonId(url: String): Int? {
        val regex = """https://pokeapi.co/api/v2/pokemon/(\d+)/""".toRegex()
        val match = regex.find(url)
        return match?.groups?.get(1)?.value?.toInt()
    }

}