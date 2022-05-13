package org.sopt.spaghettichef.presenter.friend

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.sopt.spaghettichef.model.db.friend.FriendDatabase
import org.sopt.spaghettichef.model.db.friend.FriendInfo

class FriendPresenter(
    private val view: FriendContract.View,
    private val context: Context,
    private val lifecycleScope: LifecycleCoroutineScope,
) : FriendContract.Presenter {
    private var db: FriendDatabase? = null

    init {
        initDb(context)
        fetchFriendList()
    }

    override fun initDb(context: Context) {
        db = FriendDatabase.getInstance(context)
    }

    override fun addFriend(name: String, email: String) {
        val trimmedName = name.trim()
        val trimmedEmail = email.trim()
        if (trimmedName.isBlank() || trimmedEmail.isBlank()) return

        val item = FriendInfo(trimmedName, trimmedEmail)
        lifecycleScope.launch(Dispatchers.IO) {
            db!!.friendDAO().insertFriend(item)
        }
        view.clearInput()
    }

    override fun deleteFriend(friend: FriendInfo) {
        lifecycleScope.launch(Dispatchers.IO) {
            db!!.friendDAO().deleteFriend(friend)
        }
        view.clearInput()
    }

    override fun deleteAllFriends() {
        lifecycleScope.launch(Dispatchers.IO) {
            db!!.friendDAO().deleteAllFriends()
        }
    }

    override fun updateFriend(name: String, email: String, id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            db!!.friendDAO().updateFriend(FriendInfo(name, email, id))
        }
        view.clearInput()
        view.clearSelectedFriend()
    }

    override fun fetchFriendList() {
        lifecycleScope.launch {
            val friends: LiveData<List<FriendInfo>>
            withContext(Dispatchers.IO) {
                friends = db!!.friendDAO().getAllFriends()
            }
            withContext(Dispatchers.Main) {
                view.setFriends(friends)
            }
        }
    }

    companion object {
        private const val TAG = "FriendPresenter"
    }
}