##Xiaomi Smart 1080P WiFi IP Camera with RTSP Streaming Hack 198

Being someone who just love cameras, I’ve recently come across a great *cheap* 1080p mini cam to incorporate into my home monitoring system. Only downside though, the camera is intended for the Asian market and assumes you’ll use their “Mi Home” app to control all of its features. But more so, it lacks one key feature: no RTSP. Boo… But lucky for us, and thanks to some very smart people over at this GitHub page, there’s a way to get an RTSP stream working with this camera. Read on and I’ll show you how.

Overview 
Update 2: (June, 2017)
Update 1: (May, 2017)
Hardware
Real Time Streaming Protocol
Instructions
Update the Firmware
Install Mi Home 
Fang-Hacks
Download Hack 
Apply the Hack 
The RTSP Stream URL 
Connecting to the Camera 
TinyCam Setup 
Going Further
Changing the Resolution
Heat Issue
Timestamp Overlay
Flip/Mirror Image
Remote Recording with FFMPEG
On-Device Recording with FFMPEG
RTSP Check Service
Limiting FPS (Frames Per Second)
Desktop Shortcut
VPN Server
Conclusion

#Xiaomi 1080p WiFi Camera
#Update 2: (June, 2017) 
Many have asked what would be the best approach to access the video stream via the internet. As luck would have it, I put together a guide for just that! Check out my post: Guide: VPN Server with the Rapsberry Pi.
#Update 1: (May, 2017) 
As of May, users are reporting difficulties in trying to hack this camera. However, according to the developer the hack will work fine regardless of the firmware version. Check this post over at github. But be aware that there are also reports that either a new model of the camera or new firmware is preventing the fang-hacks from working (apparently models with the QR code at the bottom and the MAC address starting with 34). Moreover for those having issues, I suggest using v4.0.11 of the MiHome app, as the most recent version v4.1.7, is apparently doing some sort of geo-blocking to prevent users outside China from using the Camera. Please go to the discord chat to get more info.
Hardware 
This camera can be procured at your favorite Asian online vendor, notable sites are (Banggood, TinyDeal, Fasttech and the list goes on…) The camera features a 2.7 inch CMOS sensor, 8X digital zoom, two-way audio and is capable of 1080p. It has a slot for an SD card and supports WiFi but unfortunately provides no Ethernet connection. As mentioned earlier, it is intended to use with the “Mi Home” app on the Apple Store or Google Play but to be honest, we don’t get care about this all that much…’cause we’ll hack it.
Real Time Streaming Protocol 
The Real Time Streaming Protocol (RTSP) is a network control protocol that can be used with a myriad of different programs and was designed such that client-side applications can begin displaying the audio and video content before the complete file has arrived. It can be delivered through UDP or TCP, but most importantly, this protocol is supported by VLC, QuickTime Player, mplayer, RealPlayer (if that’s even a thing now) and most 3G/4G compatible mobile phones (mileage may vary though).
But by default this camera does not support RTSP and is cloud only (which is a tad bit worrisome if you ask me but I digress) Fortunate for us, there’s a project on GitHub to enable RTSP. One caveat is that you’ll lose some app-only features, like motion detection – a small price to pay for RTSP I guess. Hopefully this GitHub project, what I’ll refer to as “fang-hacks” in this post, will add more app-only features over time – one can hope!
Instructions 
Follow these instructions to get up and running but please do so on a full stomach to avoid errors…apply with caution and needless to say, I will not be held responsible for bricked cameras. Basic command line skills are almost essential for the “Going Further” section below.
Update the Firmware 
We’ll need to first update the firmware. As of this writing, my camera came with version 2.9.0.7, latest version being 3.0.3.56. But before we can update the firmware, we’ll have to install the Mi Home app. Besides upgrading the firmware, the app enables us to supply our WiFi password such that we can pair the camera to our network – a much needed step, if I do say so myself.
Install Mi Home
For some reason, I wasn’t able to install Mi Home through Google Play as it prompted me that none of my devices were compatible. OK then, whatevers… For this reason, I decided to sideload the app via ADB. I went to apkpure and downloaded the app manually, then installed it via ADB. I won’t get too much into detail about ADB, google if unfamiliar.
adb install MiHome_v4.0.11_apkpure.com.apk
Next, we shall set up the camera.
Open the app with whichever finger you’re most comfortable with and select Mainland China, if you select another region you may run into install problems later. 
Power on the camera. 
Use the included “paperclip poking device” to depress the setup pin on the bottom of the camera. The camera will make an audible sound and you’ll hear a voice speaking a foreign language that I don’t quite understand. 
After a few seconds, a pop up will appear on your phone or tablet, click on OK to begin the pairing process, however, you’ll need to sign in first with a valid Xiaomi account. Create a Xiaomi account and sign in and click next. 
Select your WiFi network and fill in your WiFi password. 
You’ll see a QR Code on your screen, slowly point it in the vicinity of the camera’s field of view so it can read the code. If successful, you’ll hear some more audible words – probably telling you that you’re about to do something bad, really bad, so stop it, damn it! 
Wait a little while to finish the setup. If you get a timeout while you see the percentage, hit “try again”. 
Now click on the your camera, it should say “online”. In the top right corner you’ll see an ellipse icon, click it and go into “General Settings” and “Check for updates”. 
Begin upgrade and wait some more. 
#Fang-Hacks 
After upgrading the firmware, go to the GitHub page of fang-hacks. Read the information carefully before you proceed – but don’t worry, the process isn’t too difficult. 
#Download Hack
Download the image file from the releases page, currently as of this writing it’s up to V0.2.0. After you unzip this file you’ll need to write the fang-hacks image to your SD card. Various options exist to accomplish this feat.
#Windows
For Windows we can use a tool called Win32DiskImager. Download this small executable and proceed with these instructions while writing the file fanghacks_v0.2.0.img to the card.
#Mac OS X
You’ll need to get to the command line and proceed as follows. To list the disks currently connected:
diskutil list
Next locate the target disk/card (assume disk2 for this example) and un-mount the disk.
diskutil unmountDisk /dev/disk2
Then write the image.
dd if=/path/to/fanghacks_v0.2.0.img of=/dev/rdisk2 bs=1m
#Linux
On Linux, very similar to Mac OS X, get to the command line with Terminal App and proceed as follows (assuming your SD card is sdb.)
dd if=/path/to/fanghacks_v0.2.0.img of=/dev/sdb bs=1M
#Apply the Hack
Power on the camera and wait until the status LED is solid blue – this indicates that the camera is connected to your WiFi network.
Now put the SD card in the slot at bottom and you’ll hear a “clanking” sound when the hack starts up.
Visit this webpage to enable the hack: 
http://DEVICE_IP/cgi-bin/status 
The IP address should be attainable by inspecting your router Device’s page). Now click on “Apply” to activate the hacks (this will survive reboots so don’t fret provided you keep the SD card in the camera).
A word of warning, do not activate “Disable cloud applications”, if you do and you can’t connect to your WiFi network for some reason, you will turn your nice new camera into a somewhat inadequate paperweight!

