package main;

import java.time.LocalDateTime;
import java.util.Objects;

public class BoxState {
    private long id;
    private LocalDateTime dateTime;
    private String color;
    private Integer itemsCount;

    public BoxState(long id, LocalDateTime dateTime, String color, Integer itemsCount){
        this.id = id;
        this.dateTime = dateTime;
        this.color = color;
        this.itemsCount = itemsCount;
    }

    @Override // Added for convenience
    public String toString() {
        return "\n{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", color='" + color + '\'' +
                ", itemsCount=" + itemsCount +
                "}";
    }

    @Override // Added for tests
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoxState boxState = (BoxState) o;
        return id == boxState.id &&
                dateTime.equals(boxState.dateTime) &&
                Objects.equals(color, boxState.color) &&
                Objects.equals(itemsCount, boxState.itemsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, color, itemsCount);
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getColor() {
        return color;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

}
