package com.example.recipes.di

import android.content.Context
import com.example.recipes.data.categories.datasources.local.CategoryLocalDataSource
import com.example.recipes.data.categories.repository.CategoriesRepositoryImpl
import com.example.recipes.data.cuisines.repository.CuisinesRepositoryImpl
import com.example.recipes.data.core.datasources.remote.RecipeApiService
import com.example.recipes.data.core.datasources.remote.RecipeApiServiceImpl
import com.example.recipes.data.cuisines.datasources.local.CuisineLocalDataSource
import com.example.recipes.data.details.datasources.local.localdatasources.IngredientLocalDataSource
import com.example.recipes.data.details.datasources.local.localdatasources.MeasureLocalDataSource
import com.example.recipes.data.details.datasources.local.localdatasources.RecipeLocalDataSource
import com.example.recipes.data.details.datasources.local.localdatasources.RecipesToIngredientsAndMeasuresLocalDataSource
import com.example.recipes.data.details.repository.DetailsRepositoryImpl
import com.example.recipes.data.favorites.repository.FavoritePreviewsRepositoryImpl
import com.example.recipes.data.previews.repository.PreviewsRepositoryImpl
import com.example.recipes.data.settings.datasources.local.SettingsLocalDataSource
import com.example.recipes.data.settings.repository.SettingsRepositoryImpl
import com.example.recipes.domain.categories.CategoriesRepository
import com.example.recipes.domain.cuisines.CuisinesRepository
import com.example.recipes.domain.details.DetailsRepository
import com.example.recipes.domain.favorites.FavoritePreviewsRepository
import com.example.recipes.domain.previews.PreviewsRepository
import com.example.recipes.domain.settings.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    @Singleton
    fun provideCategoryLocalDataSource(@ApplicationContext context: Context): CategoryLocalDataSource =
        CategoryLocalDataSource(context)

    @Provides
    @Singleton
    fun provideCuisineLocalDataSource(@ApplicationContext context: Context): CuisineLocalDataSource =
        CuisineLocalDataSource(context)

    @Provides
    @Singleton
    fun provideIngredientLocalDataSource(@ApplicationContext context: Context): IngredientLocalDataSource =
        IngredientLocalDataSource(context)

    @Provides
    @Singleton
    fun provideMeasureLocalDataSource(@ApplicationContext context: Context): MeasureLocalDataSource =
        MeasureLocalDataSource(context)

    @Provides
    @Singleton
    fun provideRecipesToIngredientsAndMeasuresLocalDataSource(
        @ApplicationContext context: Context
    ): RecipesToIngredientsAndMeasuresLocalDataSource =
        RecipesToIngredientsAndMeasuresLocalDataSource(context)

    @Provides
    @Singleton
    fun provideRecipeLocalDataSource(
        @ApplicationContext context: Context,
        recipesToIngredientsAndMeasuresLocalDataSource: RecipesToIngredientsAndMeasuresLocalDataSource
    ): RecipeLocalDataSource =
        RecipeLocalDataSource(context, recipesToIngredientsAndMeasuresLocalDataSource)

    @Provides
    @Singleton
    fun provideCommonLocalDataSource(@ApplicationContext context: Context): SettingsLocalDataSource =
        SettingsLocalDataSource(context)


    @Provides
    @Singleton
    fun provideRecipeApiService(okHttpClient: OkHttpClient): RecipeApiService =
        RecipeApiServiceImpl(okHttpClient)

    @Provides
    @Singleton
    fun provideCategoriesRepository(
        recipeApiService: RecipeApiService,
        categoryLocalDataSource: CategoryLocalDataSource
    ): CategoriesRepository = CategoriesRepositoryImpl(recipeApiService, categoryLocalDataSource)

    @Provides
    @Singleton
    fun provideCuisinesRepository(
        recipeApiService: RecipeApiService,
        cuisineLocalDataSource: CuisineLocalDataSource
    ): CuisinesRepository = CuisinesRepositoryImpl(recipeApiService, cuisineLocalDataSource)

    @Provides
    @Singleton
    fun providePreviewsRepository(
        recipeApiService: RecipeApiService,
        recipeLocalDataSource: RecipeLocalDataSource
    ): PreviewsRepository = PreviewsRepositoryImpl(recipeApiService, recipeLocalDataSource)

    @Provides
    @Singleton
    fun provideDetailsRepository(
        recipeApiService: RecipeApiService,
        categoryLocalDataSource: CategoryLocalDataSource,
        cuisineLocalDataSource: CuisineLocalDataSource,
        recipeLocalDataSource: RecipeLocalDataSource,
        ingredientLocalDataSource: IngredientLocalDataSource,
        measureLocalDataSource: MeasureLocalDataSource,
        recipesToIngredientsAndMeasuresLocalDataSource: RecipesToIngredientsAndMeasuresLocalDataSource
    ): DetailsRepository = DetailsRepositoryImpl(
        recipeApiService,
        categoryLocalDataSource,
        cuisineLocalDataSource,
        recipeLocalDataSource,
        ingredientLocalDataSource,
        measureLocalDataSource,
        recipesToIngredientsAndMeasuresLocalDataSource
    )

    @Provides
    @Singleton
    fun provideCommonRepository(
        settingsLocalDataSource: SettingsLocalDataSource
    ): SettingsRepository = SettingsRepositoryImpl(settingsLocalDataSource)

    @Provides
    @Singleton
    fun provideFavoritePreviewsRepository(
        recipeLocalDataSource: RecipeLocalDataSource
    ): FavoritePreviewsRepository =
        FavoritePreviewsRepositoryImpl(recipeLocalDataSource)
}