#FangHacks: Status Page
Click on “Manage scripts” to see if all the scripts have been started successfully.

#FangHacks: Status Page
You can also turn off IR (used for night vision) so that you can point this out a windows without the IR glare. The 21-ir-control service controls this. 
Furthermore, the hack enables certain services that will aid in administrating the camera in the future. The following ports should now be open:
21 FTP
22 SSH
80 HTTP
554 RTSP 

#The RTSP Stream URL
If all is successful, you are now presented with the fruit of your labor: TADA!!! The RTSP stream! Behold its greatness!!!
It can be accessed directly from:
rtsp://DEVICE_IP/unicast 
You can use VLC and click “Open Network” with this URL, to view the stream. Congrats on a job well done!
Connecting to the Camera
At some point or another you’ll need to make modifications to certain files directly on the camera. Your options are SSH/Telnet:
ssh root@DEVICE_IP
telnet DEVICE_IP 2323
Or any FTP/SFTP program will do as well but I’ll let you mess with that…
BTW, these are the default credentials needed to log in into the camera.
username: root
password: ismart12 
#TinyCam Setup
TinyCam is a great Android app that I use to manage my cameras from mobile devices. It supports fang-hacks on this camera as of version 7.5. This is how I setup mine:

FangHacks: TinyCam Setup

