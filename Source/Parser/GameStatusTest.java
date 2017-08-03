import java.nio.file.*;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;

public class GameStatusTest {

    private GameStatus gameStatus;

    /*
      Set a local instance of GameStatus, which will
      be used by the tests below.
    */
    public void initGameStatus() {
      this.gameStatus = new GameStatus(5, "input.txt");
    }

    public Boolean isFile(String path) {
      return Files.isRegularFile(new File(path).toPath());
    }

    @Test
    public void testConstructorIntegrity() {
      initGameStatus();

      assertEquals(this.gameStatus.getThreadAmount(), 5);
      assertEquals(isFile(this.gameStatus.getInputFile()), true);
    }

    @Test
    public void testGetStatus() {
      initGameStatus();

      // Data unorganized
      assertEquals(this.gameStatus.getLogHandler().getData().getMatches().size(), 0);

      // Simulate getStatus without printing
      this.gameStatus.getLogHandler().parse();
      this.gameStatus.getLogHandler().getData().gatherData();

      assertEquals(this.gameStatus.getLogHandler().getData().getMatches().size(), 21);
    }

    @Test
    public void testParametersProcessing() {
      initGameStatus();

      String[] args = new String[2];
      args[0] = "-1000";
      args[1] = "-input.txt";
      Pair pair = this.gameStatus.processParameters(args);
      assertEquals(pair.getB(), 1000);
      assertEquals(pair.getA(), "input.txt");
    }
}
