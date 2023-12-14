package com.app.muscu3000

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.app.muscu3000.api.ApiService
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.viewmodels.GymSessionsViewModel
import com.app.muscu3000.viewmodels.TodoViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

class ViewModelUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GymSessionsViewModel
    private var todoViewModel: TodoViewModel = TodoViewModel()

    @Before
    fun setup() {
        viewModel = GymSessionsViewModel()
//        todoViewModel = TodoViewModel()
    }

    @Test
    fun testSetSelectedSession() {
        val expectedGymSession = GymSession(1, "test", "10/10/2023", "", 1)

        viewModel.setSelectedSession(expectedGymSession)

        val selectedSession = viewModel.getSelectedSession().value
        System.out.println("OOOOO")
        System.out.println(selectedSession)
        assertEquals(expectedGymSession, selectedSession)
    }

    @Test
    fun testGetTodos() {
        System.out.println("AAAAAAAAAAAA")
        System.out.println(todoViewModel.todos.value)
    }
}