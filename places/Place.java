package places;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import exceptions.InvalidInformationException;
import services.Comment;
import services.Reservation;

public class Place {
	private String name;
	private String address;
	private String emailAddress;
	private final LocalDate dateOfRegistration;
	private final LocalTime timeOfRegistration;
	private int avgRating;
	private String city;
	private String region;
	private String avgCost;
	private String description;
	private String extraOptions;
	private String paymentMethods;
	private String extras;
	private String workingTime;
	private int capacity;
	private List<Reservation> reservations;
	private List<Comment> comments;
	private List<Integer> ratings;

	public Place(String name, String address, String emailAddress, String city, String region, String avgCost,
			String workingTime) throws InvalidInformationException {
		setName(name);
		setAddress(address);
		setEmailAddress(emailAddress);
		setCity(city);
		setRegion(region);
		dateOfRegistration = LocalDate.now();
		timeOfRegistration = LocalTime.now();
		setAvgCost(avgCost);
		setWorkingTime(workingTime);

		reservations = new ArrayList<Reservation>();
		comments = new ArrayList<Comment>();
		ratings = new ArrayList<Integer>();

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

	public void setEmailAddress(String emailAddress) throws InvalidInformationException {
		if (Place.isValidString(emailAddress)) {
			this.emailAddress = emailAddress;
		} else {
			throw new InvalidInformationException("Please, enter a valid e-mail address!");
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

}
