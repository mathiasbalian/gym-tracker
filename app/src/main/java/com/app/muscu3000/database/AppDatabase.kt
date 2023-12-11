package com.app.muscu3000.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.app.muscu3000.dao.ExerciseDao
import com.app.muscu3000.dao.ExerciseGymSetDao
import com.app.muscu3000.dao.GymSessionDao
import com.app.muscu3000.dao.GymSessionExerciseDao
import com.app.muscu3000.dao.GymSetDao
import com.app.muscu3000.dao.UserDao
import com.app.muscu3000.dao.UserGymSessionDao
import com.app.muscu3000.model.Exercise
import com.app.muscu3000.model.ExerciseGymSet
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.model.GymSessionExercise
import com.app.muscu3000.model.GymSet
import com.app.muscu3000.model.User
import com.app.muscu3000.model.UserGymSession


@Database(
    entities = [User::class, GymSession::class, UserGymSession::class, GymSessionExercise::class, Exercise::class, ExerciseGymSet::class, GymSet::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    abstract fun userDao(): UserDao
    abstract fun gymSessionDao(): GymSessionDao
    abstract fun userGymSessionDao(): UserGymSessionDao
    abstract fun gymSessionExerciseDao(): GymSessionExerciseDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseGymSetDao(): ExerciseGymSetDao
    abstract fun gymSetDao(): GymSetDao
}