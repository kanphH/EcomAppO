package kan.kpo.ecomappo.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kan.kpo.ecomappo.model.Category
import kan.kpo.ecomappo.model.Product
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) {

//    จำ Keyword สำคัญ:
//
//    callbackFlow = "ฟังแล้วส่งต่อ"
//    addSnapshotListener = "ฟัง Firebase"
//    trySend = "ส่งข้อมูล"
//    awaitClose = "ปิดตอนจบ"

    fun getCategoriesFlow(): Flow<List<Category>> {
        return callbackFlow {
            val listenerRegistration = firestore
                .collection("Categories") //must match in firebase
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        println("Error fetching categories: ${error.message}")
                        return@addSnapshotListener
                    }
                    // ถ้ามีข้อมูล

//                    "Firebase ส่ง snapshot ใหม่มา"
//                    = "Firebase ส่งข้อมูลปัจจุบันมา"
//
//                    "รับ snapshot จาก Firestore"
//                    = "รับภาพรวมข้อมูลจาก Firestore"
                    if (snapshot != null) {
                        val categories = snapshot.toObjects(Category::class.java)
                        trySend(categories)
                    }

                }

            // Close The Flow when the listener is no longer needed

            awaitClose { listenerRegistration.remove() }

        }
    }


    // result will contain a QuearySnapshot of all document
    //in "prodcuts" where category matches the provide value

    suspend fun getProductByCategory(categoryId: String): List<Product> {
        return try {
            val result = firestore
                .collection("products") // ต้องมี collection นี้ใน Firebase
                .whereEqualTo("categoryId", categoryId)
                .get()
                .await()

            result.toObjects(Product::class.java).also {
                Log.v("TAGY", "getProductByCategory: $it")
            }
            // ↑ ตรงนี้จะ return List<Product> ออกไปแล้ว

        } catch (e: Exception) {
            Log.e("TAGY", "Error getting products by category: ${e.message}")
            emptyList()
        }
    }

    suspend fun getProductById(productId: String): Product? {
        return try {
            val result = firestore
                .collection("products")
                .document(productId)
                .get()
                .await()

            result.toObject(Product::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getAllProductInFirestore(): List<Product> {
        return try {
            val allProducts = firestore
                .collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Product::class.java) }
            allProducts

        } catch (e: Exception) {
            emptyList()
        }
    }


}


