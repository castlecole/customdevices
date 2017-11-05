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
	definition (name: "Virtual Contact Sensor", namespace: "castlecole", author: "Tomas Axerot") {
		capability "Contact Sensor"		

		command "open"
		command "close"        
	}

	simulator {
		status "open": "contact:open"
		status "closed": "contact:closed"
	}

	tiles(scale: 2) {
		standardTile("contact", "device.contact", width: 6, height: 4) {			
            state("open", label:'${name}', icon:"st.contact.contact.open", backgroundColor:"#ffa81e")
			state("closed", label:'${name}', icon:"st.contact.contact.closed", backgroundColor:"#79b821")            
		}
        
		main "contact"		
	}
}

def parse(String description) {	
    log.trace "parse: $description"
    def pair = description.split(":")
	createEvent(name: pair[0].trim(), value: pair[1].trim())
}

def open() {
	log.trace "open"
	sendEvent(name: "contact", value: "open")
}

def close() {
	log.trace "close"
    sendEvent(name: "contact", value: "closed")
}
