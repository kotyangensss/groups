package org.example.entities;

import java.util.List;

public class Group {
    private final List<MultiElement> group;

    public Group(List<MultiElement> group) {
        this.group = group;
    }

    public int size() {
        return group.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (MultiElement multiElement : group) {
            builder.append(multiElement).append("\n\n");
        }

        return builder.toString();
    }
}