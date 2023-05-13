package com.example.saidur.ui.home

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.saidur.MainActivity
import com.example.saidur.R
import com.example.saidur.WeatherApplication
import com.example.saidur.databinding.FragmentWeatherBinding
import com.example.saidur.utils.MyPreferences
import com.example.saidur.utils.kelvinToCelsius
import com.example.saidur.utils.unixTimestampToDateTimeString
import com.example.saidur.utils.unixTimestampToTimeString
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.layout_input_part.view.*
import kotlinx.android.synthetic.main.layout_sunrise_sunset.view.*
import kotlinx.android.synthetic.main.layout_weather_additional_info.view.*
import kotlinx.android.synthetic.main.layout_weather_basic_info.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment(), View.OnClickListener {

    private val weatherViewModel by viewModel<WeatherViewModel>()
    private lateinit var bindingView: FragmentWeatherBinding
    private var myPreferences: MyPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingView = FragmentWeatherBinding.inflate(layoutInflater, container, false)
        bindingView.lifecycleOwner = this
        bindingView.viewModel = weatherViewModel
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        myPreferences = MyPreferences.getPreferences(WeatherApplication.instance)!!

        if (myPreferences!!.firstStatus == true) { // Auto load the last city searched
            bindingView.layoutWeatherBasic.visibility = View.VISIBLE
            bindingView.layoutWeatherAdditional.visibility = View.VISIBLE
            bindingView.layoutSunsetSunrise.visibility = View.VISIBLE
            bindingView.creditText.visibility = View.VISIBLE
            weatherViewModel.fetchWeatherData(myPreferences!!.lat,myPreferences!!.lon)
        }

        weatherViewModel.weatherLatLongData.observe(viewLifecycleOwner, Observer { weatherLatLongData ->
            val lat = weatherLatLongData.get(0).lat.toString()
            val lon =  weatherLatLongData.get(0).lon.toString()
            myPreferences!!.setLat(lat)
            myPreferences!!.setLon(lon)
            weatherViewModel.fetchWeatherData(lat,lon)
        })

        weatherViewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherData ->
            bindingView.layoutWeatherBasic.visibility = View.VISIBLE
            bindingView.layoutWeatherAdditional.visibility = View.VISIBLE
            bindingView.layoutSunsetSunrise.visibility = View.VISIBLE
            bindingView.creditText.visibility = View.VISIBLE
            val weatherConditionIconUrl = "https://openweathermap.org/img/w/${weatherData.weather?.get(0)!!.icon}.png"
            bindingView.layoutWeatherBasic.tv_date_time.text = weatherData.dt!!.unixTimestampToDateTimeString()
            bindingView.layoutWeatherBasic.tv_temperature.text = weatherData.main!!.temp!!.kelvinToCelsius().toString()
            bindingView.layoutWeatherBasic.tv_city_country.text = weatherData.name + ", " + weatherData.sys!!.country

            Glide.with(bindingView.layoutWeatherBasic.iv_weather_condition).
            load(weatherConditionIconUrl).
            into(bindingView.layoutWeatherBasic.iv_weather_condition)

            bindingView.layoutWeatherBasic.tv_weather_condition.text = weatherData.weather.get(0)!!.description
            bindingView.layoutWeatherAdditional.tv_humidity_value.text = weatherData.main.humidity.toString()
            bindingView.layoutWeatherAdditional.tv_pressure_value.text = weatherData.main.pressure.toString()
            bindingView.layoutSunsetSunrise.tv_sunrise_time.text = weatherData.sys.sunrise!!.unixTimestampToTimeString()
            bindingView.layoutSunsetSunrise.tv_sunset_time.text = weatherData.sys.sunset!!.unixTimestampToTimeString()
        })


        bindingView.layoutInput.btn_view_weather.setOnClickListener(this)
        return bindingView.root
    }

    override fun onClick(clickedView: View) {
        when (clickedView.id) {

            R.id.btn_view_weather -> { // Asking the user for location access upon view weather button clicked

                Dexter.withContext(context as Activity)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse) {
                            // When permission is given, api is calling to display the weather data
                            val cityName = bindingView.layoutInput.input_city_name.text.toString()
                            if(cityName.isNotEmpty()) {
                                weatherViewModel.fetchLatLonData("http://api.openweathermap.org/geo/1.0/direct", cityName)
                                myPreferences!!.setFirstStatus(true)
                            }
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse) {

                        }

                        override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                            token.continuePermissionRequest()
                        }
                    }).check()
            }
        }
    }
}
