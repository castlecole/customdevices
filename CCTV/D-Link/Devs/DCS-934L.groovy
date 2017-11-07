/**
 *	D-Link DCS-934L v1.0.6
 *  Image Capture and Video Streaming courtesy Patrick Stuart (patrick@patrickstuart.com)
 *  
 *  Copyright 2015 blebson
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
	definition (name: "DCS-934L", author: "Ben Lebson", namespace: "castlecole") {
		capability "Image Capture"
		capability "Sensor"
		capability "Switch"
        capability "Switch Level"
        capability "Refresh"
        capability "Notification"
        capability "Configuration"
		capability "Video Camera"
		capability "Video Capture"
        
		attribute "hubactionMode", "string"
    	attribute "switch2", "string"
        attribute "switch3", "string"
        
        command "start"
        command "stop"
        command "vidOn"
        command "vidOff"
        command "nvOff"
        command "nvOn"
        command "nvAuto"
        command "configure"
	}
    
    mappings {
    	path("/getInHomeURL") {
    		action:
    		[GET: "getInHomeURL"]
   		}
	}
    
	simulator {
    
	}

    tiles (scale: 2) {
     multiAttributeTile(name: "videoPlayer", type: "videoPlayer", width: 6, height: 4) {
			tileAttribute("device.switch2", key: "CAMERA_STATUS") {
				attributeState("on", label: "Active", icon: "st.camera.dlink-indoor", action: "vidOff", backgroundColor: "#79b821", defaultState: true)
				attributeState("off", label: "Inactive", icon: "st.camera.dlink-indoor", action: "vidOn", backgroundColor: "#ffffff")
				attributeState("restarting", label: "Connecting", icon: "st.camera.dlink-indoor", backgroundColor: "#53a7c0")
				attributeState("unavailable", label: "Unavailable", icon: "st.camera.dlink-indoor", action: "refresh.refresh", backgroundColor: "#F22000")
			}

			tileAttribute("device.errorMessage", key: "CAMERA_ERROR_MESSAGE") {
				attributeState("errorMessage", label: "", value: "", defaultState: true)
			}

			tileAttribute("device.camera", key: "PRIMARY_CONTROL") {
				attributeState("on", label: "Active", icon: "st.camera.dlink-indoor", backgroundColor: "#79b821", defaultState: true)
				attributeState("off", label: "Inactive", icon: "st.camera.dlink-indoor", backgroundColor: "#ffffff")
				attributeState("restarting", label: "Connecting", icon: "st.camera.dlink-indoor", backgroundColor: "#53a7c0")
				attributeState("unavailable", label: "Unavailable", icon: "st.camera.dlink-indoor", backgroundColor: "#F22000")
			}

			tileAttribute("device.startLive", key: "START_LIVE") {
				attributeState("live", action: "start", defaultState: true)
			}

			tileAttribute("device.stream", key: "STREAM_URL") {
				attributeState("activeURL", defaultState: true)
			}
			tileAttribute("device.betaLogo", key: "BETA_LOGO") {
				attributeState("betaLogo", label: "", value: "", defaultState: true)
			}
            }
    	carouselTile("cameraDetails", "device.image", width: 3, height: 2) { }
        standardTile("take", "device.image", width: 2, height: 2, canChangeIcon: false, inactiveLabel: true, canChangeBackground: false) {
            state "take", label: "Take", action: "Image Capture.take", icon: "st.camera.camera", backgroundColor: "#FFFFFF", nextState:"taking"
            state "taking", label:'Taking', action: "", icon: "st.camera.take-photo", backgroundColor: "#53a7c0"
            state "image", label: "Take", action: "Image Capture.take", icon: "st.camera.camera", backgroundColor: "#FFFFFF", nextState:"taking"
        }

        standardTile("refresh", "command.refresh", inactiveLabel: false) {
        	state "default", label:'refresh', action:"refresh.refresh", icon:"st.secondary.refresh-icon"        
    	}
        standardTile("motion", "device.switch", width: 2, height: 2, canChangeIcon: false) {
			state "off", label: 'Motion Off', action: "switch.on", icon: "st.motion.motion.inactive", backgroundColor: "#ccffcc", nextState: "toggle"
            state "toggle", label:'toggle', action: "", icon: "st.motion.motion.inactive", backgroundColor: "#53a7c0"
			state "on", label: 'Motion On', action: "switch.off", icon: "st.motion.motion.active", backgroundColor: "#EE0000", nextState: "toggle"            
		}         
        standardTile("nightVision", "device.switch3", width: 2, height: 2, canChangeIcon: false) {
			state "off", label: 'NV Off', action: "nvAuto", icon: "st.Weather.weather14", backgroundColor: "#ffff00", nextState: "toggle"
            state "toggle", label:'toggle', action: "", icon: "st.motion.motion.inactive", backgroundColor: "#53a7c0"
			state "on", label: 'NV On', action: "nvOff", icon: "st.Weather.weather4", backgroundColor: "#4169E1", nextState: "toggle"  
            state "auto", label: 'NV Auto', action: "nvOn", icon: "st.motion.motion.active", backgroundColor: "#ccffcc", nextState: "toggle"  
		}
       controlTile("levelSliderControl", "device.level", "slider", height: 1, width: 5, inactiveLabel: false, range:"(0..100)") {
            state "level", action:"switch level.setLevel"
        }
        valueTile("Sensitivity", "device.level", inactiveLabel: false){
        	state "default", label:'${currentValue}%', unit:"%"
        }
        valueTile("blank", "device.camera"){
        	state "blank", action: ""
            }
        
        main "motion"
        details(["videoPlayer", "take", "cameraDetails", "refresh", "blank", "levelSliderControl", "Sensitivity", "motion", "nightVision"])
    }
}

def parse(String description) {
    log.debug "Parsing '${description}'"
    if( description != "updated" ){
    def map = [:]
	def retResult = []
	def descMap = parseDescriptionAsMap(description)
    def msg = parseLanMessage(description)
    //log.debug "status ${msg.status}"
    //log.debug "data ${msg.data}"
    
	//Image
	if(descMap["tempImageKey"] ) {
				try {
					storeTemporaryImage(descMap.tempImageKey, getPictureName())
				} catch(Exception e) {
					log.error e
				}
	}      
    else if (descMap["headers"] && descMap["body"]){
    	def body = new String(descMap["body"].decodeBase64())
        log.debug "Body: ${body}"
    }
    
    if (msg.body) {
    
    //log.debug "Motion Enabled: ${msg.body.contains("enable=yes")}"
    //log.debug "Motion Disabled: ${msg.body.contains("enable=no")}"
    //log.debug "PIR Enabled: ${msg.body.contains("pir=yes")}"
    //log.debug "PIR Disabled: ${msg.body.contains("pir=no")}"
    
        if (msg.body.contains("MotionDetectionEnable=1")) {
            log.debug "Motion is on"
            sendEvent(name: "switch", value: "on");
        }
        else if (msg.body.contains("MotionDetectionEnable=0")) {
            log.debug "Motion is off"
            sendEvent(name: "switch", value: "off");
        }        
        if(msg.body.contains("MotionDetectionSensitivity="))
        {
        	//log.debug msg.body        
        	String[] lines = msg.body.split( '\n' )
        	//log.debug lines[2]
            String[] sensitivity = lines[2].split( '=' )
            //log.debug sensitivity[1]
            int[] senseValue = sensitivity[1].toInteger()
            //log.debug senseValue
            
            sendEvent(name: "level",  value: "${senseValue[0]}")
            //sendEvent(name: "switch.setLevel", value: "${senseValue}")
        }    
        if (msg.body.contains( "DayNightMode=3")) {
            log.debug "Night Vision is on"
            sendEvent(name: "switch3", value: "on");
        }
        else if (msg.body.contains("DayNightMode=2")) {
            log.debug "Night Vision is off"
            sendEvent(name: "switch3", value: "off");
        }
        else if (msg.body.contains("DayNightMode=0")) {
            log.debug "Night Vision is auto"
            sendEvent(name: "switch3", value: "auto");
        }                   
    }
    }
}

// handle commands
def take() {
	log.debug "${state.cameraUser} and ${state.cameraPassword}"
	def userpassascii = "${state.cameraUser}:${state.cameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = state.cameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(state.cameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
    
    def path = "/image/jpeg.cgi" 
    log.debug "path is: $path"
    
    def headers = [:] 
    headers.put("HOST", "$host:$state.cameraPort")
   	headers.put("Authorization", userpass)
    
    log.debug "The Header is $headers"
    
    def method = "GET"    
    
    log.debug "The method is $method"
    
    try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: method,
    	path: path,
    	headers: headers
        )
        	
    hubAction.options = [outputMsgToS3:true]
    log.debug hubAction
    hubAction
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
}

def motionCmd(int motion)
{
	def userpassascii = "${state.cameraUser}:${state.cameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = state.cameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(state.cameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
    
    def headers = [:] 
    headers.put("HOST", "$host:$state.cameraPort")
    headers.put("Authorization", userpass)
    
    log.debug "The Header is $headers"
    
    
 def path = "/motion.cgi?MotionDetectionEnable=${motion}&ConfigReboot=No"
 log.debug "path is: $path"
  try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
    	path: path,
    	headers: headers
        )
        	
   
    log.debug hubAction
    return hubAction
    
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
  }
  
def nightCmd(String attr)
{
	def userpassascii = "${state.cameraUser}:${state.cameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = state.cameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(state.cameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
    
    def headers = [:] 
    headers.put("HOST", "$host:$state.cameraPort")
    headers.put("Authorization", userpass)
    
    log.debug "The Header is $headers"
    
 def path = "/daynight.cgi?DayNightMode=${attr}&ConfigReboot=no"
 log.debug "path is: $path"
  try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
    	path: path,
    	headers: headers
        )
        	
   
    log.debug hubAction
    return hubAction
    
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    } 
 
}

def sensitivityCmd(int percent)
{
	def userpassascii = "${state.cameraUser}:${state.cameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = state.cameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(state.cameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
       
    
    log.debug "Sensitivity is ${percent}"
    
    def path = "/motion.cgi?MotionDetectionSensitivity=${percent}&ConfigReboot=No"
    log.debug "path is: $path"
        
    def headers = [:] 
    headers.put("HOST", "$host:$state.cameraPort")
    headers.put("Authorization", userpass)
    
    log.debug "The Header is $headers"
   
  try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
    	path: path,
    	headers: headers
        )
        	
   
    log.debug hubAction
    return hubAction
    
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
  
}

def parseDescriptionAsMap(description) {
	description.split(",").inject([:]) { map, param ->
		def nameAndValue = param.split(":")
		map += [(nameAndValue[0].trim()):nameAndValue[1].trim()]
	}
}

private getPictureName() {
	def pictureUuid = java.util.UUID.randomUUID().toString().replaceAll('-', '')
    log.debug pictureUuid
    def picName = device.deviceNetworkId.replaceAll(':', '') + "_$pictureUuid" + ".jpg"
	return picName
}

private String convertIPtoHex(ipAddress) { 
    String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
    log.debug "IP address entered is $ipAddress and the converted hex code is $hex"
    return hex

}

private String convertPortToHex(port) {
	String hexport = port.toString().format( '%04x', port.toInteger() )
    log.debug hexport
    return hexport
}

private Integer convertHexToInt(hex) {
	Integer.parseInt(hex,16)
}


private String convertHexToIP(hex) {
	log.debug("Convert hex to ip: $hex") 
	[convertHexToInt(hex[0..1]),convertHexToInt(hex[2..3]),convertHexToInt(hex[4..5]),convertHexToInt(hex[6..7])].join(".")
}

private getHostAddress() {
    def parts = device.deviceNetworkId.split(":")
    log.debug device.deviceNetworkId
    def ip = convertHexToIP(parts[0])
    def port = convertHexToInt(parts[1])
    return ip + ":" + port
}


def on() {
	log.debug "Enabling motion detection"
    return motionCmd(1)    
}

def off() {
	log.debug "Disabling motion detection"
    return motionCmd(0)    
}

def setLevel(percent) {
	log.debug "Executing 'setLevel'"
	return sensitivityCmd(percent)	   
}

def nvOn() {
	log.debug "Enabling Night Vision"
    return nightCmd("3")   
    
}

def nvOff() {
	log.debug "Disabling Night Vision"
    return nightCmd("2")    
    
}

def nvAuto() {
	log.debug "Automatic Night Vision"
    return nightCmd("0")    
    
}

def refresh(){

	log.debug "Refresh"
	def userpassascii = "${state.cameraUser}:${state.cameraPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    def host = state.cameraIP 
    def hosthex = convertIPtoHex(host)
    def porthex = convertPortToHex(state.cameraPort)
    device.deviceNetworkId = "$hosthex:$porthex" 
    
    log.debug "The device id configured is: $device.deviceNetworkId"
    def path = "/motion.cgi"
    log.debug "path is: $path"
    
    def headers = [:] 
    headers.put("HOST", "$host:$state.cameraPort")
    headers.put("Authorization", userpass)
    
    log.debug "The Header is $headers"
   
  try {
    def hubAction = new physicalgraph.device.HubAction(
    	method: "GET",
    	path: path,
    	headers: headers
        )
        	
   
    log.debug hubAction
    return hubAction
    
    }
    catch (Exception e) {
    	log.debug "Hit Exception $e on $hubAction"
    }
  
  
}

def start() {
	log.trace "start()"
	def dataLiveVideo = [
		OutHomeURL  : "http://${state.cameraUser}:${state.cameraPassword}@${state.videoIP}:${state.videoPort}/mjpeg.cgi?channel=1.mjpeg",
		InHomeURL   : "http://${state.cameraUser}:${state.cameraPassword}@${state.videoIP}:${state.videoPort}/mjpeg.cgi?channel=1.mjpeg",
		ThumbnailURL: "http://cdn.device-icons.smartthings.com/camera/dlink-indoor@2x.png",
		cookie      : [key: "key", value: "value"]
	]

	def event = [
		name           : "stream",
		value          : groovy.json.JsonOutput.toJson(dataLiveVideo).toString(),
		data		   : groovy.json.JsonOutput.toJson(dataLiveVideo),
		descriptionText: "Starting the livestream",
		eventType      : "VIDEO",
		displayed      : false,
		isStateChange  : true
	]
	sendEvent(event)
}

def installed(){
	configure()
}

def updated(){
	configure()
}

def configure(){
	sendEvent(name:"switch2", value:"on")
    //log.debug parent.state.CameraIP
    state.cameraIP = parent.state.CameraIP
    state.cameraPort = parent.state.CameraPort
    state.videoIP = parent.state.VideoIP
    state.videoPort = parent.state.VideoPort
    state.cameraUser = parent.state.CameraUser
    state.cameraPassword = parent.state.CameraPassword
}

def getInHomeURL() {
   [InHomeURL: "http://${state.cameraUser}:${state.cameraPassword}@${state.videoIP}:${state.videoPort}/mjpeg.cgi?channel=1.mjpeg"]
}

def stop() {
	log.trace "stop()"
}

def vidOn() {
	log.trace "on()"
	// no-op
}

def vidOff() {
	log.trace "off()"
	// no-op
}
