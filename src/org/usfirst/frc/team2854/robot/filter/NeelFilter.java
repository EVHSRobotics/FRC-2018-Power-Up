package org.usfirst.frc.team2854.robot.filter;

/**For reference only, as this only considers 1 state variable and 1 sensor.
 *
 */
public class NeelFilter {
    double x, a, b, z, w, gain, u, c; //g around 0.7, x = current prediction, xlast is previous, p around 1
    double r; //published accuracy of the sensor
    double p; //prediction error
    // u = control signal, b = rate of change of degrees

    /**
     *
     * @param g
     * @param noise
     * @param prediction_error
     * @param input input for z and x
     * @param a
     * @param u
     * @param b
     * @param c
     */
    public NeelFilter(double g, double noise, double prediction_error, double input, double a, double u, double b, double c){
        gain = g;
        r = noise;
        p = prediction_error;
        w = 0; //Assume 0 process noise
        z = input;
        x = input;
        this.a = a;
    }
    public double prediction(){
        x = a*x + b*u + w;
        p = a*p*a;
        return x;
    }
    public void update(double input){
        z = input;
        gain = (p*c)/(c*p*c + r);
        x = x + gain*(z - x*c);
        p = (1-gain*c)*p;
    }
}
