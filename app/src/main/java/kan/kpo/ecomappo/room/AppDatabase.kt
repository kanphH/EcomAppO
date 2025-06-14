package kan.kpo.ecomappo.room
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kan.kpo.ecomappo.model.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    // Define DAo to interact with the database

    abstract fun cartDao() : CartDao

    //Singleton DB Instance
    companion object{
        @Volatile // ensure visibility of change across threads

        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context:Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cart_database"
                ).build()
                INSTANCE = instance
                instance
            }

        }

        }

}