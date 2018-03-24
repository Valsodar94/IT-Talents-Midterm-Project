package services;

import java.time.LocalDateTime;

import places.Place;
import userStuff.User;
//changed LocalDate to LocalDateTime and removed hour
public class Reservation {
	private LocalDateTime dateAndTimeOfReservation;
	private int numberOfPeople;
	private int numberOfChildren;
	private String location;
	private int discount;
	private String extraOptions;
	private final User user;
	private final Place place;
	
	

	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, String location, int discount, User user, Place place) {
		this.dateAndTimeOfReservation = dateAndTimeOfReservation;
		this.numberOfPeople = numberOfPeople;
		this.location = location;
		this.discount = discount;
		this.user = user;
		this.place = place;
	}

	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, String location, int discount, String extraOptions, User user,
			Place place) {
		this.dateAndTimeOfReservation = dateAndTimeOfReservation;
		this.numberOfPeople = numberOfPeople;
		this.location = location;
		this.discount = discount;
		this.extraOptions = extraOptions;
		this.user = user;
		this.place = place;
	}

	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren, String location, int discount,
			User user, Place place) {
		this.dateAndTimeOfReservation = dateAndTimeOfReservation;
		this.numberOfPeople = numberOfPeople;
		this.numberOfChildren = numberOfChildren;
		this.location = location;
		this.discount = discount;
		this.user = user;
		this.place = place;
	}

	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren, String location, int discount,
			String extraOptions, User user, Place place) {
		this.dateAndTimeOfReservation = dateAndTimeOfReservation;
		this.numberOfPeople = numberOfPeople;
		this.numberOfChildren = numberOfChildren;
		this.location = location;
		this.discount = discount;
		this.extraOptions = extraOptions;
		this.user = user;
		this.place = place;
	}

	
	
	//getters and setters

	public LocalDateTime getDateAndTime() {
		return dateAndTimeOfReservation;
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
