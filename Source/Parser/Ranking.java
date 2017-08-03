import java.io.FileReader;
import java.io.FileNotFoundException;

/*
  This module is responsible for communicating with the
  Data and LogHandler modules and handling ranking data.
*/

public class Ranking {

  private LogHandler log;
  private int threadAmount;
  private String inputFile;
  private static final String defaultPath = "input.txt";

  public Ranking(int threadAmount, String inputFile) {
    this.threadAmount = threadAmount;
    this.inputFile = inputFile;
    try {

      String path;
      if (inputFile.equals("input.txt")) {
        path = new Util(inputFile).getPathToInput();
      } else {
        path = inputFile;
      }
      this.inputFile = path;
      FileReader file = new FileReader(this.inputFile);
      this.log = new LogHandler(file, this.threadAmount);

    } catch (FileNotFoundException exception) {
      // System found exception in trying to find the file
      exception.printStackTrace();
    }
  }

  /*
    Main method of the module. Communicate with the LogHandler
    to parse the data from the log file to the Data module,
    organizing and sorting the data there.
  */
  public void getRanking() {
    this.log.parse();
    this.log.getData().gatherData();
    this.log.getData().sortData();
    printRanking();
  }

  /*
    Prints the data in the output path defined by the bash script.
    The format has not been specified by the handsOn guidelines,
    but I assumed it was something alike the GameStatus format.
  */
  public void printRanking() {
    int matchNumber = 1;
    for(MatchStatus match : this.log.getData().getMatches()) {
      System.out.println("game_" + matchNumber + ": {");
      Boolean first = true;
      int position = 1;
      for(Pair player : match.getPlayers()) {
        if (!first) {
          System.out.println(",\n    "+position+": { ");
        } else {
          System.out.print("    "+position+": {");
        }
        System.out.println(" username: \""+player.getA()+"\", kills: "+player.getB()+" }");
        position++;
      }
      System.out.println("}\n");
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
    Ranking ranking = new Ranking(pair.getB(), pair.getA());
    ranking.getRanking();
  }

}
