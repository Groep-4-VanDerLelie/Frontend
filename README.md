# Frontend

### Creating a new page
To create a new page, follow the next steps:
- Create FXML file under `resources/views/{name}-page.fxml`
- Create a view under `java/views/{Name}View.java`
    - Create the observers, observables and controllers needed for your page
    - Create enums under `enums/ViewRoute`
    - Handle enums under `views/ViewNavigator`
- Create languages under `resources/languages/{name}-page_{language}.properties` for all 3 languages
  - **en_GB** - English
  - **nl_NL** - Dutch
  - **ro_RO** - Romanian

### Troubleshooting
Language Resources not found:
- check if the variables are spelled correct
- check if file languages files are formatted as:
    - login-page_en_GB.properties (difference between _ and -)
- `mvn clean build` and build again

FXML not found:
- check file names
- check if enums have been added under `enums/ViewRoute`
- check if enums are handled under `views/ViewNavigator`
- `mvn clean build` and build again