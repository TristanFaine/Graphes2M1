package src.utils;

import java.util.ArrayList;
import java.util.List;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

// A class used to load a txt file given a path as string
public class Lecteur {
  // Parse 4 different paragraphs from a text file given a path
  public static String[] parseParagraphs(String chemin) {
    List<String> paragraphes = new ArrayList<>();
    List<String> lignes = Lecteur.load(chemin);

    int i = 0;

    for (String ligne : lignes) {
      if (ligne.length() > 0) {
        System.out.println(ligne);
      }
    }

    return null;
  }

  // Load a text file given a path
  public static List<String> load(String path) {
    List<String> lines = new ArrayList<>();

    try {
      lines = Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return lines;
  }
}
