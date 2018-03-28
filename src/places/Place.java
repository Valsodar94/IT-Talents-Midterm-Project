package places;

import java.time.LocalDateTime;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import enums.ExtraReservationOptions;
import exceptions.InvalidInformationException;
import services.Comment;
import services.Reservation;
import userStuff.UserAdministration;

public class Place {
	private String name;
	private String address;
	private String emailAddress;
	private final LocalDateTime dateAndTimeOfRegistration;
	private int avgRating;
	// for restaurant - true, for club - false
	private boolean isRestaurant;
	// kitchen for restaurant, music for club
	private String characteristicOfPlace;
	private String city;
	private String region;
	private String avgCost;
	private String description;
	private String extraOptions;
	private String paymentMethods;
	private String extras;
	private LocalTime startHour;
	private LocalTime closeHour;
	private int maxCapacity;
	private int currentCapacity = maxCapacity;

	private SortedMap<LocalDateTime, Reservation> reservations;
	private List<Comment> comments;
	private List<Integer> ratings;
	private List<String> locationPrefs;
	private List<ExtraReservationOptions> extraReservationOptions;

	public Place(String name, String address, String emailAddress, boolean isRestaurant, String characteristicOfPlace,
			String city, String region, String avgCost, LocalTime startHour, LocalTime closeHour, int maxCapacity)
			throws InvalidInformationException {
		setName(name);
		setAddress(address);
		setEmailAddress(emailAddress);
		this.isRestaurant = isRestaurant;
		setCharacteristicOfPlace(characteristicOfPlace);
		setCity(city);
		setRegion(region);
		dateAndTimeOfRegistration = LocalDateTime.now();
		setAvgCost(avgCost);
		setStartHour(startHour);
		setCloseHour(closeHour);
		setMaxCapacity(maxCapacity);

		reservations = new TreeMap<LocalDateTime, Reservation>();
		comments = new ArrayList<Comment>();
		ratings = new ArrayList<Integer>();
		locationPrefs = new ArrayList<>();
		extraReservationOptions = new ArrayList<ExtraReservationOptions>();

	}

	public boolean hasAvailableSeats(int number) throws InvalidInformationException {
		if (number > 0) {
			if (this.currentCapacity > number) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new InvalidInformationException("Invalid number of people!");
		}
	}

