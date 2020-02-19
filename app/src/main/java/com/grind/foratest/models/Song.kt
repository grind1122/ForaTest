package com.grind.foratest.models

import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("wrapperType") val wrapperType: String,
    @SerializedName("kind") val kind: String,
    @SerializedName("artistId") val artistId: Int,
    @SerializedName("trackId") val trackId: Int,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("trackCensoredName") val trackCensoredName: String,
    @SerializedName("trackViewUrl") val trackViewUrl: String,
    @SerializedName("previewUrl") val previewUrl: String,
    @SerializedName("trackPrice") val trackPrice: Double,
    @SerializedName("trackExplicitness") val trackExplicitness: String,
    @SerializedName("discNumber") val discNumber: Int,
    @SerializedName("trackNumber") val trackNumber: Int,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Int
) {
}