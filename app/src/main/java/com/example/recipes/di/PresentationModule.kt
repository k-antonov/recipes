package com.example.recipes.di

import android.content.Context
import com.example.recipes.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(FragmentComponent::class)
class PresentationModule {

    @Provides
    fun provideChildFragmentTitles(@ApplicationContext context: Context): List<String> =
        listOf(
            context.resources.getString(R.string.categories),
            context.resources.getString(R.string.cuisines)
        )
}