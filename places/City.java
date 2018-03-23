package places;

import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidInformationException;

public class City {
	private final String name;
	private List<String> regions;

	public City(String name) throws InvalidInformationException {
		if (Place.isValidString(name)) {
			this.name = name;
		} else {
			throw new InvalidInformationException("Please, enter a valid city name");
		}
		regions = new ArrayList<String>();
	}

	public void addRegion(String region) {
		if(Place.isValidString(region) && !(regions.contains(region))) {
			regions.add(region);
		}
	}
	
	public boolean hasRegion(String region) {
		if(regions.contains(region)) {
			return true;
		}
		return false;
	}

	
	//getters and setters
	public String getName() {
		return name;
	}

}
