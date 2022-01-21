package com.example.rickmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.rickmorty.databinding.ActivityMainBinding
import com.example.rickmorty.repository.RickAndMortyRepository
import com.example.rickmorty.viewmodel.RickAndMortyViewModel
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val rickAndMortyViewModel: RickAndMortyViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        rickAndMortyViewModel.refreshCharacter(54)

        rickAndMortyViewModel.characterByIdLiveData.observe(this) { response ->
            if (response == null) {
                Toast.makeText(this@MainActivity, "Unsuccessful Network Call", Toast.LENGTH_SHORT)
                    .show()
                return@observe
            }

            binding.nameTextView.text = response.name
            binding.aliveTextView.text = response.status
            binding.speciesTextView.text = response.species
            binding.originTextView.text = response.origin.name

            if(response.gender.equals("male",true))
                binding.genderImageView.setImageResource(R.drawable.ic__male_24)
            else
                binding.genderImageView.setImageResource(R.drawable.ic__female_24)

            Picasso.get().load(response.image).into(binding.headerImageView)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}