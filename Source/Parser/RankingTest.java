import java.nio.file.*;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.util.List;

public class RankingTest {

    private Ranking ranking;

    /*
      Set a local instance of Ranking, which will
      be used by the tests below.
    */
    public void initRanking() {
      this.ranking = new Ranking(5, "input.txt");
    }

    public Boolean isFile(String path) {
      return Files.isRegularFile(new File(path).toPath());
    }

    @Test
    public void testConstructorIntegrity() {
      initRanking();

      assertEquals(this.ranking.getThreadAmount(), 5);
      assertEquals(isFile(this.ranking.getInputFile()), true);
    }

    @Test
    public void testGetRanking() {
      initRanking();

      // Simulate getRanking without printing
      this.ranking.getLogHandler().parse();
      this.ranking.getLogHandler().getData().gatherData();
      this.ranking.getLogHandler().getData().sortData();

      // Check if data is sorted
      List<Pair> players;
      for(MatchStatus match : this.ranking.getLogHandler().getData().getMatches()) {
        players = match.getPlayers();
        assertEquals(new Util("").is_sorted(players), true);
      }
    }

    @Test
    public void testParametersProcessing() {
      initRanking();

      String[] args = new String[2];
      args[0] = "-123";
      args[1] = "-/home/rbb3/Desktop/logparser/Logs/sample.txt";
      Pair pair = this.ranking.processParameters(args);
      assertEquals(pair.getB(), 123);
      assertEquals(pair.getA(), args[1].substring(1, args[1].length()));
    }
}
