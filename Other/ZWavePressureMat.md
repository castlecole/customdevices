# Z-Wave Pressure Mat

Pressure mats work opposite to Door/Window sensors, normally “open” status. Inside the mat is a contact sensor, which is open. When weight is applied to the mat, the contact sensor is physically forced closed.
Pressure mats are also used as doorbells for houseboats.
The mat itself has the contact pad, but doesn’t have a zwave or zigbee radio, so you have to buy that separately. Use a door sensor as the z-wave part.
Just connect the leads from the mat to the screw terminals inside a suitable Door/Window sensor and you have a connected pressure sensitive mat. These are great little devices because of the “undocumented” connections inside them.

## Suitable Door/Window Z-Wave Sensors:
- Ecolink Intelligent Technology Z-Wave Easy Install, Battery Operated, Door/Window Sensor, White & Brown (DWZWAVE2-ECO)
- Schlage Door/Window Sensor (RS100HC)

## Suitable Pressure Mats
- United Security Products 901PR 60lb Pre-Wired Pressure Mat 9x15
- Ideal Security Inc. SK630 Pressure Mat Alarm with Chime
- LondonMat CVP Sensing Mats - 5lb sensitivity

Someone who wants to know when the Roomba vacuum is away from the docking station needs a high sensitivity pressure mat. These are ones that will trigger with just 5 pounds of pressure instead of the typical 25 pounds.
LondonMat makes a nice variety, including some that trigger at 5 pounds of pressure. That’s just the mat, you still need to connect to a sensor exactly as with the United Security mat.
The downside is that the LondonMat switching mat will trigger from cats and dogs. But for something like a Roomba docking station, would be a nice solution. So just one more option for a slightly different use case.

## Connection
The above Door/Window sensors pair as a door sensor and open is the default state, when some stands on it closes.
Changing to this DH, the Door/Window DH status on/off activation is reversed, so it triggers correctly.

