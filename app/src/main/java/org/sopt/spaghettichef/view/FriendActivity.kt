package org.sopt.spaghettichef.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import org.sopt.spaghettichef.R
import org.sopt.spaghettichef.adapter.FriendListAdapter
import org.sopt.spaghettichef.databinding.ActivityFriendBinding
import org.sopt.spaghettichef.model.db.friend.FriendInfo
import org.sopt.spaghettichef.presenter.friend.FriendContract
import org.sopt.spaghettichef.presenter.friend.FriendPresenter

class FriendActivity : AppCompatActivity(), FriendContract.View {
    private lateinit var binding: ActivityFriendBinding
    private val friendAdapter = FriendListAdapter(::onItemClick)
    lateinit var presenter: FriendPresenter
    private var selectedFriend = MutableLiveData<FriendInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_friend)
        presenter = FriendPresenter(this@FriendActivity, this, lifecycleScope)

        initView()
        addListeners()
        addObservers()
    }

    private fun initView() {
        binding.friendList.adapter = friendAdapter
    }

    private fun addListeners() {
        binding.save.setOnClickListener {
            if (selectedFriend.value == null)
                presenter.addFriend(binding.name.text.toString(), binding.email.text.toString())
            else
                presenter.updateFriend(binding.name.text.toString(),
                    binding.email.text.toString(),
                    selectedFriend.value!!.id)
        }

        binding.deleteAll.setOnClickListener {
            if (selectedFriend.value == null)
                presenter.deleteAllFriends()
            else
                presenter.deleteFriend(selectedFriend.value!!)
        }
    }

    private fun addObservers() {
        selectedFriend.observe(this) {
            if (it != null) {
                binding.deleteAll.text = getString(R.string.delete)
                binding.save.text = getString(R.string.update)
            } else {
                binding.deleteAll.text = getString(R.string.delete_all)
                binding.save.text = getString(R.string.save)
            }
        }
    }

    override fun setFriends(friends: LiveData<List<FriendInfo>>) {
        friends.observe(this) {
            friendAdapter.submitList(friends.value?.toMutableList())
        }
    }

    override fun clearInput() {
        binding.name.text = null
        binding.email.text = null
    }

    override fun clearSelectedFriend() {
        selectedFriend.value = null
    }

    private fun onItemClick(friend: FriendInfo) {
        selectedFriend.value = friend
        binding.name.setText(friend.name)
        binding.email.setText(friend.email)
    }

    companion object {
        private const val TAG = "FriendActivity"
    }
}