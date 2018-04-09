package places;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import enums.ExtraReservationOptions;
import exceptions.InvalidInformationException;
import services.Comment;
import services.Event;
import services.Offer;
import services.Reservation;

public class Place implements Comparable<Place> {
	private String name;
	private String address;
	private String emailAddress;
	private final LocalDateTime dateAndTimeOfRegistration;
	private int avgRating;
	// for restaurant - true, for club - false
	private boolean isRestaurant;
	// kitchen for restaurant, music for club
	private String characteristicInput;
	// List keeping kitchens for restaurant or music for clubs
	private List<String> characteristicOfPlace;
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
	private int discount;

	private SortedMap<LocalDate, List<Reservation>> reservations;
	private List<Comment> comments;
	private List<Integer> ratings;
	private List<String> locationPrefs;
	private List<ExtraReservationOptions> extraReservationOptions;
	private List<Event> events;
	private List<Offer> offers;

	public Place(String name, String address, String emailAddress, boolean isRestaurant, String characteristicInput,
			String city, String region, String avgCost, LocalTime startHour, LocalTime closeHour, int maxCapacity,
			List<String> locationPrefs, List<ExtraReservationOptions> extraReservationOptions)
			throws InvalidInformationException {

		setName(name);
		setAddress(address);
		setEmailAddress(emailAddress);
		this.isRestaurant = isRestaurant;
		setCharacteristicInput(characteristicInput);
		setCity(city);
		setRegion(region);
		setAvgCost(avgCost);
		setStartHour(startHour);
		setCloseHour(closeHour);
		setMaxCapacity(maxCapacity);
		dateAndTimeOfRegistration = LocalDateTime.now();
		this.reservations = new TreeMap<LocalDate, List<Reservation>>();
		this.comments = new ArrayList<Comment>();
		this.ratings = new ArrayList<Integer>();
		setLocationPrefs(locationPrefs);
		setExtraReservationOptions(extraReservationOptions);
		this.events = new ArrayList<Event>();
		this.offers = new ArrayList<Offer>();
	}

	public boolean hasAvailableSeats(LocalDate date, int number) throws InvalidInformationException {
		if (date != null && number > 0) {
			int sumOfSeats = 0;
			if (this.reservations.containsKey(date)) {
				List<Reservation> list = new ArrayList<>();
				list = this.reservations.get(date);
				for (int res = 0; res < list.size(); res++) {
					int seats = list.get(res).getNumberOfPeople() + list.get(res).getNumberOfChildren();
					sumOfSeats += seats;
				}
				this.currentCapacity = this.maxCapacity - sumOfSeats;
				return this.currentCapacity > number;
			} else {
				return true;
			}
		} else {
			throw new InvalidInformationException("Invalid information given! Cannot check for availability!");
		}
	}

