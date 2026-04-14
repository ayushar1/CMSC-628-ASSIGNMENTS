# Assignment 2: Phone Orientation App



## Course: Mobile Computing

## Project Name: Phone Orientation Estimation Using Accelerometer and Gyroscope



### \-|-



### App Design:

##### The application is designed with a clean, single-screen user interface optimized for portrait mode. The UI is divided into three clearly labeled sections: Accelerometer Data, Gyroscope Data, and Orientation Output. To adhere to Android best practices, all visible text and output templates are stored in res/values/strings.xml rather than being hardcoded in the Java classes.



### Orientation Method:

##### The assignment noted that a simple implementation is acceptable and a full sensor-fusion engine is not required. To estimate the device's orientation, this app utilizes the hardware **Accelerometer:**

##### **Pitch and Roll Calculation:** The app calculates the pitch and roll angles (in degrees) using trigonometric functions (Math.atan2) applied to the X, Y, and Z values of the accelerometer.

##### 

##### **Textual Orientation Description:** Based on the calculated pitch and roll, the app outputs a textual description of the phone's physical state (e.g., "Upright", "Flat", or "Tilted").

##### 

##### **Gyroscope Integration:** The gyroscope X, Y, and Z rotational rates are continuously read and displayed on the screen in real-time alongside the accelerometer data.



### Lifecycle and Battery Management:

##### To avoid battery drain and background resource misuse, the app implements strict lifecycle-aware sensor handling:

##### 

##### **Activation:** Sensor listeners are registered within the onResume() lifecycle callback, ensuring data is only polled when the user is actively viewing the app.

##### 

##### **Deactivation:** Listeners are explicitly unregistered in the onPause() callback so they do not remain active in the background.



## Assumptions \& Error Handling:

##### **Sensor Availability:** It is assumed that the primary device testing the app has both an accelerometer and a gyroscope. However, defensive programming principles were applied: the app checks for null sensors during initialization and gracefully displays an "unavailable" message in the UI if a specific hardware sensor is missing, preventing the app from crashing.

##### 

##### **Sensor Delay:** The SensorManager.SENSOR\_DELAY\_UI rate was chosen to provide smooth real-time updates suitable for a user interface while minimizing excessive processing overhead.

