package com.example.rickmorty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.rickmorty.databinding.FragmentEpisodeListBinding

class EpisodeListFragment : Fragment(R.layout.fragment_episode_list) {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)

        // todo
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}