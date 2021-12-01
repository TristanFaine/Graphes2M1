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
    Integer[][] PLigne = null;
    Integer[][] PColonne = null;

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

            PColonne = new Integer[n - 1][m];
            PLigne = new Integer[n][m - 1];
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
            valeursLigne = ligne.split(" ");
            // Pénalités par "ligne" | voisin horizontal
            // couple : (1,1) => (1,2) par exemple
            // Suite : (1,2) => (1,3)
            // Lecture nv colonne : => (2,1) => (2,2)
            // Exemple sur clavier : couple A,Z => Z,E => E,R
            for (int j = 0; j < valeursLigne.length; j++) {
              PLigne[i][j] = Integer.valueOf(valeursLigne[j]);
            }

            i++;
            break;
          case 4:
            valeursLigne = ligne.split(" ");
            // Pénalités par "colonne" | voisin vertical
            // couple : (1,1) => (2,1) par exemple
            // Suite : (1,2) => (2,2)
            // Lecture nv colonne : => (2,1) => (3,1)
            // Exemple sur clavier : couple A,Q => Z,S => E,D
            for (int j = 0; j < valeursLigne.length; j++) {
              PColonne[i][j] = Integer.valueOf(valeursLigne[j]);
            }

            i++;
            break;
        }
      }

      // Lorsqu'on rencontre une ligne vide on change de mode
      else {
        i = 0;
        mode++; // On passe au mode suivant
      }
    }

    HashMap<String, Object> result = new HashMap<String, Object>();
    result.put("A", A);
    result.put("B", B);
    result.put("n", n);
    result.put("m", m);
    result.put("PColonne", PColonne);
    result.put("PLigne", PLigne);

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
