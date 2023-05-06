import java.util.Scanner;

public class Restaurant {
	public String name;
	public String type;
	public String food;
	public double rating;
	public String location;
	public Scanner scnr = new Scanner(System.in);

	public Restaurant(String name) {
		this.name = name;
		this.rating = -1.0;
	}
	
	

	/**
	 * Method to prompt user to enter restaurant metadata. Loops for good input ('y', 'n').
	 * Displays user's options for choosing which metadata to enter. Loops for good input (1-7).
	 * Calls edit method(s) with respect to user input.
	 * Loops until user chooses to stop editing. //TODO
	 * @param this
	 */
	public void query() {
		String sResponse;
		int iResponse;
		boolean done = false;
		boolean goodInput = false;

		

		while (!done) {
			System.out.println("Which information would you like to enter? (1-6):\n1: All\n2: Name\n3: Food\n4: Rating\n"
							+ "5: Type\n6: Location\n7: Cancel");
			sResponse = scnr.nextLine();
		

		switch (sResponse) {
		case "1":
			this.editAll();
			break;
		case "2":
			this.editName();
			break;
		case "3":
			this.editFood();
			break;
		case "4":
			this.editRating();
			break;
		case "5":
			this.editType();
			break;
		case "6":
			this.editLocation();
			break;
		case "7":
			break;
		default: 
			System.out.println("Error: Command not recognized.");
			break;
		}
		System.out.println("Continue adding information? (y, n):");
		while (!goodInput) {
			sResponse = scnr.nextLine();
			if (sResponse.equalsIgnoreCase("y")) {
				goodInput = true;
			} else if (sResponse.equalsIgnoreCase("n")) {
				done = true;
				goodInput = true;
			} else {
				System.out.println("Please enter \"y\" or \"n\".");
				
			}
		}
		goodInput = false;
		}
		return;
	}
	
	/**
	 * Method that creates a string representation of the available metadata of a single restaurant.
	 * @return - String representation of target restaurant.
	 */
	public String display() {
		String dis = this.name + "\n";
		if (this.food != null) {
			dis += "Style: " + this.food + " food\n";
		}
		if (this.type != null) {
			dis += "Type: " + this.type + " restaurant\n";
		}
		if (this.rating != -1.0) {
			dis += "Personal rating: " + this.rating + " / 10.0\n";
		}
		if (this.location != null) {
			dis += "Located: " + this.location +"\n";
		}
		return dis;
	}

	/**
	 * Method to alter the name metadata of a restaurant.
	 * Checks if current name is null (new restaurant) and gives the appropriate prompt.
	 * If there is existing metadata, confirms the users wishes to overwrite the metadata.
	 * Loops for good responses ('y', 'n') and then updates metadata as given. 
	 */
	
	
	public void editAll() {
		if (this.name == null) {
		this.editName();
		}
		this.editFood();
		this.editRating();
		this.editType();
		this.editLocation();
	}
	public void editName() {
		String response;
		boolean done = false;
		if (this.name == null) {
			System.out.println("What is the name of this restuarant?");
			this.name = scnr.nextLine();
			return;
		} 
		
		
		System.out.println("Update restaurant name? y/n:");
		while (!done) {
			response = scnr.nextLine();
			if (response.equalsIgnoreCase("y")) {
				System.out.println("What is the name of this restuarant?");
				this.name = scnr.nextLine();
				done = true;
			} else if (response.equalsIgnoreCase("n")) {
				done  = true;
			} else {
				System.out.println("Please enter \"y\" or \"n\".");
			}
		}
		
	}
	
	/**
	 * Method to alter the name metadata of a restaurant.
	 * Checks if current food is null (new restaurant) and gives the appropriate prompt.
	 * If there is existing metadata, confirms the users wishes to overwrite the metadata.
	 * Loops for good responses ('y', 'n') and then updates metadata as given. 
	 */
	public void editFood() {
		String response;
		boolean done = false;
		if (this.food == null) {
			System.out.println("What kind of food does this restaurant serve?");
			this.food = scnr.nextLine();
			return;
		} 
		
		
		System.out.println("Update restaurant food? y/n:");
		while (!done) {
			response = scnr.nextLine();
			if (response.equalsIgnoreCase("y")) {
				System.out.println("What kind of food does this restaurant serve?");
				response = scnr.nextLine();
				this.food = response;
				done = true;
			} else if (response.equalsIgnoreCase("n")) {
				done  = true;
			} else {
				System.out.println("Please enter \"y\" or \"n\".");
			}
		}
		
	}
	
