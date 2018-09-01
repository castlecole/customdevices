## GoControl Multifunction Siren

Device Handler for the GoControl Siren, Linear Siren and possibly some other generic sirens.
Model: WA105DBZ-1
<img src="https://github.com/castlecole/customdevices/blob/master/GoControl/GoControl_Siren.jpg" width="360px" height="360px" />
### This device handler supports the following capabilities:
- Alarm
- Battery
- Music Player
- Polling
- Switch
- Tone

**MAIN SCREEN**

<img src="https://github.com/castlecole/customdevices/blob/master/GoControl/MainScreen.png" width="281px" height="500px" />

The main screen displays the current state of the device and allows you to turn on/off the Siren, Strobe, Siren/Strobe and also play the Beep tone.

**SETTINGS**

**_NB: There are settings that can override the functionality of the siren, strobe, and both buttons._**

<img src="https://github.com/castlecole/customdevices/blob/master/GoControl/Settings1.png" width="281px" height="500px" />    <img src="https://github.com/castlecole/customdevices/blob/master/GoControl/Settings2.png" width="281px" height="500px" />
 

### Automatically turn off after:
- Turns the device off after certain amount of time.
- This is a hardware setting so it is not affected by ST scheduling issues.
- This should be set to “Disable Auto Off” when testing to prevent unexpected results.

### Length of beep:
- This determines the length of the beep in milliseconds, but it’s not reliable for short times.
- Beep lengths of at least 1 second (1000) are reliable, but only practical when the “strobe instead of beep” setting is enabled.

### What should the ‘both’ and ‘on’ commands turn on?
- Smart Home Monitor uses the both command so this setting allows you to override the behavior of the both command so that just the siren or strobe is turned on.

### Alarm Delay in Seconds
- If this is set to something other than 0, when the alarm is turned on, it will wait this amount of time before doing anything.
- This sets the default behavior, but you can also specify the delay as needed using the custom commands.
- The mobile app will display Alarm Pending and if the off command is executed before the delay is up, the alarm won’t go off.
- The mobile app will display Alarm Pending and if the off command is executed before the delay is up, the alarm won’t go off.

### Strobe during alarm delay
- If this feature is enabled and a delay time has been specified, the strobe will stay on during the delay.
- This sets the default behavior, but you can also enable this as needed using the custom commands.
- If your delay time exceeds the auto off time, the alarm won’t sound.
- The delay time is counted towards the auto off so if you set the delay to 45 seconds and have the auto off set to 60, the alarm will turn off 15 seconds after it turns on.

### Smart Home Monitor / Notify with Sound / CoRE / Rule Machine
The GoControl Multifunction Siren device handler supports the Music Player and Speech Synthesis capabilities so you can pass the commands as a custom message through the playText or Speak commands.
Here’s an overview of how you can use these features in the 3 SmartApps mentioned.

**Smart Home Monitor**
The Custom Monitoring section of SHM supports Audio Notifications and if you choose “Custom Message” for the Notification, you can specify the command to execute in the “Play this message” field.

**Speaker Notify with Sound**
You can setup a Custom Message action and specify the command to execute in the “Play this message” field.

**CoRE**
Add an action and select the siren. Then add a task for “Speak text” and enter the command into the “text” field.

**Rule Machine**
You can use the “Send or speak a message” action to execute the command. You do this by entering the command in the “Custom message to send” field, enabling the “Speak this message?” option, and choosing the siren for the “On this music device” field.

**Supported Capability Commands**

_(The content in parenthesis provide additional information and are not part of the command)_
You can enter these into the speak text or play text fields exactly as shown:
- off
- beep
- both
- siren
- strobe
- stop (executes off) 
- on (executes both) 
- play (executes both) 

**Supported Custom Commands**
_(The content in parenthesis provide additional information and is not part of the command)_
- beep #,#,bool (beepLengthMilliseconds, delaySeconds, useStrobe) 
- both #, #, bool (delaySeconds, autoOffSeconds, useStrobe) 
- siren #, #, bool (delaySeconds, autoOffSeconds, useStrobe) 
- strobe #, # (delaySeconds, autoOffSeconds) 

### Examples:
- Perform 250 millisecond beep: **beep 250, 0, false**
- Wait 15 seconds and then perform 100 millisecond beep: **beep 100, 15, false**
- Strobe for 15 seconds and then perform 100 millisecond beep: **beep 100, 15, true**
- Turn on siren and strobe immediately and turn off in 30 seconds: **strobe 0, 30, false**
- Display strobe for 30 seconds and then turn on siren and leave it on: **siren 30, 0, true**
- Wait 45 seconds, turn on sire/strobe, and turn it off 15 seconds later: **both 45, 15, false**

### Important Information
After switching to this device handler, make sure you go into the device’s settings in the mobile app and complete the configuration.
When testing, disable the auto off feature in the settings and I recommend putting the device under a pillow so you don’t end up going deaf.

Unfortunately the device has a slow response time so the beep feature may work most of the time for some users and rarely work for others.

I have a feeling that the beep feature is going to be too loud and unreliable for most people to use.

### Auto Off feature
The device has an optional built-in feature that automatically turns the device off after 30 seconds, 60 seconds, or 120 seconds.

- If you have the "Automatically turn off after" option set to one of those values, the alarm will turn off after that amount of time regardless of ST having major performance issues or your internet/power going down while the siren is on.
- Since the device has no off button and you can’t quickly get at and take out the batteries, it’s probably a good idea to have this feature enabled for normal use.
- It’s important to turn this feature off while testing because the time doesn’t get extended every time the device is turned on.

For example, if you have it set to turn off after 30 seconds and you manually turn it on and then off 1 second later, the physical device will automatically turn off 29 seconds later, even if you started another test.

