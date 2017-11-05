/**
 *  Copyright 2017 Tomas Axerot
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
	definition (name: "Virtual Motion Sensor", namespace: "castlecole", author: "Tomas Axerot") {
		capability "Motion Sensor"

		command "active"
		command "inactive"
	}

	simulator {
		status "active": "motion:active"
		status "inactive": "motion:inactive"
	}

	tiles(scale: 2) {
		standardTile("motion", "device.motion", width: 6, height: 4) {
			state("active", label:'motion', icon:"st.motion.motion.active", backgroundColor:"#53a7c0")
            state("inactive", label:'no motion', icon:"st.motion.motion.inactive", backgroundColor:"#ffffff")
		}
        
		main "motion"		
	}
}

def parse(String description) {
	log.trace "parse: $description"
	def pair = description.split(":")
	createEvent(name: pair[0].trim(), value: pair[1].trim())
}

def active() {
	log.trace "active"
	sendEvent(name: "motion", value: "active")
}

def inactive() {
	log.trace "inactive"
    sendEvent(name: "motion", value: "inactive")
}
