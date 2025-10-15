package movies;

public class Movie {
    private String title;
    private int year;
    private String genre;
    private double rating;

    public Movie(String title, int year, String genre, double rating) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public double getRating() { return rating; }

    public void setTitle(String title) { this.title = title; }
    public void setYear(int year) { this.year = year; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return String.format("%s,%d,%s,%.1f", title, year, genre, rating);
    }
}
