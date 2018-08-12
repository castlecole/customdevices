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
	return "v2 (20180812)\nOrvibo Gas Detector"
}

metadata {
	definition(name: "Orvibo Gas Detector V2", namespace: "castlecole", author: "SmartThings", runLocally: false, minHubCoreVersion: '000.017.0012', executeCommandsLocally: false, mnmn: "SmartThings", vid: "generic-smoke") {
		capability "Smoke Detector"
		capability "Configuration"
		capability "Health Check"
		capability "Sensor"
		capability "Refresh"
    
   	command "resetClear"
		command "resetSmoke"

    attribute "lastTested", "String"
		attribute "lastTestedDate", "Date"
		attribute "lastCheckinDate", "Date"		
		attribute "lastCheckin", "string"
		attribute "lastSmoke", "String"
		attribute "lastSmokeDate", "Date"
		attribute "lastDescription", "String"
    
		fingerprint profileId: "0104", deviceId: "0402", inClusters: "0000, 0003, 0500, 0009", outClusters: "0019", manufacturer: "Heiman", model:"d0e857bfd54f4a12816295db3945a421"
	}

	simulator {
		status "active": "Zone Status 0x0001 -- extended status 0x00"
	}

	preferences {
		//Date & Time Config
		input description: "", type: "paragraph", element: "paragraph", title: "DATE & CLOCK"    
		input name: "dateformat", type: "enum", title: "Set Date Format\nUS (MDY) - UK (DMY) - Other (YMD)", description: "Date Format", options:["US","UK","Other"]
		input name: "clockformat", type: "bool", title: "Use 24 hour clock?"
		input description: "Version: ${version()}", type: "paragraph", element: "paragraph", title: ""
	}

/*	tiles {
		standardTile("smoke", "device.smoke", width: 2, height: 2) {
			state("clear", label: "Clear", icon:"st.alarm.smoke.clear", backgroundColor:"#ffffff")
			state("detected", label: "Smoke!", icon:"st.alarm.smoke.smoke", backgroundColor:"#e86d13")
		}
		standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 1, height: 1) {
			state "default", action: "refresh.refresh", icon: "st.secondary.refresh"
		}
		main "smoke"
		details(["smoke","refresh"])
	}
} */

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
        	valueTile("battery", "device.battery", decoration: "flat", inactiveLabel: false, width: 2, height: 2) {
            		state "battery", label:'99%'+"\n", unit:"%", icon:"https://raw.githubusercontent.com/castlecole/Xiaomi/master/Battery.png",
				backgroundColors:[
					[value: 0, color: "#ff1800"],
					[value: 10, color: "#fb854a"],
					[value: 25, color: "#ceec24"],
					[value: 50, color: "#71f044"],
					[value: 75, color: "#33d800"]
				]
		}
		valueTile("lastCheckin", "device.lastCheckin",  inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:'', icon: "st.Health & Wellness.health9", backgroundColor: "#ceec24"
		}
		valueTile("lastSmoke", "device.lastSmoke", inactiveLabel: false, decoration: "flat", width: 4, height: 1) {
        		state "default", label:'Last GAS Detected:\n ${currentValue}'
		}
		standardTile("refresh", "device.refresh", inactiveLabel: False, decoration: "flat", width: 2, height: 2) {
		    	state "default", action:"refresh.refresh", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/refresh.png"
    		}
		valueTile("lastTested", "device.lastTested", inactiveLabel: false, decoration: "flat", width: 4, height: 1) {
        		state "default", label:'Last Tested:\n ${currentValue}'
		}
		valueTile("lastDescription", "device.lastDescription", inactiveLabel: false, decoration: "flat", width: 4, height: 1) {
        		state "default", label:'${currentValue}'
		}
		
		main (["smoke2"])
		details(["smoke", "lastSmoke", "lastTested", "refresh"])
	}
}

def installed() {
	log.debug "installed"
	refresh()
}

def parse(String description) {
	log.debug "${device.displayName}: Parsing description: ${description}"

	// Determine current time and date in the user-selected date format and clock style
	def now = formatDate()    
	def nowDate = new Date(now).getTime()

	// Any report - test, smoke, clear in a lastCheckin event and update to Last Checkin tile
	// However, only a non-parseable report results in lastCheckin being displayed in events log
	sendEvent(name: "lastCheckin", value: now, displayed: true)
	sendEvent(name: "lastCheckinDate", value: nowDate, displayed: false)
	sendEvent(name: "lastDescription", value: description, displayed: false)

// Got this far !!!
  def map = zigbee.getEvent(description)
	if (!map) {
	if (description?.startsWith('zone status')) {
		map = parseZoneStatusMessage(description)
		if (map.value == "detected") {
			sendEvent(name: "lastSmoke", value: now, displayed: true)
			sendEvent(name: "lastSmokeDate", value: nowDate, displayed: false)
			sendEvent(name: "lastDescription", value: map.descriptionText, displayed: false)
		} else if (map.value == "tested") {
			sendEvent(name: "lastTested", value: now, displayed: true)
			sendEvent(name: "lastTestedDate", value: nowDate, displayed: false)
			sendEvent(name: "lastDescription", value: map.descriptionText, displayed: false)
		}
	} else if (description?.startsWith('catchall:')) {
		map = parseCatchAllMessage(description)
	} else if (description?.startsWith('read attr - raw:')) {
		map = parseReadAttr(description)
	} else if (description?.startsWith('enroll request')) {
		List cmds = zigbee.enrollResponse()
		log.debug "enroll response: ${cmds}"
		result = cmds?.collect { new physicalgraph.device.HubAction(it) }
	} else {
		log.debug "${device.displayName}: was unable to parse ${description}"
		sendEvent(name: "lastCheckin", value: now, displayed: true) 
	}
	if (map) {
		log.debug "${device.displayName}: Parse returned ${map}"
		return createEvent(map)
	} else {
		return [:]
	}
// ========


if (description?.startsWith('zone status')) {
			map = parseIasMessage(description)
		} else {
			map = zigbee.parseDescriptionAsMap(description)
		}
	}
	log.debug "Parse returned $map"
	def result = map ? createEvent(map) : [:]
	if (description?.startsWith('enroll request')) {
		List cmds = zigbee.enrollResponse()
		log.debug "Enroll Response: ${cmds}"
		result = cmds?.collect { new physicalgraph.device.HubAction(it)}
	}
	return result
}

def parseIasMessage(String description) {
	ZoneStatus zs = zigbee.parseZoneStatus(description)
	return getDetectedResult(zs.isAlarm1Set() || zs.isAlarm2Set())

/// Got This far !!!
	def result = [
		name: 'smoke',
		value: value,
		descriptionText: 'Gas detected',
		displayed: true
	]
	if (description?.startsWith('zone status')) {
		if (description?.startsWith('zone status 0x0002')) { // User Test
			result.value = "tested"
			result.descriptionText = "${device.displayName} has been tested"
		} else if (description?.startsWith('zone status 0x0001')) { // gas detected
			result.value = "detected"
			result.descriptionText = "${device.displayName} has detected gas!!!"
		} else if (description?.startsWith('zone status 0x0000')) { // situation normal... no gas
			result.value = "clear"
			result.descriptionText = "${device.displayName} is all clear"
		}
		return result
	}
	return [:]

// =====================

}

def getDetectedResult(value) {
	def detected = value ? 'detected': 'clear'
	String descriptionText = "${device.displayName} smoke ${detected}"
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
    sendEvent(name:"smoke", value:"clear", display: false)
}

def resetSmoke() {
    sendEvent(name:"smoke", value:"smoke", display: false)
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
