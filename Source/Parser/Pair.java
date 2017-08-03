public class Pair{

  /*
    This is a container class used to organize
    information. This is usually used to represent
    a player, but is also used to represent a
    Pair <inputLine, matchNumber>. That's why this is
    not called Player =D.
  */

  private String objectA;
  private int objectB;

  public Pair(String objectA, int objectB) {
    this.objectA = objectA;
    this.objectB = objectB;
  }

  public void setA(String objectA) {
    this.objectA = objectA;
  }

  public String getA() {
    return this.objectA;
  }

  public void setB(int objectB) {
    this.objectB = objectB;
  }

  public int getB() {
    return this.objectB;
  }

  /*
    Both methods below are necessary for the sorting
    process made by the Ranking module. The first one
    implement a polynomial hash function, using a prime
    base B and a large prime modulo M to avoid collision.
  */
  @Override
  public int hashCode() {
    int B = 31, M = 1000000007, code = 0;
    for(int pos = 0; pos < this.objectA.length(); pos++) {
      code = ((this.objectA.charAt(pos) - 'a') + B * code) % M;
    }
    return (code * this.objectB) % M;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Pair)) return false;
    Pair pair = (Pair) obj;
    return this.objectA.equals(pair.getA()) && this.objectB == pair.getB();
  }

}
