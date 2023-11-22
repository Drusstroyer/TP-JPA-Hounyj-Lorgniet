package org.tp.data;

import jakarta.persistence.*;

@Entity

@DiscriminatorValue("PAPER")
public class PaperBook extends Book {
    private int pageCount;

    // Getters and Setters
    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}