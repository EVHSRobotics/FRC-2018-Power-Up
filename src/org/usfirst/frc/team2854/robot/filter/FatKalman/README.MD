1. Use *KalmanFilterSimple*; all other Kalman files are for reference.
 * Look to KalmanFilterSimpleExampleDriver for an example on how to use it.
2. For actual FRC use, use
    ```java
    //pseudocode bc idk FRC implementation

    double[][] sensorValuesTemp = new double[howManySensors][1];

    while(robotIsActive()) {

    sensorValuesTemp[0, 0] = gyro1.getAngle();
    sensorValuesTemp[1, 0] = gyro2.getAngle();
    sensorValuesTemp[2, 0] = gyro3.getAngle();

    filter.predict();
    filter.update(new DMatrixRMaj(sensorValuesTemp), new DMatrixRMaj(tempR));
    }

    ```

3. Note that *KalmanFilterSimple* and its driver currently predict the **NEXT, not CURRENT** state.
   This can be adapted to FRC purposes (predicting the **CURRENT** state) by
     1. **CHANGING** the state transition matrix F to a nonconstant one; i.e., one that repeatedly changes
     because of a method (modeling vectors like velocity or acceleration)
     2. Going back one step and adding an if block if you're running
        the filter for the first time.
4. Contact me if any variables are not explained.