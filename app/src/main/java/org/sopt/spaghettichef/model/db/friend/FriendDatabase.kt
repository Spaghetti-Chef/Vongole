package org.sopt.spaghettichef.model.db.friend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FriendInfo::class], version = 1)
abstract class FriendDatabase : RoomDatabase() {

    abstract fun friendDAO(): FriendDao

    companion object {
        @Volatile
        private var instance: FriendDatabase? = null
        fun getInstance(context: Context): FriendDatabase {
            synchronized(this) {
                return instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FriendDatabase::class.java,
                    "friend_data_database"
                ).build()
            }
        }
    }
}