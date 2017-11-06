
## SCRATCH DIRECTORY FOR BUILDING CAMERA INTERFACES

Example code for connect to Synology Diskstation / Surveillance Station on your local network and create camera devices for each camera in the Survellance Station application.

Camera devices support the following capabilities:
- Image Capture -- Image capture uses the standard take command
- Motion Detection -- Motion detection status lasts for a user specified number of minutes past the last motion sent by the Diskstation
- Record -- Using the device button or switch on/off commands to start and stop recording 
- Refresh -- Use the refresh button / command when you change presets or patrols

The follow commands are also supported from SmartApps:
- "left", "right", "up", and "down"
- "zoomIn" and "zoomOut"
- "home"
- "presetGoName", ["string"]
- "patrolGoName", ["string"]
- "autoTakeOn" and "autoTakeOff"

## Installation

To set up the your Synology Diskstation cameras from Surveillance Station as SmartThings devices, follow these steps:

Set up the SmartApp:
* Create a new SmartApp
* Enter any values in the required * fields
* Click the button "Enable OAuth in Smart App"
* Create the Smart App
* Copy the code from DiskstationConnect.groovy over the code of your SmartApp
* Save and Publish For Me

Set up the Device Type:
* Create a new Device Type
* Enter any values in the required * fields
* Create the Device Type
* Copy the code from DiskstationCamera.groovy over the code of your Device Type
* Save and Publish For Me

Connect to your DiskStation:
* Open your SmartThings application on your iPhone or Android device
* Go to My Apps and Choose Diskstation (Connect)
* If you don't see it in My Apps, kill the SmartThings application and restart it
* Follow the instructions in the App for the rest of the details

If you add new cameras to your system, go through the Diskstation (Connect) app again to add the new cameras. If you already set up motion detection, you do not need to do it again.


# Generic-Video-Camera
Generic Video Camera Live Streaming Video in SmartThings Tile View

Install the 2 smartapps and devicetype in IDE.

Add SmartApp Connect app to your mobile install
Go into SmartApps, Generic Video Camera Connect App
Add Cameras
Either use drop down and edit in the child smartapp in IDE for your cameras
or
use the custom url to add any url to stream.


# Panasonic PTZ Camera

Based on a few projects in the ST Community, I wrote a camera device handler that can control a Panasonic PTZ Camera. So you can move it in the four directions and also call 3 presets defined on the camera. This was successfully tested with these models: BL-C30A, BB-HCM511A, BB-HCM531A

Thanks to: patrickstuart & blebson

Link to the project: https://community.smartthings.com/t/panasonic-ptz-ip-camera/43336

