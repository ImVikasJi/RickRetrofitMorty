package com.example.rickmorty

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.epoxy.CharacterDetailsEpoxyController
import com.example.rickmorty.epoxy.CharacterListPagingEpoxyController
import com.example.rickmorty.util.Constants
import com.example.rickmorty.viewmodel.CharacterViewModel
import com.example.rickmorty.viewmodel.RickAndMortyViewModel

class CharacterListFragment : Fragment() {

    private val characterViewModel: CharacterViewModel by viewModels()

    private val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterViewModel.characterPagedListLiveData.observe(viewLifecycleOwner) { pageList ->
            epoxyController.submitList(pageList)
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)

    }

    private fun onCharacterSelected(characterId: Int) {

        val directions = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
            characterId = characterId
        )

        findNavController().navigate(directions)
    }
}

