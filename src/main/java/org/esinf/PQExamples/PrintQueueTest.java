package org.esinf.PQExamples;

import org.esinf.Priority_Queue.Entry;

public class PrintQueueTest {

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("     PRINT QUEUE SYSTEM - DEMONSTRATION");
        System.out.println("════════════════════════════════════════════════════════\n");

        // Test 1: Inicialização e adição de documentos
        testAddDocuments();

        // Test 2: Ver próximo documento sem remover
        testNextDocument();

        // Test 3: Enviar documentos para impressora
        testSendToPrinter();

        // Test 4: Calcular tempo de espera
        testTimeToprint();

        // Test 5: Cenário realista completo
        testRealisticScenario();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 1: Adicionar Documentos à Fila
    // ═══════════════════════════════════════════════════════════════
    private static void testAddDocuments() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 1: Adding Documents to Print Queue             │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        PrintQueue pq = new PrintQueue();

        System.out.println("--- Adding documents with different priorities ---");
        System.out.println("(Lower priority number = prints first)\n");

        pq.addDoc2Queue(3, new PrintQueue.Document(101, 5));   // Normal user
        pq.addDoc2Queue(1, new PrintQueue.Document(102, 2));   // Admin user
        pq.addDoc2Queue(5, new PrintQueue.Document(103, 10));  // Guest user
        pq.addDoc2Queue(2, new PrintQueue.Document(104, 3));   // Premium user
        pq.addDoc2Queue(3, new PrintQueue.Document(105, 7));   // Normal user

        System.out.println("\nCurrent queue (size: " + pq.size() + "):");
        pq.listDocuments();

        waitForUser();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 2: Ver Próximo Documento (sem remover)
    // ═══════════════════════════════════════════════════════════════
    private static void testNextDocument() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 2: View Next Document (without removing)       │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        PrintQueue pq = new PrintQueue();

        pq.addDoc2Queue(5, new PrintQueue.Document(201, 8));
        pq.addDoc2Queue(2, new PrintQueue.Document(202, 4));
        pq.addDoc2Queue(7, new PrintQueue.Document(203, 12));
        pq.addDoc2Queue(1, new PrintQueue.Document(204, 3));

        System.out.println("Current queue:");
        pq.listDocuments();

        System.out.println("\n--- Checking next document to print ---");
        PrintQueue.Document next = pq.nextDoc2Print();
        if (next != null) {
            System.out.println("Next document: " + next);
        }

        System.out.println("\nQueue after checking (unchanged, size: " + pq.size() + "):");
        pq.listDocuments();

        waitForUser();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 3: Enviar Documentos para Impressora
    // ═══════════════════════════════════════════════════════════════
    private static void testSendToPrinter() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 3: Sending Documents to Printer                │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        PrintQueue pq = new PrintQueue();

        pq.addDoc2Queue(4, new PrintQueue.Document(301, 6));
        pq.addDoc2Queue(1, new PrintQueue.Document(302, 2));
        pq.addDoc2Queue(3, new PrintQueue.Document(303, 8));
        pq.addDoc2Queue(2, new PrintQueue.Document(304, 4));
        pq.addDoc2Queue(5, new PrintQueue.Document(305, 10));

        System.out.println("Initial queue (5 documents):");
        pq.listDocuments();

        System.out.println("\n--- Sending documents to printer (in priority order) ---\n");

        int count = 1;
        while (!pq.isEmpty()) {
            System.out.println("Printing #" + count + ":");
            Entry<Integer, PrintQueue.Document> sent = pq.send2Printer();
            System.out.println("  Pages: " + sent.getValue().getNPags());
            System.out.println("  Remaining in queue: " + pq.size());
            System.out.println();
            count++;
        }

        System.out.println("✓ All documents printed!");

        waitForUser();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 4: Calcular Tempo de Espera
    // ═══════════════════════════════════════════════════════════════
    private static void testTimeToprint() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 4: Calculate Waiting Time (time2print)         │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        PrintQueue pq = new PrintQueue();

        PrintQueue.Document doc1 = new PrintQueue.Document(401, 5);
        PrintQueue.Document doc2 = new PrintQueue.Document(402, 3);
        PrintQueue.Document doc3 = new PrintQueue.Document(403, 8);
        PrintQueue.Document doc4 = new PrintQueue.Document(404, 4);
        PrintQueue.Document doc5 = new PrintQueue.Document(405, 10);

        pq.addDoc2Queue(2, doc1);  // Priority 2
        pq.addDoc2Queue(4, doc2);  // Priority 4
        pq.addDoc2Queue(1, doc3);  // Priority 1 (prints first!)
        pq.addDoc2Queue(3, doc4);  // Priority 3
        pq.addDoc2Queue(5, doc5);  // Priority 5

        System.out.println("Queue setup:");
        pq.listDocuments();

        System.out.println("\n--- Calculating wait times (2 seconds per page) ---\n");

        double timeslot = 2.0; // seconds per page

