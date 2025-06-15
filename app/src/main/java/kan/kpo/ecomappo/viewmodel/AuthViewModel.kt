package kan.kpo.ecomappo.viewmodel

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kan.kpo.ecomappo.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject



@HiltViewModel
class AuthViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val userId: String) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    // เพิ่ม Auth State Listener
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            _authState.value = AuthState.Success(firebaseUser.uid)
            _currentUser.value = UserProfile(
                uid = firebaseUser.uid,
                name = firebaseUser.displayName ?: "",
                email = firebaseUser.email ?: ""
            )
            Log.d("AuthViewModel", "User logged in: ${firebaseUser.email}")
        } else {
            _authState.value = AuthState.Idle
            _currentUser.value = null
            Log.d("AuthViewModel", "User logged out")
        }
    }

    init {
        // เพิ่ม listener ตั้งแต่เริ่มต้น
        auth.addAuthStateListener(authStateListener)

        // ตั้งค่าเริ่มต้นถ้ามี user อยู่แล้ว
        auth.currentUser?.let { firebaseUser ->
            _authState.value = AuthState.Success(firebaseUser.uid)
            _currentUser.value = UserProfile(
                uid = firebaseUser.uid,
                name = firebaseUser.displayName ?: "",
                email = firebaseUser.email ?: ""
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        // ลบ listener เมื่อ ViewModel ถูกทำลาย
        auth.removeAuthStateListener(authStateListener)
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                Log.d("AuthViewModel", "Sign in successful: ${result.user?.email}")
                // AuthStateListener จะจัดการการอัพเดต state ให้อัตโนมัติ
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign in failed: ${e.message}")
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                Log.d("AuthViewModel", "Sign up successful: ${result.user?.email}")
                // AuthStateListener จะจัดการการอัพเดต state ให้อัตโนมัติ
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign up failed: ${e.message}")
                _authState.value = AuthState.Error(e.message ?: "Registration failed")
            }
        }
    }

    fun signOut() {
        Log.d("AuthViewModel", "Signing out user")
        auth.signOut()
        // AuthStateListener จะจัดการการอัพเดต state ให้อัตโนมัติ
    }
}