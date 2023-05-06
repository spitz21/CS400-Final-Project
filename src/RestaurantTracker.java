import java.util.LinkedList;
import java.util.Scanner;

public class RestaurantTracker {
	LinkedList<Restaurant>[] data;
	int capacity;
	Scanner scnr = new Scanner(System.in);

	/**
	 * Default RestaurantTracker constructor with a capacity of 101;
	 */
	@SuppressWarnings("unchecked")
	public RestaurantTracker() {
		data = (LinkedList<Restaurant>[]) new LinkedList[101];
		this.capacity = 101;
	}

	/**
	 * Constructor that takes in the starting capacity as a parameter.
	 * 
	 * @param capacity - integer value for starting capacity of data list.
	 */
	@SuppressWarnings("unchecked")
	public RestaurantTracker(int capacity) {
		data = (LinkedList<Restaurant>[]) new LinkedList[capacity];
		this.capacity = capacity;
	}

	/**
	 * Method to insert a given restaurant into the data list. Hash value and
	 * default grouping are generated based on food type. Creates a new linked list
	 * in the data array if one does not exist. Restaurants that do not have food
	 * metadata are stored in a linked list at value 0;
	 * 
	 * @param rest - Restaurant to be inserted
	 */
	public void insert(Restaurant rest) {
		int hash;

		// Gen hash from food type, hash = 0 if food type is null.
		if (rest.food == null) {
			hash = 0;
		} else {
			hash = (Math.abs(rest.food.hashCode()) % data.length) + 1;
		}

		// Check for existing linkedlist or create one if needed and then add restaurant.
		if (data[hash] == null) {
			data[hash] = new LinkedList<Restaurant>();
			data[hash].add(rest);
		} else {
			data[hash].add(rest);
		}
	}

	/**
	 * Method to insert a given Restaurant into the data list at a given hash value.
	 * Used for sorting.
	 * 
	 * @param hash - integer hash value to insert the restaurant at.
	 * @param rest
	 */
	public void insertHash(int hash, Restaurant rest) {
		// Check hash val for existing linkedlist or create one if needed, then add restaurant.
		if (data[hash] == null) {
			data[hash] = new LinkedList<Restaurant>();
			data[hash].add(rest);
		} else {
			data[hash].add(rest);
		}
	}

	/**
	 * Method to double the size of the data array whenever the data array is fully hashed.
	 */
	public void expandData() {
		RestaurantTracker temp = new RestaurantTracker();
		this.capacity *= 2;
		temp.data = this.data;
		this.data = new RestaurantTracker(capacity).data;
		//Check data array for non-null linked lists.
		for (int i = 0; i < temp.data.length; i++) {
			if (temp.data[i] != null) {
				//insert every data val into new double sized data array.
				for (int j = 0; j < temp.data[i].size(); j++) {
					this.insert(temp.data[i].get(j));
				}
			}
		}
		return;

	}

	/**
	 * Method to use when user wants to add a new restaurant. Checks for duplicate
	 * name within data. If there is an existing restaurant with the same name,
	 * returns false, otherwise a new Restaurant node is created and the user is
	 * queried for metadata, then the Restaurant is inserted.
	 * 
	 * @return - true if a new Restaurant is inserted, false if the given name is
	 *         already in the data.
	 */
	public boolean createNewRestaurant(String name) {
		String sResponse;
		Boolean done = false;
		boolean goodInput = false;

		// Search for duplicate name in data.
		if (this.search(name) != null) {
			return false;
		}

		// Create new Restaurant with given name Display success message and new Restaurant. Query user for metadata.
		Restaurant newR = new Restaurant(name);
		
		System.out.println("Would you like to fill out the information for this restaurant now? y/n:");
		sResponse = scnr.nextLine();
		while (!done) {
			if (sResponse.equalsIgnoreCase("y")) {
				newR.query();
				done = true;
			} else if (sResponse.equalsIgnoreCase("n")) {
				done = true;
			} else {
				System.out.println("Please enter \"y\" or \"n\".");
				sResponse = scnr.nextLine();
			}
		}
		insert(newR);
		System.out.println("Successfully added:\n\n" + newR.display());
		return true;
	}

	/**
	 * Method that searches the data for a Restaurant with the same name as the
	 * given name (case insensitive). Returns the restaurant if found, otherwise
	 * returns null.
	 * 
	 * @param name - Name of Restaurant to find in the data.
	 * @return - Restaurant if found, otherwise null.
	 */
	public Restaurant search(String name) {
		// Search data for matching name, case insensitive.
		for (int i = 0; i < this.data.length; i++) {
			if (this.data[i] != null) {
				for (int j = 0; j < this.data[i].size(); j++) {
					if (name.equalsIgnoreCase(this.data[i].get(j).name)) {
						return this.data[i].get(j);
					}

				}
			}
		}
		return null;
	}

