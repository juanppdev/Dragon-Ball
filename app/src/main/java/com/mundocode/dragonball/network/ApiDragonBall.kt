package com.mundocode.dragonball.network

import com.mundocode.dragonball.models.DragonBallModel
import com.mundocode.dragonball.models.SingleDragonBallLista
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDragonBall {

    @GET("characters?page=1&limit=100")
    suspend fun obtenerPersonajes(): Response<DragonBallModel>

    @GET("characters/{id}")
    suspend fun obtenerPersonaje(
        @Path("id") id: Int
    ): Response<SingleDragonBallLista>

}