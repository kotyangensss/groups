package org.example.entities;

import org.apache.commons.lang3.StringUtils;
import org.example.entities.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class MultiElement {
    private final List<Element> elements = new ArrayList<>();
    private boolean skip = false;

    public MultiElement(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            if (!strings[i].isEmpty() && !StringUtils.isNumeric(strings[i])) {
                skip = true;
                break;
            }

            elements.add(new Element(i, strings[i]));
        }
    }

    public boolean isSkip() {
        return skip;
    }

    public List<Element> getNotEmptyEntities() {
        return elements.stream().filter(Element::notEmpty).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(";");
        for (Element element : elements)
            joiner.add(element.toString());
        return joiner.toString();
    }
}