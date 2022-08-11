package com.tads.mhsf.util;

import java.util.List;

public class TableGenerator {

    public static void generate(String[] headers, List<List<String>> rows, int[] columnsLength) {
        int tableDivisorLength = 0;
        for (int index = 0; index < headers.length; index++) {
            tableDivisorLength += columnsLength[index];
        }

        System.out.println("=".repeat(tableDivisorLength));

        for (int index = 0; index < headers.length; index++) {
            String header = headers[index];
            System.out.print(header + " ".repeat(columnsLength[index] - header.length()));
        }
        System.out.println();

        for (List<String> row : rows) {
            for (int index = 0; index < row.size(); index++) {
                String data = row.get(index);
                System.out.print(data + " ".repeat(columnsLength[index] - data.length()));
            }
            System.out.println();
        }

        System.out.println("=".repeat(tableDivisorLength));
    }

}
