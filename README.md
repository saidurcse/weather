# Weather
<snippet>
  <content><![CDATA[
# ${1:Project Name}

A simple project for the Weather based on Kotlin MVVM with Android Jetpack Navigation.

![alt text](https://github.com/saidurcse/weather/blob/main/app/src/main/assets/clear_sky.jpg)
![alt text](https://github.com/saidurcse/weather/blob/main/app/src/main/assets/broken_clouds.jpg)

## Build Process

Add Weather base url and app id in local.properties file.

base_url=http\://api.openweathermap.org/data/2.5/

app_id= YOUR_API_KEY


## Tech stack & Specs

    * Minimum SDK 21
    * Kotlin based with Coroutines
    * MVVM Architecture
    * Architecture Components (Lifecycle, LiveData, ViewModel, Room Persistence, DataBinding, Navigation)
    * The api.openweathermap.org API
    * Retrofit2 & Gson for constructing the REST API
    * OkHttp3 for implementing interceptor, logging and more
    * Glide for loading images
    * Koin dependency injection library
    * Mockito-kotlin for Junit mock test

</content>
</snippet>
