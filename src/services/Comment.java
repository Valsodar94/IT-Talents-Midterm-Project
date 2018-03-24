package services;

import java.time.LocalDate;
import java.time.LocalTime;

import enums.Cities;
import places.Place;
import userStuff.User;

public class Comment {
	private final LocalDate date;
	private final LocalTime hour;
	private Cities city;
	private String description;
	private int rating;
	private final User user;
	private final Place place;

	public Comment(int rating, User user, Place place) throws Exception {
		this.date = LocalDate.now();
		this.hour = LocalTime.now();
		setRating(rating);
		if (user != null) {
			this.user = user;
		} else {
			throw new Exception();
		}
		if (place != null) {
			this.place = place;
		} else {
			throw new Exception();
		}
	}

	public Comment(String description, int rating, User user, Place place) throws Exception {
		this.date = LocalDate.now();
		this.hour = LocalTime.now();
		setDescription(description);
		setRating(rating);
		if (user != null) {
			this.user = user;
		} else {
			throw new Exception();
		}
		if (place != null) {
			this.place = place;
		} else {
			throw new Exception();
		}
	}

	// getters and setters
	public void setDescription(String description) {
		if (description != null && description.trim().length() > 0) {
			this.description = description;
		}
	}

	public void setRating(int rating) {
		if (rating > 0 && rating < 6) {
			this.rating = rating;
		}
	}
	

	public Cities getCity() {
		return city;
	}

	public int getRating() {
		return rating;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getHour() {
		return hour;
	}

	public User getUser() {
		return user;
	}

	public Place getPlace() {
		return place;
	}

}
