# TestPOIMVP

App that shows a set of touristic Points of Interest with details. 

Endpoint: http://t21services.herokuapp.com/points 

Functionalities: 

Data obtained has to be persistent in database. PARTIALLY IMPLEMENTED
	-Implemented with Room, data is saved when there is internet connection automatically. If the internet connection is gone, it will retrieve the data from the database and send it to the adapter to be shown in the recyclerview
App show a list of POIs. FULLY IMPLEMENTED
	-RecyclerView added here to show the data, format: linearLayout
Selection of POI and details. FULLY IMPLEMENTED
	-Implemented a second activity, when user click on an item a method will get the detail data from an endpoint and send it to a new activity

User is able to search and filter the results. FULLY IMPLEMENTED
	SearchView implemented, so the user can write a title’s name and get a filtered list of results.

-------------------------------------------------------------------------------------------------------------
User interface and usability: 
	Implemented recyclerview over listview and constraint layout over relative, last version of android too

Libraries: Koin, retrofit2, Room, Rxjava2

Architecture: Implemented following MVP

Time Invested: Around 18 hours, between coding, doing research of pattern to follow and study of the architecture to implement, creating documentation and User Flow 





















