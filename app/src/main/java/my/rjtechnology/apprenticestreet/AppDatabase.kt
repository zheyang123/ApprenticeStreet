package my.rjtechnology.apprenticestreet

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import my.rjtechnology.apprenticestreet.dao.JobDao
import my.rjtechnology.apprenticestreet.dao.LoginDao
import my.rjtechnology.apprenticestreet.models.Job
import my.rjtechnology.apprenticestreet.models.LearningOutcome

@Database(entities = [Job::class, LearningOutcome::class,LoginActivity.User::class,LoginActivity.Company::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
    abstract fun LoginDao(): LoginDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            synchronized(this) {
                var db = INSTANCE
                if (db != null) return db

                db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "apprentice_street"
                ).build()

                INSTANCE = db
                return db
            }
        }
    }
}
