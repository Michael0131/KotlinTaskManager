# KotlinTaskManager

# Overview

This project is a console-based task manager application written in Kotlin. The application allows users to efficiently manage their tasks by providing features such as adding tasks, removing tasks, listing all tasks, marking tasks as completed, and filtering tasks by category. 

The purpose of writing this software was to deepen my understanding of the Kotlin language and its syntax, including its features for collections, data classes, and control flow structures like `when`. Additionally, the project helped reinforce concepts like file handling and dynamic collection management.

[Software Demo Video](hhttps://youtu.be/2pgG5BoK_jk)


# Development Environment

I developed the software using the Kotlin programming language and the Java Development Kit (JDK). The development environment was Visual Studio Code, equipped with the Kotlin Language extension for coding and debugging. The program relied on Kotlin's standard library for file handling and thread management but avoided using any external libraries to ensure simplicity and portability.

# Useful Websites

- [Kotlin Official Documentation](https://kotlinlang.org/docs/home.html)
- [Kotlin Programming Tutorials](https://kotlinlang.org/docs/tutorials.html)
- [Stack Overflow](https://stackoverflow.com/)

# Future Work

- Improve error handling for user input to make the program more robust.
- Add the ability to edit task details after creation.
- Enhance the user interface by introducing colored console output for better readability.
- Implement task prioritization and sorting based on deadlines or categories.


Instructions and Information ********

## Features

- Add tasks with a name, description, deadline, and category.
- Remove tasks by their unique ID.
- List all tasks with their details.
- Mark tasks as completed.
- Filter tasks by category.
- Save and load tasks to/from a plain text file.

## Development Environment

- **Programming Language:** Kotlin (with standard library only)
- **IDE:** Visual Studio Code with Kotlin extension
- **Java Development Kit (JDK):** Version 23.0.1

## Installation and Usage

### Prerequisites
1. Install the [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html).
2. Install [Kotlin Compiler](https://kotlinlang.org/docs/command-line.html).
3. Set up your environment to recognize the `kotlinc` and `java` commands.

### Running the Program
1. Clone this repository to your local machine:
   ```bash
   git clone https://github.com/your-repo/task-manager.git
   cd task-manager


COMPILE INSTRUCTIONS

run this in terminal to compile:

kotlinc Taskmanager.kt -include-runtime -d Taskmanager.jar

After it is compiled run this in terminal:

java -jar Taskmanager.jar
