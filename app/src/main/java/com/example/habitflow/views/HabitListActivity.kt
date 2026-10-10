package com.example.habitflow.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitflow.databinding.ActivityHabitListBinding
import com.example.habitflow.model.Habit
import com.example.habitflow.model.MockHabits

/**
 * Tela 2 (Views/XML + ViewBinding + RecyclerView).
 *
 * Interações tratadas:
 * - marcar/desmarcar um hábito como concluído atualiza a lista na hora;
 * - tocar em um hábito navega para [HabitDetailActivity] via Intent explícita,
 *   levando os dados do hábito como extras.
 */
class HabitListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHabitListBinding
    private lateinit var adapter: HabitAdapter

    // Lista imutável: cada alteração gera uma nova lista (com copy nos itens).
    private var habits: List<Habit> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        habits = restoreHabits(savedInstanceState)

        adapter = HabitAdapter(onToggle = ::toggleHabit, onOpen = ::openDetail)
        binding.recyclerHabits.layoutManager = LinearLayoutManager(this)
        binding.recyclerHabits.adapter = adapter
        adapter.submitList(habits)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guarda só os ids concluídos para sobreviver à rotação de tela.
        val completedIds = habits.filter { it.completedToday }.map { it.id }.toIntArray()
        outState.putIntArray(STATE_COMPLETED_IDS, completedIds)
    }

    private fun restoreHabits(savedInstanceState: Bundle?): List<Habit> {
        val completedIds = savedInstanceState?.getIntArray(STATE_COMPLETED_IDS)
            ?: return MockHabits.all
        return MockHabits.all.map { it.copy(completedToday = it.id in completedIds) }
    }

    private fun toggleHabit(habit: Habit) {
        habits = habits.map {
            if (it.id == habit.id) it.copy(completedToday = !it.completedToday) else it
        }
        adapter.submitList(habits)
    }

    private fun openDetail(habit: Habit) {
        startActivity(HabitDetailActivity.newIntent(this, habit))
    }

    private companion object {
        const val STATE_COMPLETED_IDS = "state_completed_ids"
    }
}
