package org.sopt.spaghettichef.presenter.friend

import android.content.Context
import androidx.lifecycle.LiveData
import org.sopt.spaghettichef.model.db.friend.FriendInfo

interface FriendContract {
    interface View {
        fun setFriends(friends: LiveData<List<FriendInfo>>)
        fun clearInput()
        fun clearSelectedFriend()
        fun showEmailFormatToast()
    }

    interface Presenter {
        fun initDb(context: Context)
        fun checkEmailFormat(email: String): Boolean
        fun fetchFriendList()
        fun addFriend(name: String, email: String)
        fun deleteFriend(friend: FriendInfo)
        fun deleteAllFriends()
        fun updateFriend(name: String, email: String, id: Int)
    }
}