GitHub Repository Link:
https://github.com/KobeZ123/cs4520-assg4-starter-code

# Application Structure
The application features a main activity that navigates between two fragments,
a login fragment and a product list fragment. The login fragment has simple username
and password text input fields that the user uses to input their credentials.
The user will be navigated to the product list fragment if they input "admin" as the
username and password. Otherwise, they will be met with an appropriate toast message
(and their input will be cleared if they input the wrong credentials). The product
list fragment uses MVVM architecture to load a list of products from an API endpoint 
and handles issues accordingly. If an error occurs, the app attempts to load the list of products
from the Room database. If there are no products, a message is displayed in the fragment. 

Pagination is also implemented to query the API by page number. Since the API still behaves
returning error messages and empty lists at times, the pagination implementation will display 
"No products available" on API responses when an empty list is returned. It will also load from the 
database when the API response is an error message. Therefore, sometimes it looks like the pagination
shows the same page twice. That is because it attempts to load the new page and an error message occurs. 
Therefore, it will load the data from the Room database, which is the most recently successful API response.

# How to Run Application
There is no special setup required to run the application. Simply connect your device
or start the emulator and run 'app'.