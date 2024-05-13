package com.mundocode.dragonball.models

import com.google.gson.annotations.SerializedName

data class DragonBallModel(
    @SerializedName("items")
    val ListItems: List<DragonBallLista>,
)

data class DragonBallPlanets(
    @SerializedName("items")
    val ListPlanets: List<planets>
)

data class DragonBallLista (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("ki")
    val ki: String,
    @SerializedName("maxKi")
    val maxKi: String,
    @SerializedName("race")
    val race: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
)

data class SingleDragonBallLista (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("ki")
    val ki: String,
    @SerializedName("maxKi")
    val maxKi: String,
    @SerializedName("race")
    val race: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("originPlanet")
    val originPlanet: originPlanet,
    @SerializedName("transformations")
    val transformations: List<transformations>
)

data class originPlanet (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String
)

data class transformations (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("ki")
    val ki: String,
    @SerializedName("image")
    val image: String,
)

// Planets
data class planets (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("isDestroyed")
    val isDestroyed: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String
)