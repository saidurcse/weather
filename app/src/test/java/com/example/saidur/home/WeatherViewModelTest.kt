package com.example.saidur.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.example.saidur.data.model.WeatherInfoResponse
import com.example.saidur.data.repository.WeatherRepository
import com.example.saidur.ui.home.WeatherViewModel
import com.example.saidur.getOrAwaitValue
import com.example.saidur.utils.AppResult
import com.example.saidur.utils.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {
    private val repository: WeatherRepository = mock(WeatherRepository::class.java)

    private lateinit var viewModel: WeatherViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    /// Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var offlineWeatherObserver: Observer<WeatherInfoResponse>

    @Mock
    lateinit var weatherListObserver: Observer<WeatherInfoResponse>

    @Mock
    lateinit var showErrorObserver: Observer<String>

    @Before
    fun setUp() {
        // create view model
        viewModel = WeatherViewModel(repository)

        viewModel.offlineWeather.observeForever(offlineWeatherObserver)
        viewModel.weatherData.observeForever(weatherListObserver)
        viewModel.showError.observeForever(showErrorObserver)
    }

    @After
    fun tearDown() {
        reset(offlineWeatherObserver)
        reset(weatherListObserver)
        reset(showErrorObserver)
    }

    @Test
    fun `should get data from DB`() {
        // given
        val dataResponse = MutableLiveData<WeatherInfoResponse>()
        dataResponse.value = getMockApiData()

        `when`(repository.getAllOfflineDB()).thenReturn(dataResponse)

        // when
        viewModel.getAllOfflineDB()

        // then
        val observeOfflineMovieListCapture = argumentCaptor<WeatherInfoResponse>()
        viewModel.offlineWeather.getOrAwaitValue()
        verify(offlineWeatherObserver).onChanged(observeOfflineMovieListCapture.capture())

        assertEquals(
            getMockApiData(),
            observeOfflineMovieListCapture.firstValue
        )
    }

    @Test
    fun `should fetch data from api successfully`() {
        runBlocking {
            launch(Dispatchers.Main) {
                // given
                val dataResponse = MutableLiveData<WeatherInfoResponse>()
                dataResponse.value = getMockApiData()

                `when`(repository.getWeatherDataInfo("32.8295", "-96.9442")).thenReturn(AppResult.Success(getMockApiData()))

                // when
                viewModel.fetchWeatherData("32.8295", "-96.9442")

                // then
                val observeMovieListCapture = argumentCaptor<WeatherInfoResponse>()
                viewModel.weatherData.getOrAwaitValue()
                verify(weatherListObserver).onChanged(observeMovieListCapture.capture())

                assertEquals(
                    getMockApiData(),
                    observeMovieListCapture.firstValue
                )

                val observeErrorCapture = argumentCaptor<String>()
                viewModel.showError.getOrAwaitValue()
                verify(showErrorObserver).onChanged(observeErrorCapture.capture())


                assertNull(observeErrorCapture.firstValue)
            }
        }
    }

    @Test
    fun `should show error when fetching weather from api`() {
        runBlocking {
            launch(Dispatchers.Main) {
                // given
                `when`(repository.getWeatherDataInfo("32.8295", "-96.9442")).thenReturn(AppResult.Error(Exception("Error!")))

                // when
                viewModel.fetchWeatherData("32.8295", "-96.9442")

                // then
                val observeErrorCapture = argumentCaptor<String>()
                viewModel.showError.getOrAwaitValue()
                verify(showErrorObserver).onChanged(observeErrorCapture.capture())


                assertNotNull(observeErrorCapture.firstValue)
            }
        }
    }

    private fun getMockApiData() = (
        WeatherInfoResponse(name = "Irving")
    )
}