#Going Further 
The following sections are things I picked up along the way that I thought I’d share because I’m a swell guy. Be forewarned that this is all based on version 0.2.0 of fang-hacks, things will inevitably change in the future (maybe) rendering the below unneeded or obsolete. Also, a big warning, be weary of Windows text editors while making manual edits to files. They’re notorious for adding control characters to line endings rendering your scripts un-runable.
I recommend that the majority of work be done with the command line and with the built-in vi text editor to avoid screw-ups. If you feel uncomfortable with this, Google usage or simply stay away!
Changing the Resolution 
Depending on how robust your WiFi network is, you may want to reduce the resolution from 1080p to 720p. This is how it’s done. You’ll to need change the following file via any method you’re most comfortable with (you can either FTP/SFTP or SSH/Telnet – I suggest SSH/Telnet with something like putty if you’re on Windows)
File to modify:
/media/mmcblk0p2/data/etc/scripts/20-rtsp-server
Remove or comment out this line with a hash (#): 
snx_rtsp_server -W 1920 -H 1080 -Q 10 -b 4096 -a >$LOG 2>&1 &
Then add in its place, this line:
snx_rtsp_server -W 1280 -H 720 -Q 10 -b 2048 -a >$LOG 2>&1 &
#Heat Issue 
After I installed the hack, I noticed that the camera got a little too hot to the touch for my liking. Searching the GitHub pages I came across a posted fix. 
There appears to be a new version of the fang-ir-control.sh script that’s not in the V0.2.0 release that’s suppose to help with heat. You can go ahead and modify the file below if you want to attempt this yourself.
The file to change is: 
/media/mmcblk0p2/data/usr/bin/fang-ir-control.sh
Update that file with this one.
#Timestamp Overlay 
To get a timestamp showing the current date/time on the top left corner of the image, follow these steps.

FangHacks: Timestamp Overlay

First Fix your TimeZone
This can be accomplished on the main status page (http://DEVICE_IP/cgi-bin/status), in the TZ field and by clicking “set”. You can look up your timezone by going here.
For example if you live in Chicago – not sure why you’d want to – (I kid, I kid – I hear it’s a great city) then the corresponding timezone would be: America/Chicago and I would copy this in the TZ field: 
CST6CDT,M3.2.0,M11.1.0
Install snx_isp_ctl
Add the file snx_isp_ctl to the camera. You can find the file from here – in fact it should already be on your camera in this path:
/media/mmcblk0p2/data/test/
You’ll first need to copy this file to:
/media/mmcblk0p2/data/usr/bin
Once copied you’ll need to add the executable flag (+x) to it, you can either do that with your FTP client or from SSH/Telnet with: 
chmod +x snx_isp_ctl
+1 Hour Offset Issue
If after adding the timestamp overlay you noticed that the time is +1 hour off, please see the following. It would appear that this is a common problem.
Run on Boot
Finally update the file 20-rtsp-server which gets executed on boot. Add the following line in this file (can be found in /media/mmcblk0p2/data/etc/scripts) right after the snx_rtsp_server line.
snx_isp_ctl --osdset-en 1 --osdset-datastr Date --osdset-ts 1 --osdset-template 1234567890./-:Date
Or with a black background:
snx_isp_ctl --osdset-en 1 --osdset-datastr Date --osdset-ts 1 --osdset-template 1234567890./-:Date --osdset-gain 1 --osdset-bgtransp 0x1 --osdset-bgcolor 0x000000
Then finally reboot.
Flip/Mirror Image 
Because we copied over the snx_isp_ctl file, we can also do handy things like flipping or mirroring the image.
To flip the image upside down:
snx_isp_ctl --mfset-mode 1
To restore the image:
snx_isp_ctl --mfset-mode 0
To mirror the image:
snx_isp_ctl --mfset-mode 2
snx_isp_ctl --mfset-mode 3
Again, if you want this to survive reboots, add the appropriate line in 20-rtsp-server file after the snx_rtsp_server line.
Remote Recording with FFMPEG 
If you’re like me, you’re probably interested in recording video from the camera. As the “Mi Home” app does provide a means for recording, it does so by uploading your video to the cloud. Now, I’d rather not get into the privacy concerns this raises but more to the point – who cares – ’cause we’re not using the app after all.
If on the other another hand, you’ve got a Linux server or some other box with ffmpeg installed, this is the command line to produce a fairly decent recording. Obviously substitute your DEVICE_IP and PATH_TO_RECORDING. The following produces multiple hour long recordings.
ffmpeg -stimeout 600 -rtsp_transport udp -i rtsp://<DEVICE_IP>/unicast -c copy -map 0 -f segment -segment_time 3600 -segment_wrap 100 -segment_format mov -reset_timestamps 1 "/<PATH_TO_RECORDING>/capture-%03d.mp4"
On-Device Recording with FFMPEG 
Interestingly, there’s a way to record video directly to the SD card! I was able to fudge together something that accomplishes this (to a certain extent) while integrating it into the existing fang-hacks interface. I basically created a new button on the main “Status” page called “Recordings”, which in turn brings you to a new screen that allows you start ffmpeg to begin recording. From there you can click a recording and view it directly from Google Chrome.

#Custom On-Device Recording with FFMPEG
Before attempting this, I suggest expanding your SD card via the interface. Be warned, this could take hours! 
On with my “hack” of the hack – if that makes sense…
My custom code relies on ffmpeg being in the directory: “/media/mmcblk0p2/data/test/ffmpeg”, which should be the case with fang-hacks, so you don’t need to do anything about this.
However, three additional things need to be done after downloading my update.
#Link for my code. 
We’ll be working in the following directory, so all modified files will be placed there. Use a SFTP/FTP client to accomplish this. 
          
/media/mmcblk0p1/bootstrap/www       
Make the following changes
Overwrite action with the one contained in the zip file and add both files player and record. 
Then add the highlighted line to the status file. 
<button title='Network' type='button' onClick="window.location.href='network'">Network</button>      
<button title='View /tmp/hacks.log' type='button' onClick="window.location.href='action?cmd=showlog'">View log</button>
<button title='Manage scripts' type='button' onClick="window.location.href='record'">Recordings</button>
<hr/>      
Now reboot. 
Hopefully it works.
Note: when you hit “start” to begin a recording, give the camera some time before you see a clickable file.
After some experimentation, this method might not be the best for long recordings for multiple reasons. First, I’m noticing some corrupted segments while recording, probably due to the weak processor on the camera and second; scrubbing through long recordings may not work at that well due to the nature of how the file gets buffered chunks at a time. Also, this will “unsync” your code from the main developer when it comes time for updates, possibly breaking things. But you can try it if you like…
If you are adventurous, you can probably incorporate some sort of motion sensing (albeit through another device, like a Raspberry Pi with a motion sensor) and then kick off a recording on the camera with a single URL, like so:
http://DEVICE_IP/cgi-bin/action?cmd=record&submit=Start&filename=test
Yes I know, an ugly and costly hack to get motion detection, but the possibility does exist to add this camera to your custom setup.
RTSP Check Service 
I haven’t had this problem as of yet, but if you noticed your RTSP going down, crashing, or not responding you can implement these simple scripts to ensure it remains up.
In the following directory, create a file called: rtsp-check.sh
1
2
cd /media/mmcblk0p2/data/usr/bin
touch rtsp-check.sh
And put the contents that follows:
1
2
3
4
5
6
7
8
9
10
11
#!/bin/sh
 
while true; do
if pgrep -x "snx_rtsp_server" > /dev/null
then
    :
else
    /media/mmcblk0p2/data/etc/scripts/20-rtsp-server start
fi
sleep 2
done
Then make the file executable.
chmod +x rtsp-check.sh
Next move to a different directory and create a file called: 99-rtsp-check
1
2
cd /media/mmcblk0p2/data/etc/scripts
touch 99-rtsp-check
Now put the below in that file.
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
#!/bin/sh
PIDFILE="/var/run/rtsp-check.pid"
 
status()
{
  pid="$(cat "$PIDFILE" 2>/dev/null)"
  if [ "$pid" ]; then
    kill -0 "$pid" >/dev/null && echo "PID: $pid" || return 1
  fi
}
 
start()
{
  echo "Starting rtsp-check script..."
  rtsp-check.sh </dev/null >/dev/null 2>&1 &
  echo "$!" > "$PIDFILE"
}
 
stop()
{
  pid="$(cat "$PIDFILE" 2>/dev/null)"
  if [ "$pid" ]; then
     kill $pid || rm "$PIDFILE"
  fi
}
 
if [ $# -eq 0 ]; then
  start
else
  case $1 in start|stop|status)
    $1
    ;;
  esac
fi
Once done, you can administer this service the same way you do others, via the “Manage scripts” page.
http://DEVICE_IP/cgi-bin/scripts 

#RTSP Check Service
#Limiting FPS (Frames Per Second) 
You may want to limiting the frame rate to reduce lag. Adding the -F switch to snx_rtsp_server command in the 20-rtsp-server file will accomplish this.
snx_rtsp_server -W 1280 -H 720 -Q 10 -F 15 -b 2048 -a >$LOG 2>&1 & 
#Desktop Shortcut 
If you have VLC installed, you can create a file on your desktop as a shortcut with the following line in it.
rtsp://DEVICE_IP/unicast 
I gave the file a .vlc extension and ensured the extension is associated with the VLC application. I tested this on MAC OS X.
#VPN Server 
If you’re interested on how access the camera stream via a secure connection, please check this post: Guide: VPN Server with the Rapsberry Pi.
#Conclusion 
So far the hacks have been stable and this cheap camera is proving to be more than a bargain. A big shout-out goes out to the smart individuals who put the fang-hacks project together. Oh, as of this writing (March 23rd, 2017) there’s also a discord chat going on, so if you have any burning questions, pop on over. Again, we all owe these dudes a debt of gratitude for their hard work and effort. Well done.
