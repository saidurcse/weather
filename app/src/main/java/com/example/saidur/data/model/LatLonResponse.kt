package com.example.saidur.data.model

import com.google.gson.annotations.SerializedName

data class LocalNames(

	@field:SerializedName("ru")
	val ru: String,

	@field:SerializedName("uk")
	val uk: String,

	@field:SerializedName("en")
	val en: String
)

data class LatLonResponseItem(

	@field:SerializedName("local_names")
	val localNames: LocalNames,

	@field:SerializedName("country")
	val country: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("state")
	val state: String,

	@field:SerializedName("lat")
	val lat: Double
)
