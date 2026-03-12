package org.esinf.PQExamples;

import org.esinf.Priority_Queue.Entry;
import org.esinf.Priority_Queue.HeapPriorityQueue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagement {
    private HeapPriorityQueue<Integer, Task> taskQueue;

    // Constructor que inicializa a fila com tarefas
    public TaskManagement(Integer[] p, Task[] t) {
        taskQueue = new HeapPriorityQueue<>(p, t);
    }

    /**
     * a. Adds a new task with a priority, description and category
     * Nota: Prioridades menores = maior prioridade (min-heap)
     */
    public void addTaskToQueue(Integer pr, Task t) {
        taskQueue.insert(pr, t);
        System.out.println("Task added: " + t.description + " with priority " + pr);
    }

    // Method that returns the size of the queue
    public int queueSize() {
        return taskQueue.size();
    }

    /**
     * a. Removes and processes the highest-priority task from the queue
     * @return the entry with the highest priority (lowest key value)
     */
    public Entry<Integer, Task> processNextTask() {
        Entry<Integer, Task> nextTask = taskQueue.removeMin();

        if (nextTask != null) {
            System.out.println("Processing task with priority " + nextTask.getKey() + ": "
                    + nextTask.getValue().description);
        } else {
            System.out.println("No tasks to process.");
        }

        return nextTask;
    }

    /**
     * c. Reschedules a task by updating its priority
     * Remove a tarefa da fila, atualiza a prioridade e reinsere
     */
    public void rescheduleTask(String taskDescription, int newPriority) {
        // Cria uma lista temporária para armazenar todas as tarefas
        List<Entry<Integer, Task>> tempList = new ArrayList<>();
        Entry<Integer, Task> targetTask = null;

        // Remove todas as tarefas da fila
        while (!taskQueue.isEmpty()) {
            Entry<Integer, Task> entry = taskQueue.removeMin();

            if (entry.getValue().description.equals(taskDescription)) {
                targetTask = entry;
            } else {
                tempList.add(entry);
            }
        }

        // Reinsere todas as tarefas, exceto a que foi reagendada
        for (Entry<Integer, Task> entry : tempList) {
            taskQueue.insert(entry.getKey(), entry.getValue());
        }

        // Reinsere a tarefa reagendada com a nova prioridade
        if (targetTask != null) {
            taskQueue.insert(newPriority, targetTask.getValue());
            System.out.println("Task '" + taskDescription + "' rescheduled with priority " + newPriority);
        } else {
            System.out.println("Task '" + taskDescription + "' not found.");
        }
    }

    /**
     * d. Dynamically adjusts priorities for tasks that have been in the queue
     * for more than T minutes (aging mechanism to prevent starvation)
     * Reduz a prioridade (valor numérico) das tarefas antigas para aumentar sua importância
     */
    public void adjustPriorities(int minuts) {
        List<Entry<Integer, Task>> allTasks = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        // Remove todas as tarefas da fila
        while (!taskQueue.isEmpty()) {
            allTasks.add(taskQueue.removeMin());
        }

        // Reinsere com prioridades ajustadas
        for (Entry<Integer, Task> entry : allTasks) {
            Task task = entry.getValue();
            int currentPriority = entry.getKey();

            // Calcula o tempo de espera em minutos
            long waitTime = Duration.between(task.creationDate, now).toMinutes();

            if (waitTime > minuts) {
                // Reduz o valor da prioridade (aumenta a importância) para tarefas antigas
                // A cada T minutos adicionais, reduz a prioridade em 1
                int adjustment = (int) ((waitTime - minuts) / minuts);
                int newPriority = Math.max(1, currentPriority - adjustment);

                System.out.println("Adjusting priority of '" + task.description
                        + "' from " + currentPriority + " to " + newPriority
                        + " (waited " + waitTime + " minutes)");

                taskQueue.insert(newPriority, task);
            } else {
                taskQueue.insert(currentPriority, task);
            }
        }
    }

    /**
     * e. Processes tasks while ensuring a limit on how many times a task
     * can be executed consecutively
     * @param executionLimit número máximo de execuções consecutivas por categoria
     * @return mapa com o número de vezes que cada categoria foi executada
     */
    public Map<String, Integer> processTasksWithLimit(int executionLimit) {
        Map<String, Integer> executionCount = new HashMap<>();
        Map<String, Integer> consecutiveCount = new HashMap<>();
        List<Entry<Integer, Task>> postponedTasks = new ArrayList<>();

        System.out.println("\n=== Processing tasks with limit " + executionLimit + " ===");

        while (!taskQueue.isEmpty() || !postponedTasks.isEmpty()) {
            // Se a fila principal estiver vazia, reinsere as tarefas adiadas
            if (taskQueue.isEmpty() && !postponedTasks.isEmpty()) {
                System.out.println("\nReinserting postponed tasks...");
                for (Entry<Integer, Task> entry : postponedTasks) {
                    taskQueue.insert(entry.getKey(), entry.getValue());
                }
                postponedTasks.clear();
                consecutiveCount.clear(); // Reset dos contadores consecutivos
            }

            if (taskQueue.isEmpty()) break;

            Entry<Integer, Task> entry = taskQueue.removeMin();
            Task task = entry.getValue();
            String category = task.category;

            // Verifica quantas vezes consecutivas esta categoria foi executada
            int consecutive = consecutiveCount.getOrDefault(category, 0);

            if (consecutive >= executionLimit) {
                // Adia esta tarefa
                System.out.println("  > Postponing: " + task.description
                        + " (category '" + category + "' reached limit)");
                postponedTasks.add(entry);
            } else {
                // Processa a tarefa
                System.out.println("  ✓ Processing: " + task.description
                        + " [Priority: " + entry.getKey() + ", Category: " + category + "]");

                // Atualiza contadores
                executionCount.put(category, executionCount.getOrDefault(category, 0) + 1);
                consecutiveCount.put(category, consecutive + 1);

                // Reset dos contadores consecutivos de outras categorias
                for (String cat : new ArrayList<>(consecutiveCount.keySet())) {
                    if (!cat.equals(category)) {
                        consecutiveCount.put(cat, 0);
                    }
                }
            }
        }

        System.out.println("\n=== Execution Summary ===");
        for (Map.Entry<String, Integer> entry : executionCount.entrySet()) {
            System.out.println("Category '" + entry.getKey() + "': " + entry.getValue() + " tasks processed");
        }

        return executionCount;
    }

    // List all tasks in the queue (for visualization)
    public void listTasks() {
        if (taskQueue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            System.out.println("\n=== Current Task Queue ===");
            System.out.println(taskQueue.toString());
        }
    }
}