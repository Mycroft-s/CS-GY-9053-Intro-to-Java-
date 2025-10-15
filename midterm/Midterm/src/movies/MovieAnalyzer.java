package movies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MovieAnalyzer {

    public static ArrayList<Movie> readMovies(String filename) {
        ArrayList<Movie> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                try {
                    movies.add(createMovie(line));
                } catch (MovieLineParseException ex) {
                    System.err.println("[WARN] Skip line " + lineNo + ": " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to read file: " + filename);
            e.printStackTrace();
        }
        return movies;
    }

    
    public static Movie createMovie(String inline) {
        try {
            String[] parts = inline.split(",", -1); 
            if (parts.length != 4)
                throw new MovieLineParseException("Expected 4 fields, got " + parts.length + " :: " + inline);

            String title = parts[0].trim();
            String yearStr = parts[1].trim();
            String genre = parts[2].trim();
            String ratingStr = parts[3].trim();

            int year = Integer.parseInt(yearStr);
            double rating = Double.parseDouble(ratingStr);

            if (title.isEmpty() || genre.isEmpty())
                throw new MovieLineParseException("Empty title/genre :: " + inline);

            return new Movie(title, year, genre, rating);
        } catch (NumberFormatException nfe) {
            throw new MovieLineParseException("Number format error :: " + inline, nfe);
        }
    }

    public static void analyze(ArrayList<Movie> movieList) {
        if (movieList == null || movieList.isEmpty()) {
            System.out.println("No movies to analyze.");
            return;
        }

        // Rating descending
        System.out.println("=== Sort by Rating (DESC) ===");
        movieList.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        for (Movie m : movieList) System.out.println(m);

        // Genre ascending
        System.out.println("\n=== Sort by Genre (ASC) ===");
        movieList.sort((a, b) -> a.getGenre().compareToIgnoreCase(b.getGenre()));
        for (Movie m : movieList) System.out.println(m);
    }

    public static void main(String[] args) {
    	//change the name depends on the file name
        String filename = (args != null && args.length > 0) ? args[0] : "movies_short.csv";
        ArrayList<Movie> list = readMovies(filename);
        System.out.println("Loaded movies: " + list.size());
        analyze(list);
    }
}
