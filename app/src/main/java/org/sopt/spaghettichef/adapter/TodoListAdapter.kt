package org.sopt.spaghettichef.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.spaghettichef.databinding.ItemTodoBinding
import org.sopt.spaghettichef.model.TodoItem
import org.sopt.spaghettichef.util.ItemTouchHelperListener

class TodoListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperListener {
    private val dataSet: MutableList<TodoItem> = mutableListOf()
    private lateinit var touchListener: OnItemTouchListener

    interface OnItemTouchListener {
        fun onItemSwipe(todoItem: TodoItem)
    }

    fun setOnItemTouchListener(listener: OnItemTouchListener) {
        this.touchListener = listener
    }

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
        notifyItemInserted(0)
    }

    fun removeData(item: TodoItem) {
        dataSet.remove(item)
        notifyItemRemoved(dataSet.indexOf(item))
    }

    fun setData(items: List<TodoItem>) {
        dataSet.clear()
        dataSet.addAll(items)
        notifyDataSetChanged() // TODO 추후 ListAdapter 사용 시 submitList() 호출로 변경 예정
    }

    override fun onItemSwipe(position: Int) {
        touchListener.onItemSwipe(dataSet[position])
    }

    companion object {
        private const val TAG = "TodoListAdapter"
    }
}