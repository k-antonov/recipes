package com.example.recipes.di

import com.example.recipes.domain.interactors.*
import com.example.recipes.domain.repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideCategoriesInteractor(categoriesRepository: CategoriesRepository): CategoriesInteractor =
        CategoriesInteractor(categoriesRepository = categoriesRepository)

    @Provides
    fun provideCuisinesInteractor(cuisinesRepository: CuisinesRepository): CuisinesInteractor =
        CuisinesInteractor(cuisinesRepository =  cuisinesRepository)

    @Provides
    fun providePreviewsInteractor(previewsRepository: PreviewsRepository): PreviewsInteractor =
        PreviewsInteractor(previewsRepository = previewsRepository)

    @Provides
    fun provideDetailsInteractor(detailsRepository: DetailsRepository): DetailsInteractor =
        DetailsInteractor(detailsRepository = detailsRepository)

    @Provides
    fun provideFavoritePreviewsInteractor(favoritePreviewsRepository: FavoritePreviewsRepository): FavoritePreviewsInteractor =
        FavoritePreviewsInteractor(favoritePreviewsRepository = favoritePreviewsRepository)

    @Provides
    fun provideSettingsInteractor(commonRepository: CommonRepository): SettingsInteractor =
        SettingsInteractor(commonRepository = commonRepository)
}