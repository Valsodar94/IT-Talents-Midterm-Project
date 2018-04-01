package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import exceptions.InvalidDateException;
import exceptions.InvalidInformationException;
import places.Place;
import userStuff.User;

public class Reservation {
	private boolean hasLeftComment = false;
	private String reservationID;
	private LocalDateTime dateAndTimeOfReservation;
	private int numberOfPeople;
	private int numberOfChildren;
	private String locationPref;
	private String extraOptions;
	private User user;
	private final Place place;
	private Offer offer;
	private Event event;

	//without extraOptions
	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place)
			throws InvalidInformationException, InvalidDateException {

		this.reservationID = generateReservationID();
		setDateAndTime(dateAndTimeOfReservation);
		setNumberOfPeople(numberOfPeople);
		setNumberOfChildren(numberOfChildren);
		setlocationPref(locationPref);
		if (user != null) {
			this.user = user;
		} else {
			throw new InvalidInformationException("Invalid user!");
		}
		if (place != null) {
			this.place = place;
		} else {
			throw new InvalidInformationException("Invalid place!");
		}
	}

	//with ExtraOptions
	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place)
			throws InvalidInformationException, InvalidDateException {
		this(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, user, place);
		setExtraOptions(extraOptions);
	}
	//without ExtraOptions, with event
	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place, Event event)
			throws InvalidInformationException, InvalidDateException {
		this(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, user, place);
		setEvent(event);
	}
	//with ExtraOptions, with event
	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place, Event event)
			throws InvalidInformationException, InvalidDateException {
		this(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, extraOptions, user,
				place);
		setEvent(event);
	}
	//without ExtraOptions, with offer
	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place, Offer offer)
			throws InvalidInformationException, InvalidDateException {
		this(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, user, place);
		setOffer(offer);
	}
	//with ExtraOptions, with offer
	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place, Offer offer)
			throws InvalidInformationException, InvalidDateException {
		this(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, extraOptions, user,
				place);
		setOffer(offer);
	}
	
	
	
	

	public String generateReservationID() {

		UUID uuid = UUID.randomUUID();
		String s = Long.toString(uuid.getMostSignificantBits(), 32);
		s = s.replace('-', 'R');

		return s;
	}

	public void listReservationInfo() {
		System.out.println("Reservation:");
		System.out.println("Date: " + this.dateAndTimeOfReservation.toLocalDate());
		System.out.println("Adults: " + this.numberOfPeople);
		System.out.println("Children: " + this.numberOfChildren);
		System.out.println("Time: " + this.dateAndTimeOfReservation.toLocalTime());
		System.out.println("Reservation number: " + this.reservationID);
		System.out.println("Place: " + this.locationPref);
		if (this.extraOptions != null) {
			System.out.println("I would like to be " + this.extraOptions);
		}
		if (this.event != null) {
			System.out.println("Event: " + this.event.getTitle());
		}
		if (this.offer != null) {
			System.out.println("Offer: " + this.offer.getTitle());
		}
	}

	// getters and setters

	public LocalDate getDate() {
		return dateAndTimeOfReservation.toLocalDate();
	}
	public LocalTime getTime() {
		return dateAndTimeOfReservation.toLocalTime();
	}
	public LocalDateTime getDateAndTime() {
		return dateAndTimeOfReservation;
	}

	public void setDateAndTime(LocalDateTime dateAndTimeOfReservation) throws InvalidDateException {
		if (dateAndTimeOfReservation != null) {
			LocalDateTime currentDateTime = LocalDateTime.now();
			LocalTime timeOfReservation = dateAndTimeOfReservation.toLocalTime();
			if (dateAndTimeOfReservation.isAfter(currentDateTime)
					&& timeOfReservation.isAfter(this.place.getStartHour())
					&& timeOfReservation.isBefore((this.place.getCloseHour()))) {
				this.dateAndTimeOfReservation = dateAndTimeOfReservation;
			}
		} else {
			throw new InvalidDateException("Invalid date or time of reservation");
		}
	}

	private void setEvent(Event event) throws InvalidInformationException {
		if (event != null) {
			this.event = event;
		} else {
			throw new InvalidInformationException("There is no valid event!");
		}
	}

	public Event getEvent() {
		return event;
	}

	private void setOffer(Offer offer) throws InvalidInformationException {
		if (offer != null) {
			this.offer = offer;
		} else {
			throw new InvalidInformationException("There is no valid offer!");
		}
	}

	public Offer getOffer() {
		return offer;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) throws InvalidInformationException {
		if (numberOfPeople > 0) {
			this.numberOfPeople = numberOfPeople;
		} else {
			throw new InvalidInformationException("Number of people must be positive!");
		}
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) throws InvalidInformationException {
		if (numberOfChildren > 0) {
			this.numberOfChildren = numberOfChildren;
		} else {
			throw new InvalidInformationException("Number of children must be positive!");
		}
	}

	public String getLocationPref() {
		return locationPref;
	}

	public void setlocationPref(String locationPref) throws InvalidInformationException {
		if (Place.isValidString(locationPref)) {
			this.locationPref = locationPref;
		} else {
			throw new InvalidInformationException("Please enter a valid location preference!");
		}
	}


	public String getExtraOptions() {
		return extraOptions;
	}

	public void setExtraOptions(String extraOptions) throws InvalidInformationException {
		if (Place.isValidString(extraOptions)) {
			this.extraOptions = extraOptions;
		} else {
			throw new InvalidInformationException("Please enter valid extra options!");
		}
	}

	public String getReservationID() {
		return reservationID;
	}

	public User getUser() {
		return user;
	}

	public Place getPlace() {
		return place;
	}

	public boolean isHasLeftComment() {
		return hasLeftComment;
	}

	public void setHasLeftComment(boolean hasLeftComment) {
		this.hasLeftComment = hasLeftComment;
	}

	
}