	/**
	 * Method to alter the name metadata of a restaurant.
	 * Checks if current rating is null (new restaurant) and gives the appropriate prompt.
	 * If there is existing metadata, confirms the users wishes to overwrite the metadata.
	 * Loops for good responses('y', 'n'), (0.0 - 10.0) and then updates metadata as given. 
	 */
	public void editRating() {
		String response;
		boolean done = false;
		boolean goodInput = false;
		boolean inBounds = false;
		while(!inBounds) {
		if (this.rating == -1.0) {
			System.out.println("What is your rating of this restuarant? (0.0 - 10.0):");
			while (!goodInput) {
				if (scnr.hasNextDouble()) {
					goodInput = true;
				} else {
					scnr.nextLine();
					System.out.println("Please enter a numerical value. (0.0 - 10.0):");
				}
			}
			if (scnr.hasNextDouble()) {
				this.rating = scnr.nextDouble();
		
			} else {
				this.rating = (double) scnr.nextInt();
				
			}
			
		} 
		if ((this.rating >= 0.0) && (this.rating <= 10.0)) {
			response = scnr.nextLine();
			return;
		} else {
			System.out.println("Please enter a numerical value. (0.0 - 10.0):");
			response = scnr.nextLine();
		}
		}
		
		
		System.out.println("Update restaurant rating? y/n:");
		while(!inBounds) {
		while (!done) {
			response = scnr.nextLine();
			if (response.equalsIgnoreCase("y")) {
				System.out.println("What is your rating of this restaurant? (0.0 - 10.0):");
				while (!(scnr.hasNextDouble()) && !(scnr.hasNextInt())) {
					System.out.println("Please enter a numerical value. (0.0 - 10.0):");
				}
				if (scnr.hasNextDouble()) {
					this.rating = scnr.nextDouble();
					return;
				} else {
					this.rating = (double) scnr.nextInt();
					return;
				}
			} else if (response.equalsIgnoreCase("n")) {
				done  = true;
			} else {
				System.out.println("Please enter \"y\" or \"n\".");
			}
		}
		if ((this.rating >= 0.0) && (this.rating <= 10.0)) {
			response = scnr.nextLine();
			return;
		} else {
			System.out.println("Please enter a numerical value. (0.0 - 10.0):");
			response = scnr.nextLine();
		}
		}
		
		return;
	}
	
	/**
	 * Method to alter the name metadata of a restaurant.
	 * Checks if current type is null (new restaurant) and gives the appropriate prompt.
	 * If there is existing metadata, confirms the users wishes to overwrite the metadata.
	 * Loops for good responses ('y', 'n') and then updates metadata as given. 
	 */
	public void editType() {
		String response;
		boolean done = false;
		if (this.type == null) {
			System.out.println("What type of restuarant is it?");
			this.type = scnr.nextLine();
			return;
		} 
		
		
		System.out.println("Update restaurant type? y/n:");
		while (!done) {
			response = scnr.nextLine();
			if (response.equalsIgnoreCase("y")) {
				System.out.println("What type of restuarant is it?");
				this.type = scnr.nextLine();
				done = true;
			} else if (response.equalsIgnoreCase("n")) {
				done  = true;
			} else {
				System.out.println("Please enter \"y\" or \"n\".");
			}
		}
		
	}
	
	/**
	 * Method to alter the name metadata of a restaurant.
	 * Checks if current location is null (new restaurant) and gives the appropriate prompt.
	 * If there is existing metadata, confirms the users wishes to overwrite the metadata.
	 * Loops for good responses ('y', 'n') and then updates metadata as given. 
	 */
	public void editLocation() {
		String response;
		boolean done = false;
		if (this.location == null) {
			System.out.println("Where is this restuarant?");
			this.name = scnr.nextLine();
			return;
		} 
		
		
		System.out.println("Update restaurant location? y/n:");
		while (!done) {
			response = scnr.nextLine();
			if (response.equalsIgnoreCase("y")) {
				System.out.println("Where is this restuarant?");
				this.name = scnr.nextLine();
				done = true;
			} else if (response.equalsIgnoreCase("n")) {
				done  = true;
			} else {
				System.out.println("Please enter \"y\" or \"n\".");
			}
		}
		
	}
	
	
	
		
	
}
