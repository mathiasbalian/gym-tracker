package com.app.muscu3000.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.muscu3000.dao.ExerciceDao
import com.app.muscu3000.dao.ExerciceSetDao
import com.app.muscu3000.dao.GymSessionDao
import com.app.muscu3000.dao.GymSessionExerciceDao
import com.app.muscu3000.dao.SetDao
import com.app.muscu3000.dao.UserDao
import com.app.muscu3000.dao.UserGymSessionDao
import com.app.muscu3000.model.Exercice
import com.app.muscu3000.model.ExerciceSet
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.model.GymSessionExercice
import com.app.muscu3000.model.User
import com.app.muscu3000.model.UserGymSession

@Database(
    entities = [User::class, GymSession::class, UserGymSession::class, GymSessionExercice::class, Exercice::class, ExerciceSet::class, Set::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun gymSessionDao(): GymSessionDao
    abstract fun userGymSessionDao(): UserGymSessionDao
    abstract fun gymSessionExerciceDao(): GymSessionExerciceDao
    abstract fun exerciceDao(): ExerciceDao
    abstract fun exerciceSetDao(): ExerciceSetDao
    abstract fun setDao(): SetDao
}
