package org.sopt.spaghettichef.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sopt.spaghettichef.adapter.TodoListAdapter
import org.sopt.spaghettichef.databinding.ActivityMainBinding
import org.sopt.spaghettichef.model.TodoDatabase
import org.sopt.spaghettichef.model.TodoItem
import org.sopt.spaghettichef.util.ItemTouchHelperCallback

class MainActivity : AppCompatActivity(), TodoListAdapter.OnItemTouchListener {
    private lateinit var binding: ActivityMainBinding
    private val adapter = TodoListAdapter()
    private var db: TodoDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        db = TodoDatabase.getInstance(this)

        initView()
    }

    private fun initView() {
        adapter.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                setData(db!!.todoDao().getAll())
            }
            setOnItemTouchListener(this@MainActivity)
            ItemTouchHelper(ItemTouchHelperCallback(this)).attachToRecyclerView(binding.todoList)
        }

        binding.todoList.adapter = adapter
    }

    fun addTodoItem(view: View) {
        if (binding.todoInput.text.trim().isBlank()) return

        val item = TodoItem(binding.todoInput.text.toString())
        lifecycleScope.launch(Dispatchers.IO) {
            db!!.todoDao().insert(item)
        }
        adapter.addData(item)

        binding.todoInput.text = null
    }

    override fun onItemSwipe(todoItem: TodoItem) {
        lifecycleScope.launch(Dispatchers.IO) {
            db!!.todoDao().delete(todoItem)
        }
        adapter.removeData(todoItem)
    }
}