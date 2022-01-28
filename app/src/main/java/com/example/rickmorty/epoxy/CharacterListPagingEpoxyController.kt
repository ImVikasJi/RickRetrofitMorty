package com.example.rickmorty.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.rickmorty.R
import com.example.rickmorty.databinding.ModelCharacterListItemBinding
import com.example.rickmorty.databinding.ModelCharacterListTitleBinding
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.squareup.picasso.Picasso
import java.util.*

class CharacterListPagingEpoxyController(
    private val onCharacterSelected: (Int) -> Unit
) : PagedListEpoxyController<GetCharacterByIdResponse>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(
            characterId = item!!.id,
            imageUrl = item.image,
            name = item.name,
            onCharacterSelected = onCharacterSelected
        ).id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }
        // This Header will show 5 main family members of Rick and morty
        CharacterGridTitleEpoxyModel("Main Family")
            .id("main_family_header")
            .addTo(this)

        // sublist will take index from 0 to n-1
        super.addModels(models.subList(0, 5))

        // This sublist will take members from index 5 to size-1 ,
        // as a List of CharacterGridTitleEpoxyModel
        // groupBy will return a list of each model with the value we have given as a map
        (models.subList(5, models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
            it.name[0].toString().uppercase(Locale.US)
        }.forEach { mapEntry ->
            val character = mapEntry.key.uppercase(Locale.US)
            CharacterGridTitleEpoxyModel(character)
                .id(character)
                .addTo(this)

            super.addModels(mapEntry.value)
        }
    }


    data class CharacterGridItemEpoxyModel(
        val characterId: Int,
        val imageUrl: String,
        val name: String,
        val onCharacterSelected: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {

        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name

            root.setOnClickListener {
                onCharacterSelected(characterId)
            }
        }

    }

    data class CharacterGridTitleEpoxyModel(
        val title: String
    ) : ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {
        override fun ModelCharacterListTitleBinding.bind() {
            tvHeaderTextView.text = title
        }

        // This will set the length of the header to the span count return by the func()
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

    }
}