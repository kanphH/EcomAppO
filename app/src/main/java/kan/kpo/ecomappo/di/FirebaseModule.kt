package kan.kpo.ecomappo.di

import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.app
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    // 🚀 Firebase หลัก - ต้องมีก่อนจะใช้ service อื่นได้
    @Provides
    @Singleton
    fun provideFirebaseApp(): FirebaseApp {
        return Firebase.app
    }

    // 💾 Database - เก็บข้อมูล products, users, orders
    @Provides
    @Singleton
    fun provideFirebaseFirestore(
        firebaseApp: FirebaseApp
    ): FirebaseFirestore {
        return FirebaseFirestore.getInstance(firebaseApp)
    }

    // 🔐 Authentication - login/logout users
    @Provides
    @Singleton
    fun provideFirebaseAuth(
        firebaseApp: FirebaseApp
    ): FirebaseAuth {
        return FirebaseAuth.getInstance(firebaseApp)
    }


}