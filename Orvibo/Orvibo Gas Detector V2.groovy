/*
 *  Copyright 2018 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy
 *  of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 *  Author : jinkang zhang / jk0218.zhang@samsung.com
 *  Date : 2018-07-04
 */
import physicalgraph.zigbee.clusters.iaszone.ZoneStatus
import physicalgraph.zigbee.zcl.DataType

def version() {
	return "v2 (20180912)\nOrvibo Gas Detector"
}

metadata {
	definition(name: "Orvibo Gas Detector", namespace: "castlecole", author: "SmartThings", runLocally: false, minHubCoreVersion: '000.017.0012', executeCommandsLocally: false, mnmn: "SmartThings", vid: "generic-smoke") {
		capability "Smoke Detector"
		capability "Configuration"
		capability "Health Check"
		capability "Sensor"
		capability "Refresh"
		fingerprint profileId: "0104", deviceId: "0402", inClusters: "0000, 0003, 0500, 0009", outClusters: "0019", manufacturer: "Heiman", model:"d0e857bfd54f4a12816295db3945a421"

		attribute "lastCheckinDate", "Date"		
		attribute "lastCheckin", "string"
		attribute "lastTested", "String"
		attribute "lastTestedDate", "Date"
		attribute "lastGas", "String"
		attribute "lastGasDate", "Date"		

		command "resetClear"
		command "resetSmoke"
	}

	preferences {
		//Date & Time Config
		input description: "", type: "paragraph", element: "paragraph", title: "DATE & CLOCK"    
		input name: "dateformat", type: "enum", title: "Set Date Format\nUS (MDY) - UK (DMY) - Other (YMD)", description: "Date Format", options:["US","UK","Other"]
		input name: "clockformat", type: "bool", title: "Use 24 hour clock?"
		input description: "Version: ${version()}", type: "paragraph", element: "paragraph", title: ""
	}
	
	simulator {
		status "active": "zone status 0x0001 -- extended status 0x00"
	}

	tiles(scale: 2) {
		multiAttributeTile(name:"smoke", type: "lighting", width: 6, height: 4) {
			tileAttribute ("device.smoke", key: "PRIMARY_CONTROL") {
           			attributeState( "clear", label:'CLEAR', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-clear0.png", backgroundColor:"#00a0dc")
				attributeState( "tested", label:"TESTED!", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-notclear0.png", backgroundColor:"#e86d13")
				attributeState( "detected", label:'GAS DETECTED!', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-notclear0.png", backgroundColor:"#ed0000")   
 			}
			tileAttribute("device.lastCheckin", key: "SECONDARY_CONTROL") {
				attributeState("default", label:'Last Checkin: ${currentValue}', icon: "st.Health & Wellness.health9")
			}
		}

		multiAttributeTile(name:"smoke2", type: "lighting", width: 6, height: 4) {
			tileAttribute ("device.smoke", key: "PRIMARY_CONTROL") {
   				attributeState( "clear", label:'CLEAR', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/House-GAS-Normal.png", backgroundColor:"#00a0dc")
				attributeState( "tested", label:"TESTED!", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/House-GAS-Event.png", backgroundColor:"#e86d13")
				attributeState( "detected", label:'GAS DETECTED!', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/House-GAS-Event.png", backgroundColor:"#ed0000")   
 			}
		}
        	
	  	valueTile("lastGas", "device.lastGas", inactiveLabel: False, decoration: "flat", width: 4, height: 1) {
        		state "default", label:'Last GAS Detected:\n ${currentValue}'
		}
		
	  	valueTile("lastTested", "device.lastTested", inactiveLabel: False, decoration: "flat", width: 4, height: 1) {
        		state "default", label:'Last Tested:\n ${currentValue}'
		}
		
		standardTile("resetClear", "device.resetSmoke", inactiveLabel: false, decoration: "flat", width: 3, height: 2) {
			state "default", action:"resetSmoke", label:'Override Clear', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/House-GAS-Event_sml.png"
		}

		standardTile("resetSmoke", "device.resetClear", inactiveLabel: false, decoration: "flat", width: 3, height: 2) {
			state "default", action:"resetClear", label:'Override Gas', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/House-GAS-Normal_sml.png"
		}

		standardTile("refresh", "device.refresh", inactiveLabel: False, decoration: "flat", width: 2, height: 2) {
			state "default", action:"refresh.refresh", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/refresh.png"
		}
		
		main (["smoke2"])
		details(["smoke", "lastGas", "lastTested", "refresh", "resetClear", "resetSmoke"])

	}
}

def installed() {
	log.debug "installed"
	refresh()
}

def parse(String description) {

	// Determine current time and date in the user-selected date format and clock style
	def now = formatDate()    
	def nowDate = new Date(now).getTime()

	log.debug "description(): $description"
	def map = zigbee.getEvent(description)

	// Any report - test, smoke, clear in a lastCheckin event and update to Last Checkin tile
	// However, only a non-parseable report results in lastCheckin being displayed in events log
	sendEvent(name: "lastCheckin", value: now, displayed: true)
	sendEvent(name: "lastCheckinDate", value: nowDate, displayed: false)

	if (!map) {
		if (description?.startsWith('zone status')) {
			map = parseIasMessage(description)
			if (map.value == "detected") {
				sendEvent(name: "lastGas", value: now, displayed: false)
				sendEvent(name: "lastGasDate", value: nowDate, displayed: false)
			} else if (map.value == "tested") {
				sendEvent(name: "lastTested", value: now, displayed: false)
				sendEvent(name: "lastTestedDate", value: nowDate, displayed: false)
			}
		} else {
			map = zigbee.parseDescriptionAsMap(description)
		}
	}
	log.debug "Parse returned $map"
	def result = map ? createEvent(map) : [:]
	if (description?.startsWith('enroll request')) {
		List cmds = zigbee.enrollResponse()
		log.debug "enroll response: ${cmds}"
		result = cmds?.collect { new physicalgraph.device.HubAction(it)}
	}
	return result
}

def parseIasMessage(String description) {
	ZoneStatus zs = zigbee.parseZoneStatus(description)
	return getDetectedResult(zs.isAlarm1Set() || zs.isAlarm2Set(), zs.isTestSet())
}

def getDetectedResult(value1, value2) {
	def detected = ''
	def detected1 = value1 ? 'detected' : 'clear'
	def detected2 = value2 ? 'tested' : 'clear'
	
	if (detected1 == 'detected') {
		detected = 'detected'
	} else if (detected2 == 'tested') {
		detected = 'tested'
	} else {
		detected = 'clear'
	}
	
	String descriptionText = "${device.displayName} GAS ${detected}"
	return [name:'smoke',
		value: detected,
		descriptionText:descriptionText,
		translatable:true]
}

def refresh() {
	log.debug "Refreshing Values"
	def refreshCmds = []
	refreshCmds += zigbee.readAttribute(zigbee.IAS_ZONE_CLUSTER, zigbee.ATTRIBUTE_IAS_ZONE_STATUS)
	return refreshCmds
}

def resetClear() {
	sendEvent(name:"smoke", value:"clear", displayed: false)
}

def resetSmoke() {
	sendEvent(name:"smoke", value:"Gas", displayed: false)
}

/**
 * PING is used by Device-Watch in attempt to reach the Device
 * */
def ping() {
	log.debug "ping"
	refresh()
}

def configure() {
	log.debug "configure"
	sendEvent(name: "checkInterval", value: 30 * 60 + 2 * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID, offlinePingable: "1"])
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
