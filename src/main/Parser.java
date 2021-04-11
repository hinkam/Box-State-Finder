package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class Parser {
    private String colorsPath;
    private String itemsCountPath;

    public Parser(String colorsPath, String itemsCountPath){
        this.colorsPath = colorsPath;
        this.itemsCountPath = itemsCountPath;
    }

    public List<BoxState> readColors() {
        List<BoxState> colorsResult = new ArrayList<>();
        try {
            List<String> colorsList = Files.readAllLines(Paths.get(colorsPath));
            for (String line : colorsList.subList(1, colorsList.size())) {
                String[] csvItems = line.split(",");
                colorsResult.add(
                        new BoxState(
                                Long.parseLong(csvItems[0]),
                                LocalDateTime.parse(csvItems[1]),
                                csvItems[2],
                                null
                        )
                );
            }
        } catch (IOException e) {
            System.out.println("File input error");
        }
        return colorsResult;
    }

    public List<BoxState> readItemsCount() {
        List<BoxState> itemsCountResult = new ArrayList<>();
        try {
            List<String> itemsCountList = Files.readAllLines(Paths.get(itemsCountPath));
            for (String line : itemsCountList.subList(1, itemsCountList.size())) {
                String[] csvItems = line.split(",");
                itemsCountResult.add(
                        new BoxState(
                                Long.parseLong(csvItems[0]),
                                LocalDateTime.parse(csvItems[1]),
                                null,
                                Integer.parseInt(csvItems[2])
                        )
                );
            }
        } catch (IOException e) {
            System.out.println("File input error");
        }
        return itemsCountResult;
    }

}
