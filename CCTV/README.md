
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
## Generic Video Camera Live Streaming Video in SmartThings Tile View

## Installation Instructions:

1. Install from Code for each smartapp and the devicetype
2. Publish the connect app and devicetype (don’t publish the child smartapp)
3. Go to Marketplace, smartapps, my apps, find the connect app. Click Done to install the smartapp.
4. Go to smartapps in fly out, side menu find the Generic Video Camera Connect smartapp.
(If it isn’t there, its a platform bug, need to go into IDE, locations, smartaps, find the smartapp, click on it and uncheck the child check box)
5. Add a camera.
6. Make sure the url is all lowercase for rtsp or http: etc, as Android and iOS can tend to capitalize the first letter, which can then fail.
7. Add as many cameras as you want.
8. They will show up in your things list.

## Known Issues:
- iOS streaming is limited, nothing I can do about this, it appears it is an iOS limitation, try a low res stream.
- Sometimes streaming will stop or lag out, this might be an ST app issue, restart app and should be fine.
- Resuming from sleep on android with stream open may result in black window or missing video tile, wait a bit or force quit and restart app.

This is completely unsupported and taking advantage of undocumented / unreleased features which can and probably will break in the next mobile app release, firmware upgrade, platform release, phase of moon change or at the flap of a butterfly’s wing.
No warranty is provided. It could melt your phone for all I know.

## NOTE: Do not port forward your cameras. This will only work when you are on your home network or using a VPN back to your home network. I repeat, DO NOT PORT FORWORD your cameras.

# Panasonic PTZ Camera

Based on a few projects in the ST Community, I wrote a camera device handler that can control a Panasonic PTZ Camera. So you can move it in the four directions and also call 3 presets defined on the camera. This was successfully tested with these models: BL-C30A, BB-HCM511A, BB-HCM531A

Thanks to: patrickstuart & blebson

Link to the project: https://community.smartthings.com/t/panasonic-ptz-ip-camera/43336

