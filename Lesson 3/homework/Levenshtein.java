/**
 * Copyright © 2014, Oracle and/or its affiliates. All rights reserved.
 *
 * JDK 8 MOOC Lesson 3 homework
 */
package lesson3;

import java.util.*;

/**
 * Look at Wikipedia for more detail on calculating Levenshtein distances
 *
 * https://en.wikipedia.org/wiki/Levenshtein_distance
 */
public class Levenshtein {
  /**
   * Utility method to return the minimum of three integers
   * 
   * @param i0 The first integer
   * @param i1 The second integer
   * @param i2 The third integer
   * @return The minimum of the three parameters
   */
  static int min3(int i0, int i1, int i2) {
    return Math.min(i0, Math.min(i1, i2));
  }

  /**
   * Compute the Levenshtein distance between Strings stringA and stringB, 
   * respecting supplementary characters (i.e., surrogate pairs). 
   * The algorithm is the two-row technique from:
   *
   * https://en.wikipedia.org/wiki/Levenshtein_distance
   *
   * which is derived from Hjelmqvist, Sten (26 Mar 2012), Fast, memory
   * efficient Levenshtein algorithm:
   * 
   * http://www.codeproject.com/Articles/13525/Fast-memory-efficient-Levenshtein-algorithm
   *
   * @param stringA the first string, must be non-null
   * @param stringB the second string, must be non-null
   * @return the Levenshtein distance between the two strings
   * @throws NullPointerException if either string is null
   */
  static int lev(String stringA, String stringB) {
    Objects.requireNonNull(stringA);
    Objects.requireNonNull(stringB);

    // handle degenerate cases
    if (stringA.equals(stringB))
      return 0;

    if (stringA.length() == 0)
      return stringB.length();

    if (stringB.length() == 0)
      return stringA.length();

    // convert strings to code points, represented as int[]
    int[] s = stringA.codePoints().toArray();
    int[] t = stringB.codePoints().toArray();

    // create work vectors
    int[] v0 = new int[t.length + 1];
    int[] v1 = new int[t.length + 1];
    Arrays.setAll(v0, i -> i);

    for (int i = 0; i < s.length; i++) {
            // calculate v1 (current row distances) from the previous row v0
      // first element of v1 is A[i+1][0]
      // edit distance is delete (i+1) chars from s to match empty t
      v1[0] = i + 1;

      // use formula to fill in the rest of the row
      for (int j = 0; j < t.length; j++) {
        int cost = (s[i] == t[j]) ? 0 : 1;
        v1[j + 1] = min3(v1[j] + 1, v0[j + 1] + 1, v0[j] + cost);
      }

      // copy v1 (current row) to v0 (previous row) for next iteration
      Arrays.setAll(v0, j -> v1[j]);
    }

    return v1[t.length];
  }

  /**
   * Main entry point that can be used to test and demonstrate the values 
   * created for the distance between pairs of strings
   * 
   * @param args the command line arguments (unused)
   */
  public static void main(String[] args) {
    System.out.println(lev("kitten", "sitting"));
    System.out.println(lev("flaw", "lawn"));
    System.out.println(lev("gumbo", "gambol"));
    System.out.println(lev("ROBERTMELANSON", "ROBERTOMELANSON"));
  }
}