Since you manually turned it off, that’s not a problem for the first test, but if you happened to start the second test about 30 seconds after starting the first test, the device turning off from the first test will probably prevent the second test from doing anything or it will turn off almost immediately after turning on.

### Default Behavior (Siren, Strobe, or Siren & Strobe)
The device allows you to specify what happens when the device is turned on, but when you change that value, the strobe flashes at least once to confirm the change.
- The DH could check to see what the device is currently set to, but that would require it to send a command, wait for the response and if it isn’t set to the correct value, it would have to send another command to change it so instead of doing that, the DH always changes that setting before turning the device on.
- Turning the device on too soon after changing the setting to siren causes the red light to stay on solid until the siren turns off which is why there’s a pause between the confirmation flash and the device turning on.
- If you only plan on using the device for one action, enabling the “Always use both/on setting?” prevents it from changing the setting first so you won’t have the confirmation flash or the long pause before it turns on. When that option is enabled, the action specified in the “What should the ‘both’ and ‘on’ commands turn on?” option will be performed regardless of which test button you press.
- If you want the option to use them independently, but you use the same action most of the time, checking the current setting instead of automatically changing it would be beneficial so I may add that as an optional setting in a future version.

### Beep Feature
- The device doesn’t support beeping/chirping so to provide that functionality, the DH turns the device on, waits the amount of time specified in the “Length of Beep” option and then turns the device off. Due to ST latency, a beep length of 0 ms usually sounds the same as a beep length of 100ms and sometimes the siren actually stays on for a couple of seconds. Since the platform changes ST made a couple of days ago, that’s only been happening about 5% of the time.
- When the “Strobe Instead of Beep?” option is enabled, the “Always use both/on setting?” option is ignored because it needs to be able to change the default behavior in order to ensure that it doesn’t make a sound.
- If you’re experiencing strange behavior, enable the “Enable debug logging?” option and look at Live Logging because it will output the action being performed and it always outputs the reason if a setting is overriding the normal functionality.

### Battery Status
- Most battery operated devices go to sleep and wake up at regular intervals so you can check the battery status. Since this device needs to be able to receive commands at all times, the device specs don’t mention that feature, and I haven’t seen any wake up events yet, I’m pretty sure this device doesn’t provide that functionality.
- Not having that functionality will prevent the battery status from getting updated at regular intervals so I’ve added the Poll Capability. Based on the ST documention, the poll method should be called every 10 minutes, but if ST calls it at all, it’s usually only called every couple of days.
- If you want it to check the battery more frequently, you could use a SmartApp like Pollster or the Simple Device Viewer SmartApp. I haven’t updated the documentation for that SmartApp yet, but there’s an option in the “Other Settings” section that allows you to enable polling.

### Possible Future Features
- I’m almost certain ST would reject this DH if I submitted it for publication because of the improper use of the Music Player/Speech Capabilities and also the reliability of the Beep feature.
- Because of that and since I really don’t need the features below because I’ve already implemented them in my Aeon Labs Siren DH, I’m not going to waste time implementing them unless they’re something users want and the DH becomes popular.
- Delayed Alarm: Allows you to specify an amount of time to wait before turning on alarm. Executing the off command before it turns on will cancel the delayed alarm.
- Strobe Delayed Alarm: Strobes for a specified amount of time and then turns on the siren. Executing the off command will turn off the strobe and prevent the siren from sounding.


## FAQs
_(The setting numbers mentioned in the answers below correspond to the numbers shown in the device’s settings screen.)_

**Why isn’t the TTS feature working?**
This device only has the ability to play the one sound it comes with. All the speak text and play text commands allow you to do is execute any of the device’s custom commands from any Smart App that supports the Speech Synthesis or Music Player capabilities.

**I’m using the delayed alarm with strobe feature, but the alarm never turns on?**
Setting #1 is a hardware setting so the delay time is counted towards the auto off time. If you have it set to 30 seconds, it will stop strobing after 30 seconds and the siren won’t turn on.

You should change setting #1 to “Disable Auto Off” while testing and for normal use, set it to the first option that’s higher than the total of the strobe delay and the amount of time you want the siren to stay on for.

Use the custom commands if you want more control over the auto off time.

Example: To make the device strobe for 15 seconds and then strobe and siren for 30 seconds, change setting #1 to 60 Seconds and use the command customSiren(15, 30, true) or for SmartApps that don’t support custom commands, use the speak or play text command with the message siren 15, 30, true.

**Why does the device take so long to turn on?**
This device is slow to respond to commands, but setting #3 also effects performance. When setting #3 is enabled it always sets the alarm type before turning on which causes the strobe to flash once before it turns on. When that setting is disabled, it first checks to see if it needs to change the setting, which eliminates the red flash every time, but also makes it take longer to turn on.
These settings will provide the best performance:
- Setting #3: Enable
- Setting #8: Disable

**Why does the beep command sometimes turn on the siren for 2-3 seconds instead of beeping?**
The response time of this device is unreliable so there’s no way to prevent this from happening. Most users probably won’t be able to use the beep functionality because of this problem and how loud the siren is.

**How can I just use the Strobe with Smart Home Monitor (SHM)?**
When SHM turns on the siren, it uses the “both” command, but if you change setting #2 to “Strobe Only”, it will override the behavior of that command and display the strobe instead.

**How can I use the siren without having it flash any lights?**
If you use the settings below, the light may flash the first time you try it, but it won’t flash again unless you use the strobe.
- Setting #2: Siren Only
- Settings #3, #4, and #5: Disabled

**How can I make the red light stay solid while the siren is on?**
You can’t with the delayed alarm, but for a regular alarm, use the settings:
- Settings #3 and #4: Enabled
- Setting #6: 0
_(This feature exploits a bug in the way the device works so it’s not completely reliable)_

