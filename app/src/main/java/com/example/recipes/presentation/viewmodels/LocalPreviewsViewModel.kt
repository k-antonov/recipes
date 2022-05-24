package com.example.recipes.presentation.viewmodels

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.recipes.domain.entities.PreviewDomain
//import com.example.recipes.domain.interactors.LocalPreviewsInteractor
//
//class LocalPreviewsViewModel(
//    private val localPreviewsInteractor: LocalPreviewsInteractor
//) : BaseViewModel<PreviewDomain>(localPreviewsInteractor) {
//
//    override val liveDataFromInteractor: LiveData<Result<List<PreviewDomain>>>
//        get() = localPreviewsInteractor.execute()
//
//    init {
//        fetch()
//    }
//}
//
//class LocalPreviewsViewModelFactory(
//    private val localPreviewsInteractor: LocalPreviewsInteractor,
//) : ViewModelProvider.Factory{
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LocalPreviewsViewModel::class.java)) {
//            return LocalPreviewsViewModel(localPreviewsInteractor) as T
//        }
//        throw IllegalArgumentException("ViewModel Not Found")
//    }
//}