package org.sopt.spaghettichef

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.spaghettichef.databinding.ItemTodoBinding

class TodoListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataSet: MutableList<TodoItem> = mutableListOf()

    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoItem) {
            binding.description.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataSet[position]
        when (holder) {
            is TodoViewHolder -> holder.bind(data)
        }
    }

    override fun getItemCount(): Int = dataSet.size

    fun addData(item: TodoItem) {
        dataSet.add(0, item)
    }

    fun setData(items: List<TodoItem>) {
        dataSet.clear()
        dataSet.addAll(items)
    }

    companion object {
        private const val TAG = "TodoListAdapter"
    }
}