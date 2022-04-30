package com.example.recipes.data.repositories

import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.domain.repositories.RecipeRepository
import com.example.recipes.domain.entities.RecipeDetailsDomain
import com.example.recipes.domain.entities.RecipeFeedDomain

class RecipeRepositoryImpl(private val recipeApiService: RecipeApiService) : RecipeRepository {

//    val recipes: LiveData<Result<List<RecipeData>>>
//        get() {
//            Log.d("RecipeRepository", "calling getRecipeList $this")
//            return recipeApiService.getRecipeList()
//        }

    override val recipeFeedDomainList: List<RecipeFeedDomain>
        get() {
            return listOf(
                RecipeFeedDomain(0, "mockTitle", "https://eda.yandex.ru/images/2394388/53893769e4f14689fb664551e7061431-680x500.jpg")
            )
        }


    override val recipeDetailsDomain: RecipeDetailsDomain
        get() {
            return RecipeDetailsDomain(
                0, "mockTitle", listOf("mock ingredients"),
                listOf("mock instructions"), "mockUrl"
            )
        }
}