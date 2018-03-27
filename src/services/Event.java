package services;

import java.time.LocalDate;

import exceptions.InvalidDateException;
import exceptions.InvalidInformationException;
import places.Place;

public class Event {
	private String title;
	private String description;
	private LocalDate date;
	private Place place;
	public Event(String title, String description, LocalDate date, Place place) throws InvalidInformationException, InvalidDateException {
		setTitle(title);
		setDescription(description);
		setDate(date);
		setPlace(place);
	}

	

	// getters and setters
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place p) throws InvalidInformationException{
		if(p!=null)
			this.place = p;
		else
			throw new InvalidInformationException("Podavash null za place na event!");
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws InvalidInformationException {
		if (Place.isValidString(title)) {
			this.title = title;
		} else {
			throw new InvalidInformationException("Please enter a valid title for the event!");
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws InvalidInformationException {
		if (Place.isValidString(description)) {
			this.description = description;
		} else {
			throw new InvalidInformationException("Please enter a valid description for the event!");
		}
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) throws InvalidDateException {
		LocalDate currentDate = LocalDate.now();
		if (this.date != null && this.date.isAfter(currentDate)) {
			this.date = date;
		} else {
			throw new InvalidDateException("Invalid event date! Date cannot be a past date.");
		}		
	}
}
