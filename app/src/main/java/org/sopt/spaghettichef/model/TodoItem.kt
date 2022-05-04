package org.sopt.spaghettichef.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItem(
    val description: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}