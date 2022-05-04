package org.sopt.spaghettichef.presenter

import android.content.Context
import org.sopt.spaghettichef.model.TodoItem

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