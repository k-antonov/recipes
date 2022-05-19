package com.example.recipes.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.data.datasources.cloud.mappers.CategoriesCloudToCategoryDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.CuisinesCloudToCuisineDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.DetailsCloudToDetailDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.PreviewsCloudToPreviewDomainListMapper
import com.example.recipes.data.datasources.local.*
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.repositories.RecipeRepository

class RecipeRepositoryImpl(
    private val recipeApiService: RecipeApiService,
    private val localDataSource: LocalDataSource
) : RecipeRepository {

    private val categoryMapper: CategoriesCloudToCategoryDomainListMapper by lazy { CategoriesCloudToCategoryDomainListMapper() }
    private val cuisineMapper: CuisinesCloudToCuisineDomainListMapper by lazy { CuisinesCloudToCuisineDomainListMapper() }
    private val previewMapper: PreviewsCloudToPreviewDomainListMapper by lazy { PreviewsCloudToPreviewDomainListMapper() }
    private val detailMapper: DetailsCloudToDetailDomainListMapper by lazy { DetailsCloudToDetailDomainListMapper() }

    override fun getCategoryDomainList(): LiveData<Result<List<CategoryDomain>>> {
        return categoryMapper.mapLiveData(recipeApiService.getCategoriesCloud())
    }

    override fun getCuisineDomainList(): LiveData<Result<List<CuisineDomain>>> {
        return cuisineMapper.mapLiveData(recipeApiService.getCuisinesCloud())
    }

    override fun getPreviewDomainList(endpoint: String): LiveData<Result<List<PreviewDomain>>> {
        val previewsCloud = recipeApiService.getPreviewsCloud(endpoint)



        return previewMapper.mapLiveData(previewsCloud)
    }

    override fun getDetailDomainList(endpoint: String): LiveData<Result<List<DetailDomain>>> {
        val detailDomain = detailMapper.mapLiveData(recipeApiService.getDetailsCloud(endpoint))

        detailDomain.observeForever { result ->
            result.onSuccess {
                Log.d("Repository", "inside onSuccess")
                val value = it[0]

                if (localDataSource.getRecipeDao().isRecordExist(value.id.toLong()) == 0) {

                    var categoryId =
                        localDataSource.getCategoryDao().getIdByName(value.nameCategory)
                    if (categoryId == 0L) {
                        categoryId = localDataSource.getCategoryDao().insert(
                            CategoryDb(
                                name = value.nameCategory
                            )
                        )
                    }

                    var cuisineId = localDataSource.getCuisineDao().getIdByName(value.nameCuisine)
                    if (cuisineId == 0L) {
                        cuisineId = localDataSource.getCuisineDao().insert(
                            CuisineDb(
                                name = value.nameCuisine
                            )
                        )
                    }

                    localDataSource.getRecipeDao().insert(
                        RecipeDb(
                            id = value.id.toLong(),
                            name = value.name,
                            categoryId = categoryId,
                            cuisineId = cuisineId,
                            instructions = value.strInstructions,
                            imageUrl = value.imageUrl
                        )
                    )

                    for (pair in value.ingredients.zip(value.measures)) {
                        var ingredientId =
                            localDataSource.getIngredientDao().getIdByName(pair.first)
                        var measureId = localDataSource.getMeasureDao().getIdByName(pair.second)

                        if (ingredientId == 0L) {
                            ingredientId = localDataSource.getIngredientDao().insert(
                                IngredientDb(
                                    name = pair.first
                                )
                            )
                        }

                        if (measureId == 0L) {
                            measureId = localDataSource.getMeasureDao().insert(
                                MeasureDb(
                                    name = pair.second
                                )
                            )
                        }

                        Log.d(
                            "RecipeRepo",
                            "recipeId=${value.id.toLong()}, ingredientId=${ingredientId}," +
                                    "measureId=${measureId}"
                        )
                        localDataSource.getRecipesToIngredientsAndMeasuresDao().insert(
                            RecipesToIngredientsAndMeasures(
                                recipeId = value.id.toLong(),
                                ingredientId = ingredientId,
                                measureId = measureId
                            )
                        )
                    }
                }
            }
        }

        return detailDomain
    }

    override fun getLocalPreviewDomainList(): LiveData<Result<List<PreviewDomain>>> {
        val liveData = MutableLiveData(localDataSource.getRecipeDao().getPreviews())

        val liveDataResult = Transformations.map(liveData) {
            Result.success(it)
        }

        return liveDataResult
    }

    override fun getLocalDetailDomainList(id: Long): LiveData<Result<List<DetailDomain>>> {

        val recipesTable = localDataSource.getRecipeDao().getDetailsById(id)
        val recipesToIngredientsAndMeasuresTable =
            localDataSource.getRecipesToIngredientsAndMeasuresDao().getIngredientsAndMeasuresById(id)

        val ingredients = mutableListOf<String>()
        val measures = mutableListOf<String>()
        for (relation in recipesToIngredientsAndMeasuresTable) {
            ingredients.add(relation.ingredient.name)
            measures.add(relation.measure.name)
        }

        val detailDomain = DetailDomain(
            id = id.toString(),
            name = recipesTable.recipe.name,
            nameCategory = recipesTable.category.name,
            nameCuisine = recipesTable.cuisine.name,
            strInstructions = recipesTable.recipe.instructions,
            imageUrl = recipesTable.recipe.imageUrl,
            ingredients = ingredients,
            measures = measures
        )

        return MutableLiveData(Result.success(listOf(detailDomain)))
    }

    override fun clearCache() {
        localDataSource.clearAllTables()
    }
}