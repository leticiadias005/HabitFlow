package com.example.habitflow.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.habitflow.R
import com.example.habitflow.databinding.ActivityHabitDetailBinding
import com.example.habitflow.model.Habit
import com.example.habitflow.model.HabitCategory

/**
 * Tela 3 (Views/XML + ViewBinding).
 *
 * Recebe os dados do hábito pelos extras da Intent explícita criada em
 * [newIntent], reconstrói o [Habit] e exibe as informações. Os campos opcionais
 * (descrição e lembrete) ganham um texto alternativo quando ausentes.
 */
class HabitDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHabitDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showHabit(readHabitFromIntent())
    }

    private fun readHabitFromIntent(): Habit = Habit(
        id = intent.getIntExtra(EXTRA_ID, 0),
        title = intent.getStringExtra(EXTRA_TITLE) ?: getString(R.string.detail_unknown_habit),
        description = intent.getStringExtra(EXTRA_DESCRIPTION),
        category = HabitCategory.fromNameOrDefault(intent.getStringExtra(EXTRA_CATEGORY)),
        targetDaysPerWeek = intent.getIntExtra(EXTRA_TARGET_DAYS, 1),
        reminderTime = intent.getStringExtra(EXTRA_REMINDER),
        completedToday = intent.getBooleanExtra(EXTRA_COMPLETED, false)
    )

    private fun showHabit(habit: Habit) {
        with(binding) {
            textDetailTitle.text = getString(
                R.string.habit_title_format,
                habit.category.emoji,
                habit.title
            )
            textDetailCategory.text = habit.category.displayName

            textDescriptionValue.text =
                habit.description ?: getString(R.string.detail_no_description)

            textTargetValue.text =
                getString(R.string.habit_target_format, habit.targetDaysPerWeek)

            textReminderValue.text = habit.reminderTime
                ?.let { getString(R.string.detail_reminder_format, it) }
                ?: getString(R.string.detail_no_reminder)

            textStatusValue.text = getString(
                if (habit.completedToday) R.string.detail_status_done
                else R.string.detail_status_pending
            )
        }
    }

    companion object {
        private const val EXTRA_ID = "extra_habit_id"
        private const val EXTRA_TITLE = "extra_habit_title"
        private const val EXTRA_DESCRIPTION = "extra_habit_description"
        private const val EXTRA_CATEGORY = "extra_habit_category"
        private const val EXTRA_TARGET_DAYS = "extra_habit_target_days"
        private const val EXTRA_REMINDER = "extra_habit_reminder"
        private const val EXTRA_COMPLETED = "extra_habit_completed"

        /** Cria a Intent explícita para esta tela, já com os dados do hábito. */
        fun newIntent(context: Context, habit: Habit): Intent =
            Intent(context, HabitDetailActivity::class.java).apply {
                putExtra(EXTRA_ID, habit.id)
                putExtra(EXTRA_TITLE, habit.title)
                putExtra(EXTRA_DESCRIPTION, habit.description) // pode ser null
                putExtra(EXTRA_CATEGORY, habit.category.name)
                putExtra(EXTRA_TARGET_DAYS, habit.targetDaysPerWeek)
                putExtra(EXTRA_REMINDER, habit.reminderTime) // pode ser null
                putExtra(EXTRA_COMPLETED, habit.completedToday)
            }
    }
}