	/**
	 * Prompts user to confirm if they want to edit a Restaurant they are viewing.
	 * If the food metadata of the Restaurant was changed, then the Restaurant is
	 * removed a reinserted to be rehashed.
	 * 
	 * @param rest - Restaurant to edit.
	 */
	public void edit(Restaurant rest) {
		String response = "";
		boolean done = false;

		// Confirm user wants to edit, loops for good input ('y', 'n').
		System.out.println("Would you like to edit this restaurant? (y, n)");
		response = scnr.nextLine();
		while (!done) {
			if (response.equalsIgnoreCase("y")) {
				done = true;
			} else if (response.equalsIgnoreCase("n")) {
				return;
			} else {
				System.out.println("Please enter \"y\" or \"n\".");
				response = scnr.nextLine();
			}
		}

		// Saves restaurant's food metadata and compares it to the new metadata after
		// editing.
		// If food metadata has changed, remove and reinsert (rehash).
		if (rest.food != null) {
			String food = rest.food;
			rest.query();
			if (!(rest.food.equalsIgnoreCase(food))) {
				this.remove(rest);
				this.insert(rest);
			}
		}
		return;
	}

	/**
	 * Method to remove a given restaurant. Searches by direct node equivalence.
	 * 
	 * @param res - Restaurant to find and remove.
	 */
	public void remove(Restaurant res) {
		// Search for restaurant node equivalent to given restaurant node.
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null) {
				for (int j = 0; j < data[i].size(); j++) {
					if (res == data[i].get(j)) {
						data[i].remove(j);
					}

				}
			}
		}
		return;
	}

	/**
	 * Method to combine a Restauranttracker's data as a string using the display()
	 * method.
	 * 
	 * @param return - String representation of the data.
	 */
	public String toString() {
		String out = "";
		// Loop through data and add the display result of every restaurant node wtih
		// the required metadata to the
		// out string.
		for (int i = 1; i < this.data.length; i++) {
			if (this.data[i] != null) {
				for (int j = 0; j < this.data[i].size(); j++) {
					out += this.data[i].get(j).display() + "\n";
				}
			}
		}
		// Add restaurants without relevant metadata to the end.
		if (this.data[0] != null) {
			for (int k = 0; k < this.data[0].size(); k++) {
				out += this.data[0].get(k).display() + "\n";
			}
		}
		return out;
	}

	/**
	 * TODO Sorting method hub. Prompts users to choose a sorting method, then sorts
	 * data and displays it. Sorting method repeats until the user cancels or
	 * sorting method finds no results.
	 */
	public void sort() {
		String response = "";
		int iResponse;
		RestaurantTracker temp = new RestaurantTracker();
		boolean done = false;
		boolean goodInput = false;
		temp.data = this.data;
		while (!done) {
			System.out.println(
					"How would you like to sort your list? (1-6):\n1: Name (Alphabetical)\n2: Food\n3: Rating\n"
							+ "4: Location\n5: Type\n6: Cancel\n");
			if ((scnr.hasNextInt())) {
				iResponse = scnr.nextInt();
				response = scnr.nextLine();
				switch (iResponse) {
				case 1:
					temp.data = temp.nameSort();
					System.out.println(temp.toString());
					break;
				case 2:

					temp.data = temp.foodSort();
					if (temp.data != null) {
						System.out.println(temp.toString());
					}
					break;
				case 3:
					temp.data = temp.ratingSort();
					System.out.println(temp.toString());
					break;
				// method in question
				// case 4:
				// this.locationSort();
				case 5:
					temp.data = temp.typeSort();
					System.out.println(temp.toString());
					break;
				case 6:
					return;
				}
				while (!goodInput) {
					System.out.println("Continue sorting? (y, n):");
					response = scnr.nextLine();
					if (response.equalsIgnoreCase("y")) {
						break;
					} else if (response.equalsIgnoreCase("n")) {
						done = true;
						break;
					} else {
						System.out.println("Please enter \"y\" or \"n\".");
					}
				}

			} else {
				System.out.println("Please enter an integer. (0 - 4):");
			}
		}

	}

	/**
	 * Method to sort data by food metadata. Called by sort method hub. Prompts user
	 * to enter the food type they are searching for. Creates a new
	 * RestaurantTracker and adds all restaurants with matching food types. Returns
	 * null if no matching restuarants are found and the user cancels the sort
	 * operation.
	 * 
	 * @return - data containing only matching restaurants or null if none match.
	 */
	public LinkedList<Restaurant>[] foodSort() {
		RestaurantTracker temp = new RestaurantTracker();
		String response = "";
		int hash = 0;
		boolean done = false;
		boolean goodInput = false;
		boolean found = false;

		// Loops until there is a possible sort or the user chooses to exit.
		while (!done) {
			System.out.println("What kind of food to sort?");
			response = scnr.nextLine();
			for (int i = 0; i < this.data.length; i++) {
				if (this.data[i] != null) {
					for (int j = 0; j < this.data[i].size(); j++) {
						if ((this.data[i].get(j).food != null)
								&& (this.data[i].get(j).food.equalsIgnoreCase(response))) {
							temp.insert(this.data[i].get(j));
							found = true;
						}
					}
				}
			}

			// If no matching restaraunts found, prompt user to do another sort or cancel.
			if (!found) {
				System.out.println("No restaurants found.\n");
				System.out.println("Try another food? (y, n):");
				goodInput = false;
				while (!goodInput) {
					response = scnr.nextLine();
					if (response.equalsIgnoreCase("y")) {
						goodInput = true;
					} else if (response.equalsIgnoreCase("n")) {
						temp.data = null;
						return temp.data;
					} else {
						System.out.println("Please enter \"y\" or \"n\".");
					}
				}
			} else {
				done = true;
			}
		}

		// Adds all restaurants found with matching food types and insert into temp
		// data.

		return temp.data;
	}

	/**
	 * Method to sort the restaurants in the given data by alphabetical order (A-Z).
	 * 
	 * @return - An alphabetically sorted data list.
	 */
	public LinkedList<Restaurant>[] nameSort() {
		RestaurantTracker temp = new RestaurantTracker();

		int hash;

		for (int i = 0; i < this.data.length; i++) {
			if (this.data[i] != null) {
				for (int j = 0; j < this.data[i].size(); j++) {
					hash = Character.getNumericValue(this.data[i].get(j).name.charAt(0)) - 9;
					temp.insertHash(hash, this.data[i].get(j));
				}
			}
		}
		return temp.data;
	}

	/**
	 * Method to sort the restaurants within given data by their user rating
	 * metadata. Restaurants are ordered from highest to lowest score
	 * 
	 * @return - Data list of all restaurants ordered by rating.
	 */
	public LinkedList<Restaurant>[] ratingSort() {
		RestaurantTracker temp = new RestaurantTracker();

		int hash;

		for (int i = 0; i < data.length; i++) {
			if (data[i] != null) {
				for (int j = 0; j < data[i].size(); j++) {
					if (data[i].get(j).rating != -1) {
						hash = (int) Math.abs((this.data[i].get(j).rating * 10) - 100) + 1;
						System.out.println(hash);
						temp.insertHash(hash, data[i].get(j));
					}
				}
			}
		}
		return temp.data;
	}

	public LinkedList<Restaurant>[] typeSort() {
		RestaurantTracker temp = new RestaurantTracker();
		String response = "";
		boolean found = false;
		boolean done = false;
		boolean goodInput = false;

		// Loops until there is a possible sort or the user chooses to exit.
		while (!done) {
			System.out.println("What kind of type to sort?");
			response = scnr.nextLine();
			for (int i = 0; i < this.data.length; i++) {
				if (this.data[i] != null) {
					for (int j = 0; j < this.data[i].size(); j++) {
						if (this.data[i].get(j).type.equalsIgnoreCase(response)) {
							temp.insert(this.data[i].get(j));
							found = true;
						}
					}
				}
			}

			// If no matching restaraunts found, prompt user to do another sort or cancel.
			if (!found) {
				System.out.println("No restaurants found.\n");
				System.out.println("Try another type? (y, n):");
				goodInput = false;
				while (!goodInput) {
					response = scnr.nextLine();
					if (response.equalsIgnoreCase("y")) {
						goodInput = true;
					} else if (response.equalsIgnoreCase("n")) {
						temp.data = null;
						return temp.data;
					} else {
						System.out.println("Please enter \"y\" or \"n\".");
					}
				}
			} else {
				done = true;
			}
		}
		return temp.data;
	}

	public static void main(String[] args) {
		boolean unique = false;
		boolean done = false;
		boolean goodInput = false;
		Scanner scnr = new Scanner(System.in);
		String response;
		RestaurantTracker myList = new RestaurantTracker();

		myList.populate();

		System.out.println("Welcome to your RestaurantTracker!\n");
		while (!done) {
			System.out.println("What would you like to do?\n"
					+ "1: Add a restaurant\n2: Search/Edit a restaurant\n3: View my List\n4: Sort my list\n5: Close");
			response = scnr.nextLine();

			switch (response) {
			case "1":
				System.out.println("Please enter the Restaurant's name.");
				response = scnr.nextLine();

				while (!unique) {
					if (myList.createNewRestaurant(response) == true) {
						unique = true;
					} else {
						System.out.println("This name is already in use.");
						System.out.println("Would you like enter a different name?");
						response = scnr.nextLine();
						while (!goodInput) {
							if (response.equalsIgnoreCase("y")) {
								System.out.println("Please enter another name");
								response = scnr.nextLine();
								response = scnr.nextLine();
								goodInput = true;
							} else if (response.equalsIgnoreCase("n")) {
								break;
							} else {
								System.out.println("Please enter \"y\" or \"n\".");
								response = scnr.nextLine();
							}
						}
						goodInput = false;
					}
				}
				unique = false;
				boolean expand = true;
				for (int i = 0; i < myList.data.length; i++) {
					if (myList.data[i] == null) {
						expand = false;
					}
				}
				if (expand) {
					myList.expandData();
				}
				break;
			case "2":
				System.out.println("Please enter the name of the Restaurant to find.");
				response = scnr.nextLine();
				if (myList.search(response) != null) {
					System.out.println("\n" + myList.search(response).display());
					myList.edit(myList.search(response));
				} else {
					System.out.println("No results.\n");
					break;
				}
				break;
			case "3":
				System.out.println(myList.toString());
				break;
			case "4":
				myList.sort();
				break;
			case "5":
				done = true;
				goodInput = true;
				System.out.println("Goodbye");
				break;
			default:
				break;
			}

			while (!goodInput) {
				System.out.println("Return to the main menu? (y/n): ");
				response = scnr.nextLine();

				if (response.equalsIgnoreCase("y")) {
					goodInput = true;
					break;
				} else if (response.equalsIgnoreCase("n")) {
					System.out.println("Goodbye");
					done = true;
					goodInput = true;
					break;
				} else {
					System.out.println("Please enter \"y\" or \"n\".");
				}
			}
			goodInput = false;
		}
		scnr.close();

		// Restaurant test = new Restaurant("MD");
		// test.query(test);

	}

	public void populate() {
		RestaurantTracker temp = new RestaurantTracker();

		Restaurant t1 = new Restaurant("Noodles & co");
		t1.food = "thai";
		t1.rating = 9.0;
		t1.type = "takeout";

		Restaurant t2 = new Restaurant("Rockys");
		t2.food = "pizza";
		t2.rating = 7.0;
		t2.type = "fast food";

		Restaurant t3 = new Restaurant("Guimo's");
		t3.food = "Mexican";
		t3.rating = 8.8;
		t3.type = "takeout";

		Restaurant t4 = new Restaurant("Old fashioned");
		t4.food = "american";
		t4.rating = 9.0;
		t4.type = "Dine-in";

		Restaurant t5 = new Restaurant("Gordon's");
		t5.food = "italian";
		t5.rating = 10.0;
		t5.type = "dine-in";

		Restaurant t6 = new Restaurant("Arbys");
		t6.food = "american";
		t6.rating = 8.5;
		t6.type = "fast";

		Restaurant t7 = new Restaurant("Chipotle");
		t7.food = "mexican";
		t7.rating = 9.0;
		t7.type = "takeout";

		Restaurant t8 = new Restaurant("Bobs burgers");
		t8.food = "american";
		t8.rating = 5.0;
		t8.type = "fast";

		Restaurant t9 = new Restaurant("Country Cafe");
		t9.food = "Italian";
		t9.rating = 8.0;
		t9.type = "Dine-in";

		Restaurant t10 = new Restaurant("Buck & Honey's");
		t10.food = "American";
		t10.rating = 9.2;
		t10.type = "Dine-in";

		Restaurant t11 = new Restaurant("La Taguara");
		t11.food = "Venezuelan";
		t11.rating = 9.5;
		t11.type = "Dine-in";

		Restaurant t12 = new Restaurant("Weary traveler");

		this.insert(t1);
		this.insert(t2);
		this.insert(t3);
		this.insert(t4);
		this.insert(t5);
		this.insert(t6);
		this.insert(t7);
		this.insert(t8);
		this.insert(t9);
		this.insert(t10);
		this.insert(t11);
		this.insert(t12);
		return;

	}
}
