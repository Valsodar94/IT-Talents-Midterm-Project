package services;

import java.time.LocalDate;

import places.Place;
import userStuff.User;

public class Reservation {
	private LocalDate date;
	private int numberOfPeople;
	private int numberOfChildren;
	private int hour;
	private String location;
	private int discount;
	private String extraOptions;
	private final User user;
	private final Place place;
	
	public Reservation(LocalDate date, int numberOfPeople, int hour, String location, int discount, User user, Place place) {
		this.date = date;
		this.numberOfPeople = numberOfPeople;
		this.hour = hour;
		this.location = location;
		this.discount = discount;
		this.user = user;
		this.place = place;
	}

	public Reservation(LocalDate date, int numberOfPeople, int hour, String location, int discount, String extraOptions, User user,
			Place place) {
		this.date = date;
		this.numberOfPeople = numberOfPeople;
		this.hour = hour;
		this.location = location;
		this.discount = discount;
		this.extraOptions = extraOptions;
		this.user = user;
		this.place = place;
	}

	public Reservation(LocalDate date, int numberOfPeople, int numberOfChildren, int hour, String location, int discount,
			User user, Place place) {
		this.date = date;
		this.numberOfPeople = numberOfPeople;
		this.numberOfChildren = numberOfChildren;
		this.hour = hour;
		this.location = location;
		this.discount = discount;
		this.user = user;
		this.place = place;
	}

	public Reservation(LocalDate date, int numberOfPeople, int numberOfChildren, int hour, String location, int discount,
			String extraOptions, User user, Place place) {
		this.date = date;
		this.numberOfPeople = numberOfPeople;
		this.numberOfChildren = numberOfChildren;
		this.hour = hour;
		this.location = location;
		this.discount = discount;
		this.extraOptions = extraOptions;
		this.user = user;
		this.place = place;
	}

	
	
	//getters and setters

	public LocalDate getDate() {
		return date;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getExtraOptions() {
		return extraOptions;
	}

	public void setExtraOptions(String extraOptions) {
		this.extraOptions = extraOptions;
	}
	
	
	
}
