package com.app.muscu3000

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.app.muscu3000.database.AppDatabase
import com.app.muscu3000.model.Exercise
import com.app.muscu3000.model.ExerciseGymSet
import com.app.muscu3000.model.ExerciseInfos
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.model.GymSet
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DaoUnitTest {

    private lateinit var database : AppDatabase

    @Before
    fun createDb() {
        System.out.println("database creation")
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        System.out.println("database closing")
        database.close()
    }

    @Test
    fun insertGymSet() = runBlocking {
        System.out.println("TEST INSERT GYMSET")
        val gymSetDao = database.gymSetDao()
        val gymSetToAdd = GymSet(1, 10, 10.0, 1)
        gymSetDao.insertGymSet(gymSetToAdd,)
        val result = gymSetDao.getGymSetById(1)
        assertEquals(result, gymSetToAdd)
    }


    @Test
    fun insertExerciseAndGymSet() = runBlocking {
        System.out.println("TEST INSERT EXERCISE & GYMSET")
        val exerciseDao = database.exerciseDao()
        val gymSetDao = database.gymSetDao()
        val exerciseGymSetDao = database.exerciseGymSetDao()

        val exerciseInfo = ExerciseInfos(
            exercise = Exercise(1, "test", ""),
            listGymSet = mutableListOf(
                GymSet(1, 10, 10.0, 1),
                GymSet(2, 11, 15.0, 2),
                GymSet(3, 12, 14.0, 3),
                GymSet(4, 13, 12.0, 4)
            )
        )

        // Insert exercise and gym sets
        val exerciseId = exerciseDao.insertExercise(exerciseInfo.exercise)

        for (gymSet in exerciseInfo.listGymSet) {
            val gymSetId = gymSetDao.insertGymSet(gymSet)

            exerciseGymSetDao.insertExerciseGymSet(ExerciseGymSet(exerciseId, gymSetId))
        }

        val resultsGymSet = exerciseGymSetDao.getAllGymSetByExerciseId(exerciseInfo.exercise.exerciseId)
        assertNotNull(resultsGymSet)
        assertEquals(4, resultsGymSet.size)

        val resultsExercise = exerciseDao.getExerciseById(exerciseInfo.exercise.exerciseId)
        assertEquals(resultsExercise, Exercise(1, "test", ""))
    }


    @Test
    fun insertGymSession() = runBlocking {
        System.out.println("TEST INSERT GYMSESSION")
        val gymSession = database.gymSessionDao()
        val gymSessionToAdd = GymSession(1, "test", "11/12/2023", "ok", 1)
        gymSession.insertGymSession(gymSessionToAdd)
        val result = gymSession.getGymSessionById(1)
        assertEquals(result, gymSessionToAdd)
    }
}