/**
 *  Simulated Alarm
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

metadata {

	definition (name: "Virtual Alarm Plus", namespace: "castlecole", author: "Sean Cole") {
		capability "Alarm"
	}

	simulator {
		// reply messages
		["strobe","siren","both","off"].each {
			reply "$it": "alarm:$it"
		}
	}

    	tiles(scale: 2) {
		multiAttributeTile(name:"alarm", type: "lighting", width: 6, height: 4, canChangeIcon: false){
            		tileAttribute ("device.alarm", key: "PRIMARY_CONTROL") { 
			attributeState "off", label:'off', action:'alarm.strobe', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-icon.png", backgroundColor:"#ffffff"
			attributeState "strobe", label:'strobe!', action:'alarm.siren', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-icon.png", backgroundColor:"#e86d13"
			attributeState "siren", label:'siren!', action:'alarm.both', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-icon.png", backgroundColor:"#e86d13"
			attributeState "both", label:'alarm!', action:'alarm.off', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-icon.png", backgroundColor:"#e86d13"
	    	}
        }
	standardTile("strobe", "device.alarm", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
		state "default", label:'', action:"alarm.strobe", icon:"st.secondary.strobe", backgroundColor:"#cccccc"
	}
	standardTile("siren", "device.alarm", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
		state "default", label:'', action:"alarm.siren", icon:"st.secondary.siren", backgroundColor:"#cccccc"
	}       
	standardTile("test", "device.alarm", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
		state "default", label:'', action:"alarm.test", icon:"st.secondary.test", backgroundColor:"#cccccc"
	}       
        standardTile("off", "device.alarm", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
		state "default", label:"", action:"alarm.off", icon:"https://raw.githubusercontent.com/castlecole/xiaomi/master/stop-icon.png"
      	}
	standardTile("blank", "device.alarm", inactiveLabel: true, decoration: "flat", width: 2, height: 2) {
		state "default", label:'', action:""
	}	    
	standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
  	    state "default", label:"", action:"refresh.refresh", icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/refresh.png"
        }
        multiAttributeTile(name:"alarm2", type: "lighting", width: 6, height: 4, canChangeIcon: false){
            tileAttribute ("device.alarm", key: "PRIMARY_CONTROL") { 
		attributeState "off", label:'off', action:'alarm.strobe', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-icon.png", backgroundColor:"#ffffff"
		attributeState "strobe", label:'strobe!', action:'alarm.siren', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-icon.png", backgroundColor:"#e86d13"
		attributeState "siren", label:'siren!', action:'alarm.both', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-icon.png", backgroundColor:"#e86d13"
		attributeState "both", label:'alarm!', action:'alarm.off', icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/alarm-icon.png", backgroundColor:"#e86d13"
            }
	}
       	main "alarm2"
	details(["alarm","strobe","siren","test","off", "blank", "refresh"])
	}
}

def strobe() {
	sendEvent(name: "alarm", value: "strobe")
}



def siren() {
	sendEvent(name: "alarm", value: "siren")
}



def both() {
	sendEvent(name: "alarm", value: "both")
}



def off() {
	sendEvent(name: "alarm", value: "off")
}

// Parse incoming device messages to generate events
def parse(String description) {
    	log.debug description
    	def pair = description.split(":")
	createEvent(name: pair[0].trim(), value: pair[1].trim())
}
