import java.util.List;
import java.util.ArrayList;

/*
  This is a container class used to organize
  information about a match.
*/

public class MatchStatus {

  private List<Pair> players;
  private int totalKills;
  private final String worldPlayer = "<world>";

  public MatchStatus() {
    this.players = new ArrayList<Pair>();
    this.totalKills = 0;
  }

  /*
    Insertion of a player into a match.
  */
  public void addPlayer(Pair player) {
    if (!player.getA().equals(worldPlayer)) {
      this.players.add(player);
    } else {
      this.totalKills += player.getB();
    }
    this.totalKills += player.getB();
  }

  public List<Pair> getPlayers() {
    return this.players;
  }

  public int getKills() {
    return this.totalKills;
  }
}
