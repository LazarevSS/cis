package ru.sibintek.cis.controller;

import java.util.List;
import java.util.Objects;

public class Page<T> {
    private List<T> elements;

    private Integer recordCount;

    private Integer pageCount;

    private Integer pageNumber;

    public Page(List<T> elements, Integer recordCount, Integer pageNumber) {
        this.elements = elements;
        this.recordCount = Objects.requireNonNullElse(recordCount, 10);
        this.pageNumber = Objects.requireNonNullElse(pageNumber,1);
        pageCount = (int) Math.ceil(elements.size() / (double) this.recordCount);
    }

    public List<T> getElementsWithLimit() {
        int limit = pageNumber * recordCount;
        return elements.subList((pageNumber - 1) * recordCount, limit > elements.size() ? elements.size() : limit);
    }

    public List<T> getPmIrEntities() {
        return elements;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public List<T> getElements() {
        return elements;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }
}
