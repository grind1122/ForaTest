package com.grind.foratest.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(@SerializedName("resultCount")val count: Int,
                          @SerializedName("results")val infoList: List<Info>) {
}