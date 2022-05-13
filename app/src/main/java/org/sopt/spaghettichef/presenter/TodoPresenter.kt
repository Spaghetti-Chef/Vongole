package org.sopt.spaghettichef.presenter

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sopt.spaghettichef.model.db.todo.TodoDatabase
import org.sopt.spaghettichef.model.db.todo.TodoItem

class TodoPresenter(
    private val view: TodoContract.View,
    private val context: Context,
    private val lifecycleScope: LifecycleCoroutineScope
) : TodoContract.Presenter {
    private var db: TodoDatabase? = null

    init {
        initDb(context)
        fetchTodoItems()
    }

    override fun initDb(context: Context) {
        db = TodoDatabase.getInstance(context)
    }

    override fun addTodoItem(todo: String) {
        val trimmedTodo = todo.trim()
        if (trimmedTodo.isBlank()) return

        val item = TodoItem(trimmedTodo)
        lifecycleScope.launch(Dispatchers.IO) {
            db!!.todoDao().insert(item)
        }
        view.addItem(item)
        view.clearInput()
    }

    override fun deleteTodoItem(todoItem: TodoItem, position: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            db!!.todoDao().delete(todoItem)
        }
        view.deleteItem(position)
    }

    override fun fetchTodoItems() {
        lifecycleScope.launch(Dispatchers.IO) {
            view.setItems(db!!.todoDao().getAll())
        }
    }

    companion object {
        private const val TAG = "TodoPresenter"
    }
}