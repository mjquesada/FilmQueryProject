package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Category;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	public DatabaseAccessorObject() {

	}

	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql;

			sql = "SELECT film.id, title, description, rating, release_year, language.name\n" + "FROM film\n"
					+ "JOIN language\n" + "ON film.language_id = language.id\n" + "WHERE  film.id = ?;";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			film = new Film();

			if (rs.next()) {
				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setRelease_year(rs.getInt("release_year"));
				film.setLanguage(rs.getString("language.name"));
				film.setRating(rs.getString("rating"));
			}

			film.setListOfActors(findActorsByFilmId(film.getId()));
			film.setListofCategories(findActorsByCategoryId(film.getId()));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return film;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		Actor actor = null;
		String user = "student";
		String pass = "student";
		Connection conn = null;
		List<Actor> listActor = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql;

			sql = "SELECT actor.id, first_name, " + "last_name FROM actor "
					+ "JOIN film_actor ON actor.id = film_actor.actor_id "
					+ "JOIN film ON film_actor.film_id = film.id " + "WHERE film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				actor = new Actor();
				actor.setId(rs.getInt("actor.id"));
				actor.setFirst_name(rs.getString("first_name"));
				actor.setLast_name(rs.getString("last_name"));

				listActor.add(actor);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return listActor;
	}

	public List<Film> findFilmByKeyword(String keyword) {
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = null;
		List<Film> anotherFilmList = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql;

			sql = "SELECT film.id, title, description, rating, release_year, language.name\n" + "FROM film\n"
					+ "JOIN language\n" + "ON film.language_id = language.id\n" + "WHERE  film.title LIKE ?"
					+ "OR  film.description LIKE ?;";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				film = new Film();
				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setRelease_year(rs.getInt("release_year"));
				film.setLanguage(rs.getString("language.name"));
				film.setRating(rs.getString("rating"));
				film.setListOfActors(findActorsByFilmId(film.getId()));
				anotherFilmList.add(film);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return anotherFilmList;
	}
	
	public List<Category> findActorsByCategoryId(int filmId) {
		Category category = null;
		String user = "student";
		String pass = "student";
		Connection conn = null;
		List<Category> listOfCategories = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql;

			sql = "SELECT category.id, category.name\n" + 
					"FROM category\n" + 
					"JOIN film_category ON category.id = film_category.category_id\n" + 
					"JOIN film ON film_category.film_id = film.id\n" + 
					"WHERE film.id = ?;";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				category = new Category();
				category.setId(rs.getInt("category.id"));
				category.setName(rs.getString("name"));

				listOfCategories.add(category);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return listOfCategories;
	}



	

}
