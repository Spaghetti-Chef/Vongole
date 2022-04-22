package org.sopt.spaghettichef

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.spaghettichef.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initView()
    }

    private fun initView() {
        val adapter = TodoListAdapter().apply {
            setData(
                listOf(
                    TodoItem("라이프 스타일과 트렌드 공부하기"),
                    TodoItem("아침 운동"),
                    TodoItem("물 2L 마시기"),
                )
            )
        }

        binding.todoList.adapter = adapter
    }
}