package threads;

import java.util.ArrayList;
import java.util.List;

import places.Place;
import services.Reservation;
import website.Website;

public class ReservationChecker implements Runnable {

	Website website;

	public ReservationChecker(Website website) {
		if (website != null) {
			this.website = website;
		}
	}

	private List<Reservation> sumAllReservations() {
		List<Reservation> reservations = new ArrayList<>();
		for (Place place : website.getAllClubs()) {
			reservations.addAll(place.getAllReservations());
		}
		for (Place place : website.getAllRestaurants()) {
			reservations.addAll(place.getAllReservations());
		}
		return reservations;

	}

	@Override
	public void run() {
		List<Reservation> allReservations = sumAllReservations();
		
	}

}
