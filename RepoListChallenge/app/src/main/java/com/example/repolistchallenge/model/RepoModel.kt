package com.example.repolistchallenge.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RepoModel(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("full_name")
    var full_name: String? = null,
    @SerializedName("owner")
    var owner: Owner? = null,
    @SerializedName("stargazers_count")
    var stargazers_count: Int? = null,
    @SerializedName("open_issues")
    var open_issues: Int? = null
) :Serializable