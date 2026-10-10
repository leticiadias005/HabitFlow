package com.example.habitflow.model

/** Dados simulados (mock): não há API nem banco de dados nesta etapa. */
object MockHabits {
    val all: List<Habit> = listOf(
        Habit(
            id = 1,
            title = "Beber 2L de água",
            description = "Manter o corpo hidratado ao longo do dia.",
            category = HabitCategory.SAUDE,
            targetDaysPerWeek = 7,
            reminderTime = "08:00"
        ),
        Habit(
            id = 2,
            title = "Ler 20 páginas",
            description = null,
            category = HabitCategory.ESTUDOS,
            targetDaysPerWeek = 5,
            reminderTime = null
        ),
        Habit(
            id = 3,
            title = "Meditar 10 minutos",
            description = "Respiração guiada antes de dormir.",
            category = HabitCategory.MENTE,
            targetDaysPerWeek = 4,
            reminderTime = "22:00"
        ),
        Habit(
            id = 4,
            title = "Planejar o dia",
            description = null,
            category = HabitCategory.PRODUTIVIDADE,
            targetDaysPerWeek = 5,
            reminderTime = "07:30"
        ),
        Habit(
            id = 5,
            title = "Anotar os gastos",
            description = "Registrar tudo o que foi gasto no dia.",
            category = HabitCategory.FINANCAS,
            targetDaysPerWeek = 3,
            reminderTime = null
        )
    )
}
