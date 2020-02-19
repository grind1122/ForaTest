package com.grind.foratest.models

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("wrapperType") val wrapperType: String,
    @SerializedName("kind") val kind: String,
    @SerializedName("artistId") val artistId: Int,
    @SerializedName("collectionId") val collectionId: Int,
    @SerializedName("trackId") val trackId: Int,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("collectionName") val collectionName: String,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("collectionCensoredName") val collectionCensoredName: String,
    @SerializedName("trackCensoredName") val trackCensoredName: String,
    @SerializedName("artistViewUrl") val artistViewUrl: String,
    @SerializedName("collectionViewUrl") val collectionViewUrl: String,
    @SerializedName("trackViewUrl") val trackViewUrl: String,
    @SerializedName("previewUrl") val previewUrl: String,
    @SerializedName("artworkUrl60") val artworkUrl60: String,
    @SerializedName("artworkUrl100") val artworkUrl100: String,
    @SerializedName("collectionPrice") val collectionPrice: Double,
    @SerializedName("trackPrice") val trackPrice: Double,
    @SerializedName("collectionExplicitness") val collectionExplicitness: String,
    @SerializedName("trackExplicitness") val trackExplicitness: String,
    @SerializedName("discCount") val discCount: Int,
    @SerializedName("discNumber") val discNumber: Int,
    @SerializedName("trackCount") val trackCount: Int,
    @SerializedName("trackNumber") val trackNumber: Int,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Int,
    @SerializedName("country") val country: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("primaryGenreName") val primaryGenreName: String,
    @SerializedName("releaseDate") val releaseDate: String
) {
    fun toAlbum(): Album {
        return Album(
            wrapperType,
            artistId,
            collectionId,
            artistName,
            collectionName,
            collectionCensoredName,
            artistViewUrl,
            collectionViewUrl,
            artworkUrl60,
            artworkUrl100,
            collectionPrice,
            collectionExplicitness,
            discCount,
            discNumber,
            trackCount,
            country,
            primaryGenreName,
            releaseDate
        )
    }

    fun toSong(): Song {
        return Song(
            wrapperType,
            kind,
            artistId,
            trackId,
            artistName,
            trackName,
            trackCensoredName,
            trackViewUrl,
            previewUrl,
            trackPrice,
            trackExplicitness,
            discNumber,
            trackNumber,
            trackTimeMillis
        )
    }
}