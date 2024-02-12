package org.example;

import java.io.*;

import static org.example.services.GroupService.*;

public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.INPUT_FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                filterData(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        group();
        print();

        System.out.println("Number of groups: " + groupCount);
        System.out.println(((System.currentTimeMillis() - start) / 1000f) + " seconds");
    }
}