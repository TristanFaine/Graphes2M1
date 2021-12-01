package src.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

// A class used to load a txt file given a path as string
public class Lecteur {
  // Parse 4 different paragraphs from a text file given a path
  public static HashMap<String, Object> chargerDonnees(String chemin) {
    List<String> lignes = Lecteur.load(chemin);

    Integer[][] A = null;
    Integer[][] B = null;
    String[] valeursLigne = null;

    int n = 0;
    int m = 0;

    int i = 0;
    int mode = 0;

    for (String ligne : lignes) {
      if (ligne.length() > 0) {
        switch (mode) {
          case 0:
            n = Integer.valueOf(ligne.split(" ")[0]);
            m = Integer.valueOf(ligne.split(" ")[1]);

            A = new Integer[n][m];
            B = new Integer[n][m];
            // On est sur n m
            break;
          case 1:
            valeursLigne = ligne.split(" ");

            // On a pour chaque ligne ai1 à aim
            for (int j = 0; j < valeursLigne.length; j++) {
              A[i][j] = Integer.valueOf(valeursLigne[j]);
            }

            i++;
            break;
          case 2:
            valeursLigne = ligne.split(" ");

            // On a pour chaque ligne ai1 à aim
            for (int j = 0; j < valeursLigne.length; j++) {
              B[i][j] = Integer.valueOf(valeursLigne[j]);
            }

            i++;
            break;
          case 3:
            // On a pour chaque ligne ai1 à aim
            // Pénalités pour p1112 --> p1213
            // p122

            break;
          case 4:

            break;
        }
      } else {
        i = 0;
        mode++; // On passe au mode suivant
      }
    }

    HashMap<String, Object> result = new HashMap<String, Object>();
    result.put("A", A);
    result.put("B", B);
    result.put("n", n);
    result.put("m", m);

    return result;
  }

  // Load a text file given a path
  public static List<String> load(String path) {
    List<String> lines = new ArrayList<String>();

    try {
      lines = Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return lines;
  }
}
