package org.sopt.spaghettichef.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.spaghettichef.databinding.ItemTodoBinding
import org.sopt.spaghettichef.model.db.todo.TodoItem
import org.sopt.spaghettichef.util.ItemTouchHelperListener

class TodoListAdapter(private val touchListener: (TodoItem, Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperListener {
    private val dataSet: MutableList<TodoItem> = mutableListOf()

    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoItem) {
            binding.todoData = item
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
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setData(items: List<TodoItem>) {
        dataSet.clear()
        dataSet.addAll(items)
        notifyDataSetChanged() // TODO 추후 ListAdapter 사용 시 submitList() 호출로 변경 예정
    }

    override fun onItemSwipe(position: Int) {
        touchListener(dataSet[position], position)
    }

    companion object {
        private const val TAG = "TodoListAdapter"
    }
}