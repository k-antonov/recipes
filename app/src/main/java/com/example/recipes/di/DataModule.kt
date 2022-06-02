package com.example.recipes.di

import android.content.Context
import com.example.recipes.data.datasources.local.localdatasources.*
import com.example.recipes.data.datasources.remote.RecipeApiService
import com.example.recipes.data.datasources.remote.RecipeApiServiceImpl
import com.example.recipes.data.repositories.*
import com.example.recipes.domain.repositories.*
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
    fun provideCommonLocalDataSource(@ApplicationContext context: Context): CommonLocalDataSource =
        CommonLocalDataSource(context)


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
        commonLocalDataSource: CommonLocalDataSource
    ): CommonRepository = CommonRepositoryImpl(commonLocalDataSource)

    @Provides
    @Singleton
    fun provideFavoritePreviewsRepository(
        recipeLocalDataSource: RecipeLocalDataSource
    ): FavoritePreviewsRepository =
        FavoritePreviewsRepositoryImpl(recipeLocalDataSource)
}