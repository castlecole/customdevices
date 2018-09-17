/**
 *  Copyright 2017 SmartThings
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
import physicalgraph.zigbee.zcl.DataType

def version() {
	return "v2 (20180917)\nRGB Light - Zigbee LL"
}

metadata {
	definition (name: "ZLL RGB Bulb", namespace: "castlecole", author: "SmartThings", ocfDeviceType: "oic.d.light", minHubCoreVersion: '000.021.00001', executeCommandsLocally: true) {

		capability "Actuator"
		capability "Color Control"
		capability "Configuration"
		capability "Polling"
		capability "Refresh"
		capability "Switch"
		capability "Switch Level"
		capability "Health Check"

		attribute "lastCheckinDate", "Date"		
		attribute "lastCheckin", "string"

		fingerprint profileId: "C05E", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0300", outClusters: "0019"
		fingerprint profileId: "C05E", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0300, 1000", outClusters: "0019"
		fingerprint profileId: "C05E", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0300, 1000", outClusters: "0019", "manufacturer":"OSRAM", "model":"Classic A60 RGBW", deviceJoinName: "OSRAM LIGHTIFY LED Classic A60 RGBW"
		fingerprint profileId: "C05E", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0300, 0B04, FC0F", outClusters: "0019", manufacturer: "OSRAM", model: "PAR 16 50 RGBW - LIGHTIFY", deviceJoinName: "OSRAM LIGHTIFY RGBW PAR 16 50"
		fingerprint profileId: "C05E", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0300, 1000, FC0F", outClusters: "0019", manufacturer: "OSRAM", model: "CLA60 RGBW OSRAM", deviceJoinName: "OSRAM LIGHTIFY LED Classic A60 RGBW"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000,FC0F", outClusters: "0019", manufacturer: "OSRAM", model: "Flex RGBW", deviceJoinName: "OSRAM LIGHTIFY Flex RGBW"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000,FC0F", outClusters: "0019", manufacturer: "OSRAM", model: "Gardenpole RGBW-Lightify", deviceJoinName: "OSRAM LIGHTIFY Gardenpole RGBW"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000,FC0F", outClusters: "0019", manufacturer: "OSRAM", model: "LIGHTIFY Outdoor Flex RGBW", deviceJoinName: "OSRAM LIGHTIFY Outdoor Flex RGBW"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000,FC0F", outClusters: "0019", manufacturer: "OSRAM", model: "LIGHTIFY Indoor Flex RGBW", deviceJoinName: "OSRAM LIGHTIFY Indoor Flex RGBW"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LCT001", deviceJoinName: "Philips Hue A19"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LCT002", deviceJoinName: "Philips Hue BR30"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LCT003", deviceJoinName: "Philips Hue GU10"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LCT007", deviceJoinName: "Philips Hue A19"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LCT010", deviceJoinName: "Philips Hue A19"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LCT011", deviceJoinName: "Philips Hue BR30"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LCT012", deviceJoinName: "Philips Hue Candle"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LCT014", deviceJoinName: "Philips Hue A19"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LCT015", deviceJoinName: "Philips Hue A19"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LCT016", deviceJoinName: "Philips Hue A19"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LST001", deviceJoinName: "Philips Hue Lightstrip"
		fingerprint profileId: "C05E", inClusters: "0000,0003,0004,0005,0006,0008,0300,1000", outClusters: "0019", manufacturer: "Philips", model: "LST002", deviceJoinName: "Philips Hue Lightstrip"
		fingerprint profileId: "C05E", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0300", outClusters: "0019", manufacturer: "innr", model: "RB 185 C", deviceJoinName: "innr Smart Bulb RGBW"
	}

    	// simulator metadata
	simulator {
    	}

	preferences {
		//Date & Time Config
		input description: "", type: "paragraph", element: "paragraph", title: "DATE & CLOCK"    
		input name: "dateformat", type: "enum", title: "Set Date Format\nUS (MDY) - UK (DMY) - Other (YMD)", description: "Date Format", options:["US","UK","Other"]
		input name: "clockformat", type: "bool", title: "Use 24 hour clock?"
		input description: "Version: ${version()}", type: "paragraph", element: "paragraph", title: ""
	}
	
 	// UI tile definitions
	tiles(scale: 2) {
		multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'', action:"switch.off", icon:"https://raw.githubusercontent.com/castlecole/Xiaomi/master/led_light_bulb_on.png", backgroundColor:"#359148", nextState:"turningOff"
				attributeState "off", label:'', action:"switch.on", icon:"https://raw.githubusercontent.com/castlecole/Xiaomi/master/led_light_bulb_off.png", backgroundColor:"#00a0dc", nextState:"turningOn"
				attributeState "turningOn", label:'${name}', action:"switch.off", icon:"https://raw.githubusercontent.com/castlecole/Xiaomi/master/led_light_bulb_off.png", backgroundColor:"#359148", nextState:"turningOff"
				attributeState "turningOff", label:'${name}', action:"switch.on", icon:"https://raw.githubusercontent.com/castlecole/Xiaomi/master/led_light_bulb_off.png", backgroundColor:"#00a0dc", nextState:"turningOn"
			}
			tileAttribute("device.lastCheckin", key: "SECONDARY_CONTROL") {
				attributeState("default", label:'Last Checkin: ${currentValue}', icon: "st.Health & Wellness.health9")
			}
			tileAttribute ("device.level", key: "SLIDER_CONTROL") {
				attributeState "level", action:"switch level.setLevel"
			}
			tileAttribute ("device.color", key: "COLOR_CONTROL") {
				attributeState "color", action:"color control.setColor"
			}
		}
        	valueTile("colorName", "device.colorName", inactiveLabel: false, decoration: "flat", width: 4, height: 2) {
        		state "colorName", label: 'Selected Colour:\n${currentValue}'
        	}
		standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:"", action:"refresh.refresh", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/refresh.png"
		}

		main(["switch"])
		details(["switch", "colorName", "refresh"])
	}
}

//Globals
private getATTRIBUTE_HUE() { 0x0000 }
private getATTRIBUTE_SATURATION() { 0x0001 }
private getHUE_COMMAND() { 0x00 }
private getSATURATION_COMMAND() { 0x03 }
private getMOVE_TO_HUE_AND_SATURATION_COMMAND() { 0x06 }
private getCOLOR_CONTROL_CLUSTER() { 0x0300 }

// Parse incoming device messages to generate events
def parse(String description) {
	log.debug "description is $description"

	// Determine current time and date in the user-selected date format and clock style
	def now = formatDate()    
	def nowDate = new Date(now).getTime()

	// Any report - test, smoke, clear in a lastCheckin event and update to Last Checkin tile
	// However, only a non-parseable report results in lastCheckin being displayed in events log
	sendEvent(name: "lastCheckin", value: now, displayed: false)
	sendEvent(name: "lastCheckinDate", value: nowDate, displayed: false)

	def finalResult = zigbee.getEvent(description)
	if (finalResult) {
		log.debug finalResult
		sendEvent(finalResult)
	}
	else {
		def zigbeeMap = zigbee.parseDescriptionAsMap(description)
		log.trace "zigbeeMap : $zigbeeMap"

		if (zigbeeMap?.clusterInt == COLOR_CONTROL_CLUSTER) {
			if(zigbeeMap.attrInt == ATTRIBUTE_HUE){  //Hue Attribute
				def hueValue = Math.round(zigbee.convertHexToInt(zigbeeMap.value) / 0xfe * 100)
				sendEvent(name: "hue", value: hueValue, displayed:false)
			}
			else if(zigbeeMap.attrInt == ATTRIBUTE_SATURATION){ //Saturation Attribute
				def saturationValue = Math.round(zigbee.convertHexToInt(zigbeeMap.value) / 0xfe * 100)
				sendEvent(name: "saturation", value: saturationValue, displayed:false)
			}
		}
		else {
			log.info "DID NOT PARSE MESSAGE for description : $description"
		}
	}
}

def on() {
	zigbee.on() + ["delay 1500"] + zigbee.onOffRefresh()
}

def off() {
	zigbee.off() + ["delay 1500"] + zigbee.onOffRefresh()
}

def refresh() {
	refreshAttributes() + configureAttributes()
}

def poll() {
	configureHealthCheck()

	refreshAttributes()
}

def configure() {
	log.debug "Configuring Reporting and Bindings."
	configureAttributes() + refreshAttributes()
}

def ping() {
	refreshAttributes()
}

def healthPoll() {
	log.debug "healthPoll()"
	def cmds = refreshAttributes()
	cmds.each{ sendHubCommand(new physicalgraph.device.HubAction(it))}
}

def configureHealthCheck() {
	if (!state.hasConfiguredHealthCheck) {
		log.debug "Configuring Health Check, Reporting"
		unschedule("healthPoll", [forceForLocallyExecuting: true])
		runEvery5Minutes("healthPoll", [forceForLocallyExecuting: true])
		state.hasConfiguredHealthCheck = true
	}
}

def configureAttributes() {
	zigbee.onOffConfig() +
	zigbee.levelConfig()
}

def refreshAttributes() {
	zigbee.onOffRefresh() +
	zigbee.levelRefresh() +
	zigbee.readAttribute(COLOR_CONTROL_CLUSTER, ATTRIBUTE_HUE) +
	zigbee.readAttribute(COLOR_CONTROL_CLUSTER, ATTRIBUTE_SATURATION)
}

def updated() {
	sendEvent(name: "checkInterval", value: 2 * 10 * 60 + 1 * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID])
	configureHealthCheck()
}

def installed() {
	sendEvent(name: "checkInterval", value: 2 * 10 * 60 + 1 * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID])
	configureHealthCheck()
}

def setLevel(value) {
	zigbee.setLevel(value) + zigbee.onOffRefresh() + zigbee.levelRefresh()         //adding refresh because of ZLL bulb not conforming to send-me-a-report
}

private getScaledHue(value) {
	zigbee.convertToHexString(Math.round(value * 0xfe / 100.0), 2)
}

private getScaledSaturation(value) {
	zigbee.convertToHexString(Math.round(value * 0xfe / 100.0), 2)
}

def setColor(value){
	log.trace "setColor($value)"
	zigbee.on() +
	zigbee.command(COLOR_CONTROL_CLUSTER, MOVE_TO_HUE_AND_SATURATION_COMMAND,
		getScaledHue(value.hue), getScaledSaturation(value.saturation), "0000") +
	zigbee.readAttribute(COLOR_CONTROL_CLUSTER, ATTRIBUTE_HUE) +
	zigbee.readAttribute(COLOR_CONTROL_CLUSTER, ATTRIBUTE_SATURATION)
}

def setHue(value) {
	//payload-> hue value, direction (00-> shortest distance), transition time (1/10th second)
	zigbee.command(COLOR_CONTROL_CLUSTER, HUE_COMMAND, getScaledHue(value), "00", "0000") +
	zigbee.readAttribute(COLOR_CONTROL_CLUSTER, ATTRIBUTE_HUE)
}

def setSaturation(value) {
	//payload-> sat value, transition time
	zigbee.command(COLOR_CONTROL_CLUSTER, SATURATION_COMMAND, getScaledSaturation(value), "0000") +
	zigbee.readAttribute(COLOR_CONTROL_CLUSTER, ATTRIBUTE_SATURATION)
}
def formatDate(batteryReset) {

	def correctedTimezone = ""
	def timeString = clockformat ? "HH:mm:ss" : "h:mm:ss aa"

	// If user's hub timezone is not set, display error messages in log and events log, and set timezone to GMT to avoid errors
	if (!(location.timeZone)) {
		correctedTimezone = TimeZone.getTimeZone("GMT")
		log.error "${device.displayName}: Time Zone not set, so GMT was used. Please set up your location in the SmartThings mobile app."
		sendEvent(name: "error", value: "", descriptionText: "ERROR: Time Zone not set, so GMT was used. Please set up your location in the SmartThings mobile app.")
	} else {
		correctedTimezone = location.timeZone
	}

	if (dateformat == "US" || dateformat == "" || dateformat == null) {
		if (batteryReset){
			return new Date().format("MMM dd yyyy", correctedTimezone)
		} else {
			return new Date().format("EEE MMM dd yyyy ${timeString}", correctedTimezone)
		}
	} else if (dateformat == "UK") {
		if (batteryReset) {
			return new Date().format("dd MMM yyyy", correctedTimezone)
		} else {
			return new Date().format("EEE dd MMM yyyy ${timeString}", correctedTimezone)
		}
	} else {
		if (batteryReset) {
			return new Date().format("yyyy MMM dd", correctedTimezone)
		} else {
		return new Date().format("EEE yyyy MMM dd ${timeString}", correctedTimezone)
		}
	}
}
