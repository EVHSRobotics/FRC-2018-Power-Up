/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.numbers.gamma.Gamma;
import org.apache.commons.numbers.gamma.RegularizedGamma;
import org.apache.commons.math4.util.FastMath;

/**
 * This class implements the Nakagami distribution.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Nakagami_distribution">Nakagami Distribution (Wikipedia)</a>
 *
 * @since 3.4
 */
public class NakagamiDistribution extends AbstractRealDistribution {

    /** Default inverse cumulative probability accuracy. */
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1e-9;

    /** Serializable version identifier. */
    private static final long serialVersionUID = 20160311L;

    /** The shape parameter. */
    private final double mu;
    /** The scale parameter. */
    private final double omega;
    /** Inverse cumulative probability accuracy. */
    private final double inverseAbsoluteAccuracy;

    /**
     * Creates a distribution.
     *
     * @param mu shape parameter
     * @param omega scale parameter (must be positive)
     * @throws NumberIsTooSmallException if {@code mu < 0.5}
     * @throws NotStrictlyPositiveException if {@code omega <= 0}
     */
    public NakagamiDistribution(double mu,
                                double omega) {
        this(mu, omega, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    /**
     * Creates a distribution.
     *
     * @param mu shape parameter
     * @param omega scale parameter (must be positive)
     * @param inverseAbsoluteAccuracy the maximum absolute error in inverse
     * cumulative probability estimates (defaults to {@link #DEFAULT_INVERSE_ABSOLUTE_ACCURACY}).
     * @throws NumberIsTooSmallException if {@code mu < 0.5}
     * @throws NotStrictlyPositiveException if {@code omega <= 0}
     */
    public NakagamiDistribution(double mu,
                                double omega,
                                double inverseAbsoluteAccuracy) {
        if (mu < 0.5) {
            throw new NumberIsTooSmallException(mu, 0.5, true);
        }
        if (omega <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_SCALE, omega);
        }

        this.mu = mu;
        this.omega = omega;
        this.inverseAbsoluteAccuracy = inverseAbsoluteAccuracy;
    }

    /**
     * Access the shape parameter, {@code mu}.
     *
     * @return the shape parameter.
     */
    public double getShape() {
        return mu;
    }

    /**
     * Access the scale parameter, {@code omega}.
     *
     * @return the scale parameter.
     */
    public double getScale() {
        return omega;
    }

    /** {@inheritDoc} */
    @Override
    protected double getSolverAbsoluteAccuracy() {
        return inverseAbsoluteAccuracy;
    }

    /** {@inheritDoc} */
    @Override
    public double density(double x) {
        if (x <= 0) {
            return 0.0;
        }
        return 2.0 * FastMath.pow(mu, mu) / (Gamma.value(mu) * FastMath.pow(omega, mu)) *
                     FastMath.pow(x, 2 * mu - 1) * FastMath.exp(-mu * x * x / omega);
    }

    /** {@inheritDoc} */
    @Override
    public double cumulativeProbability(double x) {
        return RegularizedGamma.P.value(mu, mu * x * x / omega);
    }

    /** {@inheritDoc} */
    @Override
    public double getNumericalMean() {
        return Gamma.value(mu + 0.5) / Gamma.value(mu) * FastMath.sqrt(omega / mu);
    }

    /** {@inheritDoc} */
    @Override
    public double getNumericalVariance() {
        double v = Gamma.value(mu + 0.5) / Gamma.value(mu);
        return omega * (1 - 1 / mu * v * v);
    }

    /** {@inheritDoc} */
    @Override
    public double getSupportLowerBound() {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isSupportConnected() {
        return true;
    }

}
