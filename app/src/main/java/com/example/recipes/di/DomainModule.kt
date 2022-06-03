package com.example.recipes.di

import com.example.recipes.domain.categories.CategoriesInteractor
import com.example.recipes.domain.categories.CategoriesRepository
import com.example.recipes.domain.cuisines.CuisinesInteractor
import com.example.recipes.domain.cuisines.CuisinesRepository
import com.example.recipes.domain.details.DetailsInteractor
import com.example.recipes.domain.details.DetailsRepository
import com.example.recipes.domain.favorites.FavoritePreviewsInteractor
import com.example.recipes.domain.favorites.FavoritePreviewsRepository
import com.example.recipes.domain.previews.PreviewsInteractor
import com.example.recipes.domain.previews.PreviewsRepository
import com.example.recipes.domain.settings.SettingsInteractor
import com.example.recipes.domain.settings.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideCategoriesInteractor(categoriesRepository: CategoriesRepository): CategoriesInteractor =
        CategoriesInteractor(categoriesRepository)

    @Provides
    fun provideCuisinesInteractor(cuisinesRepository: CuisinesRepository): CuisinesInteractor =
        CuisinesInteractor(cuisinesRepository)

    @Provides
    fun providePreviewsInteractor(previewsRepository: PreviewsRepository): PreviewsInteractor =
        PreviewsInteractor(previewsRepository)

    @Provides
    fun provideDetailsInteractor(detailsRepository: DetailsRepository): DetailsInteractor =
        DetailsInteractor(detailsRepository)

    @Provides
    fun provideFavoritePreviewsInteractor(favoritePreviewsRepository: FavoritePreviewsRepository): FavoritePreviewsInteractor =
        FavoritePreviewsInteractor(favoritePreviewsRepository)

    @Provides
    fun provideSettingsInteractor(settingsRepository: SettingsRepository): SettingsInteractor =
        SettingsInteractor(settingsRepository)
}