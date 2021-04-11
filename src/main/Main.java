package main;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String colorsPath = "./colorLog.csv";
        String itemsCountPath = "./itemsCountLog.csv";

        Parser parser = new Parser(colorsPath, itemsCountPath);
        List<BoxState> boxColors = parser.readColors();
        List<BoxState> boxItemCounts = parser.readItemsCount();

        BoxStateManager boxStateManager = new BoxStateManager(boxColors, boxItemCounts);
        boxStateManager.fillStates();

        LocalDateTime testDateTime = LocalDateTime.parse("2020-04-01T19:25:00");
        Long testID = 1L;
        int limit = 4;

        List<BoxState> foundBoxStates = boxStateManager.getBoxStates(testID, testDateTime, limit);
        boxStateManager.printBoxStates(foundBoxStates);

    }

}
