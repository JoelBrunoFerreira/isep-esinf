package org.esinf.PQExamples;

import org.esinf.Priority_Queue.Entry;
import org.esinf.Priority_Queue.HeapPriorityQueue;

public class PrintQueue {

    //------------ Static nested Document class ------------
    public static class Document {
        private Integer id;
        private Integer npags;

        public Document(Integer i, Integer np) {
            id = i;
            npags = np;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer i) {
            id = i;
        }

        Integer getNPags() {
            return npags;
        }

        public void setNPags(Integer np) {
            npags = np;
        }

        @Override
        public boolean equals(Object otherObj) {
            if (this == otherObj) {
                return true;
            }
            if (otherObj == null || this.getClass() != otherObj.getClass()) {
                return false;
            }
            Document otherDoc = (Document) otherObj;
            return this.id.equals(otherDoc.id);
        }

        @Override
        public String toString() {
            return "Doc." + id + " (N. pags " + npags + ")";
        }
    }
    //------------ end of Static nested Document class ------------

    private HeapPriorityQueue<Integer, Document> prn;

    /**
     * Constructor - initializes an empty print queue
     */
    public PrintQueue() {
        prn = new HeapPriorityQueue<>();
    }

    /**
     * Constructor with initial documents
     * @param priorities array of priorities
     * @param documents array of documents
     */
    public PrintQueue(Integer[] priorities, Document[] documents) {
        prn = new HeapPriorityQueue<>(priorities, documents);
    }

    /**
     * Add a Document to the printing queue
     * @param p priority (lower value = higher priority)
     * @param doc document to add
     */
    public void addDoc2Queue(Integer p, Document doc) {
        prn.insert(p, doc);
        System.out.println("Document added to queue: " + doc + " with priority " + p);
    }

    /**
     * Send a Document to printer, removing it from the queue
     * @return the entry with the highest priority document
     */
    public Entry<Integer, Document> send2Printer() {
        Entry<Integer, Document> docEntry = prn.removeMin();

        if (docEntry != null) {
            System.out.println("Sending to printer: " + docEntry.getValue()
                    + " (Priority: " + docEntry.getKey() + ")");
        } else {
            System.out.println("No documents in queue to print.");
        }

        return docEntry;
    }

    /**
     * Returns the next Document in line to be printed (without removing it)
     * @return the document with highest priority, or null if queue is empty
     */
    public Document nextDoc2Print() {
        Entry<Integer, Document> next = prn.min();

        if (next != null) {
            return next.getValue();
        }

        return null;
    }

    /**
     * Returns the estimated time before the printing of a specific document starts,
     * considering that the printer takes in average 2 seconds to print each page
     * @param doc the document to find
     * @param timeslot time in seconds per page (typically 2.0)
     * @return estimated waiting time in seconds, or -1 if document not found
     */
    public double time2print(Document doc, double timeslot) {
        // Cria uma lista temporária para armazenar os documentos
        java.util.List<Entry<Integer, Document>> tempList = new java.util.ArrayList<>();
        double totalTime = 0.0;
        boolean found = false;

        // Remove documentos da fila até encontrar o documento procurado
        while (!prn.isEmpty()) {
            Entry<Integer, Document> entry = prn.removeMin();
            tempList.add(entry);

            // Verifica se é o documento procurado
            if (entry.getValue().equals(doc)) {
                found = true;
                break;
            }

            // Acumula o tempo de impressão dos documentos anteriores
            totalTime += entry.getValue().getNPags() * timeslot;
        }

        // Reinsere todos os documentos na fila
        for (Entry<Integer, Document> entry : tempList) {
            prn.insert(entry.getKey(), entry.getValue());
        }

        // Reinsere os documentos restantes (se houver)
        while (!prn.isEmpty()) {
            Entry<Integer, Document> entry = prn.removeMin();
            tempList.add(entry);
        }

        // Reinsere apenas os que não foram reinseridos no primeiro loop
        for (int i = tempList.size() - 1; i >= 0; i--) {
            if (!prn.toString().contains(tempList.get(i).getValue().toString())) {
                prn.insert(tempList.get(i).getKey(), tempList.get(i).getValue());
            }
        }

        // Reconstrói a fila completamente
        prn = new HeapPriorityQueue<>();
        for (Entry<Integer, Document> entry : tempList) {
            prn.insert(entry.getKey(), entry.getValue());
        }

        if (found) {
            return totalTime;
        } else {
            return -1.0; // Documento não encontrado
        }
    }

    /**
     * Returns the number of documents in the queue
     * @return queue size
     */
    public int size() {
        return prn.size();
    }

    /**
     * Checks if the queue is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return prn.isEmpty();
    }

    /**
     * Lists all documents in the queue
     */
    public void listDocuments() {
        if (prn.isEmpty()) {
            System.out.println("Print queue is empty.");
        } else {
            System.out.println("\n=== Current Print Queue ===");
            System.out.println(prn.toString());
        }
    }

    @Override
    public String toString() {
        return prn.toString();
    }
}