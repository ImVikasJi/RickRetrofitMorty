package com.example.rickmorty.character

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.R
import com.example.rickmorty.databinding.ActivityCharacterListBinding
import com.example.rickmorty.databinding.ActivityMainBinding
import com.example.rickmorty.epoxy.CharacterListPagingEpoxyController
import com.example.rickmorty.viewmodel.CharacterViewModel

class CharacterListActivity : AppCompatActivity() {

    private var _binding: ActivityCharacterListBinding? = null
    private val binding get() = _binding!!

    private val epoxyController = CharacterListPagingEpoxyController()

    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        characterViewModel.characterPagedListLiveData.observe(this) { pageList ->
            epoxyController.submitList(pageList)
        }
            findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}