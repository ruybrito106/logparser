import java.util.concurrent.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogHandler {

  private FileReader file;
  private List<Pair> lines;
  private Data data;
  private int lineAmount;
  private int threadAmount;

  public LogHandler(FileReader file, int threadAmount) {
    this.file = file;
    this.lines = new ArrayList<Pair>();
    this.data = null;
    this.lineAmount = 0;
    this.threadAmount = threadAmount;

    defineMatchBoundaries();
  }

  /*
    Check if a string is an instance of an init query,
    based on a regex definition.
  */
  public Boolean isInitQuery(String line) {
    return line.matches("^\\d+:\\d+\\sInitGame:.*");
  }

  /*
    Method to define at which line each match starts
    (has a init query). This is necessary to separate the
    data from different matches.
  */
  public void defineMatchBoundaries() {
    BufferedReader buffer;
    this.lineAmount = 0;

    try {

      buffer = new BufferedReader(this.file);
      String currentLine;
      int currentMatch = -1;

      while ((currentLine = buffer.readLine()) != null) {
        if (isInitQuery(currentLine)) {
          currentMatch++;
        }

        this.lineAmount++;
        this.lines.add(new Pair(currentLine, currentMatch));
      }

      this.data = new Data(this.lines, currentMatch + 1);

    } catch(IOException exception) {
      // System found an error in the file reading process
      exception.printStackTrace();
    }
  }

  /*
    Method responsible for defining the bucket size (number of queries
    per thread). All functions related to the parsing process are
    found here.
  */
  public void parse() {
    int bucketSize = this.lineAmount / this.threadAmount;

    ParserThread[] threads = new ParserThread[this.threadAmount];
    int startLine = 0;
    for(int currentThread = 0; currentThread < this.threadAmount; currentThread++) {
      if (currentThread == this.threadAmount - 1) {
        threads[currentThread] = new ParserThread(this.data, startLine, this.lineAmount - startLine);
      } else {
        threads[currentThread] = new ParserThread(this.data, startLine, bucketSize);
      }
      threads[currentThread].start();
      startLine += bucketSize;
    }

    for(ParserThread thread : threads) {
      try {
        thread.join();
      } catch(InterruptedException exception) {
        // System found a problem (interrupetion) in some thread
        exception.printStackTrace();
      }
    }
  }

  public Data getData() {
    return this.data;
  }

  public int getLineAmount() {
    return this.lineAmount;
  }

  public int getThreadAmount() {
    return this.threadAmount;
  }

  public List<Pair> getLines() {
    return this.lines;
  }

  public FileReader getFileReader() {
    return this.file;
  }

}
