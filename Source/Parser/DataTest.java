import static org.junit.Assert.*;
import org.junit.Test;

import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class DataTest {

  private Data data;

  /*
    Set a local instance of Data, which will
    be used by the tests below.
  */
  public void initData() {
    try {
      LogHandler log = new LogHandler(new FileReader("../../Logs/sample.txt"), 5);
      log.parse();
      data = log.getData();
    } catch (FileNotFoundException exception) {
      // System found exception in trying to find the file
      exception.printStackTrace();
    }
  }

  @Test
  public void testConstructorIntegrity() {
    initData();
    this.data.gatherData();
    assertEquals(this.data.getMatches().size(), this.data.getMatchesAmount());
    assertEquals(this.data.getItem(1).getA(), "20:54 Kill: 1022 2 22: A killed B by MOD_TRIGGER_HURT");
    assertEquals(this.data.getItem(1).getB(), 0);
    assertEquals(this.data.getHashMap().size(), 7);
  }

  @Test
  public void testAddition() {
    initData();

    //Size remains unchanged
    int size = this.data.getHashMap().size();
    this.data.add(new Pair("A", 0));
    assertEquals(this.data.getHashMap().size(), size);

    //Size changes
    this.data.add(new Pair("G", 0));
    assertEquals(this.data.getHashMap().size(), size + 1);
  }

  @Test
  public void testAdditionWithModification() {
    initData();

    //Kill increases
    Pair player = new Pair("A", 0);
    int kills = this.data.getHashMap().get(player);
    this.data.addAndModify(player, 1);
    assertEquals((int)this.data.getHashMap().get(player), (int)kills + 1);

    //Kill decreases
    kills = this.data.getHashMap().get(player);
    this.data.addAndModify(player, -1);
    assertEquals((int)this.data.getHashMap().get(player), (int)kills - 1);
  }

  @Test
  public void testGathering() {
    initData();

    // Matches array size increases after gathering
    assertEquals(this.data.getMatches().size(), 0);
    this.data.gatherData();
    assertEquals(this.data.getMatches().size(), 1);
  }

  @Test
  public void testSorting() {
    initData();

    this.data.gatherData();
    this.data.sortData();

    // Check if the data is sorted
    List<Pair> players = this.data.getMatches().get(0).getPlayers();
    assertEquals(new Util("").is_sorted(players), true);
  }

}
