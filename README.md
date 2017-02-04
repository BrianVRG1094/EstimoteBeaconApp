# EstimoteBeaconApp
This app was designed with the intention of working with an Estimote Beacon to display a simple notification on a device.
The notification would be a prompt or reminder to the user that pertained to the specific Beacon the device detected.
For example, if the user wanted to reminded about leaving water for their pets in the morning the beacon would be set up near the front door.
As the device approached said door, a notification would appear with the message.
Another use for the app would be to prompt the user about opening an app.
If the user wanted to prompted to open their music app on their phone when they entered their car, the app could do this as well.

-----------------------------
I. Prerequisites
-----------------------------
For the app
1. An Android device running at least Android v2.0 and has Bluetooth capabilities.
2. At least 1 Estimote Beacon
For the project file
1. Android Studio

-----------------------------
II. Installing
-----------------------------
To install the app:
1. Open Android Studio
2. Open the project file within Android Studio
3. If you wish to have the app on another device, connect via USB to your computer and make sure any drivers for it are installed. If you wish to run the app on a virtual device running on your computer, continue to next step.
4. Click on the "Run" button in Android Studio or hit "SHIFT + F10" on your keyboard.
5. Select your device in the prompt and select OK
6. The app will be installed on the device.

-----------------------------
I. Using the Appp
-----------------------------
Once you have installed the app, using it is simple. 
A list is prepoluated with the 3 Beacons. 
Simply click one, and a new window is opened which will present you with some information about the Beacon. 
From there you can change the setting of the Beacon. 
This consists of choosing which app you want to be prompted to open.

-----------------------------
I. Design Changes & Issues
-----------------------------
1. Changes
Throughout the development process, changes had to be made to the app (shocking, I know). 
Originally I wanted the app to be condensed into a single Activity that had the Beacons in a list.
Once you clicked on a Beacon, a "drawer" of sorts would be opened underneath the Beacon on the list and all its settings would be displayed there.
Due to time constraints and a lack of "knowing how to do this", I instead opted to display these settings in a new Activity (something I knew how to do).
Not all the changes were dropping features however. 
Some things were additions that were implemented with relative ease. 
An original app Icon was made within a day and added to the project. 
The loading screen when the app is opened was also something that wasn't planned from the beginning.
2. Issues
There really isn't much issues with the app itself. 
The detection of the Beacons has been tested multiple times and has always worked. 
The settings are saved succesfully and correctly applied to each individual Beacon. 
The only big issue currently with the app is it only works with 3 specific Beacons that were supplied to me for the duration of the project. 
If you were to install this app on an Android device and walk near an Estimote Beacon, it would not work (given that the Beacon in question is not one of the original 3).

