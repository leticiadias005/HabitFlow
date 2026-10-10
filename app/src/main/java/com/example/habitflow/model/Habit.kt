package com.example.habitflow.model

/**
 * Modelo imutável (data class) de um hábito.
 *
 * [description] e [reminderTime] são opcionais (nullable): nem todo hábito tem
 * descrição ou horário de lembrete, e as telas precisam tratar a ausência deles.
 *
 * Por ser imutável, "alterar" um hábito significa criar uma cópia com [copy].
 */
data class Habit(
    val id: Int,
    val title: String,
    val description: String? = null,
    val category: HabitCategory,
    val targetDaysPerWeek: Int,
    val reminderTime: String? = null,
    val completedToday: Boolean = false
)

enum class HabitCategory(val displayName: String, val emoji: String) {
    SAUDE("Saúde", "💪"),
    ESTUDOS("Estudos", "📚"),
    MENTE("Mente", "🧘"),
    PRODUTIVIDADE("Produtividade", "✅"),
    FINANCAS("Finanças", "💰");

    companion object {
        /** Converte o nome vindo de um extra da Intent; se ausente ou inválido, usa um padrão. */
        fun fromNameOrDefault(name: String?): HabitCategory =
            entries.firstOrNull { it.name == name } ?: PRODUTIVIDADE
    }
}
