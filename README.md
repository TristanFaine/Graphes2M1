# Graphes2M1

## Dev

```
rm ./src/*/*.class && javac src/*.java && java src/App # Pour compiler et lancer le projet sur place
```

## Compile

Compile dans un dossier build à créer auparavant avec `mkdir build` si nécessaire.

```
rm ./build/**/* # Avoir un dossier build vide
javac src/**/*.java -d build # Compiler en .class dans le dossier build
jar -cvfm ./build/BinarisationImage.jar ./MANIFEST.mf ./src/**/*.class   # Compiler en .jar
```

## Lancer le programme

```
java -jar ./build/BinarisationImage.jar <chemin-vers-jeu-de-donnees> # Lancer le programme
```
