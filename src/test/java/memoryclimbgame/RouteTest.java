package memoryclimbgame;

import memoryclimbgame.route.Route;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteTest {
    @Test
    void addHoldIncreasesLength() {
        Route route = new Route();
        Hold hold = new Hold(0, 0);

        route.addHold(hold);

        assertEquals(1, route.length());
    }
    @Test
    void addHold_preservesOrder() {
        Route route = new Route();
        Hold hold1 = new Hold(0, 0);
        Hold hold2 = new Hold(0, 1);
        Hold hold3 = new Hold(1, 1);

        route.addHold(hold1);
        route.addHold(hold2);
        route.addHold(hold3);

        assertEquals(hold1, route.getHoldAt(0));
        assertEquals(hold2, route.getHoldAt(1));
        assertEquals(hold3, route.getHoldAt(2));
    }
}
