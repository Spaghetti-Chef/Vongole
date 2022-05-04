package org.sopt.spaghettichef.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert
    fun insert(todo: TodoItem)

    @Update
    fun update(todo: TodoItem)

    @Delete
    fun delete(todo: TodoItem)

    @Query("SELECT * FROM TodoItem ORDER BY id DESC") // 테이블의 모든 값을 가져와라
    fun getAll(): List<TodoItem>

}