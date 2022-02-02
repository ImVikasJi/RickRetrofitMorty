package com.example.rickmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.epoxy.CharacterDetailsEpoxyController
import com.example.rickmorty.epoxy.CharacterListPagingEpoxyController
import com.example.rickmorty.util.Constants
import com.example.rickmorty.viewmodel.CharacterViewModel
import com.example.rickmorty.viewmodel.RickAndMortyViewModel


class CharacterDetailFragment : Fragment() {

    private val rickAndMortyViewModel: RickAndMortyViewModel by viewModels()

    private val epoxyController = CharacterDetailsEpoxyController()

    private val safeArgs: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        rickAndMortyViewModel.characterByIdLiveData.observe(viewLifecycleOwner) { character ->

            /** This epoxyController will now contain the response and pass into the
            HeaderEpoxyModel , ImageEpoxyModel and DataPointEpoxyModel **/
            epoxyController.character = character
            if (character == null) {
                Toast.makeText(requireActivity(), "Unsuccessful Network Call", Toast.LENGTH_SHORT)
                    .show()
                return@observe
            }
        }

        rickAndMortyViewModel.refreshCharacter(characterId = safeArgs.characterId)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}