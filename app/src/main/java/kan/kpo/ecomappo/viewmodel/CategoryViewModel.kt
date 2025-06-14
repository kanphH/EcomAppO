package kan.kpo.ecomappo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kan.kpo.ecomappo.model.Category
import kan.kpo.ecomappo.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: FirestoreRepository) :
    ViewModel() {


    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow() //    สร้าง ReadOnlyStateFlow wrapper object

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            repository.getCategoriesFlow()
                .catch {
                    // Handle Error
                    println("Error in Flow")

                }.collect { categories ->
                    //each time new data is emitted this block runs
                    _categories.value = categories
                    //Update the stateflow with new data
                    println("category updated in viewModel")

                }
        }
    }

}