package ir.arash.altafi.musiccompose.data.db

import androidx.room.*
import ir.arash.altafi.musiccompose.data.model.TestDetailEntity

@Dao
interface TestDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTest(test: TestDetailEntity)

    @Delete
    suspend fun removeTest(test: TestDetailEntity)

    @Query("SELECT * FROM test WHERE id = :id")
    suspend fun getTestById(id: String): TestDetailEntity?
}