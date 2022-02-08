package com.example.rickmorty.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.rickmorty.R
import com.example.rickmorty.databinding.FragmentEpisodeListBinding
import com.example.rickmorty.epoxy.EpisodeListEpoxyController
import com.example.rickmorty.model.Character
import com.example.rickmorty.viewmodel.EpisodeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeListFragment : Fragment(R.layout.fragment_episode_list) {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding

    private val episodeViewModel: EpisodeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)

        val epoxyController = EpisodeListEpoxyController { episodeClickedId ->
            val navDirections =
                EpisodeListFragmentDirections.actionEpisodeListFragmentToEpisodeDetailBottomSheetFragment(
                    episodeClickedId
                )
            findNavController().navigate(navDirections)
        }

        lifecycleScope.launch {
            episodeViewModel.flow.collectLatest { pagingData: PagingData<EpisodeUiModel> ->
                epoxyController.submitData(pagingData)
            }
        }

        binding?.epoxyRecyclerView?.setController(epoxyController) // Maybe a problem here
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}