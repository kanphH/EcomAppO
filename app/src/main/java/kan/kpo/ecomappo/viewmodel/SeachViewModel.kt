package kan.kpo.ecomappo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kan.kpo.ecomappo.model.Product
import kan.kpo.ecomappo.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _searchResult = MutableStateFlow<List<Product>>(emptyList())
    val searchResult: StateFlow<List<Product>> = _searchResult.asStateFlow() // ✅ แก้ exposure

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    fun searchProduct(query: String) {
        if (query.isBlank()) {
            _searchResult.value = emptyList()
            _isSearching.value = false
        } else {
            viewModelScope.launch { // ✅ แก้ async
                try {
                    _isSearching.value = true
                    val results = repository.searchProduct(query)
                    _searchResult.value = results
                } catch (e: Exception) {
                    _searchResult.value = emptyList()
                } finally {
                    _isSearching.value = false
                }
            }
        }
    }
}