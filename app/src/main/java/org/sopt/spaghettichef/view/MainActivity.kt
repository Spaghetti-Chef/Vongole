package org.sopt.spaghettichef.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import org.sopt.spaghettichef.adapter.TodoListAdapter
import org.sopt.spaghettichef.databinding.ActivityMainBinding
import org.sopt.spaghettichef.model.TodoItem
import org.sopt.spaghettichef.presenter.TodoContract
import org.sopt.spaghettichef.presenter.TodoPresenter
import org.sopt.spaghettichef.util.ItemTouchHelperCallback

class MainActivity : AppCompatActivity(), TodoListAdapter.OnItemTouchListener, TodoContract.View {
    private lateinit var binding: ActivityMainBinding
    private val adapter = TodoListAdapter()
    private lateinit var presenter: TodoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        presenter = TodoPresenter(this@MainActivity, this, lifecycleScope)

        initView()
    }

    private fun initView() {
        adapter.apply {
            setOnItemTouchListener(this@MainActivity)
            ItemTouchHelper(ItemTouchHelperCallback(this)).attachToRecyclerView(binding.todoList)
        }

        binding.todoList.adapter = adapter
    }

    fun onClick(view: View) {
        presenter.addTodoItem(binding.todoInput.text.toString())
    }

    override fun onItemSwipe(todoItem: TodoItem, position: Int) {
        presenter.deleteTodoItem(todoItem, position)
    }

    override fun addItem(item: TodoItem) {
        adapter.addData(item)
    }

    override fun deleteItem(position: Int) {
        adapter.removeData(position)
    }

    override fun clearInput() {
        binding.todoInput.text = null
    }

    override fun setItems(items: List<TodoItem>) {
        adapter.setData(items)
    }
}