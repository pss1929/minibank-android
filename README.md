# minibank-android
Mini Bank Application for Assignment
Mini Bank is sample Android Banking application developed to demonstarte modern android developement. This application allows users to view bank acccounts, check transaction history and fund transfer between accounts.
This project follows **MVVM clean Architecture** and uses **MockWebServer** to stimulate backed APIs.

## Features
- Login with OTP
- View list of bank accounts
- View account transaction history
- Internal fund transfer between accounts
- Secure screens using FLAG_SECURE
- Mock backend using MockWebServer
- Clean Architecture with Repository Pattern
- Basic root detection
- Offline support

## Tech Stack
- Kotlin
- MVVM Architecture
- Retrofit
- Hilt (Dependency Injection)
- Coroutines
- MockWebServer
- Navigation Component
- ViewBinding
- RecyclerView
- Room 

## Setup Instructions
### 1 Clone the Repository
git clone https://github.com/yourusername/minibank-app.git
### 2. Open latest android studio Otter
### 3. Run on physical device

## Screens
- Spalsh Screen
- Onboarding Screen
- Login Screen
- OTP Screen
- Account Dashboard
- Transaction History
- Transfer Money

## Security
- Screen protected using FLAG_SECURE
- Biometric Login
- Root detection
- R8/Proguard Enable
- Session management
- MockWebServer Self SSL Pinning

## Testing 
Basic unit test includes one domain layer test and repository test



## Note: 
This project uses MockWebServer to simulate backend APIs. As this was my first time implementing MockWebServer, the implementation focuses on demonstrating API flow and architecture rather than a fully production ready backend simulation.

## Known Limitations
- Pagination handling can be improved further
- Empty state handling for lists can be enhanced
- SwipeRefreshLayout progress indicator may require additional handling in some cases
- MockWebServer was used for API simulation and may be further refined

## Build Note
The project was developed using a recent version of the Android Gradle Plugin.  If using an older Android Studio version, you may need to downgrade the AGP version.

## Screenshot
Please find screenshot from  https://github.com/pss1929/minibank-android/blob/main/scrrenshot/Account%20Screen.jpeg

## Author
**Pooja Sankar**  
Android Developer


