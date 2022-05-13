package org.sopt.spaghettichef.model.db.todo

import androidx.room.*

@Dao
interface TodoDao {
    @Insert
    fun insert(todo: TodoItem)

    @Update
    fun update(todo: TodoItem)

    @Delete
    fun delete(todo: TodoItem)

    @Query("SELECT * FROM TodoItem ORDER BY id DESC")
    fun getAll(): List<TodoItem>
}