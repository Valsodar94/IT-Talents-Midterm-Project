package places;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.InvalidInformationException;
import services.Comment;
import services.Reservation;
import userStuff.User;
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
	private String workingTime;
	private int capacity;

	private Map<LocalDateTime, Reservation> reservations;
	private List<Comment> comments;
	private List<Integer> ratings;

	public Place(String name, String address, String emailAddress, boolean isRestaurant, String characteristicOfPlace,
			String city, String region, String avgCost, String workingTime) throws InvalidInformationException {
		setName(name);
		setAddress(address);
		setEmailAddress(emailAddress);
		this.isRestaurant = isRestaurant;
		setCharacteristicOfPlace(characteristicOfPlace);
		setCity(city);
		setRegion(region);
		dateAndTimeOfRegistration = LocalDateTime.now();
		setAvgCost(avgCost);
		setWorkingTime(workingTime);

		reservations = new TreeMap<LocalDateTime, Reservation>();
		comments = new ArrayList<Comment>();
		ratings = new ArrayList<Integer>();

	}

	public boolean hasAvailableSeats(int number) throws InvalidInformationException {
		if (number > 0) {
			if (this.capacity > number) {
				return true;
			}else {
				return false;
			}
		}else {
			throw new InvalidInformationException("Invalid number of people!");
		}
	}

	public static boolean isValidString(String string) {
		if (string != null && string.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public void addComment(Comment comment) {
		if (comment != null) {
			this.comments.add(comment);
			int rating = comment.getRating();
			this.ratings.add(rating);
			this.avgRating = averageRating();
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
// Trqbva nova validaciq za email, tazi v clasa userAdministration e prispobena za user
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

	public String getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(String workingTime) throws InvalidInformationException {
		if (Place.isValidString(workingTime)) {
			this.workingTime = workingTime;
		} else {
			throw new InvalidInformationException("Please, enter a valid working time!");
		}
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) throws InvalidInformationException {
		if (capacity > 0) {
			this.capacity = capacity;
		} else {
			throw new InvalidInformationException("Please, enter valid capacity of place!");
		}
	}

	public LocalDateTime getDateAndTimeOfRegistration() {
		return dateAndTimeOfRegistration;
	}

}
