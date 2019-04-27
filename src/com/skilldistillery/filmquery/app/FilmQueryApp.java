package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
	Scanner input = new Scanner(System.in);
	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		String result = null;

		do {
			System.out.println();
			System.out.println("Would you like to: ");
			System.out.println("1. Look up a film by its id");
			System.out.println("2. Look up a film by a search keyword");
			System.out.println("3. Exit the application");

			result = input.nextLine();

			switch (result) {
			case "1":
				System.out.println("What is the id of your film?");
				result = input.nextLine();
				
				try {
					Film film = db.findFilmById(Integer.parseInt(result));
					if (film.getTitle() == null) {
						System.out.println("This film is not in our database.");
					} else {
						System.out.println(film);
						System.out.println("Would you like to view all of the details? (yes/no)");
						result = input.nextLine();
						if (result.equals("yes")) {
							System.out.println(film.printDetails());
						}
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input.");
				}
				
				break;
			case "2":
				System.out.println("What is your keyword?");
				result = input.nextLine();
				List<Film> film2 = db.findFilmByKeyword(result);
				if (film2.size() == 0) {
					System.out.println("This film is not in our database.");
				} else {
					for (Film film3 : db.findFilmByKeyword(result)) {

						System.out.println(film3);
						System.out.println();
					}
				}
				break;
			case "3":
				System.out.println("Goodbye");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid input.  Please try again");
			}

		} while (!result.equals("3"));
	}
}
