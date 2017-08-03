import static org.junit.Assert.*;
import org.junit.Test;

import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;

public class LogHandlerTest {

  private LogHandler log;

  /*
    Set a local instance of LogHandler, which will
    be used by the tests below.
  */
  public void initLog() {
    try {
      this.log = new LogHandler(new FileReader("../../Logs/sample.txt"), 5);
    } catch (FileNotFoundException exception) {
      // System found exception in trying to find the file
      exception.printStackTrace();
    }
  }

  @Test
  public void testConstructorIntegrity() {
    initLog();
    assertEquals(this.log.getThreadAmount(), 5);
    assertEquals(this.log.getLineAmount(), 7);
    assertNotNull((Object) this.log.getData());
    assertNotNull((Object) this.log.getFileReader());
    assertEquals(this.log.getLines().size(), this.log.getLineAmount());
  }

  @Test
  public void testTrivialInitQueryRegex() {
    initLog();
    assertEquals(this.log.isInitQuery("0:00 InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0"), true);
    assertEquals(this.log.isInitQuery("0:14 Item: 2 item_armor_body"), false);
    assertEquals(this.log.isInitQuery("0:49 Kill: 3 4 10: Isgalamido killed Zeh by MOD_RAILGUN"), false);
    assertEquals(this.log.isInitQuery("54:21 ShutdownGame:"), false);
    assertEquals(this.log.isInitQuery("0:00 InitGame: \\capturelimit\\8\\g_maxGameClients\\0\\timelimit\\15\\fraglimit\\20\\dmflags\\0\\sv_allowDownload\\0\\sv_maxclients\\16\\sv_privateClients\\2\\g_gametype\\0\\sv_hostname\\Code Miner Server\\sv_minRate\\0\\sv_maxRate\\10000\\sv_minPing\\0\\sv_maxPing\\0\\sv_floodProtect\\1\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0"), true);
  }

  @Test
  public void testTrickyInitQueryRegex() {
    initLog();
    assertEquals(this.log.isInitQuery("0:25 Kill: 2 4 6: InitGame killed afasf by MOD_ROCKET"), false);
    assertEquals(this.log.isInitQuery("0:25 Kill: 2 4 6: InitGame killed InitGame by InitGame"), false);
  }

}
