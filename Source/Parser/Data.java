import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

/*
  This module is responsible for storing data from the log
  and communicating with high-level modules, by providing them
  the data in some specific format.
*/

public class Data {

  private List<Pair> lines;
  private ConcurrentHashMap<Pair, Integer> hashMap;
  private List<MatchStatus> matches;
  int matchesAmount;

  public Data(List<Pair> lines, int matchesAmount) {
    this.lines = lines;
    this.hashMap = new ConcurrentHashMap<Pair, Integer>();
    this.matches = new ArrayList<MatchStatus>();
    this.matchesAmount = matchesAmount;
  }

  /*
    Synchronized method accessed by multiple threads and
    responsible to add a player (if absent) in the hashMap and
    increase or decrease the killer count (update parameter).
  */
  public synchronized void addAndModify(Pair player, int update) {
    if (this.hashMap.containsKey(player)) {
      this.hashMap.replace(player, this.hashMap.get(player) + update);
    } else {
      this.hashMap.put(player, update);
    }
  }

  /*
    Synchronized method accessed by multiple threads and
    responsible to add a player (if absent) in the hashMap.
  */
  public synchronized void add(Pair player) {
    this.hashMap.putIfAbsent(player, 0);
  }

  /*
    Method for parsing data from hashMap to containers (MatchStatus)
    and storing the in a List.
  */
  public void gatherData() {
    for(int match = 0; match < this.matchesAmount; match++) {
      this.matches.add(new MatchStatus());
    }

    String username;
    int kills, match;
    for (Map.Entry<Pair, Integer> query : this.hashMap.entrySet()) {
      username = query.getKey().getA();
      match = query.getKey().getB();
      kills = query.getValue();
      this.matches.get(match).addPlayer(new Pair(username, kills));
    }
  }

  /*
    Method responsible for sorting non-increasingly the players for the
    Ranking and WebRanking modules.
  */
  public void sortData() {
    for(MatchStatus match : this.matches) {
      Collections.sort(match.getPlayers(), new Comparator<Pair>() {
        @Override
        public int compare(Pair player1, Pair player2) {
            if (player1.getB() > player2.getB()) return -1;
            else if (player1.getB() < player2.getB()) return 1;
            else return 0;
        }
      });
    }
  }

  public List<MatchStatus> getMatches() {
    return this.matches;
  }

  public int getMatchesAmount() {
    return this.matchesAmount;
  }

  public Pair getItem(int itemIndex) {
    return this.lines.get(itemIndex);
  }

  public List<Pair> getLines() {
    return this.lines;
  }

  public ConcurrentHashMap<Pair, Integer> getHashMap() {
    return this.hashMap;
  }

}
