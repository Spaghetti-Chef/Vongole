package org.sopt.spaghettichef.model.db.friend

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friend_data_table")
data class FriendInfo(
    val name: String,
    val email: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)