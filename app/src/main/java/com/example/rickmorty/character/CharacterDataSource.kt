package com.example.rickmorty.character


import androidx.paging.PageKeyedDataSource
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.example.rickmorty.repository.CharacterRepository
import com.example.rickmorty.repository.RickAndMortyRepository
import com.example.rickmorty.viewmodel.RickAndMortyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharacterDataSource(
    private val coroutineScope: CoroutineScope,
    private val characterRepository: CharacterRepository
) : PageKeyedDataSource<Int, GetCharacterByIdResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = characterRepository.getCharacterPage(1)

            if (page == null) {
                callback.onResult(emptyList(), null, null)
                return@launch
            }

            callback.onResult(page.results, null, getPageIndexFromNext(page.info.next))
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = characterRepository.getCharacterPage(params.key)

            if (page == null) {
                callback.onResult(emptyList(), null)
                return@launch
            }

            callback.onResult(page.results, getPageIndexFromNext(page.info.next))
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        // Nothing to do
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }


}