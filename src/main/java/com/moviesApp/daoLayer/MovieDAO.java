package com.moviesApp.daoLayer;

import com.moviesApp.entities.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that accesses the database and performs any activity connected with database.
 * <p>
 * This particular class deals with movie data in database.
 */
public class MovieDAO {

    /**
     * Query for counting rows found by queries
     */
    private static final String COUNT_FOUND_ROWS = "SELECT FOUND_ROWS()";

    /**
     * Query for retrieving all movies records from database
     */
    private static final String SQL_GET_ALL_MOVIES = "SELECT * FROM MOVIE";

    /**
     * Query for retrieving part of movies records specified by "LIMIT"
     */
    private static final String SQL_GET_ALL_MOVIES_WITH_LIMIT = "SELECT SQL_CALC_FOUND_ROWS * FROM MOVIE LIMIT ?, ?";

    /**
     * Query for selecting movie with given ID from database
     */
    private static final String SQL_GET_MOVIE_BY_ID = "SELECT * FROM MOVIE WHERE ID = ?";

    /**
     * Query for adding new record with movie data to database
     */
    private static final String SQL_ADD_MOVIE = "INSERT INTO MOVIE (moviename, director, releasedate, posterurl, trailerurl, rating, description) VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Query for updating movie record in database
     */
    private static final String SQL_UPDATE_MOVIE = "UPDATE MOVIE SET " +
            "moviename = ?, " +
            "director = ?, " +
            "releasedate = ?, " +
            "posterurl = ?, " +
            "trailerurl = ?, " +
            "rating = ?, " +
            "description = ? WHERE ID = ?";

    /**
     * Query for removing movie with given ID from database
     */
    private static final String SQL_DELETE_MOVIE = "DELETE FROM MOVIE WHERE ID = ?";

    /**
     * Query for retrieving movie with specified title from database
     */
    private static final String SQL_SEARCH_MOVIE_BY_TITLE = "SELECT SQL_CALC_FOUND_ROWS * FROM MOVIE WHERE moviename LIKE ? LIMIT ?, ?";

    private Integer numberOfRecords;

    /**
     * Helper method to parse result set
     *
     * @param resultSet result set to parse
     * @return Movie object with filled fields
     * @throws SQLException
     */
    private static Movie parseMovieResultSet(ResultSet resultSet) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getLong("ID"));
        movie.setMovieName(resultSet.getString("moviename"));
        movie.setDirector(resultSet.getString("director"));
        movie.setReleaseDate(resultSet.getDate("releasedate"));
        movie.setPosterURL(resultSet.getString("posterurl"));
        movie.setTrailerURL(resultSet.getString("trailerurl"));
        movie.setRating(resultSet.getDouble("rating"));
        movie.setDescription(resultSet.getString("description"));
        return movie;
    }

    /**
     * Creates a record for new movie in database
     *
     * @param movieName   desired movie title
     * @param director    movie director
     * @param releaseDate movie release date in  "yyyy-MM-dd" format
     * @param posterURL   URL leading to movie poster
     * @param trailerUrl  URL leading to embed video
     * @param rating      movie rating. Set to 0 by default
     * @param description some description for the movie
     * @return ID of created movie record in database. If record hasn't been created returns 0.
     * @throws SQLException
     */
    public Long create(String movieName, String director, Date releaseDate, String posterURL, String trailerUrl, Double rating, String description) throws SQLException {
        Long movieID = 0L;
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_MOVIE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, movieName);
            statement.setString(2, director);
            statement.setDate(3, releaseDate);
            statement.setString(4, posterURL);
            statement.setString(5, trailerUrl);
            statement.setDouble(6, rating);
            statement.setString(7, description);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    movieID = resultSet.getLong(1);
                }
            }

        }
        return movieID;
    }

    /**
     * Searches for movie with specified ID in database
     *
     * @param movieId ID of movie to be found
     * @return Movie entity object if movie with given ID is found in database. Otherwise returns null.
     * @throws SQLException
     */
    public Movie get(Long movieId) throws SQLException {
        Movie movie = null;
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_MOVIE_BY_ID)) {

            statement.setLong(1, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    movie = parseMovieResultSet(resultSet);
                }
            }

        }
        return movie;
    }

    /**
     * Updates movie data in database
     *
     * @param movie movie entity to update
     * @throws SQLException
     */
    public void update(Movie movie) throws SQLException {
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MOVIE)) {

            statement.setString(1, movie.getMovieName());
            statement.setString(2, movie.getDirector());
            statement.setDate(3, movie.getReleaseDate());
            statement.setString(4, movie.getPosterURL());
            statement.setString(5, movie.getTrailerURL());
            statement.setDouble(6, movie.getRating());
            statement.setString(7, movie.getDescription());
            statement.setLong(8, movie.getId());
            statement.executeUpdate();

        }
    }

    /**
     * Deletes movie record from database
     *
     * @param movieID ID of movie to be removed from database
     * @return <b>true</b> if movie has been successfully deleted. Otherwise returns <b>false</b>
     * @throws SQLException
     */
    public boolean delete(Long movieID) throws SQLException {
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MOVIE)) {

            statement.setLong(1, movieID);
            int afterUpdate = statement.executeUpdate();
            if (afterUpdate >= 1) {
                return true;
            }

        }
        return false;
    }

    /**
     * Returns records for all movies in database
     *
     * @return List of Movie objects if any found. Otherwise returns an empty list
     * @throws SQLException
     */
    public List<Movie> getAll() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_MOVIES)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Movie movie;
                    movie = parseMovieResultSet(resultSet);
                    movies.add(movie);
                }
            }

        }
        return movies;
    }

    /**
     * Returns some part of movies from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @return List of Movie objects in given range if any movies found. Otherwise returns empty list
     * @throws SQLException
     */
    public List<Movie> getAllLimit(Integer offset, Integer noOfRows) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_MOVIES_WITH_LIMIT)) {

            statement.setInt(1, offset);
            statement.setInt(2, noOfRows);
            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Movie movie;
                    movie = parseMovieResultSet(resultSet);
                    movies.add(movie);
                }
                resultSet.close();
                resultSet = statement.executeQuery(COUNT_FOUND_ROWS);
                if (resultSet.next()) {
                    this.numberOfRecords = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        }
        return movies;
    }

    /**
     * Gets all movies (or some part of them) with matching name from database.
     *
     * @param movieName movie name to look for
     * @param offset    starting position of select query
     * @param noOfRows  desired number of records per page
     * @return List of Movie objects in given range if any movies found. Otherwise empty list
     * @throws SQLException
     */
    public List<Movie> getMoviesLike(String movieName, Integer offset, Integer noOfRows) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SEARCH_MOVIE_BY_TITLE)) {

            statement.setString(1, movieName + "%");
            statement.setInt(2, offset);
            statement.setInt(3, noOfRows);
            ResultSet resultSet =  null;
            try {
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Movie movie;
                    movie = parseMovieResultSet(resultSet);
                    movies.add(movie);
                }
                resultSet.close();
                resultSet = statement.executeQuery(COUNT_FOUND_ROWS);
                if (resultSet.next()) {
                    this.numberOfRecords = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        }

        return movies;
    }

    /**
     * Method used in pagination. Retrieves number of records for current query
     *
     * @return number of records retireved during last query
     */
    public Integer getNumberOfRecords() {
        return this.numberOfRecords;
    }

}
