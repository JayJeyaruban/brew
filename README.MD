# brew

Contributor guide: see `CONTRIBUTING.md` (setup, validation, PR guidance, and commit message conventions).

## Prerequisites

- JDK 17
- Git
- Android Studio (recommended for Kotlin Multiplatform and Android workflows)
- Xcode / Apple iOS toolchain installed on macOS (required for iOS targets)
- A modern browser (for web testing)

Use the Gradle wrapper (`./gradlew`) instead of a system Gradle install.

## Getting Set Up

1. Clone the repository.
2. Open the project in Android Studio or IntelliJ IDEA.
3. Let Gradle sync and download dependencies.
4. Verify Gradle works:
   - `./gradlew help`
   - or `./gradlew tasks`
5. Run one supported target (Android, iOS, or Web JS) to confirm your local setup.

If you plan to work on iOS, make sure the Apple iOS toolchain is installed.
In the current workflow, you can test/run iOS from Android Studio; opening the project in the Xcode app is optional.

### Android
To run the application on an Android device/emulator:  
 - Open the project in Android Studio and run the imported Android run configuration  

To build a debug APK:  
 - Run `./gradlew :androidApp:assembleDebug`  
 - Find the `.apk` file in `androidApp/build/outputs/apk/debug/androidApp-debug.apk`  

### iOS
To run the application on iPhone device/simulator:  
 - Run from Android Studio in the current workflow (with Xcode / Apple iOS toolchain installed on macOS)  
 - Or open `iosApp/iosApp.xcproject` in Xcode and run standard configuration  

### Web
Supported web target: JavaScript (browser)

Run the JS browser application: `./gradlew :webApp:jsBrowserDevelopmentRun`  

Build web distribution: `./gradlew :webApp:composeCompatibilityBrowserDistribution`  
Deploy the `webApp/build/dist/composeWebCompatibility/productionExecutable` directory to a web server  