	public static boolean isValidString(String string) {
		if (string != null && string.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public boolean doesContainReservation(Reservation reservation) throws InvalidInformationException {
		if (reservation != null) {
			if (this.reservations.containsKey(reservation.getDate())) {
				List<Reservation> list = new ArrayList<>();
				list = this.reservations.get(reservation.getDate());
				for (int res = 0; res < list.size(); res++) {
					if (reservation.getReservationID().equals(list.get(res).getReservationID())) {
						return true;
					}
				}
				return false;
			} else {
				return false;
			}
		} else {
			throw new InvalidInformationException("Invalid reservation details!");
		}
	}

	public void addReservation(Reservation reservation) throws InvalidInformationException {
		if (reservation != null) {
			if (!this.reservations.containsKey(reservation.getDate())) {
				this.reservations.put(reservation.getDate(), new ArrayList<>());
			}
			reservations.get(reservation.getDate()).add(reservation);
		} else {
			throw new InvalidInformationException("Invalid reservation datas!");
		}
	}

	public void removeReservation(Reservation reservation) {
		if (reservation != null) {
			this.reservations.remove(reservation);
		}
	}

	public void cancelReservation(Reservation reservation) throws InvalidInformationException {
		if (reservation != null) {
			List<Reservation> list = new ArrayList<>();
			list = this.reservations.get(reservation.getDate());
			for (int res = 0; res < list.size(); res++) {
				if (reservation.getReservationID().equals(list.get(res).getReservationID())) {
					this.reservations.get(reservation.getDate()).remove(res);
				} else {
					throw new InvalidInformationException("No reservation with this id!");
				}
			}
		} else {
			throw new InvalidInformationException("Invalid reservation details!");
		}
	}

	public void addExtraReservationOptions(ExtraReservationOptions extraReservationOption)
			throws InvalidInformationException {
		if (extraReservationOption != null && !this.extraReservationOptions.contains(extraReservationOption)) {
			this.extraReservationOptions.add(extraReservationOption);
		} else {
			throw new InvalidInformationException("The option you entered is not correct!");
		}
	}

	public void removeExtraReservationOptions(ExtraReservationOptions extraReservationOption)
			throws InvalidInformationException {
		if (extraReservationOption != null && this.extraReservationOptions.contains(extraReservationOption)) {
			this.extraReservationOptions.remove(extraReservationOption);
		} else {
			throw new InvalidInformationException("The option you entered to delete is not correct!");
		}
	}

	public void addLocationPref(String locationPref) throws InvalidInformationException {
		if (locationPref != null && !this.locationPrefs.contains(locationPref)) {
			this.locationPrefs.add(locationPref);
		} else {
			throw new InvalidInformationException("The location you entered is not correct!");
		}
	}

	public void removeLocationPref(String locationPref) throws InvalidInformationException {
		if (locationPref != null && this.locationPrefs.contains(locationPref)) {
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

	public static boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public void addEvent(Event event) throws InvalidInformationException {
		if (event != null) {
			if (!this.events.contains(event)) {
				this.events.add(event);
			}
		} else {
			throw new InvalidInformationException("Event cannot be add! Invalid input!");
		}
	}

	public void removeEvent(Event event) throws InvalidInformationException {
		if (event != null) {
			if (this.events.contains(event)) {
				this.events.remove(event);
			}
		} else {
			throw new InvalidInformationException("Event cannot be deleted! Invalid input!");
		}
	}

	public void addOffer(Offer offer) throws InvalidInformationException {
		if (offer != null) {
			if (!this.offers.contains(offer)) {
				this.offers.add(offer);
			}
		} else {
			throw new InvalidInformationException("Offer cannot be add! Invalid input!");
		}
	}

	public void removeOffer(Offer offer) throws InvalidInformationException {
		if (offer != null) {
			if (this.offers.contains(offer)) {
				this.offers.remove(offer);
			}
		} else {
			throw new InvalidInformationException("Offer cannot be deleted! Invalid input!");
		}
	}

	// getters and setters
	public boolean isRestaurant() {
		return isRestaurant;
	}

	public String getCharacteristicInput() {
		return characteristicInput;
	}

	public void addCharacteristicOfPlace(String characteristicOfPlace) throws InvalidInformationException {
		if (characteristicOfPlace != null) {
			this.characteristicOfPlace.add(characteristicOfPlace);
			this.characteristicInput = String.join(", ", this.characteristicOfPlace);
		} else {
			throw new InvalidInformationException("Invalid enter of characteristic of place");
		}
	}

	public void removeCharacteristicOfPlace(String characteristicOfPlace) throws InvalidInformationException {
		if (characteristicOfPlace != null) {
			this.characteristicOfPlace.remove(characteristicOfPlace);
			this.characteristicInput = joinList(this.characteristicOfPlace);
		} else {
			throw new InvalidInformationException("Invalid enter of characteristic of place");
		}
	}

	public String joinList(List<String> characteristicOfPlace) {
		String string = String.join(", ", characteristicOfPlace);
		return string;
	}

	// String format is: indian,bulgarian,sushi (with "," between the strings)
	public void setCharacteristicInput(String characteristicInput) throws InvalidInformationException {
		if (isValidString(characteristicInput)) {
			this.characteristicInput = characteristicInput;
			this.characteristicOfPlace = Arrays.asList(characteristicInput.split(","));

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

	public void setEmailAddress(String emailAddress) throws InvalidInformationException {
		if (isValidEmailAddress(emailAddress)) {
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

	public int getCurrentCapacity() {
		return currentCapacity;
	}

	public void increaseCurrentCapacity() {
		this.currentCapacity++;
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

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) throws InvalidInformationException {
		if (discount > 0 && discount <= 100) {
			this.discount = discount;
		} else {
			throw new InvalidInformationException(
					"Please, enter a valid discount for the place (must be bigger than 0!)!");
		}
	}

	public void setLocationPrefs(List<String> locationPrefs) {
		if (locationPrefs != null) {
			this.locationPrefs = new ArrayList<>(locationPrefs);
		}
	}

	public void setExtraReservationOptions(List<ExtraReservationOptions> extraReservationOptions) {
		if (extraReservationOptions != null) {
			this.extraReservationOptions = new ArrayList<ExtraReservationOptions>(extraReservationOptions);
		}
	}

	public ArrayList<Reservation> getAllReservations() {
		ArrayList<Reservation> allReservations = new ArrayList<Reservation>();
		for (LocalDate date : this.reservations.keySet()) {
			allReservations.addAll(this.reservations.get(date));
		}
		return allReservations;
	}

	public List<String> getCharacteristicOfPlace() {
		return Collections.unmodifiableList(characteristicOfPlace);
	}

	@Override
	public int compareTo(Place p2) {
		double p1Rating = this.getAvgRating();
		double p2Rating = p2.getAvgRating();
		if (!(p2Rating == p1Rating)) {
			return (int) (p2Rating - p1Rating);
		} else
			return p2.getName().compareTo(this.getName());
	}

	public List<String> getLocationPrefs() {
		return Collections.unmodifiableList(locationPrefs);
	}

	public List<ExtraReservationOptions> getExtraReservationOptions() {
		return Collections.unmodifiableList(extraReservationOptions);
	}

	@Override
	public String toString() {
		return "name: " + this.name;
	}
}
