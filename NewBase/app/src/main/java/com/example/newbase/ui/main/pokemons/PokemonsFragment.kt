package com.example.newbase.ui.main.pokemons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbase.R
import com.example.newbase.core.entity.Resource
import com.example.newbase.data.entities.PokemonResults
import com.example.newbase.databinding.FragmentPokemonsBinding
import com.example.newbase.ui.main.adapter.PokemonAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFragment : Fragment(), PokemonAdapter.itemClickListener {

    private val viewModel: PokemonViewModel by activityViewModels()
    private var binding: FragmentPokemonsBinding? = null
    private val pokemonAdapter: PokemonAdapter by lazy {
        PokemonAdapter(requireContext(), arrayListOf()).apply {
            setItemListener(this@PokemonsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        initObservers()
    }

    private fun setUpView() {
        binding?.pokeRv?.let {
            it.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = pokemonAdapter
            }
        }
    }

    private fun initObservers() {
        viewModel.pokemons.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    if(!it.data.isNullOrEmpty()) pokemonAdapter.addItems(it.data)
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding?.progressBar?.visibility = View.VISIBLE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onItemClicked(poke: PokemonResults) {
        viewModel.selectedPokemon = poke
        viewModel.navController?.navigate(R.id.action_pokeFragment_to_pokeDetailFragment)
    }

    override fun onPause() {
        super.onPause()
    }

}