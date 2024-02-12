package org.example.services;

import org.example.Constants;
import org.example.entities.Element;
import org.example.entities.Group;
import org.example.entities.MultiElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GroupService {
    public static int groupCount = 0;
    private static final Set<String> uniqueStrings = new HashSet<>();
    private static final Map<Integer, ArrayList<Group>> orderedGroup = new TreeMap<>(Collections.reverseOrder());
    private static Map<Element, ArrayList<MultiElement>> primaryContainers = new HashMap<>();
    public static void filterData(String line) {
        String[] values= line.replace("\"", "").split(";", -1);
        MultiElement multiElement = new MultiElement(values);
        if (!multiElement.isSkip() && !uniqueStrings.contains(line)) {
            uniqueStrings.add(line);
            addToContainer(multiElement);
        }
    }

    public static void addToContainer(MultiElement multiElement) {
        for (var entity : multiElement.getNotEmptyEntities()) {
            ArrayList<MultiElement> v = primaryContainers.getOrDefault(entity, new ArrayList<>());
            v.add(multiElement);
            primaryContainers.put(entity, v);
        }
    }

    public static void group() {
        primaryContainers = primaryContainers.entrySet().stream()
                .filter(ent -> ent.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<MultiElement, Integer> multiEntityCount = new HashMap<>();
        for (var entry : primaryContainers.entrySet()) {
            ArrayList<MultiElement> entities = entry.getValue();
            for (var multiEntity : entities) {
                int value = multiEntityCount.getOrDefault(multiEntity, 0);
                multiEntityCount.put(multiEntity, value + 1);
            }
        }

        List<Set<MultiElement>> multiEntitySubGroup = new ArrayList<>();
        for (var entry : multiEntityCount.entrySet()) {
            if (entry.getValue() > 1) {
                Set<MultiElement> set = new HashSet<>();
                for (var entity : entry.getKey().getNotEmptyEntities()) {
                    if (primaryContainers.containsKey(entity)) {
                        set.addAll(primaryContainers.remove(entity));
                    }
                }
                if (set.size() > 1)
                    multiEntitySubGroup.add(set);
            }
        }

        for (var subGroup : multiEntitySubGroup) {
            ArrayList<Group> v = orderedGroup.getOrDefault(subGroup.size(), new ArrayList<>());
            Group group = new Group(new ArrayList<>(subGroup));
            v.add(group);
            orderedGroup.put(group.size(), v);
            groupCount++;
        }

        for (var entry : primaryContainers.entrySet()) {
            ArrayList<Group> v = orderedGroup.getOrDefault(entry.getValue().size(), new ArrayList<>());
            Group group = new Group(entry.getValue());
            v.add(group);
            orderedGroup.put(group.size(), v);
            groupCount++;
        }
    }

    public static void print() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.OUTPUT_FILEPATH))) {
            int count = 1;
            for (var entry : orderedGroup.entrySet()) {
                for (var group : entry.getValue()) {
                    writer.write("Группа " + count + "\n\n");
                    writer.write(group.toString());
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
