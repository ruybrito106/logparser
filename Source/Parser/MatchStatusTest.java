import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

public class MatchStatusTest {

  private MatchStatus match;

  /*
    Set a local instance of MatchStatus, which will
    be used by the tests below.
  */
  public void initMatchStatus() {
    this.match = new MatchStatus();
  }

  @Test
  public void testConstructorIntegrity() {
    initMatchStatus();

    assertEquals(this.match.getKills(), 0);
    assertEquals(this.match.getPlayers().size(), 0);
  }

  @Test
  public void testInsertionOrdinaryPlayer() {
    initMatchStatus();

    int previousKills = this.match.getKills();
    int previousSize = this.match.getPlayers().size();
    Pair pair = new Pair("A", 10);
    this.match.addPlayer(pair);
    assertEquals(this.match.getKills(), previousKills + pair.getB());
    assertEquals(this.match.getPlayers().size(), previousKills + 1);
  }

  @Test
  public void testInsertionNonOrdinaryPlayer() {
    initMatchStatus();

    int previousKills = this.match.getKills();
    int previousSize = this.match.getPlayers().size();
    int kills = 23;
    Pair pair = new Pair("<world>", kills);
    this.match.addPlayer(pair);
    assertEquals(this.match.getKills(), previousKills + 2 * (kills));
    assertEquals(this.match.getPlayers().size(), previousKills);
  }

}
