package my.rjtechnology.apprenticestreet.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import my.rjtechnology.apprenticestreet.models.LearningOutcome

@WorkerThread @Dao interface LearningOutcomeDao {
    @Insert suspend fun insertAll(vararg learningOutcomes: LearningOutcome)
}
