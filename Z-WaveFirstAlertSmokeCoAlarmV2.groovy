/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

def version() {
	  return "v2 (20170320)\nFirst Alert Smoke & CO Detector - Z-Wave"
}

metadata {
	definition (name: "Z-Wave First Alert Smoke CO Alarm", namespace: "castlecole", author: "SmartThings") {
		capability "Smoke Detector"
		capability "Carbon Monoxide Detector"
		capability "Sensor"
		capability "Battery"
		capability "Health Check"
    		capability "Refresh"
		attribute  "alarmState", "string"
		attribute  "lastTested", "String"
		attribute  "lastTestedDate", "Date"
		attribute  "lastMonoxide", "String"
		attribute  "lastMonoxideDate", "Date"
		attribute  "lastCheckinDate", "Date"		
		attribute  "lastCheckin", "string"
		attribute  "lastSmoke", "String"
		attribute  "lastSmokeDate", "Date"
		attribute  "batteryRuntime", "String"

		command "resetBatteryRuntime"

		fingerprint mfr:"0138", prod:"0001", model:"0002", deviceJoinName: "First Alert Smoke Detector and Carbon Monoxide Alarm (ZCOMBO)"
	}

	simulator {
		status "smoke": "command: 7105, payload: 01 FF"
		status "clear": "command: 7105, payload: 01 00"
		status "test": "command: 7105, payload: 0C FF"
		status "carbonMonoxide": "command: 7105, payload: 02 FF"
		status "carbonMonoxide clear": "command: 7105, payload: 02 00"
		status "battery 100%": "command: 8003, payload: 64"
		status "battery 5%": "command: 8003, payload: 05"
	}

	preferences {
		//Date & Time Config
		input description: "", type: "paragraph", element: "paragraph", title: "DATE & CLOCK"    
		input name: "dateformat", type: "enum", title: "Set Date Format\nUS (MDY) - UK (DMY) - Other (YMD)", description: "Date Format", options:["US","UK","Other"]
		input name: "clockformat", type: "bool", title: "Use 24 hour clock?"
		input description: "Version: ${version()}", type: "paragraph", element: "paragraph", title: ""
	}

	tiles (scale: 2){
		multiAttributeTile(name:"smoke", type:"generic", width:6, height:4){
			tileAttribute ("device.alarmState", key: "PRIMARY_CONTROL") {
				attributeState("clear", label:"CLEAR", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-clear-co0.png", backgroundColor:"#00a0dc")
				attributeState("smoke", label:"SMOKE DETECTED!", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-notclear-co0.png", backgroundColor:"#ed0000")
				attributeState("carbonMonoxide", label:"CARBON MONOXIDE DETECTED!", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-notclearco-co0.png", backgroundColor:"#e86d13")
				attributeState("tested", label:"TESTED!", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-notclearco0.png", backgroundColor:"#e86d13")
			}
			tileAttribute("device.lastCheckin", key: "SECONDARY_CONTROL") {
				attributeState("default", label:'Last Checkin: ${currentValue}', icon: "st.Health & Wellness.health9")
			}
		}

	valueTile("battery", "device.battery", decoration: "flat", inactiveLabel: false, width: 2, height: 2) {
            	state "default", label:'${currentValue}%'+"\n", unit:"", icon:"https://raw.githubusercontent.com/castlecole/Xiaomi/master/Battery.png", 
            			backgroundColors: [
					[value: 0, color: "#ff1800"],
					[value: 10, color: "#fb854a"],
					[value: 25, color: "#ceec24"],
					[value: 50, color: "#71f044"],
					[value: 75, color: "#33d800"]
            			]
	}

	valueTile("lastCheckin", "device.lastCheckin", inactiveLabel: false, decoration: "flat", width: 4, height: 1) {
        	state "default", label:'Last Checkin:\n ${currentValue}', backgroundColor:"#00a0dc"
	}

	valueTile("lastSmoke", "device.lastSmoke", inactiveLabel: false, decoration: "flat", width: 4, height: 1) {
        	state "default", label:'Last Smoke Detected:\n ${currentValue}'
	}

	valueTile("lastTested", "device.lastTested", inactiveLabel: false, decoration: "flat", width: 4, height: 1) {
        	state "default", label:'Last Tested:\n ${currentValue}'
	}

	standardTile("info", "device.alarmState", inactiveLabel: True, decoration: "flat", width:2, height:2) {
		state "clear", icon:"", label: "All Clear"
		state "smoke", icon:"", label: "Fire! Fire! Fire!"
		state "carbonMonoxide", icon:"", label: "Carbon Monoxide"
		state "tested", icon:"", label: "Alarm Test"
	}

	valueTile("lastMonoxide", "device.lastMonoxide", inactiveLabel: false, decoration: "flat", width: 4, height: 1) {
		state "default", label:'Last Carbon Monoxide Detected:\n ${currentValue}'
	}

   	valueTile("batteryRuntime", "device.batteryRuntime", inactiveLabel: false, decoration: "flat", width: 4, height: 1) {
            state "batteryRuntime", label:'Battery Changed (tap to reset):\n ${currentValue}', unit:"", action:"resetBatteryRuntime"
   	}

	standardTile("refresh", "device.refresh", inactiveLabel: False, decoration: "flat", width: 2, height: 2) {
		state "default", action:"refresh.refresh", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/refresh.png"
    	}

	multiAttributeTile(name:"smoke2", type:"generic"){
		tileAttribute ("device.alarmState", key:"PRIMARY_CONTROL") {
			attributeState("clear", label:"CLEAR", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/House-CO-Normal.png", backgroundColor:"#00a0dc")
			attributeState("detected", label:"SMOKE", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/House-Fire-CO.png", backgroundColor:"#ed0000")
			attributeState("carbonMonoxide", label:"MONOXIDE", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/House-CO-Event.png", backgroundColor:"#e86d13")
                        attributeState("tested", label:"TEST", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/House-Fire-CO.png", backgroundColor:"#e86d13")
        	}
	}

	main "smoke2"
	details(["smoke", "battery", "lastSmoke",  "lastMonoxide", "lastTested", "batteryRuntime", "refresh"])

	}
}


//Reset the date displayed in Battery Changed tile to current date
def resetBatteryRuntime() {
    	def now = new Date().format("MMM dd yyyy", location.timeZone)
    	sendEvent(name: "batteryRuntime", value: now)
}


def installed() {

	sendEvent(name: "lastTested", value: "--", displayed: false)
	sendEvent(name: "lastSmoke", value: "--", displayed: false)
	sendEvent(name: "lastMonoxide", value: "--", displayed: false)

	// Device checks in every hour, this interval allows us to miss one check-in notification before marking offline
	sendEvent(name: "checkInterval", value: 2 * 60 * 60 + 2 * 60, displayed: false, data: [protocol: "zwave", hubHardwareId: device.hub.hardwareID])

	def cmds = []
	createSmokeOrCOEvents("allClear", cmds) // allClear to set inital states for smoke and CO
	cmds.each { cmd -> sendEvent(cmd) }
}


def updated() {
	// Device checks in every hour, this interval allows us to miss one check-in notification before marking offline
	sendEvent(name: "checkInterval", value: 2 * 60 * 60 + 2 * 60, displayed: false, data: [protocol: "zwave", hubHardwareId: device.hub.hardwareID])
}

def parse(String description) {
	def results = []

	//  send event for heartbeat    
  	def now = new Date().format("yyyy MMM dd EEE h:mm:ss a", location.timeZone)
  	sendEvent(name: "lastCheckin", value: now)

	if (description.startsWith("Err")) {
	    results << createEvent(descriptionText:description, displayed:true)
	} else {
		def cmd = zwave.parse(description, [ 0x80: 1, 0x84: 1, 0x71: 2, 0x72: 1 ])
		if (cmd) {
			zwaveEvent(cmd, results)
		}
	}

	log.debug "'$description' parsed to ${results.inspect()}"
	return results
}


def refresh() {

	If (device.lastTested == "" ) {
		sendEvent(name: "lastTested", value: "--", displayed: false)
	}

	If (device.lastSmoke == "" ) {
		sendEvent(name: "lastSmoke", value: "--", displayed: false)
	}

  	If (device.lastMonoxide == "" ) {
		sendEvent(name: "lastMonoxide", value: "--", displayed: false)
	}

  	// Clear smoke in case they pulled batteries and we missed the clear msg
	if(device.currentValue("smoke") != "clear") {
		createSmokeEvents("smokeClear", results)
	}
}

def createSmokeOrCOEvents(name, results) {
	def text = null

  	//  Times for events
  	def now = new Date().format("yyyy MMM dd EEE h:mm:ss a", location.timeZone)
	def nowDate = new Date(now).getTime()

	switch (name) {
		case "smoke":
			sendEvent(name: "lastSmoke", value: now, displayed: false)
			sendEvent(name: "lastSmokeDate", value: nowDate, displayed: false)
			text = "$device.displayName smoke was detected!"
			// these are displayed:false because the composite event is the one we want to see in the app
			results << createEvent(name: "smoke",          value: "detected", descriptionText: text, displayed: false)
			break
		case "carbonMonoxide":
			sendEvent(name: "lastMonoxide", value: now, displayed: false)
			sendEvent(name: "lastMonoxideDate", value: nowDate, displayed: false)
			text = "$device.displayName carbon monoxide was detected!"
			results << createEvent(name: "carbonMonoxide", value: "detected", descriptionText: text, displayed: false)
			break
		case "tested":
			sendEvent(name: "lastTested", value: now, displayed: false)
			sendEvent(name: "lastTestedDate", value: nowDate, displayed: false)
			text = "$device.displayName was tested"
			results << createEvent(name: "smoke",          value: "tested", descriptionText: text, displayed: false)
			results << createEvent(name: "carbonMonoxide", value: "tested", descriptionText: text, displayed: false)
			break
		case "smokeClear":
			text = "$device.displayName smoke is clear"
			results << createEvent(name: "smoke",          value: "clear", descriptionText: text, displayed: false)
			name = "clear"
			break
		case "carbonMonoxideClear":
			text = "$device.displayName carbon monoxide is clear"
			results << createEvent(name: "carbonMonoxide", value: "clear", descriptionText: text, displayed: false)
			name = "clear"
			break
		case "allClear":
			text = "$device.displayName all clear"
			results << createEvent(name: "smoke",          value: "clear", descriptionText: text, displayed: false)
			results << createEvent(name: "carbonMonoxide", value: "clear", displayed: false)
			name = "clear"
			break
		case "testClear":
			text = "$device.displayName test cleared"
			results << createEvent(name: "smoke",          value: "clear", descriptionText: text, displayed: false)
			results << createEvent(name: "carbonMonoxide", value: "clear", displayed: false)
			name = "clear"
			break
	}

	// This composite event is used for updating the tile
	results << createEvent(name: "alarmState", value: name, descriptionText: text)
}

def zwaveEvent(physicalgraph.zwave.commands.alarmv2.AlarmReport cmd, results) {
	if (cmd.zwaveAlarmType == physicalgraph.zwave.commands.alarmv2.AlarmReport.ZWAVE_ALARM_TYPE_SMOKE) {
		if (cmd.zwaveAlarmEvent == 3) {
			createSmokeOrCOEvents("tested", results)
		} else {
			createSmokeOrCOEvents((cmd.zwaveAlarmEvent == 1 || cmd.zwaveAlarmEvent == 2) ? "smoke" : "smokeClear", results)
		}
	} else if (cmd.zwaveAlarmType == physicalgraph.zwave.commands.alarmv2.AlarmReport.ZWAVE_ALARM_TYPE_CO) {
		createSmokeOrCOEvents((cmd.zwaveAlarmEvent == 1 || cmd.zwaveAlarmEvent == 2) ? "carbonMonoxide" : "carbonMonoxideClear", results)
	} else switch(cmd.alarmType) {
		case 1:
			createSmokeOrCOEvents(cmd.alarmLevel ? "smoke" : "smokeClear", results)
			break
		case 2:
			createSmokeOrCOEvents(cmd.alarmLevel ? "carbonMonoxide" : "carbonMonoxideClear", results)
			break
		case 12:  // test button pressed
			createSmokeOrCOEvents(cmd.alarmLevel ? "tested" : "testClear", results)
			break
		case 13:  // sent every hour -- not sure what this means, just a wake up notification?
			if (cmd.alarmLevel == 255) {
				results << createEvent(descriptionText: "$device.displayName checked in", isStateChange: false)
			} else {
				results << createEvent(descriptionText: "$device.displayName code 13 is $cmd.alarmLevel", isStateChange:true, displayed:false)
			}

			// Clear smoke in case they pulled batteries and we missed the clear msg
			if(device.currentValue("smoke") != "clear") {
				createSmokeOrCOEvents("smokeClear", results)
			}

			// Check battery if we don't have a recent battery event
			if (!state.lastbatt || (now() - state.lastbatt) >= 48*60*60*1000) {
				results << response(zwave.batteryV1.batteryGet())
			}
			break
		default:
			results << createEvent(displayed: true, descriptionText: "Alarm $cmd.alarmType ${cmd.alarmLevel == 255 ? 'activated' : cmd.alarmLevel ?: 'deactivated'}".toString())
			break
	}
}

// SensorBinary and SensorAlarm aren't tested, but included to preemptively support future smoke alarms
//
def zwaveEvent(physicalgraph.zwave.commands.sensorbinaryv2.SensorBinaryReport cmd, results) {
	if (cmd.sensorType == physicalgraph.zwave.commandclasses.SensorBinaryV2.SENSOR_TYPE_SMOKE) {
		createSmokeOrCOEvents(cmd.sensorValue ? "smoke" : "smokeClear", results)
	} else if (cmd.sensorType == physicalgraph.zwave.commandclasses.SensorBinaryV2.SENSOR_TYPE_CO) {
		createSmokeOrCOEvents(cmd.sensorValue ? "carbonMonoxide" : "carbonMonoxideClear", results)
	}
}

def zwaveEvent(physicalgraph.zwave.commands.sensoralarmv1.SensorAlarmReport cmd, results) {
	if (cmd.sensorType == 1) {
		createSmokeOrCOEvents(cmd.sensorState ? "smoke" : "smokeClear", results)
	} else if (cmd.sensorType == 2) {
		createSmokeOrCOEvents(cmd.sensorState ? "carbonMonoxide" : "carbonMonoxideClear", results)
	}
}

def zwaveEvent(physicalgraph.zwave.commands.wakeupv1.WakeUpNotification cmd, results) {
	results << createEvent(descriptionText: "$device.displayName woke up", isStateChange: false)
	if (!state.lastbatt || (now() - state.lastbatt) >= 56*60*60*1000) {
		results << response([
				zwave.batteryV1.batteryGet().format(),
				"delay 2000",
				zwave.wakeUpV1.wakeUpNoMoreInformation().format()
			])
	} else {
		results << response(zwave.wakeUpV1.wakeUpNoMoreInformation())
	}
}

def zwaveEvent(physicalgraph.zwave.commands.batteryv1.BatteryReport cmd, results) {
	def map = [ name: "battery", unit: "%", isStateChange: true ]
	state.lastbatt = now()
	if (cmd.batteryLevel == 0xFF) {
		map.value = 1
		map.descriptionText = "$device.displayName battery is low!"
	} else {
		map.value = cmd.batteryLevel
	}
	results << createEvent(map)
}

def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityMessageEncapsulation cmd, results) {
	def encapsulatedCommand = cmd.encapsulatedCommand([ 0x80: 1, 0x84: 1, 0x71: 2, 0x72: 1 ])
	state.sec = 1
	log.debug "encapsulated: ${encapsulatedCommand}"
	if (encapsulatedCommand) {
		zwaveEvent(encapsulatedCommand, results)
	} else {
		log.warn "Unable to extract encapsulated cmd from $cmd"
		results << createEvent(descriptionText: cmd.toString())
	}
}

def zwaveEvent(physicalgraph.zwave.Command cmd, results) {
	def event = [ displayed: false ]
	event.linkText = device.label ?: device.name
	event.descriptionText = "$event.linkText: $cmd"
	results << createEvent(event)
}