	public static boolean isValidString(String string) {
		if (string != null && string.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public void addReservation(Reservation reservation) throws InvalidInformationException {
		if (reservation != null) {
			this.reservations.put(reservation.getDateAndTime(), reservation);
		} else {
			throw new InvalidInformationException("Invalid reservation!");
		}
	}

	public void cancelReservation(Reservation reservation, LocalDateTime date) throws InvalidInformationException {
		if (reservation != null && date != null) {
			if (this.reservations.containsKey(date)) {
				for (Entry<LocalDateTime, Reservation> entry : this.reservations.entrySet()) {
					String id = entry.getValue().getReservationID();
					if (date.equals(entry.getKey()) && reservation.getReservationID().equals(id)) {
						this.reservations.remove(entry.getKey(), id);
					} else {
						throw new InvalidInformationException("No reservation with this id!");
					}
				}
			} else {
				throw new InvalidInformationException("No reservations on this date!");
			}
		} else {
			throw new InvalidInformationException("Invalid reservation details!");
		}
	}

	public void addExtraReservationOptions(ExtraReservationOptions extraReservationOption)
			throws InvalidInformationException {
		if (extraReservationOption != null) {
			this.extraReservationOptions.add(extraReservationOption);
		} else {
			throw new InvalidInformationException("The option you entered is not correct!");
		}
	}

	public void removeExtraReservationOptions(ExtraReservationOptions extraReservationOption)
			throws InvalidInformationException {
		if (extraReservationOption != null) {
			this.extraReservationOptions.remove(extraReservationOption);
		} else {
			throw new InvalidInformationException("The option you entered to delete is not correct!");
		}
	}

	public void addLocationPref(String locationPref) throws InvalidInformationException {
		if (locationPref != null) {
			this.locationPrefs.add(locationPref);
		} else {
			throw new InvalidInformationException("The location you entered is not correct!");
		}
	}

	public void removeLocationPref(String locationPref) throws InvalidInformationException {
		if (locationPref != null) {
			this.locationPrefs.remove(locationPref);
		} else {
			throw new InvalidInformationException("The location you entered to delete is not correct!");
		}
	}

	public void addComment(Comment comment) throws InvalidInformationException {
		if (comment != null) {
			this.comments.add(comment);
			int rating = comment.getRating();
			this.ratings.add(rating);
			this.avgRating = averageRating();
		} else {
			throw new InvalidInformationException("The comment you entered is empty! Please, enter valid comment!");
		}
	}

	public int averageRating() {
		int sum = 0;
		int rateCount = ratings.size();
		for (Integer rate : ratings) {
			sum = sum + rate;
		}
		return sum / rateCount;
	}

	// getters and setters
	public boolean isRestaurant() {
		return isRestaurant;
	}

	public String getCharacteristicOfPlace() {
		return characteristicOfPlace;
	}

	public void setCharacteristicOfPlace(String characteristicOfPlace) throws InvalidInformationException {
		if (isValidString(characteristicOfPlace)) {
			this.characteristicOfPlace = characteristicOfPlace;
		} else {
			throw new InvalidInformationException("Please enter valid characteristic of the place");
		}
	}

	private void setCity(String city) throws InvalidInformationException {
		if (Place.isValidString(city)) {
			this.city = city;
		} else {
			throw new InvalidInformationException("Please enter a valid city name");
		}
		// Should add it to the collection with the cities!
	}

	public String getCity() {
		return city;
	}

	public String getRegion() {
		return region;
	}

	private void setRegion(String region) throws InvalidInformationException {
		if (Place.isValidString(region)) {
			this.region = region;
		} else {
			throw new InvalidInformationException("Please enter a valid region");
		}
		// Should add it to the regions of the city.
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws InvalidInformationException {
		if (Place.isValidString(name)) {
			this.name = name;
		} else {
			throw new InvalidInformationException("Please, enter a valid name!");
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) throws InvalidInformationException {
		if (Place.isValidString(address)) {
			this.address = address;
		} else {
			throw new InvalidInformationException("Please, enter a valid address!");
		}
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	// Trqbva nova validaciq za email, tazi v clasa userAdministration e prispobena
	// za user
	public void setEmailAddress(String emailAddress) throws InvalidInformationException {
		if (UserAdministration.checkForValidEMail(emailAddress)) {
			this.emailAddress = emailAddress;
		} else {
			throw new InvalidInformationException("E-mail address is not correct!");
		}
	}

	public int getAvgRating() {
		return avgRating;
	}

	public String getAvgCost() {
		return avgCost;
	}

	public void setAvgCost(String avgCost) throws InvalidInformationException {
		if (Place.isValidString(avgCost)) {
			this.avgCost = avgCost;
		} else {
			throw new InvalidInformationException("Please, enter a valid average cost!");
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws InvalidInformationException {
		if (Place.isValidString(description)) {
			this.description = description;
		} else {
			throw new InvalidInformationException("Please, enter a valid description!");
		}
	}

	public String getExtraOptions() {
		return extraOptions;
	}

	public void setExtraOptions(String extraOptions) throws InvalidInformationException {
		if (Place.isValidString(extraOptions)) {
			this.extraOptions = extraOptions;
		} else {
			throw new InvalidInformationException("Please, enter valid extra options!");
		}
	}

	public String getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(String paymentMethods) throws InvalidInformationException {
		if (Place.isValidString(paymentMethods)) {
			this.paymentMethods = paymentMethods;
		} else {
			throw new InvalidInformationException("Please, enter valid payment methods!");
		}
	}

	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) throws InvalidInformationException {
		if (Place.isValidString(extras)) {
			this.extras = extras;
		} else {
			throw new InvalidInformationException("Please, enter a valid description of extras!");
		}
	}

	public LocalTime getStartHour() {
		return this.startHour;
	}

	public void setStartHour(LocalTime startHour) throws InvalidInformationException {
		if (startHour != null) {
			this.startHour = startHour;
		} else {
			throw new InvalidInformationException("Please, enter a valid start hour for the working day!");
		}
	}

	public LocalTime getCloseHour() {
		return this.closeHour;
	}

	public void setCloseHour(LocalTime closeHour) throws InvalidInformationException {
		if (closeHour != null) {
			this.closeHour = closeHour;
		} else {
			throw new InvalidInformationException("Please, enter a valid close hour for the working day!");
		}
	}

	public int getCapacity() {
		return currentCapacity;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) throws InvalidInformationException {
		if (maxCapacity > 0) {
			this.maxCapacity = maxCapacity;
		} else {
			throw new InvalidInformationException("Please, enter valid maximum capacity of place!");
		}
	}

	public LocalDateTime getDateAndTimeOfRegistration() {
		return dateAndTimeOfRegistration;
	}

}
