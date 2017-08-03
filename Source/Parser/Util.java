import java.io.File;
import java.util.List;

public class Util {

  /*
    Utility class with some singleton functions
    used by multiple modules.
  */

  private String input;
  private final String logs = "/Logs/";
  private final String source = "Source";

  public Util(String input) {
    this.input = input;
  }

  /*
    Returns the directory path to the input, always
    assuming that it is located in the Logs folder.
  */
  public String getPathToInput() {
    String path = new File("").getAbsolutePath();
    int index = 0;
    while (path.substring(index, index + 6).equals(source) == false) {
      index++;
    }
    path = path.substring(0, index - 1) + logs + this.input;
    return path;
  }

  /*
    Checks if a List of players is non-increasingly sorted,
    based on the kill counter (second argument of Pair object)
  */
  public Boolean is_sorted(List<Pair> players) {
    Boolean sorted = true;
    for(int player = 0; player < players.size() - 1; player++) {
      sorted = sorted &&
        (players.get(player).getB() >= players.get(player + 1).getB());
    }
    return sorted;
  }

  public String getInput() {
    return this.input;
  }

}
