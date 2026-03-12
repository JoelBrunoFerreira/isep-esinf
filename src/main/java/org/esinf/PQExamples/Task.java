package org.esinf.PQExamples;

import java.time.LocalDateTime;

class Task {

    protected String description;
    protected String category;
    protected LocalDateTime creationDate; // Creation date field

    public Task(String description, String category) {
        this.description = description;
        this.category = category;
        this.creationDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Task: " + description + " | Category: " + category + " | Date: " + creationDate;
    }
}
