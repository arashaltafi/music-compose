package ir.arash.altafi.musiccompose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.arash.altafi.musiccompose.data.model.TestDetailEntity

@Database(entities = [TestDetailEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testDetailDao(): TestDetailDao
}