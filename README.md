# SimpleTask
Create a new branch and apply the following changes to it:

1. Create a new Activity where you'll be integrating with https://api.androidhive.info/pizza/?format=xml to get a list of foods, their prices and short descriptions. You should satisfy all the following
	- Integrate using `Retrofit2` and XML parsing
	- Display a list of the foods retrieved from the web service sorted alphabetically
	- Show the Progress Dialog during the fetch of the data to be hidden once the data is fetched
	- Use the Snackbar to display any Connection Errors 
	- `Cache` the data, show it once the activity is opened and have a refresh button to re-fetch the data
	- Display the last update time (In the toolbar or anywhere in the activity. In the toolbar would be a plus!)
	- Have at least one `unit` test (The sorting fuction for example)
	- Verify on the caching using `automation` testing

2. Create another Activity using kotlin. This activity should satisfy all the following
	- Display a default image (keeping the `performance` in mind)
	- Clicking on the image should open the camera and capture a picture then display it instead of the default picture


Things to keep in mind
- Coding style, formatting and documentation
- Commits and their messages
- Project structure
- Using all the provided classes in the package named `bases`
- All the provided classes should be used


