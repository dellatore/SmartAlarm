# Smart Alarm(SmartAlarm) 
SmartAlarm is a Kotlin application designed to provide a more intuitive alarm experience. It allows users to turn off the alarm using voice commands or by moving the device. The alarm can be configured to suit your preferences, offering a more dynamic and interactive way to wake up.


## 🚀 Starting
The instructions below will allow you to clone and run the project on your machine, in addition to the recommended tests based on the main functions of the app.


### 📋 Prerequisites
- Android Studio (or another IDE with support for Kotlin and Android development).
- JDK and Android SDK correctly configured. (JDK 21 used)
- Gradle (via the wrapper in the repository: gradlew / gradlew.bat).


## 🔧 Installation
Step-by-step: 

1. Clone the repository:
```
git clone https://github.com/dellatore/SmartAlarm.git
```
```
cd SmartAlarm
```
2. Open the project in Android Studio or run via command line:
```
./gradlew build
```
```
./gradlew installDebug
```
3. Launch the app on an emulator or physical device..


## ⚙️ Main Functions
1. Intelligent alarm scheduling.
2. Alarm termination via voice command, using APIs such as SpeechRecognizer, RecognizerIntent, and the RecognitionListener interface.
3. Alarm termination is also possible via device movement, using sensors such as SensorManager, SensorEventListener, etc.


## 🔩 Suggested Tests
1. Try scheduling an alarm in the app.
2. Try turning off an alarm using voice recognition by saying the keyword.
3. Try turning off the alarm using the motion sensor by shaking your phone.


## 🛠️ Build with
Tools and technologies used in the project:


- Kotlin – main code language
- Gradle (Kotlin DSL) – build system
- Firestore (Database)
  
## 📌 Version
v1.0.0


## ✒️ Authors
Dellatore – Main author of this project


## 📄 License
Apache 2.0


## 🎥 Demonstration
An explanatory video is available below to show how the app works in action.

https://youtube.com/shorts/lw4jcgdRVS0?feature=share
