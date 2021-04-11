package test;

import main.BoxState;
import main.BoxStateManager;
import main.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

public class BoxStateManagerTest {

    private Parser testParser = new Parser("./src/test/resources/testColorLog.csv",
            "./src/test/resources/testItemsCount.csv");

    private List<BoxState> boxColors = testParser.readColors();
    private List<BoxState> boxItemCounts = testParser.readItemsCount();

    private BoxStateManager testBoxStateManager = new BoxStateManager(boxColors, boxItemCounts);

    @Test
    public void testFillStates(){
        testBoxStateManager.fillStates();
        List<BoxState> testBoxStateList = testBoxStateManager.getBoxStateList();

        assertEquals(new BoxState(2, LocalDateTime.parse("2020-04-01T12:00:50"), null, 5), testBoxStateList.get(5));
    }

    @Test
    public void testGetBoxState(){
        testBoxStateManager.fillStates();

        assertEquals(new BoxState(2, LocalDateTime.parse("2020-04-01T19:25:25"), "Green", 125),
                testBoxStateManager.getBoxStates(2L, LocalDateTime.parse("2020-04-01T19:25:25"), 2).get(0));

        assertNull(testBoxStateManager.getBoxStates(2L, LocalDateTime.parse("2020-04-01T12:00:25"), 2));
    }

}
