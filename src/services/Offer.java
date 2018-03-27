package services;

import java.time.LocalDate;

import exceptions.InvalidDateException;
import exceptions.InvalidInformationException;
import places.Place;

public class Offer {
	private String title;
	private String description;
	private String termsAndConditions;
	private LocalDate startDate;
	private LocalDate endDate;
	private Place place;

	public Offer(String title, String description, String termsAndConditions, LocalDate startDate, LocalDate endDate, Place place) throws InvalidInformationException, InvalidDateException {
		setTitle(title);
		setDescription(description);
		setTermsAndConditions(termsAndConditions);
		setStartDate(startDate);
		setEndDate(endDate);
		setPlace(place);
	}

	

	// getters and setters
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) throws InvalidInformationException{
		if(place!=null)
			this.place = place;
		else
			throw new InvalidInformationException("Podavash null za place na oferta.");
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws InvalidInformationException {
		if (Place.isValidString(title)) {
			this.title = title;
		} else {
			throw new InvalidInformationException("Please enter a valid title for the offer!");
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws InvalidInformationException {
		if (Place.isValidString(description)) {
			this.description = description;
		} else {
			throw new InvalidInformationException("Please enter a valid description for the offer!");
		}
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) throws InvalidInformationException {
		if (Place.isValidString(termsAndConditions)) {
			this.termsAndConditions = termsAndConditions;
		} else {
			throw new InvalidInformationException("Please enter valid termsAndConditions!");
		}
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) throws InvalidDateException {
		LocalDate currentDate = LocalDate.now();
		if (startDate != null && startDate.isAfter(currentDate)) {
			this.startDate = startDate;
		} else {
			throw new InvalidDateException("Invalid start date! Date cannot be a past date.");
		}
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) throws InvalidDateException {
		LocalDate currentDate = LocalDate.now();
		if (endDate != null && endDate.isAfter(currentDate)) {
			this.endDate = endDate;
		} else {
			throw new InvalidDateException("Invalid end date! Date cannot be a past date.");
		}
	}

}
