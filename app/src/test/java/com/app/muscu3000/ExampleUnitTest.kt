package com.app.muscu3000

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.muscu3000.database.AppDatabase
import com.app.muscu3000.model.Exercise
import com.app.muscu3000.model.ExerciseGymSet
import com.app.muscu3000.model.ExerciseInfos
import com.app.muscu3000.model.GymSet
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    private lateinit var database : AppDatabase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertGymSet() = runBlocking {
        val gymSetDao = database.gymSetDao()
        val gymSetToAdd = GymSet(1, 10, 10.0, 1)
        gymSetDao.insertGymSet(gymSetToAdd,)
        val test = gymSetDao.getGymSetById(1)
        System.out.println(test)
        System.out.println("AAAAA")
        assertEquals(test, GymSet(1, 9, 10.0, 1))
    }

//    fun insertExercise() = runBlocking {
//        val exerciseDao = database.exerciseDao()
//        exerciseDao.in
//    }

//    @Test
//    @Throws(Exception::class)
//    fun insertExerciseAndRead() = runBlocking {
//        System.out.println("ok2")
//        val exerciseDao = database.exerciseDao()
//        val gymSetDao = database.gymSetDao()
//        val exerciseGymSetDao = database.exerciseGymSetDao()
//        System.out.println("ok2")
//
//        val exerciseInfo = ExerciseInfos(
//            exercise = Exercise(1, "test", ""),
//            listGymSet = mutableListOf(
//                GymSet(1, 10, 10.0, 1),
//                GymSet(2, 11, 15.0, 2),
//                GymSet(3, 12, 14.0, 3),
//                GymSet(4, 13, 12.0, 4)
//            )
//        )
//        System.out.println("ok2")
//
//        // Insert exercise and gym sets
//        val exerciseId = exerciseDao.insertExercise(exerciseInfo.exercise)
//        System.out.println("ok2")
//
//        for (gymSet in exerciseInfo.listGymSet) {
//            val gymSetId = gymSetDao.insertGymSet(gymSet)
//
//            exerciseGymSetDao.insertExerciseGymSet(ExerciseGymSet(exerciseId, gymSetId))
//        }
//        System.out.println("ok2")
//
//        // Retrieve results and assert
//        val results = exerciseGymSetDao.getAllGymSetByExerciseId(exerciseInfo.exercise.exerciseId)
//        assertNotNull(results)
//        assertEquals(4, results.size)
//        // Add more assertions based on your data and expectations
//    }


}