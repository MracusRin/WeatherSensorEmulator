## Weather Sensor emulator 

> for communicate with RESTWeatherApp
## Configuration
- Endpoint `RESTWeatherApp`. Change `localhost:8080` if you run the application on a non-local system

   ``` 
        String postMeasurementUrl = "http://localhost:8080/measurements/add";
        String postSensorUrl = "http://localhost:8080/sensor/registration";
        String getMeasurementsUrl = "http://localhost:8080/measurements";
   ```
  
## Using
- Create sensor  `sensorEmulator.getSensor("name")`
- Post sensor to RESTWeatherApp `sensorEmulator.postSensor(postSensorUrl, sensor)`
- Generate and post random measurements to RESTWeatherApp `sensorEmulator.postRandomMeasurements(postMeasurementUrl, sensor, intCount)`
- Show in console all measurements in RESTWeatherApp DB `sensorEmulator.showMeasurements(getMeasurementsUrl)`
- Show graph measurements `sensorEmulator.showTemp(getMeasurementsUrl, intCount)`