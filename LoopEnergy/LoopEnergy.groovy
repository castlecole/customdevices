/**
 *  Loop Energy
 *
 *  Copyright 2016 Phil Dye
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
	definition (name: "Loop Energy", namespace: "castlecole", author: "Phil Dye") {
		capability "Energy Meter"
		capability "Power Meter"
		capability "Sensor"
        capability "Refresh"
        
        command "refresh"
		command "poll"
        
		attribute "power", "string"
		attribute "energy", "string"
	}


	simulator {
// 		for (int i = 0; i <= 10000; i += 1000) {
//            status "power  ${i} W": "{\\\"power\\\": ${i}}"
//        }
    }

    tiles(scale: 2) {
		multiAttributeTile(name:"power", type:"generic", width:6, height:4, wordWrap: false) {
    		tileAttribute("device.power", key: "PRIMARY_CONTROL") {
      			attributeState "default", label: '${currentValue}W', 
                foregroundColor: "#000000",
                backgroundColors:[
					[value: 500, color: "#90d2a7"], //Light Green
                	[value: 1000, color: "#44b621"], //Darker Green
                    [value: 3000, color: "#f1d801"], //Yellow
                	[value: 4000, color: "#d04e00"], //Orange
                    [value: 5000, color: "#bc2323"] //Bright Red
				]
    		}
			tileAttribute ("device.energy", key: "SECONDARY_CONTROL") {
				attributeState "energy", label:'Energy: ${currentValue}kWh', icon: "st.Appliances.appliances17"
			}
		}	
      	valueTile("lastUpdated", "device.lastUpdated", width: 4, height: 2, decoration: "flat") {
			state "default", label: 'Last updated\r\n\${currentValue}'
		}
        standardTile("refresh", "device.switch", width: 2, height: 2, decoration: "flat") {
			state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
		}
        
        main (["power"])
        details (["power","energy","lastUpdated","refresh"])
	}
}

preferences {
// TODO
}

def initialize() {
	log.debug "Executing 'initialize'"
    
	poll()
}

void installed() {
	log.debug "Executing 'installed'"
}

def parse(String description) {
    log.debug "Executing 'parse': ${description}"
}

def poll() {
	log.debug "Executing 'poll'"

    parent.refresh(this)
}

def refresh() {
	log.debug "Executing 'refresh'"
    
    poll()
}
