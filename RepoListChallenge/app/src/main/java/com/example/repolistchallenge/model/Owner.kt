package com.example.repolistchallenge.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Owner(
    @SerializedName("login")
    var login: String? = null,
    @SerializedName("avatar_url")
    var avatar_url: String? = null

): Serializable