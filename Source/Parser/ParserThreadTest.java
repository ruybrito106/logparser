import static org.junit.Assert.*;
import org.junit.Test;

import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;

public class ParserThreadTest {

  private ParserThread thread;

  /*
    Set a local instance of ParserThread, which will
    be used by the tests below.
  */
  public void initParserThread() {
    LogHandler log = null;
    try {
      log = new LogHandler(new FileReader("../../Logs/input.txt"), 5);
    } catch (FileNotFoundException exception) {
      // System found exception in trying to find the file
      exception.printStackTrace();
    }
    log.parse();
    this.thread = new ParserThread(log.getData(), 0, 100);
  }

  @Test
  public void testConstructorIntegrity() {
    initParserThread();

    assertEquals(this.thread.getBucketSize(), 100);
    assertEquals(this.thread.getStartLine(), 0);
  }

  @Test
  public void testTrivialKillQueryRegex() {
    initParserThread();
    assertEquals(this.thread.isKillQuery("15:00 Exit: Timelimit hit."), false);
    assertEquals(this.thread.isKillQuery("23:06 Kill: 1022 2 22: <world> killed Isgalamido by MOD_TRIGGER_HURT"), true);
    assertEquals(this.thread.isKillQuery("16:47 ClientDisconnect: 5"), false);
    assertEquals(this.thread.isKillQuery("0:25 Kill: 2 4 6: Oootsimo killed Zeh by MOD_ROCKET"), true);
    assertEquals(this.thread.isKillQuery("0:26 Item: 2 weapon_shotgun"), false);
    assertEquals(this.thread.isKillQuery("0:49 Kill: 3 4 10: Isgalamido killed Zeh by MOD_RAILGUN"), true);
  }

  @Test
  public void testTrickyKillQueryRegex() {
    initParserThread();
    assertEquals(this.thread.isKillQuery("23:06 Kill: 1022 2 22: Kill killed Kill by killed"), true);
    assertEquals(this.thread.isKillQuery("23:06 Kill: 1022 2 22: killed killed killed by Kill"), true);
    assertEquals(this.thread.isKillQuery("1:19 ClientUserinfoChanged: 5 n\\Kill\\t\\0\\killed\\by/kill\\killed\\sarge/krusade\\g_redteam\\\\g_blueteam\\\\c1\\5\\c2\\5\\hc\\95\\w\\0\\l\\0\\tt\\0\\tl\\0"), false);
  }

  @Test
  public void testQueryProcessing() {
    initParserThread();

    Pair pair = this.thread.getData().getItem(213);
    Pair sample = new Pair("Isgalamido", pair.getB());

    this.thread.getData().getHashMap().clear();
    assertEquals(this.thread.getData().getHashMap().containsKey(sample), false);
    this.thread.processQuery(213);
    assertEquals(this.thread.getData().getHashMap().containsKey(sample), true);
  }
}
