// Data class to represent a Task
data class Task(
    val id: Int,                // Unique identifier for the task
    var name: String,           // Name or title of the task
    var description: String,    // Detailed description of the task
    var deadline: String,       // Deadline for the task in yyyy-mm-dd format
    var category: String,       // Category of the task (e.g., Work, Personal)
    var status: String = "Pending"  // Status of the task (default is "Pending")
)

// Function to clear the console for better readability
fun clearConsole() {
    try {
        // Executes the command to clear the console on Windows
        val process = ProcessBuilder("cmd", "/c", "cls").inheritIO().start()
        process.waitFor() // Waits for the command to finish
    } catch (e: Exception) {
        // If an error occurs, print the error message
        println("Error clearing the console: ${e.message}")
    }
}

fun main() {
    // Load tasks from a plain text file at the start of the program
    val tasks = loadTasksFromFile()
    // Determine the next available task ID by finding the maximum existing ID
    var nextId = (tasks.keys.maxOrNull() ?: 0) + 1

    // Main program loop
    while (true) {
        clearConsole() // Clear the console before displaying the menu
        // Display the main menu
        println("\n=== Task Manager ===")
        println("1. Add Task")
        println("2. Remove Task")
        println("3. List All Tasks")
        println("4. Mark Task as Completed")
        println("5. Filter Tasks by Category")
        println("6. Exit")
        print("Enter your choice: ")

        // Handle the user's menu selection using the when statement
        when (readLine()?.toIntOrNull()) {
            1 -> addTask(tasks, nextId++)    // Add a new task
            2 -> removeTask(tasks)          // Remove an existing task by ID
            3 -> listTasks(tasks)           // List all tasks
            4 -> markTaskAsCompleted(tasks) // Mark a task as completed by ID
            5 -> filterTasksByCategory(tasks) // Filter tasks by their category
            6 -> {
                saveTasksToFile(tasks) // Save tasks to a file before exiting
                println("Tasks saved. Exiting Task Manager. Goodbye!")
                break
            }
            else -> println("Invalid choice. Please try again.") // Handle invalid input
        }
    }
}

// Function to add a new task
fun addTask(tasks: MutableMap<Int, Task>, id: Int) {
    clearConsole() // Clear the console for better input visibility
    print("Enter task name: ")
    val name = readLine() ?: "" // Read the task name
    print("Enter task description: ")
    val description = readLine() ?: "" // Read the task description
    print("Enter deadline (yyyy-mm-dd): ")
    val deadline = readLine() ?: "" // Read the task deadline
    print("Enter category (e.g., Work, Personal): ")
    val category = readLine() ?: "Uncategorized" // Read the task category

    // Add the new task to the collection
    tasks[id] = Task(id, name, description, deadline, category)
    println("Task added successfully!")
    Thread.sleep(2000) // Pause to allow the user to read the message
}

// Function to remove a task by its ID
fun removeTask(tasks: MutableMap<Int, Task>) {
    clearConsole() // Clear the console for better input visibility
    print("Enter the ID of the task to remove: ")
    val id = readLine()?.toIntOrNull() // Read the task ID
    if (id != null && tasks.remove(id) != null) {
        println("Task removed successfully!") // Confirm removal
    } else {
        println("Task with ID $id not found.") // Handle invalid or non-existent ID
    }
    Thread.sleep(2000) // Pause to allow the user to read the message
    
}

// Function to list all tasks
fun listTasks(tasks: Map<Int, Task>) {
    clearConsole() // Clear the console for better readability
    if (tasks.isEmpty()) {
        println("No tasks available.") // Handle case with no tasks
    } else {
        println("\n=== Task List ===")
        // Iterate over tasks and display their details
        tasks.values.forEach {
            println("ID: ${it.id}, Name: ${it.name}, Description: ${it.description}, Deadline: ${it.deadline}, Category: ${it.category}, Status: ${it.status}")
        }
    }
    Thread.sleep(4000) // Pause to allow the user to read the message
}

// Function to mark a task as completed by its ID
fun markTaskAsCompleted(tasks: MutableMap<Int, Task>) {
    clearConsole() // Clear the console for better input visibility
    print("Enter the ID of the task to mark as completed: ")
    val id = readLine()?.toIntOrNull() // Read the task ID
    val task = tasks[id] // Retrieve the task from the collection
    if (task != null) {
        task.status = "Completed" // Update the task's status
        println("Task marked as completed!") // Confirm completion
    } else {
        println("Task with ID $id not found.") // Handle invalid or non-existent ID
    }
    Thread.sleep(2000) // Pause to allow the user to read the message
}

// Function to filter tasks by their category
fun filterTasksByCategory(tasks: Map<Int, Task>) {
    print("Enter category to filter by: ")
    val category = readLine() ?: "" // Read the category input
    // Filter tasks matching the input category (case-insensitive)
    val filteredTasks = tasks.values.filter { it.category.equals(category, ignoreCase = true) }
    if (filteredTasks.isEmpty()) {
        println("No tasks found in category '$category'.") // Handle no matches
    } else {
        clearConsole() // Clear the console for better input visibility
        println("\n=== Tasks in '$category' ===")
        // Display tasks in the specified category
        filteredTasks.forEach {
            println("ID: ${it.id}, Name: ${it.name}, Description: ${it.description}, Deadline: ${it.deadline}, Status: ${it.status}")
        }
    }
    Thread.sleep(2000) // Pause to allow the user to read the message
}

// Function to save tasks to a plain text file
fun saveTasksToFile(tasks: Map<Int, Task>, fileName: String = "tasks.txt") {
    // Serialize tasks to a single string, each task on a new line
    val fileContent = tasks.values.joinToString("\n") {
        "${it.id}|${it.name}|${it.description}|${it.deadline}|${it.category}|${it.status}"
    }
    // Write the string to the specified file
    java.io.File(fileName).writeText(fileContent)
    println("Tasks saved to $fileName.") // Confirm save
    Thread.sleep(2000) // Pause to allow the user to read the message
}

// Function to load tasks from a plain text file
fun loadTasksFromFile(fileName: String = "tasks.txt"): MutableMap<Int, Task> {
    val file = java.io.File(fileName) // Open the file
    if (!file.exists()) return mutableMapOf() // Return empty collection if file doesn't exist

    val tasks = mutableMapOf<Int, Task>() // Initialize an empty task map
    // Read and process each line of the file
    file.readLines().forEach { line ->
        val parts = line.split("|") // Split the line into parts
        if (parts.size == 6) { // Ensure all required parts are present
            val id = parts[0].toIntOrNull() // Parse the ID
            if (id != null) {
                // Reconstruct the task and add it to the map
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
    Thread.sleep(2000) // Pause to allow the user to read the message
    return tasks // Return the loaded tasks
}
