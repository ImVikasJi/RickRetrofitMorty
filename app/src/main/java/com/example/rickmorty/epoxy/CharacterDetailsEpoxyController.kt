package com.example.rickmorty.epoxy

import android.view.View
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.rickmorty.R
import com.example.rickmorty.databinding.*
import com.example.rickmorty.model.Character
import com.example.rickmorty.model.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController : EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var character: Character? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
        }

        if (character == null) {
            // todo error state
            return
        }

        // add header model
        HeaderEpoxyModel(
            name = character!!.name,
            gender = character!!.gender,
            status = character!!.status
        ).id("header").addTo(this)


        // add image model
        ImageEpoxyModel(
            imageUrl = character!!.image
        ).id("image").addTo(this)

        // Episode carousel list section
        if (character!!.episodeList.isNotEmpty()) {
            val items = character!!.episodeList.map {
                EpisodeCarouselItemEpoxyModel(it).id(it.id)
            }
            TitleEpoxyModel("Episodes").id("titles_episodes").addTo(this)
            CarouselModel_()
                .id("episode_carousel")
                .models(items)
                .numViewsToShowOnScreen(1.15F)
                .addTo(this)
        }


        // add the data points models
        DataPointEpoxyModel(
            title = "Origin",
            description = character!!.origin.name
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            title = "Species",
            description = character!!.species
        ).id("data_point_2").addTo(this)
    }

    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
        val status: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            nameTextView.text = name
            aliveTextView.text = status

            if (gender.equals("male", true))
                genderImageView.setImageResource(R.drawable.ic__male_24)
            else
                genderImageView.setImageResource(R.drawable.ic__female_24)
        }

    }

    data class ImageEpoxyModel(
        val imageUrl: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(imageUrl).into(headerImageView)
        }
    }

    data class DataPointEpoxyModel(
        val title: String,
        val description: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }
    }

    data class EpisodeCarouselItemEpoxyModel(
        val episode: Character.Episode
    ) : ViewBindingKotlinModel<ModelEpisodeCarouselItemBinding>(R.layout.model_episode_carousel_item) {
        override fun ModelEpisodeCarouselItemBinding.bind() {
            episodeTextView.text = episode.getFormattedSeasonTruncated()
            episodeDetailsTextView.text = "${episode.name}\n${episode.airDate}"
        }
    }

    data class TitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelTitleBinding>(R.layout.model_title){
        override fun ModelTitleBinding.bind() {
            titleTextView.text = title
        }

    }
}