package com.example.rickmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.example.rickmorty.databinding.ActivityMainBinding
import com.example.rickmorty.epoxy.CharacterDetailsEpoxyController
import com.example.rickmorty.repository.RickAndMortyRepository
import com.example.rickmorty.util.Constants.Companion.INTENT_EXTRA_CHAR_ID
import com.example.rickmorty.viewmodel.RickAndMortyViewModel
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val rickAndMortyViewModel: RickAndMortyViewModel by viewModels()

    private val epoxyController = CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rickAndMortyViewModel.refreshCharacter(54)

        rickAndMortyViewModel.characterByIdLiveData.observe(this) { response ->

            /** This epoxyController will now contain the response and pass into the
            HeaderEpoxyModel , ImageEpoxyModel and DataPointEpoxyModel **/
            epoxyController.characterResponse = response
            if (response == null) {
                Toast.makeText(this@MainActivity, "Unsuccessful Network Call", Toast.LENGTH_SHORT)
                    .show()
                return@observe
            }
        }

        val id = intent.getIntExtra(INTENT_EXTRA_CHAR_ID, 1)
        rickAndMortyViewModel.refreshCharacter(characterId = id)

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    // This will return back to previous activity when the back button will be clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}