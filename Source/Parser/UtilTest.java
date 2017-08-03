import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class UtilTest {

  private Util util;

  /*
    Set a local instance of Util, which will
    be used by the tests below.
  */
  public void initUtil() {
    this.util = new Util("anyInput.txt");
  }

  @Test
  public void testConstructorIntegrity() {
    initUtil();
    assertEquals(this.util.getInput(), "anyInput.txt");
  }

  @Test
  public void testIsSorted() {
    initUtil();

    // Test non-increasingly sorted array
    List<Pair> players = new ArrayList<Pair>();
    players.add(new Pair("A", 10));
    players.add(new Pair("B", 8));
    players.add(new Pair("C", 7));
    players.add(new Pair("D", 6));
    assertEquals(this.util.is_sorted(players), true);

    // Change order
    Collections.swap(players, 0, 1);
    assertEquals(this.util.is_sorted(players), false);
  }
}
