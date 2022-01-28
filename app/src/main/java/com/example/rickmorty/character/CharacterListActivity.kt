package com.example.rickmorty.character

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.MainActivity
import com.example.rickmorty.R
import com.example.rickmorty.databinding.ActivityCharacterListBinding
import com.example.rickmorty.databinding.ActivityMainBinding
import com.example.rickmorty.epoxy.CharacterListPagingEpoxyController
import com.example.rickmorty.util.Constants.Companion.INTENT_EXTRA_CHAR_ID
import com.example.rickmorty.viewmodel.CharacterViewModel

class CharacterListActivity : AppCompatActivity() {

    private var _binding: ActivityCharacterListBinding? = null
    private val binding get() = _binding!!

    private val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)

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

    private fun onCharacterSelected(characterId: Int){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(INTENT_EXTRA_CHAR_ID,characterId)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}