# HabitFlow

Aplicativo Android para acompanhar hábitos diários (beber água, ler, meditar,
etc.). O objetivo é **ajudar a pessoa a visualizar seus pequenos hábitos do dia,
marcar os que já cumpriu e consultar os detalhes de cada um**.

Esta etapa usa apenas **Android Views (layouts XML)**, navegação por **`Intent`
explícita** e **dados simulados (mocks)** — sem API, sem banco de dados e sem
chaves/senhas (portanto, não há arquivo `.env`).

## Telas e fluxo

1. **Boas-vindas** (`WelcomeActivity`) → toque em "Começar".
2. **Lista de hábitos** (`HabitListActivity`) → `RecyclerView` com hábitos
   mockados. Marcar o checkbox atualiza a lista na hora; tocar no hábito abre os
   detalhes.
3. **Detalhes do hábito** (`HabitDetailActivity`) → mostra os dados recebidos
   pela `Intent` (descrição, meta semanal, lembrete e se foi concluído hoje).

## Como rodar o projeto localmente

1. Instale o **Android Studio** (Koala ou mais recente), com o **Android SDK 34**
   e **JDK 17**.
2. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```
3. No Android Studio, use **File → Open** e selecione a pasta do projeto. Aguarde
   o *Gradle Sync* (é necessária internet na primeira vez para baixar o Gradle 8.9
   e as dependências).
4. Rode em um emulador ou aparelho com **Android 8.0 (API 26) ou superior**,
   clicando em **Run ▶**.

## Bibliotecas externas

| Biblioteca | Para que serve |
|---|---|
| **AndroidX AppCompat** | `AppCompatActivity` e o tema AppCompat usados pelas três telas em Views. |
| **AndroidX RecyclerView** | Exibe a lista de hábitos de forma eficiente (`RecyclerView`, `ListAdapter` e `DiffUtil`). |

O **ViewBinding** é um recurso do próprio Android Gradle Plugin
(`buildFeatures { viewBinding = true }`), não uma biblioteca adicional.

## Como os requisitos foram atendidos

| Requisito | Onde está |
|---|---|
| Telas em Views com layouts XML | `res/layout/activity_welcome.xml`, `activity_habit_list.xml`, `activity_habit_detail.xml` e `item_habit.xml`. Usam `TextView`, `ImageView`, `Space`, `CheckBox`, `Button` e os ViewGroups `LinearLayout`, `FrameLayout`, `ScrollView` e `RecyclerView`. |
| Navegação por `Intent` explícita, com dados | `WelcomeActivity` → `HabitListActivity` (sem dados) e `HabitListActivity` → `HabitDetailActivity` (extras criados em `HabitDetailActivity.newIntent`). |
| Views conectadas ao Kotlin por ViewBinding + interação | Todas as telas usam ViewBinding. Interações: botão "Começar" (avança o fluxo), checkbox do hábito (atualiza a interface) e toque no item (abre os detalhes). |
| Modelos imutáveis e valores opcionais | `data class Habit` (somente `val`); `description` e `reminderTime` são `String?` e as telas tratam o `null` com `?:` / `?.let`. A alteração do estado usa `copy()`. |
| Dados simulados | `model/MockHabits.kt`. |

## Decisões técnicas

- **Uma `Activity` por tela**, ligadas por `Intent` explícita (`Intent(context, Classe::class.java)`),
  como pede o enunciado.
- **`ViewBinding` em vez de `findViewById`**: acesso às Views com segurança de tipos e
  sem risco de erro de ID em tempo de execução.
- **`ListAdapter` + `DiffUtil`**: como o `Habit` é imutável, cada mudança gera uma
  nova lista (`submitList`) e o `DiffUtil` atualiza só o item alterado.
- **Extras da `Intent`**: os campos do hábito são enviados individualmente
  (`putExtra`). Os opcionais podem ir como `null`; a categoria é enviada pelo
  `name` do enum e lida com `HabitCategory.fromNameOrDefault`, que tem um valor
  padrão caso o extra esteja ausente.
- **Estado da lista na rotação**: `HabitListActivity` salva em
  `onSaveInstanceState` os ids dos hábitos concluídos e os reaplica sobre os mocks.
- **Sem persistência**: as marcações ficam apenas em memória, enquanto o app está
  aberto, já que esta etapa não usa banco de dados.

## Estrutura de pastas

```
app/src/main/
├── java/com/example/habitflow/
│   ├── model/                 # Habit, HabitCategory (data classes) e MockHabits
│   └── views/                 # WelcomeActivity, HabitListActivity,
│                              # HabitDetailActivity e HabitAdapter
└── res/
    ├── layout/                # activity_welcome, activity_habit_list,
    │                          # activity_habit_detail, item_habit
    ├── drawable/              # bg_card, bg_logo, ic_launcher_foreground
    └── values/                # strings, colors, themes
```
