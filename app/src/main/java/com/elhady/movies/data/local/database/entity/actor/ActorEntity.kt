package com.elhady.movies.data.local.database.entity.actor

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ACTOR_TABLE")
data class ActorEntity(@PrimaryKey val id: Int, val name: String, val characterName:String, val imageUrl: String)
