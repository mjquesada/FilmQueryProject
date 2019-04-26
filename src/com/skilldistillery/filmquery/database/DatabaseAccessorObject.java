package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	public DatabaseAccessorObject() {

	}
	
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public Film findFilmById(int filmId) {
		String user = "student";
		String pass = "student";
		Connection conn;
		
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql;
			
			sql = "SELECT id, first_name, last_name, email, " 
			+ "last_update FROM customer ORDER BY last_name";
			
			
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		finally {
			conn.close();
		}
		return null;
	}

	@Override
	public Actor findActorById(int actorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// TODO Auto-generated method stub
		return null;
	}

}
