package org.esinf.PQExamples;

import org.esinf.Priority_Queue.Entry;

import java.util.Map;

public class TaskManagementTest {

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("     TASK MANAGEMENT SYSTEM - DEMONSTRATION");
        System.out.println("════════════════════════════════════════════════════════\n");

        // Test 1: Inicialização do sistema com tarefas
        testInitialization();

        // Test 2: Adicionar novas tarefas
        testAddTasks();

        // Test 3: Processar tarefas
        testProcessTasks();

        // Test 4: Reagendar tarefas
        testReschedule();

        // Test 5: Ajuste de prioridades (aging)
        testPriorityAdjustment();

        // Test 6: Processar com limite de execuções consecutivas
        testProcessWithLimit();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 1: Inicialização do Sistema
    // ═══════════════════════════════════════════════════════════════
    private static void testInitialization() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 1: System Initialization                       │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        Integer[] priorities = {5, 2, 8, 1, 3};
        Task[] tasks = {
                new Task("Database Backup", "Maintenance"),
                new Task("Process User Payment", "Financial"),
                new Task("Send Newsletter", "Marketing"),
                new Task("Critical Security Patch", "Security"),
                new Task("Generate Monthly Report", "Reports")
        };

        TaskManagement tm = new TaskManagement(priorities, tasks);

        System.out.println("Initial queue size: " + tm.queueSize());
        tm.listTasks();

        waitForUser();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 2: Adicionar Novas Tarefas
    // ═══════════════════════════════════════════════════════════════
    private static void testAddTasks() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 2: Adding New Tasks                            │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        Integer[] priorities = {5, 3};
        Task[] tasks = {
                new Task("Update Customer Records", "Database"),
                new Task("Check Server Status", "Monitoring")
        };

        TaskManagement tm = new TaskManagement(priorities, tasks);

        System.out.println("Queue before additions:");
        tm.listTasks();

        System.out.println("\n--- Adding new tasks ---");
        tm.addTaskToQueue(1, new Task("Emergency: System Down", "Critical"));
        tm.addTaskToQueue(7, new Task("Clean Log Files", "Maintenance"));
        tm.addTaskToQueue(2, new Task("Deploy New Feature", "Development"));

        System.out.println("\nQueue after additions (size: " + tm.queueSize() + "):");
        tm.listTasks();

        waitForUser();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 3: Processar Tarefas
    // ═══════════════════════════════════════════════════════════════
    private static void testProcessTasks() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 3: Processing Tasks (by Priority)              │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        Integer[] priorities = {5, 2, 8, 1, 3, 6};
        Task[] tasks = {
                new Task("Task A", "Type1"),
                new Task("Task B", "Type2"),
                new Task("Task C", "Type1"),
                new Task("Task D", "Type3"),
                new Task("Task E", "Type2"),
                new Task("Task F", "Type1")
        };

        TaskManagement tm = new TaskManagement(priorities, tasks);

        System.out.println("Initial queue:");
        tm.listTasks();

        System.out.println("\n--- Processing next 3 tasks ---");
        for (int i = 0; i < 3; i++) {
            Entry<Integer, Task> processed = tm.processNextTask();
            System.out.println();
        }

        System.out.println("Remaining queue (size: " + tm.queueSize() + "):");
        tm.listTasks();

        waitForUser();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 4: Reagendar Tarefas
    // ═══════════════════════════════════════════════════════════════
    private static void testReschedule() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 4: Rescheduling Tasks                          │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        Integer[] priorities = {5, 8, 3};
        Task[] tasks = {
                new Task("Backup Database", "Maintenance"),
                new Task("Update Software", "Updates"),
                new Task("Send Notifications", "Communications")
        };

        TaskManagement tm = new TaskManagement(priorities, tasks);

        System.out.println("Queue before rescheduling:");
        tm.listTasks();

        System.out.println("\n--- Rescheduling 'Update Software' from priority 8 to 1 ---");
        tm.rescheduleTask("Update Software", 1);

        System.out.println("\nQueue after rescheduling:");
        tm.listTasks();

        System.out.println("\n--- Attempting to reschedule non-existent task ---");
        tm.rescheduleTask("Non-existent Task", 1);

        waitForUser();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 5: Ajuste de Prioridades (Aging)
    // ═══════════════════════════════════════════════════════════════
    private static void testPriorityAdjustment() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 5: Priority Adjustment (Aging Mechanism)       │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        Integer[] priorities = {5, 8, 3, 10};
        Task[] tasks = {
                new Task("Old Task 1", "Batch"),
                new Task("Old Task 2", "Batch"),
                new Task("Medium Priority Task", "Processing"),
                new Task("Low Priority Task", "Cleanup")
        };

        TaskManagement tm = new TaskManagement(priorities, tasks);

        // Simula que as tarefas foram criadas há algum tempo
        // (Em produção, teríamos tarefas com creationDate antigas)
        System.out.println("Simulating aging mechanism...");
        System.out.println("NOTE: In this demo, tasks were just created, so aging won't apply.");
        System.out.println("In a real scenario with old tasks, priorities would be adjusted.\n");

        System.out.println("Queue before adjustment:");
        tm.listTasks();

        System.out.println("\n--- Adjusting priorities for tasks older than 5 minutes ---");
        tm.adjustPriorities(5);

        System.out.println("\nQueue after adjustment attempt:");
        tm.listTasks();

        System.out.println("\n💡 TIP: To see aging in action, modify Task creation dates");
        System.out.println("   or wait several minutes before calling adjustPriorities()");

        waitForUser();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 6: Processar com Limite de Execuções Consecutivas
    // ═══════════════════════════════════════════════════════════════
    private static void testProcessWithLimit() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 6: Processing with Consecutive Execution Limit │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        Integer[] priorities = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Task[] tasks = {
                new Task("Database Query 1", "Database"),
                new Task("Database Query 2", "Database"),
                new Task("API Call 1", "API"),
                new Task("Database Query 3", "Database"),
                new Task("Email 1", "Email"),
                new Task("API Call 2", "API"),
                new Task("Database Query 4", "Database"),
                new Task("Email 2", "Email"),
                new Task("API Call 3", "API"),
                new Task("File Process 1", "FileOps")
        };

        TaskManagement tm = new TaskManagement(priorities, tasks);

        System.out.println("Initial queue (10 tasks):");
        tm.listTasks();

        System.out.println("\n--- Processing all tasks with limit of 2 consecutive executions per category ---");
        Map<String, Integer> results = tm.processTasksWithLimit(2);

        System.out.println("\n✓ All tasks processed successfully!");
        System.out.println("  Notice how no category was executed more than 2 times consecutively.");

        waitForUser();
    }

    // ═══════════════════════════════════════════════════════════════
    // Utilidade para pausar entre testes
    // ═══════════════════════════════════════════════════════════════
    private static void waitForUser() {
        System.out.println("\n" + "─".repeat(57));
        System.out.println("Press Enter to continue to next test...");
        System.out.println("─".repeat(57));
        try {
            System.in.read();
            // Limpa o buffer
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            // Ignora exceções
        }
    }
}
