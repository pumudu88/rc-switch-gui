/*
 ******************************************************************************************************** 
 *********************************** ARDUINO RF TRANSMITTER *********************************************
 ********************************************************************************************************
 *
 *  This sketch turns Arduino into a PC controlled RF transmitter
 *    
 *  When new serial data arrives, it'll put into a input char array.
 *  After that it'll loop through all parameters inside input array, 
 *  separated by specified separator(&). Each key and value pairs will be 
 *  extracted and registered separately.
 *  Finally based on serial input, all the transmission parameters are set 
 *  and binary code will be transmitted.
 *  
 *  
 *  Sample serial inputs for testing purposes:
 *  
 *  binaryCode:a&pulseLength:b&rfTransmitPin:c&rfType:2&repeatIterations:5
 *  binaryCode:10101010101001100101&pulseLength:34434&rfTransmitPin:342342&rfType:2&repeatIterations:5
 *  binaryCode:10101010101003838300400000000000000110010101010110101010101010101101101&pulseLength:34434&rfTransmitPin:342342&rfType:2&repeatIterations:50
 *  binaryCode:010101010010010010001100&pulseLength:321&rfTransmitPin:10&rfType:1&repeatIterations:5
 *  
 *  IMPORTANT : 
 *  Uncomment serial print lines in the code to verify input parameters.
 *  Use baud rate 9600.
 *  
 *  Created 2 December 2016
 *  by Pumudu Ruhunage
 * 
 */


#include <RCSwitch.h>

// Calculated based on max serial input size expected for one command
#define INPUT_SIZE 150

RCSwitch mySwitch = RCSwitch();
char* binaryCode;
char* pulseLength;
char* rfTransmitPin;
char* rfProtocolType;
char* repeatIterations;

void setup()
{
  // Initialize serial:
  Serial.begin(9600);
  pinMode(13, OUTPUT);

  // Flush serial buffer
  serialFlush();
}

void loop()
{
  if (Serial.available())
  {
    // Get next command from Serial (add one for final character, 0)
    char input[INPUT_SIZE + 1];
    byte size = Serial.readBytes(input, INPUT_SIZE);
    // Add the final 0 to end the input string
    input[size] = 0;
      
    // Read each command pair
    char* command = strtok(input, "&");

    // This while loop will loop through all key value command pairs and
    // map them to corrosponding variable
    while (command != 0)
    {
      // Split the command in two values
      char* separator = strchr(command, ':');
      if (separator != 0)
      {
        // Split the string in 2: replace ':' with 0
        *separator = 0;
        ++separator;

        String cmd = command;

        // Map variables to parameters based on receiving input
        if (cmd.equals("binaryCode"))
        {
          binaryCode = separator;
        }
        if (cmd.equals("pulseLength"))
        {
          pulseLength = separator;
        }
        if (cmd.equals("rfTransmitPin"))
        {
          rfTransmitPin = separator;
        }
        if (cmd.equals("rfType"))
        {
          rfProtocolType = separator;
        }
        if (cmd.equals("repeatIterations"))
        {
          repeatIterations = separator;
        }
      }
      // Find the next command in input string
      command = strtok(0, "&");
    }

    // Report progress
    Serial.println("progress:25");

    // Finally, parameters registered to rcSwitch based on received inputs.
    int tempInt = 0;
    // temp int variable to hold required parameters
    tempInt = atoi(rfTransmitPin);
    // Serial.println(tempInt);
    // set connected to Arduino Pin to transmitter
    mySwitch.enableTransmit(tempInt);

    tempInt = atoi(pulseLength);
    // Serial.println(tempInt);
    // Optional set pulse length.
    mySwitch.setPulseLength(tempInt);

    tempInt = atoi(rfProtocolType);
    // Serial.println(tempInt);
    // set protocol.
    mySwitch.setProtocol(tempInt);

    tempInt = atoi(repeatIterations);
    // Serial.println(tempInt);
    // set number of transmission repetitions.
    mySwitch.setRepeatTransmit(tempInt);

    digitalWrite(13, HIGH);
    delay(500);
    
    // Report progress
    Serial.println("progress:50");
    
    //Finally, transmit the code
    mySwitch.send(binaryCode);
    digitalWrite(13, LOW);

    // Report progress
    Serial.println("progress:100");
  }
}


// Flush data from serial buffer
void serialFlush() {
  while (Serial.available() > 0) {
    char t = Serial.read();
  }
}
