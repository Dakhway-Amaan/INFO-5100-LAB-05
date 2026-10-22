# School Registration Form
 
A JavaFX desktop app built in NetBeans for INFO-5100 Lab 05. The user enters a name, age and email and submits. Input is validated with inline error messages, and a confirmation dialog shows the details entered.
 
## Structure
 
```
src/
├── model/
│   ├── User.java                    Data class for name, age and email
│   └── UserValidator.java           Validation rules for each field
├── controller/
│   └── MainFXMLController.java      Handles submit, validation and the pop-up
└── ui/
    ├── Launcher.java                Plain main class that starts FXMain (see below)
    ├── FXMain.java                  JavaFX Application, loads the FXML
    ├── MainFXML.fxml                Form layout (built with Scene Builder)
    └── style.css                    Styling for the form and dialogs
```
 
The project follows MVC: `model` holds the data and rules, `ui` holds the view, and `controller` connects them.
 
## Validation
 
Each field is checked when it loses focus and again on submit. All errors appear at once under their fields, and the pop-up only shows when everything is valid:
 
* Name required, 2 to 50 characters, letters plus spaces, apostrophes and hyphens only
* Age between 1 and 120 (the spinner only accepts digits)
* Email must be a valid address (no consecutive dots, max 254 characters)
After a successful submit, a dialog shows the details and the form resets.

## Problems we faced and how we tackled them
 
| Problem | Cause | Fix |
|---|---|---|
| `Module javafx.controls not found` | The VM options were not pointing at a valid JavaFX `lib` folder. In `project.properties`, single backslashes in the path were also read as escape characters, so the path was mangled | Used forward slashes (`D:/javafx-sdk-27/lib`), checked the folder with `java --module-path <lib> --list-modules`, and ran the whole project with F6 instead of a single file |
| `JavaFX runtime components are missing` | An `Application` subclass cannot start from the plain classpath without the JavaFX modules | Either pass `--module-path` and `--add-modules` as VM options, or add a small `Launcher` class (it does not extend `Application` and just calls `FXMain.main`) and use it as the main class |

## Why there is a Launcher class
 
When a main class extends `javafx.application.Application`, Java checks at startup that the JavaFX modules are available. If the JavaFX jars are only on the classpath and no `--module-path` is given, the app stops with `Error: JavaFX runtime components are missing` before any of our code runs.
 
`Launcher` works around this. It is a plain class that does not extend `Application`, so Java skips that check, and its `main` method simply calls `FXMain.main(args)`:
 
```java
package ui;
 
public class Launcher {
    public static void main(String[] args) {
        FXMain.main(args);
    }
}
```
 
With the JavaFX jars on the project classpath, setting `ui.Launcher` as the main class lets the app start without any VM options. This also avoids the module path problems listed above. If you prefer not to use it, set `ui.FXMain` as the main class and pass the `--module-path` and `--add-modules` VM options from the "Running it" section instead.
