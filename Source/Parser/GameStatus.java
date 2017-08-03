import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
  This module is responsible for communicating with the
  Data and LogHandler modules. All functions related to
  GameStatus modules are found here.
*/

public class GameStatus {

  private LogHandler log;
  private int threadAmount;
  private String inputFile;
  private static final String defaultPath = "input.txt";

  public GameStatus(int threadAmount, String inputFile) {
    this.threadAmount = threadAmount;
    this.inputFile = inputFile;
    try {

      if (inputFile.equals("input.txt")) {
        this.inputFile = new Util(inputFile).getPathToInput();
      } else {
        this.inputFile = inputFile;
      }

      FileReader file = new FileReader(this.inputFile);
      this.log = new LogHandler(file, this.threadAmount);

    } catch (FileNotFoundException exception) {
      // System found exception in trying to find the file
      exception.printStackTrace();
    }
  }

  /*
    Communicate with the LogHandler to parse the data from
    the log file to the Data module and organize the data there.
  */
  public void getStatus() {
    this.log.parse();
    this.log.getData().gatherData();
    printStatus();
  }

  /*
    Prints the data in the format was specified by the
    handsOn guidelines.
  */
  public void printStatus() {
    int matchNumber = 1;
    for(MatchStatus match : this.log.getData().getMatches()) {
      System.out.println("game_" + matchNumber + ": {");
      System.out.println("    total_kills: " + match.getKills());
      System.out.print("    players: [");
      Boolean first = true;
      for(Pair player : match.getPlayers()) {
        if (!first) {
          System.out.print(", \"" + player.getA() + "\"");
        } else {
          System.out.print("\"" + player.getA() + "\"");
          first = !first;
        }
      }
      System.out.println("]");
      System.out.println("    kills: {");
      first = true;
      for(Pair player : match.getPlayers()) {
        if (!first) {
          System.out.print(",\n        \"" + player.getA() + "\": " + player.getB());
        } else {
          System.out.print("        \"" + player.getA() + "\": " + player.getB());
          first = !first;
        }
      }
      System.out.println("\n    }\n}\n");
      matchNumber++;
    }
  }

  /*
    Check whether the parameters have been passed,
    if so used them, otherwise use default.
  */
  public static Pair processParameters(String[] args) {

    int threads = 5;
    String input = defaultPath;

    int size = args.length;
    if (size > 0 && args[0].length() > 1) {
      if (args[0].charAt(0) == '-') {
        threads = Integer.parseInt(args[0].substring(1, args[0].length()));
      }

      if (size > 1 && args[1].length() > 1) {
        if (args[1].charAt(0) == '-') {
          input = args[1].substring(1, args[1].length());
        }
      }
    }

    return new Pair(input, threads);

  }

  public LogHandler getLogHandler() {
    return this.log;
  }

  public int getThreadAmount() {
    return this.threadAmount;
  }

  public String getInputFile() {
    return this.inputFile;
  }

  public static void main(String[] args) {
    Pair pair = processParameters(args);
    GameStatus status = new GameStatus(pair.getB(), pair.getA());
    status.getStatus();
  }

}
