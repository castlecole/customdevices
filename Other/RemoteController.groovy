/**
 *  Remote Controller.
 *
 *  This SmartApp allows using remote controls (e.g. Aeon Labs Minimote) to
 *  execute routines, change modes and set the Smart Home Monitor mode.
 *
 *  --------------------------------------------------------------------------
 *
 *  Copyright © 2015 Statusbits.com. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may obtain a
 *  copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 *
 *  --------------------------------------------------------------------------
 *
 *  Version 1.0.2 (10/04/2015)
 */

import groovy.json.JsonSlurper

definition(
    name: "Remote Controller",
    namespace: "castlecole",
    author: "geko@statusbits.com",
    description: "Use remote controls to execute routines, change modes " +
        "and set the Smart Home Monitor mode.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartthings-device-icons/unknown/zwave/remote-controller@2x.png",
    iconX2Url: "https://s3.amazonaws.com/smartthings-device-icons/unknown/zwave/remote-controller@2x.png",
    oauth: false
)

preferences {
    page(name:"pageSetup")
    page(name:"pageAddButton")
    page(name:"pageEditButton")
}

// Show "Setup Menu" page
private def pageSetup() {
    LOG("pageSetup()")

    def buttons = getButtons()

    def hrefAbout = [
        url:        "http://statusbits.github.io/smartthings/",
        style:      "embedded",
        title:      "More information...",
        description:"http://statusbits.github.io/smartthings/",
        required:   false
    ]

    def inputRemotes = [
        name:       "remotes",
        type:       "capability.button",
        title:      "Which remote controls?",
        multiple:   true,
        required:   false
    ]

    def pageProperties = [
        name:       "pageSetup",
        //title:      "Setup",
        nextPage:   null,
        install:    true,
        uninstall:  state.installed ?: false
    ]

    return dynamicPage(pageProperties) {
        section("About", hideable:true, hidden:true) {
            paragraph about()
            href hrefAbout
        }

        section("Setup Menu") {
            input inputRemotes
            href "pageAddButton", title:"Add button", description:"Tap to open"
            buttons.each() { button ->
                def n = button.button.toInteger()
                href "pageEditButton", params:[button:n], title:"Configure button ${n}",
                    description:"Tap to open"
            }
        }

        section([title:"Options", mobileOnly:true]) {
            label title:"Assign a name", required:false
        }
    }
}

// Show "Add Button" setup page.
private def pageAddButton() {
    LOG("pageAddButton()")

    def textHelp =
        "Enter button number between 1 and 99."

    def pageProperties = [
        name:       "pageAddButton",
        title:      "Add Button",
        nextPage:   "pageEditButton",
    ]

    return dynamicPage(pageProperties) {
        section() {
            paragraph textHelp
            input "cfg_button", "number", title:"Which button?"
        }
    }
}

// Show "Configure Button" setup page.
private def pageEditButton(params) {
    LOG("pageEditButton(${params})")

    def button = params.button?.toInteger()
    if (!button) {
        button = settings.cfg_button?.toInteger()
    }

    if (!button || button < 1 || button > 99) {
        log.error "Invalid button number '${button}'"
        return pageSetup()
    }

    def textHelp =
        "You can configure buttons to execute routines, change modes and " +
        "set the Smart Home Monitor mode.\n\n" +
        "Some remote controls, for example Aeon Labs Minimote, can " +
        "recognize whether the button was pushed momentarily or held down. " +
        "You can configure Remote Controller to perform different actions " +
        "depending on the type of button action."

    def routines = getRoutineNames()
    def modes = getModeNames()
    def alarmModes = ["Away", "Stay", "Off"]

    def pageProperties = [
        name:       "pageEditButton",
        title:      "Configure Button ${button}",
        nextPage:   "pageSetup",
    ]

    return dynamicPage(pageProperties) {
        section() {
            paragraph textHelp
        }

        section("'Push' Button Actions") {
            input "push_${button}_routine", "enum", title:"Execute routine",
                    options:routines, required:false
            input "push_${button}_mode", "enum", title:"Change the mode to",
                    options:modes, required:false
            input "push_${button}_alarm", "enum", title:"Set Smart Home Monitor to",
                    options:alarmModes, required:false
        }

        section("'Hold' Button Actions") {
            input "hold_${button}_routine", "enum", title:"Execute routine",
                    options:routines, required:false
            input "hold_${button}_mode", "enum", title:"Change the mode to",
                    options:modes, required:false
            input "hold_${button}_alarm", "enum", title:"Set Smart Home Monitor to",
                    options:alarmModes, required:false
        }
    }
}

