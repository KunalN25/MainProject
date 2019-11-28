# Crave Better
Crave Better is an Android app that detects your current location and shows all the restaurants available within 5 km from that location.
It can show 20 restaurants around any location that you type in the search box.

* Login And Registration
This app uses Firebase Authentication for user login and registration. UserData is stored in hierarchical manner in Firebase Database. The user fills his details in the fields on the registration page and those details are stored in the firebase database.

* Google Maps
After logging in the user's current location is displayed on a map with a search box on top. The user can also type any location in the search box and he will be taken to the location. We have used Google Maps API for this purpose.

* Restaurants List
When the current location or any location is shown on the map, its lontitude and latitude are taken and then using Zomato API, the data is loaded in JSON form and it is shown in a Recycler View. 

* Menus and Restaurant Data 
For every restaurant, we have the details like cuisines, phone no,its address,reviews,etc all of which are taken from <a href="https://developers.zomato.com/">Zomato Developers</a> The menus are stored in our Firebase Cloud Firestore and the menu is displayed along with the prices of all the items.

* Payments
We have also added a payment feature in this app where there is a virtual balance and the user has to add some balance into his account for which we have yet again used RealTime Database. Whatever items the user orders, the prices of all the items are added and the total bill is produced which is deducted from the balance.

* Collaborators
<a href="https://github.com/Bhushan-Koyande">Bhushan Koyande</a> and Shyam Mehta.




