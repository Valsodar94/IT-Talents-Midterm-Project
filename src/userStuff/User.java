package userStuff;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import exceptions.InvalidInformationException;
import places.City;
import places.Place;
import services.Comment;
import services.Reservation;
import website.Website;

public class User {
	private String firstName;
	private String lastName;
	private City city;
	private String emailAdress;
	private String password;
	private String phoneNumber;
	private LocalDate birthday;
	
	private List<Reservation> myReservations;
	private List<Place> favouritePlaces;
	private List<Place> placesJournal;
	private List<Comment> comments;
	private List<Reservation> pastReservations;
	
	private User(String firstName, String lastName, City city, String emailAdress, String password, String phoneNumber, LocalDate birthday) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.city = city;
		this.emailAdress = emailAdress;
		this.password = password;
		this.birthday=birthday;
		this.myReservations = new ArrayList<>();
		this.favouritePlaces = new ArrayList<>();
		this.placesJournal = new ArrayList<>();
		this.comments = new ArrayList<>();
		this.pastReservations = new ArrayList<>();
	}

	
	
	public static User register(String firstName, String lastName, City city, String emailAdress, String password, String phoneNumber, LocalDate birthday, Website w) throws InvalidInformationException{
		if(checkForValidString(firstName)) {
			if(checkForValidString(lastName)) {
				if(city!=null) {
					try {
						if(checkForValidEMail(emailAdress)) {
							if(checkForValidPassword(password)) {
								if(checkForValidPhoneNumber(phoneNumber)) {
									if(birthday!=null) {
										System.out.println("Registration sucessfull!");
										User u = new User(firstName,lastName,city,emailAdress,password,phoneNumber,birthday);
										w.addUser(u);
										return u;
									}
									else
										throw new InvalidInformationException("Neuspeshna registraciq.Podavash null za birthday..");
								}
							}
						}
					} catch (InvalidInformationException e) {
						System.out.println("Neuspeshna registraciq! Prichina: " + e.getMessage());
					}
				} else 
					throw new InvalidInformationException("Neuspeshna registraciq.Podavash null za city..");
			}	
		} else 
			throw new InvalidInformationException("Neuspeshna registraciq!!!");
		return null;
	} 
	
	
	public void refreshPresentAndPastReservations() {
		LocalDateTime now = LocalDateTime.now();
		Iterator<Reservation> it = myReservations.iterator();
		while(it.hasNext()) {
			Reservation r = it.next();
			if(r.getDateAndTime().isBefore(now)) {
				pastReservations.add(r);
				it.remove();
			}
		}
	}
	
	public void changePassword(String newPassword,String oldPassword) {
		try {
			if(checkForPasswordMatch(oldPassword)) {
				if(checkForValidPassword(newPassword)) 
					this.password=newPassword;
			}
		} catch (InvalidInformationException e) {
			System.out.println("The password was not changed! Reason: " + e.getMessage());
		}
	}
	
	

	public void changeEMail(String oldPassword, String newEMail) {
		try {
			if(checkForPasswordMatch(oldPassword)) {
				if(checkForValidEMail(newEMail))
					this.emailAdress=newEMail;
			}
		} catch (InvalidInformationException e) {
			System.out.println("The email was not changed! Reason: " + e.getMessage());

		}
	}
	public void changeFirstName(String firstName, String password) {
		try {
			if(checkForPasswordMatch(password)) {
				if(checkForValidString(firstName)) {
					this.firstName = firstName;
				}
			}
		} catch (InvalidInformationException e) {
			System.out.println("The name was not changed! Reason: " + e.getMessage());
		}
	}
	public void changeLastName(String lastName, String password) {
		try {
			if(checkForPasswordMatch(password)) {
				if(checkForValidString(lastName)) {
					this.firstName = lastName;
				}
			}
		} catch (InvalidInformationException e) {
			System.out.println("The last name was not changed! Reason: " + e.getMessage());
		}
	}
	public void changeCity(String password,City newCity) {
		try {
			if(checkForPasswordMatch(password)) {
				if(newCity!=null) {
					this.city = newCity;
				}
			}
		} catch (InvalidInformationException e) {
			System.out.println("The city was not changed! Reason: " + e.getMessage());
		}
	}
	public void changeMobileNumber(String password, String phoneNumber) {
		try {
			if(checkForPasswordMatch(password))
				if(checkForValidPhoneNumber(phoneNumber))
					this.phoneNumber=phoneNumber;
		} catch (InvalidInformationException e) {
			System.out.println("The mobile number was not changed! Reason: " + e.getMessage());

		}
	}
	public void changeBirthday(String password, LocalDate birthday) {
		try {
			if(checkForPasswordMatch(password)) {
				if(birthday!=null) {
					this.birthday = birthday;
				}
			}
		} catch (InvalidInformationException e) {
			System.out.println("The birthday was not changed! Reason: " + e.getMessage());
		}
	}
	public void addAPlaceInJournal(Place p) {
		if(p!=null)
			this.placesJournal.add(p);
	}
	public void addReservation(Reservation r) {
		if(r!=null) {
			this.myReservations.add(r);
		}
	}
	public void addLubimoZavedenie(Place fav) {
		if(fav!=null) {
			this.favouritePlaces.add(fav);
		}
	}
	public void removeLubimoZavedenie(Place fav) {
		if(fav!=null) {
			if(this.favouritePlaces.contains(fav)) {
				this.favouritePlaces.remove(fav);
			}
		}
	}
	
	
	private static boolean  checkForValidPhoneNumber(String phoneNumber) throws InvalidInformationException{
		if(phoneNumber!=null) {
			if(phoneNumber.trim().length()==10) {
				for(int i=0;i<phoneNumber.length();i++) {
					if(!(Character.isDigit(i))) 
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

	private static boolean checkForValidString(String str) {
		if((str!=null) && (str.trim().length()>0))
			return true;
		return false;
	}

	public static boolean checkForValidEMail(String eMail) throws InvalidInformationException{
		if(eMail!=null) {
			if(eMail.trim().length()>10) {
				if(eMail.contains("@"))
					return true;
				else
					throw new InvalidInformationException("Email-a ti e nevaliden! Ne sydyrzha @");
			} else 
				throw new InvalidInformationException("Nevaliden email!");
		} else
			throw new InvalidInformationException("Podavash mi null za email...");
	}

	private boolean checkForPasswordMatch(String password) throws InvalidInformationException{
		if(password!=null) {
			if(password.equals(this.password))
				return true;
			else
				throw new InvalidInformationException("Parolite ne syvpadat!!");
		} else {
			throw new InvalidInformationException("Podavash null za parola..");
		}
	}

	private static boolean checkForValidPassword (String password) throws InvalidInformationException {
		if(password!=null) {
			if(password.trim().length()>5) 
				return true;
			else 
				throw new InvalidInformationException("Dylzhinata na parolata trqbva da e pone 5 znaka");
		} else 
			throw new InvalidInformationException("Podavash null za parola..");
	}
	

//	getters and setters:
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
	public City getCity() {
		return city;
	}
	private void setCity(City city) {
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
	
	public List<Reservation> getMyReservations() {
		return Collections.unmodifiableList(this.myReservations);
	}
	
	public List<Place> getLybimiZavedeniq() {
		return Collections.unmodifiableList(this.favouritePlaces);
	}
	
	public List<Place> getDnevnikZaZavedeniq() {
		return Collections.unmodifiableList(this.placesJournal);
	}
	
	public List<Comment> getComments() {
		return Collections.unmodifiableList(this.comments);
	}

	public List<Reservation> getPastReservations() {
		return Collections.unmodifiableList(this.pastReservations);
	}
	
}