def installed() {
    state.installed = true
    initialize()
}

def updated() {
    unsubscribe()
    initialize()
}

def onButtonEvent(evt) {
    LOG("onButtonEvent(${evt.value})")

    if (!state.buttons || !evt.data) {
        return
    }

    def slurper = new JsonSlurper()
    def data = slurper.parseText(evt.data)
    def button = data?.buttonNumber?.toInteger()
    if (!button) {
        log.error "cannot parse button data: ${data}"
        return
    }

    LOG("Button '${button}' was ${evt.value}.")

    def item = state.buttons.find { it.button == button }
    if (!item) {
        LOG("Button '${button}' is not configured")
        return
    }

    def actions = item[evt.value]
    if (!actions) {
        LOG("Unknown button action '${evt.value}'")
        return
    }

    if (actions.routine) {
        executeRoutine(actions.routine)
    }

    if (actions.mode) {
        setMode(actions.mode)
    }

    if (actions.alarm) {
        setAlarmMode(actions.alarm)
    }
}

private def initialize() {
    log.info "Remote. Version ${version()}. ${copyright()}"
    LOG("initialize with ${settings}")

    if (settings.remotes) {
        state.buttons = getButtons()
        subscribe(settings.remotes, "button", onButtonEvent)
    } else {
        state.buttons = []
    }

    LOG("state: ${state}")
}

private def getButtons() {
    LOG("getButtons()")

    def buttons = []
    (1..99).each() { btn ->
        def pushRoutine = settings["push_${btn}_routine"]
        def pushMode = settings["push_${btn}_mode"]
        def pushAlarm = settings["push_${btn}_alarm"]?.toLowerCase()
        def holdRoutine = settings["hold_${btn}_routine"]
        def holdMode = settings["hold_${btn}_mode"]
        def holdAlarm = settings["hold_${btn}_alarm"]?.toLowerCase()
        if (pushRoutine || pushMode || pushAlarm || holdRoutine || holdMode || holdAlarm) {
            def button = [
                button: btn,
                pushed: [routine:pushRoutine, mode:pushMode, alarm:pushAlarm],
                held:   [routine:holdRoutine, mode:holdMode, alarm:holdAlarm],
            ]

            buttons << button
        }
    }

    if (buttons.size() > 1) {
        buttons = buttons.sort() { it.button }
    }

    //log.debug "buttons: ${buttons}"
    return buttons
}

private def getRoutineNames() {
    def routines = location.helloHome?.getPhrases().collect() { it.label }
    return routines.sort()
}

private def getModeNames() {
    def modes = location.modes?.collect() { it.name }
    return modes.sort()
}

private def executeRoutine(name) {
    log.trace "Executing Routine \'${name}\'"
    location.helloHome.execute(name)
}

private def setMode(name) {
    log.trace "Setting location mode to \'${name}\'"
    setLocationMode(name)
}

private def setAlarmMode(name) {
    log.trace "Setting alarm system mode to \'${name}\'"

    def event = [
        name:           "alarmSystemStatus",
        value:          name,
        isStateChange:  true,
        displayed:      true,
        description:    "alarm system status is ${name}",
    ]

    sendLocationEvent(event)
}

private def version() {
    return "1.0.2"
}

private def copyright() {
    return "Copyright © 2015 Statusbits.com"
}

private def about() {
    def text =
        "This SmartApp allows using remote controls (e.g. Aeon Labs " +
        "Minimote) to execute routines, change modes and set the Smart " +
        "Home Monitor mode.\n\n" +
        "Version ${version()}\n${copyright()}\n\n" +
        "You can contribute to the development of this app by making a " +
        "PayPal donation to geko@statusbits.com. We appreciate your support."
}

private def LOG(message) {
    //log.trace message
}
