# Movie Booking System Using Java (AWT)

This project is a **Java-based Movie Booking System** built using **Abstract Window Toolkit (AWT)**. It allows users to book movie tickets by selecting the city, theater, movie, and show timing. The system automatically allocates a screen and keeps track of available tickets.

---

## Features
- **City, Theater, Movie, and Timing Selection**: Users can choose options for their booking.
- **Random Screen Allocation**: Screens are assigned automatically upon booking.
- **Ticket Availability Check**: A maximum of 10 tickets are available per show.
- **Dynamic Pricing**: Evening shows have a 20% surcharge.
- **Error Handling**: Validates user inputs and shows descriptive error messages.
- **Persistent Storage**: Booking details are saved in a text file (`movie_bookings.txt`).
- **Auto Reset**: Fields reset automatically after successful booking or on reset action.

---

## Demo

### Booking Example:
1. Select a city, theater, movie, and timing.
2. Enter the number of tickets (up to 10).
3. Click "Book Tickets" to confirm the booking.
4. A confirmation dialog will display the allocated screen, total cost, and remaining tickets.
5. Fields reset after confirmation.

### Reset Example:
1. Click "Reset" to clear all selections and start fresh.

---

## File Structure
```plaintext
MovieBookingAWT.java   # Main source code for the movie booking system.
movie_bookings.txt     # Stores booking details after every confirmed booking.
README.md              # Project documentation (this file).
