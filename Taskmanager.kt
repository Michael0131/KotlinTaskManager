// Data class to represent a Task
data class Task(
    val id: Int,
    var name: String,
    var description: String,
    var deadline: String,
    var category: String,
    var status: String = "Pending"
)

fun clearConsole() {
    try {
        val process = ProcessBuilder("cmd", "/c", "cls").inheritIO().start()
        process.waitFor()
    } catch (e: Exception) {
        println("Error clearing the console: ${e.message}")
    }
}

fun main() {
    val tasks = loadTasksFromFile()  // Load tasks from a plain text file at startup
    var nextId = (tasks.keys.maxOrNull() ?: 0) + 1
    

    while (true) {
        clearConsole()
        println("\n=== Task Manager ===")
        println("1. Add Task")
        println("2. Remove Task")
        println("3. List All Tasks")
        println("4. Mark Task as Completed")
        println("5. Filter Tasks by Category")
        println("6. Exit")
        print("Enter your choice: ")

        when (readLine()?.toIntOrNull()) {
            1 -> addTask(tasks, nextId++)
            2 -> removeTask(tasks)
            3 -> listTasks(tasks)
            4 -> markTaskAsCompleted(tasks)
            5 -> filterTasksByCategory(tasks)
            6 -> {
                saveTasksToFile(tasks)
                println("Tasks saved. Exiting Task Manager. Goodbye!")
                break
            }
            else -> println("Invalid choice. Please try again.")
        }
    }
}

// Add a new task
fun addTask(tasks: MutableMap<Int, Task>, id: Int) {
    clearConsole()
    print("Enter task name: ")
    val name = readLine() ?: ""
    print("Enter task description: ")
    val description = readLine() ?: ""
    print("Enter deadline (yyyy-mm-dd): ")
    val deadline = readLine() ?: ""
    print("Enter category (e.g., Work, Personal): ")
    val category = readLine() ?: "Uncategorized"

    tasks[id] = Task(id, name, description, deadline, category)
    println("Task added successfully!")
}

// Remove a task by ID
fun removeTask(tasks: MutableMap<Int, Task>) {
    clearConsole()
    print("Enter the ID of the task to remove: ")
    val id = readLine()?.toIntOrNull()
    if (id != null && tasks.remove(id) != null) {
        println("Task removed successfully!")
    } else {
        println("Task with ID $id not found.")
    }
}

// List all tasks
fun listTasks(tasks: Map<Int, Task>) {
    clearConsole()
    if (tasks.isEmpty()) {
        println("No tasks available.")
    } else {
        println("\n=== Task List ===")
        tasks.values.forEach {
            println("ID: ${it.id}, Name: ${it.name}, Description: ${it.description}, Deadline: ${it.deadline}, Category: ${it.category}, Status: ${it.status}")
        }
    }
}

// Mark a task as completed
fun markTaskAsCompleted(tasks: MutableMap<Int, Task>) {
    clearConsole()
    print("Enter the ID of the task to mark as completed: ")
    val id = readLine()?.toIntOrNull()
    val task = tasks[id]
    if (task != null) {
        task.status = "Completed"
        println("Task marked as completed!")
    } else {
        println("Task with ID $id not found.")
    }
}

// Filter tasks by category
fun filterTasksByCategory(tasks: Map<Int, Task>) {
    clearConsole()
    print("Enter category to filter by: ")
    val category = readLine() ?: ""
    val filteredTasks = tasks.values.filter { it.category.equals(category, ignoreCase = true) }
    if (filteredTasks.isEmpty()) {
        println("No tasks found in category '$category'.")
    } else {
        println("\n=== Tasks in '$category' ===")
        filteredTasks.forEach {
            println("ID: ${it.id}, Name: ${it.name}, Description: ${it.description}, Deadline: ${it.deadline}, Status: ${it.status}")
        }
    }
}

// Save tasks to a plain text file
fun saveTasksToFile(tasks: Map<Int, Task>, fileName: String = "tasks.txt") {
    val fileContent = tasks.values.joinToString("\n") {
        "${it.id}|${it.name}|${it.description}|${it.deadline}|${it.category}|${it.status}"
    }
    java.io.File(fileName).writeText(fileContent)
    println("Tasks saved to $fileName.")
}

// Load tasks from a plain text file
fun loadTasksFromFile(fileName: String = "tasks.txt"): MutableMap<Int, Task> {
    clearConsole()
    val file = java.io.File(fileName)
    if (!file.exists()) return mutableMapOf()

    val tasks = mutableMapOf<Int, Task>()
    file.readLines().forEach { line ->
        val parts = line.split("|")
        if (parts.size == 6) {
            val id = parts[0].toIntOrNull()
            if (id != null) {
                tasks[id] = Task(
                    id,
                    parts[1],
                    parts[2],
                    parts[3],
                    parts[4],
                    parts[5]
                )
            }
        }
    }
    return tasks
}
