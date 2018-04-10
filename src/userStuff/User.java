package userStuff;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Demo.Demo;
import exceptions.InvalidInformationException;
import places.Place;
import services.Comment;
import services.Reservation;

public class User {
	private String firstName;
	private String lastName;
	private String city;
	private String emailAdress;
	private String password;
	private boolean isAdmin = false;
	private String phoneNumber;
	private LocalDate birthday;

	private List<Reservation> myReservations;
	private List<Place> favouritePlaces;
	private List<Place> placesJournal;
	private List<Comment> comments;
	private List<Reservation> pastReservations;

	protected User(String firstName, String lastName, String city, String emailAdress, String password, String phoneNumber,
			LocalDate birthday) throws InvalidInformationException {
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.emailAdress = emailAdress;
		this.phoneNumber = phoneNumber;
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
			if (UserAdministration.checkForPasswordMatch(oldPassword)) {
				if (UserAdministration.checkForValidPassword(newPassword))
					this.password = newPassword;
			}
		} catch (InvalidInformationException e) {
			System.out.println("The password was not changed! Reason: " + e.getMessage());
		}
	}

	public void changeEMail(String oldPassword, String newEMail) {
		try {
			if (UserAdministration.checkForPasswordMatch(oldPassword)) {
				if (UserAdministration.checkForValidEMail(newEMail))
					this.emailAdress = newEMail;
			}
		} catch (InvalidInformationException e) {
			System.out.println("The email was not changed! Reason: " + e.getMessage());

		}
	}

	public void changeFirstName(String firstName, String password) {
		try {
			if (UserAdministration.checkForPasswordMatch(password)) {
				if (UserAdministration.checkForValidString(firstName)) {
					this.firstName = firstName;
				}
			}
		} catch (InvalidInformationException e) {
			System.out.println("The name was not changed! Reason: " + e.getMessage());
		}
	}

	public void changeLastName(String lastName, String password) {
		try {
			if (UserAdministration.checkForPasswordMatch(password)) {
				if (UserAdministration.checkForValidString(lastName)) {
					this.firstName = lastName;
				}
			}
		} catch (InvalidInformationException e) {
			System.out.println("The last name was not changed! Reason: " + e.getMessage());
		}
	}

	public void changeCity(String password, String newCity) {
		try {
			if (UserAdministration.checkForPasswordMatch(password)) {
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
			if (UserAdministration.checkForPasswordMatch(password))
				if (UserAdministration.checkForValidPhoneNumber(phoneNumber))
					this.phoneNumber = phoneNumber;
		} catch (InvalidInformationException e) {
			System.out.println("The mobile number was not changed! Reason: " + e.getMessage());

		}
	}

	public void changeBirthday(String password, LocalDate birthday) {
		try {
			if (UserAdministration.checkForPasswordMatch(password)) {
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
	
	public void removeReservation(Reservation reservation) {
		if(reservation != null) {
			this.myReservations.remove(reservation);
		}
	}

	public void cancelReservation(String reservationID) throws InvalidInformationException {
		if (reservationID != null && myReservations.size()>0) {
			Iterator<Reservation> it = myReservations.iterator();
			while(it.hasNext()) {
				Reservation r = it.next();
				if(r.getReservationID().equals(reservationID)) {
					Place place = r.getPlace();
					place.cancelReservation(r);
					it.remove();
					System.out.println("Your reservation has been canceled successfully!");
				}
			}
			
		} else {
			throw new InvalidInformationException("Not existing reservation!");
		}
	}
	
	public void cancelReservation() {
		System.out.println("Enter the reservationId of the reservation you want to cancel");
		String reservationID = Demo.sc.next();
		User u = UserAdministration.getU();
		try {
			u.cancelReservation(reservationID);
		} catch (InvalidInformationException e) {
			System.out.println("No such reservation.");
		}
	}
	public void leaveAComment(){
		System.out.println("Enter the reservation id of the reservation you want to comment");
		String reservationID = Demo.sc.next();
		System.out.println("Enter your comment for the place: ");
		String comment = Demo.sc.nextLine();
		System.out.println("Now enter your rating (1-6).");
		int rating = Demo.sc.nextInt();
		try {
			leaveComment(reservationID, comment, rating);
		} catch (InvalidInformationException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void leaveComment(String reservationId, String description, int rating) throws InvalidInformationException {
		if (reservationId != null && description != null && rating > 0 && rating < 6) {
			if(myReservations.size()>0) {
				Iterator<Reservation> it = myReservations.iterator();
				while(it.hasNext()) {
					Reservation r = it.next();
					if(r.getReservationID().equals(reservationId)) {
						if (!r.isHasLeftComment()) {
							try {
								Comment comment = new Comment(description, rating, r.getUser(), r.getPlace());
								this.comments.add(comment);
								r.getPlace().addComment(comment);
							} catch (InvalidInformationException e) {
								e.printStackTrace();
							}
						} else {
							throw new InvalidInformationException("Cannot leave comment to this reservation!");
						}
					}
				}
			}
		} else {
			throw new InvalidInformationException("Invalid input!");
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

	// getters and setters:
	public String getFirstName() {
		return firstName;
	}


	
	public boolean isAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public String getLastName() {
		return lastName;
	}

	

	public String getCity() {
		return city;
	}


	public String getEmailAdress() {
		return emailAdress.trim();
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public String getPassword() {
		return this.password.trim();
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
		
	@Override
	public String toString() {
		return "name: " + this.firstName + " " + this.lastName + ", city: " + this.city + ", phone: " + this.phoneNumber+", e-mail: "+ this.emailAdress;
	}

}
