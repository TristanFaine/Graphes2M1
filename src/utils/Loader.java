package src.utils;

import java.util.ArrayList;
import java.util.List;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

// A class used to load a txt file given a path as string
public class Loader {
  // Parse 4 different paragraphs from a text file given a path
  public static String[] parseParagraphs(String path) {
    List<String> paragraphs = new ArrayList<>();
    List<String> lines = Loader.load(path);

    int i = 0;

    for (String line : lines) {
      if (line.length() > 0) {
        System.out.println(line);
      }
    }

    return null;
  }

  // Load a text file given a path
  public static List<String> load(String path) {
    List<String> lines = new ArrayList<>();

    try {
      lines = Files.readAllLines(Paths.get(path), new Charset("UTF-8", "UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return lines;
  }
}
