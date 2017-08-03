import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
  This is the thread class responsible for the
  processing of each query.
*/

public class ParserThread extends Thread {

  private Data data;
  private int startLine;
  private int bucketSize;
  private final String killedExtract = "killed";
  private final String byExtract = "by";
  private final String worldPlayer = "<world>";

  public ParserThread(Data data, int startLine, int bucketSize) {
    this.data = data;
    this.startLine = startLine;
    this.bucketSize = bucketSize;
  }

  /*
    Check if a string is an instance of a kill query,
    based on a regex definition.
  */
  public Boolean isKillQuery(String line) {
    return line.matches("^.*Kill:.*killed.*by.*");
  }

  /*
    Main processing method responsible for parsing a line
    from the input and extracting information about it. The
    information extracted is stored by the Data module.
  */
  public void processQuery(int itemNumber) {
    Pair pair = this.data.getItem(itemNumber);
    String line = pair.getA();
    int match = pair.getB();

    if (isKillQuery(line)) {
      String[] words = line.split(" ");
      String killerName = "", killedName = "";
      Boolean isFirst = true;

      for(int currentWord = 5; currentWord < words.length; currentWord++) {
        if (words[currentWord].equals(killedExtract)) {
          isFirst = false;
        } else if (words[currentWord].equals(byExtract)) {
          break;
        } else if (isFirst) {
          if (killerName.length() == 0) {
            killerName = words[currentWord];
          } else {
            killerName = killerName + " " + words[currentWord];
          }
        } else {
          if (killedName.length() == 0) {
            killedName = words[currentWord];
          } else {
            killedName = killedName + " " + words[currentWord];
          }
        }
      }

      // <world> is handled differently, since its kills must be added
      // to the total amount of kills but it is disregarded as a player.
      this.data.addAndModify(new Pair(killerName, match), 1);
      if (killerName.equals(worldPlayer)) {
        this.data.addAndModify(new Pair(killedName, match), -1);
      } else {
        this.data.add(new Pair(killedName, match));
      }
    }
  }

  /*
    Run method from the thread, responsible for processing
    all the queries which the thread is responsible for.
  */
  public void run() {
    int limit = this.startLine + this.bucketSize;
    for(int currentLine = this.startLine; currentLine < limit; currentLine++) {
      processQuery(currentLine);
    }
  }

  public Data getData() {
    return this.data;
  }

  public int getBucketSize() {
    return this.bucketSize;
  }

  public int getStartLine() {
    return this.startLine;
  }

}
