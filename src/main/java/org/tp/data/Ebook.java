package org.tp.data;

import jakarta.persistence.*;

@Entity

@DiscriminatorValue("EBOOK")
public class Ebook extends Book {
    private String format;

    // Getters and Setters
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}