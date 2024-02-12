package org.example.entities;

import java.util.Objects;

public class Element {
    private final Integer columnOrder;
    private final String number;

    public Element(Integer columnOrder, String number) {
        this.columnOrder = columnOrder;
        this.number = number;
    }

    public boolean notEmpty() {
        return !number.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Element element = (Element) o;
        return Objects.equals(columnOrder, element.columnOrder) &&
                number.equals(element.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnOrder, number);
    }

    @Override
    public String toString() {
        return "\"" + number + "\"";
    }
}