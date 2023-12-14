package com.app.muscu3000

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.activityViewModels
import com.app.muscu3000.dao.GymSessionDao
import androidx.fragment.app.activityViewModels
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.app.muscu3000.database.AppDatabase
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.viewmodels.GymSessionsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.app.muscu3000.model.Exercise
import com.app.muscu3000.model.ExerciseGymSet
import com.app.muscu3000.model.ExerciseInfos
import com.app.muscu3000.model.GymSessionExercise
import com.app.muscu3000.model.GymSet
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.io.IOException
import java.lang.Exception

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    private lateinit var database : AppDatabase
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertExerciseAndRead() = runBlocking {
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

        // Retrieve results and assert
        val results = exerciseGymSetDao.getAllGymSetByExerciseId(exerciseInfo.exercise.exerciseId)
        assertNotNull(results)
        assertEquals(4, results.size)
        // Add more assertions based on your data and expectations
    }


}