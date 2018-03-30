package userStuff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import exceptions.InvalidDateException;
import exceptions.InvalidInformationException;
import places.Place;
import services.Comment;
import services.Reservation;
import website.Website;

public class User {
	private String firstName;
	private String lastName;
	private String city;
	private String emailAdress;
	private String password;
	private String phoneNumber;
	private LocalDate birthday;
	private Website website;

	private List<Reservation> myReservations;
	private List<Place> favouritePlaces;
	private List<Place> placesJournal;
	private List<Comment> comments;
	private List<Reservation> pastReservations;

	protected User(String firstName, String lastName, String city, String emailAdress, String password,
			String phoneNumber, LocalDate birthday) throws InvalidInformationException {
		if (checkForValidString(firstName)) {
			if (checkForValidString(lastName)) {
				// Da se dobavi kachestvena proverka za city s exception + systoto za birtday
				if (city != null) {
					if (UserAdministration.checkForValidEMail(emailAdress)) {
						if (checkForValidPassword(password)) {
							if (checkForValidPhoneNumber(phoneNumber)) {
								if (birthday != null) {
								}
							}
						}
					}
				}
			}
		}
		System.out.println("Registration sucessfull!");
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.emailAdress = emailAdress;
		this.password = password;
		this.birthday = birthday;
		this.myReservations = new ArrayList<>();
		this.favouritePlaces = new ArrayList<>();
		this.placesJournal = new ArrayList<>();
		this.comments = new ArrayList<>();
		this.pastReservations = new ArrayList<>();

	}
	

	public void changePassword(String newPassword, String oldPassword) {
		try {
			if (checkForPasswordMatch(oldPassword)) {
				if (checkForValidPassword(newPassword))
					this.password = newPassword;
			}
		} catch (InvalidInformationException e) {
			System.out.println("The password was not changed! Reason: " + e.getMessage());
		}
	}

	public void changeEMail(String oldPassword, String newEMail) {
		try {
			if (checkForPasswordMatch(oldPassword)) {
				if (UserAdministration.checkForValidEMail(newEMail))
					this.emailAdress = newEMail;
			}
		} catch (InvalidInformationException e) {
			System.out.println("The email was not changed! Reason: " + e.getMessage());

		}
	}

	public void changeFirstName(String firstName, String password) {
		try {
			if (checkForPasswordMatch(password)) {
				if (checkForValidString(firstName)) {
					this.firstName = firstName;
				}
			}
		} catch (InvalidInformationException e) {
			System.out.println("The name was not changed! Reason: " + e.getMessage());
		}
	}

	public void changeLastName(String lastName, String password) {
		try {
			if (checkForPasswordMatch(password)) {
				if (checkForValidString(lastName)) {
					this.firstName = lastName;
				}
			}
		} catch (InvalidInformationException e) {
			System.out.println("The last name was not changed! Reason: " + e.getMessage());
		}
	}

	public void changeCity(String password, String newCity) {
		try {
			if (checkForPasswordMatch(password)) {
				if (newCity != null) {
					this.city = newCity;
				}
			}
		} catch (InvalidInformationException e) {
			System.out.println("The city was not changed! Reason: " + e.getMessage());
		}
	}

	public void changeMobileNumber(String password, String phoneNumber) {
		try {
			if (checkForPasswordMatch(password))
				if (checkForValidPhoneNumber(phoneNumber))
					this.phoneNumber = phoneNumber;
		} catch (InvalidInformationException e) {
			System.out.println("The mobile number was not changed! Reason: " + e.getMessage());

		}
	}

	public void changeBirthday(String password, LocalDate birthday) {
		try {
			if (checkForPasswordMatch(password)) {
				if (birthday != null) {
					this.birthday = birthday;
				}
			}
		} catch (InvalidInformationException e) {
			System.out.println("The birthday was not changed! Reason: " + e.getMessage());
		}
	}

	public void addAPlaceInJournal(Place p) {
		if (p != null)
			this.placesJournal.add(p);
	}

	public void addReservation(Reservation r) {
		if (r != null) {
			this.myReservations.add(r);
		}
	}

	// za otkazvane na rezervaciq
	public void cancelReservation(Reservation r) throws InvalidInformationException {
		if (r != null && myReservations.contains(r)) {
			myReservations.remove(r);
			Place place = r.getPlace();
			LocalDateTime date = r.getDateAndTime();
			place.cancelReservation(r, date);
			System.out.println("Your reservation has been canceled successfully!");
		} else {
			throw new InvalidInformationException("Not existing reservation!");
		}
	}
	
	//napravih za edit na imeto no trqbva i za drugite poleta da se napravi
	public void editReservation(Reservation r, LocalDateTime date) throws InvalidInformationException, InvalidDateException {
		if (r != null && myReservations.contains(r)) {
			//makeReservation
			cancelReservation(r);
			//addReservation
		} else {
			throw new InvalidInformationException("Not existing reservation!");
		}
	}

	public void addLubimoZavedenie(Place fav) {
		if (fav != null) {
			this.favouritePlaces.add(fav);
		}
	}

	public void removeLubimoZavedenie(Place fav) {
		if (fav != null) {
			if (this.favouritePlaces.contains(fav)) {
				this.favouritePlaces.remove(fav);
			}
		}
	}

	protected static boolean checkForValidPhoneNumber(String phoneNumber) throws InvalidInformationException {
		if (phoneNumber != null) {
			if (phoneNumber.trim().length() == 10) {
				for (int i = 0; i < phoneNumber.length(); i++) {
					if (!(Character.isDigit(phoneNumber.charAt(i))))
						throw new InvalidInformationException("Nomera trqbva da se systoi samo ot cifri!");
					else
						return true;
				}
			} else
				throw new InvalidInformationException("Nomera trqbva da sydyrza tochno 10 cifri!");
		} else
			throw new InvalidInformationException("Podavash null za phonenumber");
		return false;
	}

	protected static boolean checkForValidString(String str) throws InvalidInformationException {
		if ((str != null) && (str.trim().length() > 0))
			return true;
		else
			throw new InvalidInformationException("Podavash null za String ili imash po-malko ot 1 znak");
	}

	protected boolean checkForPasswordMatch(String password) throws InvalidInformationException {
		if (password != null) {
			if (password.equals(this.password))
				return true;
			else
				throw new InvalidInformationException("Parolite ne syvpadat!!");
		} else {
			throw new InvalidInformationException("Podavash null za parola..");
		}
	}

	protected static boolean checkForValidPassword(String password) throws InvalidInformationException {
		if (password != null) {
			if (password.trim().length() > 5)
				return true;
			else
				throw new InvalidInformationException("Dylzhinata na parolata trqbva da e pone 5 znaka");
		} else
			throw new InvalidInformationException("Podavash null za parola..");
	}

	// getters and setters:
	public String getFirstName() {
		return firstName;
	}

	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	private void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	private void setCity(String city) {
		this.city = city;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	private void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public String getPassword() {
		return this.password;
	}

	protected List<Reservation> getMyReservations() {
		return this.myReservations;
	}

	protected List<Place> getLybimiZavedeniq() {
		return this.favouritePlaces;
	}

	protected List<Place> getDnevnikZaZavedeniq() {
		return this.placesJournal;
	}

	protected List<Comment> getComments() {
		return this.comments;
	}

	protected List<Reservation> getPastReservations() {
		return this.pastReservations;
	}


}
