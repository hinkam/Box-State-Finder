package test;

import main.BoxState;
import main.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

public class ParserTest {

    private Parser testParser = new Parser("./src/test/resources/testColorLog.csv",
                                        "./src/test/resources/testItemsCount.csv");

    @Test
    public void testColorsParsing() {
        List<BoxState> colors = testParser.readColors();

        assertEquals(6, colors.size());
        assertEquals(new BoxState(1, LocalDateTime.parse("2020-04-01T19:00:00"), "Black", null), colors.get(2));
    }

    @Test
    public void testItemsCountParsing(){
        List<BoxState> itemsCount = testParser.readItemsCount();

        assertEquals(5, itemsCount.size());
        assertEquals(new BoxState(2, LocalDateTime.parse("2020-04-01T12:00:50"), null, 5), itemsCount.get(1));
    }

}
