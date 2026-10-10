package com.example.habitflow.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitflow.R
import com.example.habitflow.databinding.ItemHabitBinding
import com.example.habitflow.model.Habit

/**
 * Adapter do RecyclerView. Como o modelo [Habit] é imutável, a lista é sempre
 * substituída por uma nova (submitList) e o DiffUtil descobre o que mudou.
 *
 * - [onToggle]: usuário marcou/desmarcou o checkbox do hábito.
 * - [onOpen]: usuário tocou no item para ver os detalhes.
 */
class HabitAdapter(
    private val onToggle: (Habit) -> Unit,
    private val onOpen: (Habit) -> Unit
) : ListAdapter<Habit, HabitAdapter.HabitViewHolder>(HabitDiffCallback) {

    class HabitViewHolder(val binding: ItemHabitBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = getItem(position)
        val context = holder.itemView.context

        with(holder.binding) {
            textHabitTitle.text = context.getString(
                R.string.habit_title_format,
                habit.category.emoji,
                habit.title
            )
            // Descrição é opcional: sem ela, mostramos a meta semanal.
            textHabitSubtitle.text = habit.description
                ?: context.getString(R.string.habit_target_format, habit.targetDaysPerWeek)

            checkCompleted.isChecked = habit.completedToday
            checkCompleted.contentDescription =
                context.getString(R.string.habit_check_description, habit.title)
            checkCompleted.setOnClickListener { onToggle(habit) }

            root.setOnClickListener { onOpen(habit) }
        }
    }
}

private object HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean =
        oldItem == newItem
}
