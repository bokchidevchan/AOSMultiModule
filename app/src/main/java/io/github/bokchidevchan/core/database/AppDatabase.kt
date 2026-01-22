package io.github.bokchidevchan.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.bokchidevchan.core.database.dao.ArticleDao
import io.github.bokchidevchan.core.database.dao.UserDao
import io.github.bokchidevchan.core.database.entity.ArticleEntity
import io.github.bokchidevchan.core.database.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ArticleEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun articleDao(): ArticleDao
}
