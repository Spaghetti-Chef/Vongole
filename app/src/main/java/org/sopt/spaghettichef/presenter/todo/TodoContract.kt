package org.sopt.spaghettichef.presenter.todo

import android.content.Context
import org.sopt.spaghettichef.model.db.todo.TodoItem

interface TodoContract {
    interface View {
        fun setItems(items: List<TodoItem>)
        fun addItem(item: TodoItem)
        fun deleteItem(position: Int)
        fun clearInput()
    }

    interface Presenter {
        fun initDb(context: Context)
        fun fetchTodoItems()
        fun addTodoItem(todo: String)
        fun deleteTodoItem(todoItem: TodoItem, position: Int)
    }
}