        System.out.println("Doc.403 (Priority 1, 8 pages):");
        double time1 = pq.time2print(doc3, timeslot);
        System.out.println("  Wait time: " + time1 + " seconds (prints immediately!)");

        System.out.println("\nDoc.401 (Priority 2, 5 pages):");
        double time2 = pq.time2print(doc1, timeslot);
        System.out.println("  Wait time: " + time2 + " seconds");
        System.out.println("  Explanation: Must wait for Doc.403 (8 pages × 2s = 16s)");

        System.out.println("\nDoc.404 (Priority 3, 4 pages):");
        double time3 = pq.time2print(doc4, timeslot);
        System.out.println("  Wait time: " + time3 + " seconds");
        System.out.println("  Explanation: Doc.403 (16s) + Doc.401 (10s) = 26s");

        System.out.println("\nDoc.402 (Priority 4, 3 pages):");
        double time4 = pq.time2print(doc2, timeslot);
        System.out.println("  Wait time: " + time4 + " seconds");

        System.out.println("\nDoc.405 (Priority 5, 10 pages):");
        double time5 = pq.time2print(doc5, timeslot);
        System.out.println("  Wait time: " + time5 + " seconds (last in queue)");

        // Test with non-existent document
        PrintQueue.Document docNotFound = new PrintQueue.Document(999, 1);
        System.out.println("\nDoc.999 (not in queue):");
        double timeNotFound = pq.time2print(docNotFound, timeslot);
        System.out.println("  Wait time: " + timeNotFound + " seconds (document not found)");

        System.out.println("\n✓ Queue remains intact after time calculations");
        System.out.println("  Current size: " + pq.size());

        waitForUser();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST 5: Cenário Realista Completo
    // ═══════════════════════════════════════════════════════════════
    private static void testRealisticScenario() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│ TEST 5: Realistic Office Scenario                   │");
        System.out.println("└─────────────────────────────────────────────────────┘\n");

        System.out.println("🏢 Office Print Queue Simulation");
        System.out.println("Priority levels:");
        System.out.println("  1 = CEO/Executive (highest priority)");
        System.out.println("  2 = Manager");
        System.out.println("  3 = Regular Employee");
        System.out.println("  4 = Intern");
        System.out.println("  5 = Guest\n");

        PrintQueue pq = new PrintQueue();

        // Different users submit documents
        System.out.println("--- Documents being submitted ---\n");

        PrintQueue.Document ceoReport = new PrintQueue.Document(1001, 25);
        pq.addDoc2Queue(1, ceoReport);
        System.out.println("  CEO: Quarterly Report (25 pages)");

        PrintQueue.Document internDoc = new PrintQueue.Document(1002, 2);
        pq.addDoc2Queue(4, internDoc);
        System.out.println("  Intern: Meeting Notes (2 pages)");

        PrintQueue.Document employeeDoc1 = new PrintQueue.Document(1003, 5);
        pq.addDoc2Queue(3, employeeDoc1);
        System.out.println("  Employee: Project Proposal (5 pages)");

        PrintQueue.Document managerDoc = new PrintQueue.Document(1004, 8);
        pq.addDoc2Queue(2, managerDoc);
        System.out.println("  Manager: Budget Analysis (8 pages)");

        PrintQueue.Document employeeDoc2 = new PrintQueue.Document(1005, 3);
        pq.addDoc2Queue(3, employeeDoc2);
        System.out.println("  Employee: Status Update (3 pages)");

        PrintQueue.Document guestDoc = new PrintQueue.Document(1006, 1);
        pq.addDoc2Queue(5, guestDoc);
        System.out.println("  Guest: Visitor Pass (1 page)");

        System.out.println("\n--- Current print queue ---");
        pq.listDocuments();

        System.out.println("\n--- Intern checks waiting time for their document ---");
        double internWaitTime = pq.time2print(internDoc, 2.0);
        System.out.println("Intern's document will print in: " + internWaitTime + " seconds");
        System.out.println("That's " + (internWaitTime / 60.0) + " minutes");

        System.out.println("\n--- Manager checks waiting time ---");
        double managerWaitTime = pq.time2print(managerDoc, 2.0);
        System.out.println("Manager's document will print in: " + managerWaitTime + " seconds");
        System.out.println("That's " + (managerWaitTime / 60.0) + " minutes");

        System.out.println("\n--- Starting to print documents ---\n");

        int printCount = 1;
        while (printCount <= 3 && !pq.isEmpty()) {
            System.out.println("Print job #" + printCount + ":");
            Entry<Integer, PrintQueue.Document> doc = pq.send2Printer();
            int pages = doc.getValue().getNPags();
            double printTime = pages * 2.0;
            System.out.println("  Printing time: " + printTime + " seconds (" + pages + " pages)");
            System.out.println("  Remaining documents: " + pq.size());
            System.out.println();
            printCount++;
        }

        System.out.println("--- Remaining queue ---");
        pq.listDocuments();

        System.out.println("\n✓ Simulation complete!");
        System.out.println("  Notice how higher priority documents printed first.");

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
