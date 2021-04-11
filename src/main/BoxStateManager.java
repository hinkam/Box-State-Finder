package main;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BoxStateManager {
    private List<BoxState> boxColors;
    private List<BoxState> boxItemCounts;
    private List<BoxState> boxState;

    public BoxStateManager(List<BoxState> boxColors, List<BoxState> boxItemCounts) {
        this.boxColors = boxColors;
        this.boxItemCounts = boxItemCounts;
    }

    /*
    Merges two parsed files together, after which it will sort the resulting list by date and restructure it.
    Restructuring is being carried out with the help of HashMap, which contains previous state for each found
    box ID (dateTime, color and itemsCount).
    That allows us to fill the 'null' gaps by taking necessary information from the previous box state, and also
    helps in removing duplicate entries with the same dateTime.
    At the end list is being sorted by id and dateTime for the convenience.
    */
    public void fillStates() {
        boxState = new ArrayList<>(boxColors);
        boxState.addAll(boxItemCounts);
        boxState.sort(Comparator.comparing(BoxState::getDateTime).thenComparing(BoxState::getId));

        Map<Long, Triple> lastStates = new HashMap<>();
        for (int i = 0; i < boxState.size(); i++) {
            BoxState resultLine = boxState.get(i);
            Triple lastState = lastStates.get(resultLine.getId());

            if (lastState == null) {
                lastStates.put(resultLine.getId(),
                        new Triple(resultLine.getDateTime(),
                                resultLine.getColor(),
                                resultLine.getItemsCount()));
            } else {
                if (!resultLine.getDateTime().equals(lastState.getDateTime())) {
                    if (resultLine.getColor() == null) {
                        resultLine.setColor(lastState.getColor());
                        lastState.setCount(resultLine.getItemsCount());
                        lastState.setDateTime(resultLine.getDateTime());

                    } else if (resultLine.getItemsCount() == null) {
                        resultLine.setItemsCount(lastState.getCount());
                        lastState.setColor(resultLine.getColor());
                        lastState.setDateTime(resultLine.getDateTime());

                    } else {
                        lastStates.put(resultLine.getId(),
                                new Triple(resultLine.getDateTime(),
                                        resultLine.getColor(),
                                        resultLine.getItemsCount()));
                    }
                } else {
                    if ((resultLine.getColor() == null)) {
                        boxState.get(i - 1).setItemsCount(resultLine.getItemsCount());
                        lastState.setCount(resultLine.getItemsCount());
                    } else {
                        boxState.get(i - 1).setColor(resultLine.getColor());
                        lastState.setColor(resultLine.getColor());
                    }
                    boxState.remove(i);
                    i--;
                }
            }
        }
        boxState.sort(Comparator.comparing(BoxState::getId).thenComparing(BoxState::getDateTime));
    }

    public List<BoxState> getBoxStateList() {
        return boxState;
    }

    public List<BoxState> getBoxStates(Long id, LocalDateTime before, int limit) {
        List<BoxState> foundBoxStates = boxState.stream()
                .filter(boxState -> id.equals(boxState.getId()) &&
                        (before.isAfter(boxState.getDateTime()) || before.isEqual(boxState.getDateTime())))
                .collect(Collectors.toList());

        if (foundBoxStates.isEmpty()) {
            return null;
        } else {
            if (foundBoxStates.size() > limit) {
                foundBoxStates = foundBoxStates.subList(foundBoxStates.size() - limit, foundBoxStates.size());
            }
            if (foundBoxStates.size() < limit) {
                System.out.printf("There is only %d elements that match filter\n", foundBoxStates.size());
            }
        }
        foundBoxStates.sort(Comparator.comparing(BoxState::getDateTime).reversed());
        return foundBoxStates;
    }

    public void printBoxStates(List<BoxState> boxStates) {
        if (boxStates != null) {
            System.out.printf("%-5s%-22s%-10s%-10s%n\n",
                    "ID",
                    "DateTime",
                    "Color",
                    "ItemsCount");
            for (BoxState boxState : boxStates) {
                System.out.printf("%-5d%-22s%-10s%-10d%n\n",
                        boxState.getId(),
                        boxState.getDateTime(),
                        boxState.getColor(),
                        boxState.getItemsCount());
            }
        } else {
            System.out.println("There is no elements that match filter");
        }
    }

    private static class Triple {
        private LocalDateTime dateTime;
        private String color;
        private Integer count;

        Triple(LocalDateTime dateTime, String color, Integer count) {
            this.dateTime = dateTime;
            this.color = color;
            this.count = count;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public String getColor() {
            return color;
        }

        public Integer getCount() {
            return count;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

}
