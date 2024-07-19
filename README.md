# Setup
Create secret.properties in the project root and add your MAPS_API_KEY

# Architecture
The application follows a clean architecture:

*The dependencies go inward from low-level to high-level layers*

![technical test](https://github.com/user-attachments/assets/d16e3713-517b-413d-ab64-a62eb94d267b)


### Domain layer
Contains all the business logic, it does not depend on any framework.

- Entities: data classes used by the business, mainly in use cases, made up of simple classes such as [Contact, Coordinates](https://github.com/enmanuel52/technical-test/tree/dev/app/src/main/java/com/enmanuelbergling/technicaltest/domain/entity).

- Use cases: In this case, [GetPaginatedContactsUseCase](https://github.com/enmanuel52/technical-test/blob/dev/app/src/main/java/com/enmanuelbergling/technicaltest/domain/usecase/GetPaginatedContactsUseCase.kt) is implemented from the network because the pagination API depends on the Android framework and the domain should not.

*The interfaces to access the data must be within this layer, so that the data layer can implement them by applying dependency inversion.*

### Data layer
Implements the data interfaces defined within the domain.

In this case, there is only one network implementation, for this, the entities are converted to DTO (Data Transformer Object) to make the API request to the server. 
The server then returns another DTO that must be converted to return what the interfaces declare.

The conversion functions are isolated within the mapper package. [ContactMappers](https://github.com/enmanuel52/technical-test/blob/dev/app/src/main/java/com/enmanuelbergling/technicaltest/data/network/mappers/ContactMappers.kt)

When data comes from multiple data sources, a data source layer is needed, and a repository is also needed to unite the data sources in one place.
In this case, all the data comes from one place, so the repository was fine.

### UI layer
Represents data and handles user interactions

- Components: contains those that are common throughout the application, such as buttons.
- Navigation: Contains the navigation host when all destinations are defined in the application.
From here, the callbacks are passed to each screen so they can navigate between them. [AppNavigation](https://github.com/enmanuel52/technical-test/blob/dev/app/src/main/java/com/enmanuelbergling/technicaltest/ui/navigation/AppNavigation.kt)
- Features: designed so that each one can be isolated into its own module. Within each one the navigation logic is isolated,
containing the destination and the function to navigate to it. [ContactNavigation](https://github.com/enmanuel52/technical-test/blob/dev/app/src/main/java/com/enmanuelbergling/technicaltest/ui/feature/contact/navigation/ContactNavigation.kt)

# Testing
In each screen, a statefull and a stateless version were implemented to use the latter to test the screen in isolation. [ContactScreen](https://github.com/enmanuel52/technical-test/blob/dev/app/src/main/java/com/enmanuelbergling/technicaltest/ui/feature/contact/detail/ContactScreen.kt)

[See Isolated Test](https://github.com/enmanuel52/technical-test/blob/dev/app/src/androidTest/java/com/enmanuelbergling/technicaltest/tests/ContactDetailsTest.kt)

An [End-to-End](https://github.com/enmanuel52/technical-test/blob/dev/app/src/androidTest/java/com/enmanuelbergling/technicaltest/tests/EndToEndTest.kt) test was performed covering the critical user journey through the application.

For this it was necessary to replace only the [app hilt module](https://github.com/enmanuel52/technical-test/blob/dev/app/src/main/java/com/enmanuelbergling/technicaltest/data/network/di/NetworkModule.kt) 
for a [new one](https://github.com/enmanuel52/technical-test/blob/dev/app/src/androidTest/java/com/enmanuelbergling/technicaltest/di/TestNetworkModule.kt) to provide fake implementations of the repository so that it can run faster.

# Screenshots

<div style="margin: 10px;">
  <img src="https://github.com/user-attachments/assets/fbb0e94b-a420-4119-8987-b6e231c0fc2e" style="display: flex; width: 30%; padding: 0% 3%;">
  <img src="https://github.com/user-attachments/assets/62502d13-ebaf-4994-8e25-c9f0971b3e62" style="display: flex; width: 30%; padding: 0% 3%;">
  <img src="https://github.com/user-attachments/assets/df01cb66-970f-4965-8fd6-70586d97c2ff" style="display: flex; width: 30%; padding: 0% 3%;">
</div>
