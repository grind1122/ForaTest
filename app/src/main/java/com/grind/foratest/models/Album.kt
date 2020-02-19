package com.grind.foratest.models

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("wrapperType") val wrapperType: String,
    @SerializedName("artistId") val artistId: Int,
    @SerializedName("collectionId") val collectionId: Int,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("collectionName") val collectionName: String,
    @SerializedName("collectionCensoredName") val collectionCensoredName: String,
    @SerializedName("artistViewUrl") val artistViewUrl: String,
    @SerializedName("collectionViewUrl") val collectionViewUrl: String,
    @SerializedName("artworkUrl60") val artworkUrl60: String,
    @SerializedName("artworkUrl100") val artworkUrl100: String,
    @SerializedName("collectionPrice") val collectionPrice: Double,
    @SerializedName("collectionExplicitness") val collectionExplicitness: String,
    @SerializedName("discCount") val discCount: Int,
    @SerializedName("discNumber") val discNumber: Int,
    @SerializedName("trackCount") val trackCount: Int,
    @SerializedName("country") val country: String,
    @SerializedName("primaryGenreName") val primaryGenreName: String,
    @SerializedName("releaseDate") val releaseDate: String
)