/**
 * Copyright 2012 Lyncode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lyncode.performance;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.nanoTime;

public class PerformanceRequester {
    private List<Long> measurements = new ArrayList<Long>();
    private HttpClient client = new HttpClient();

    private static final String BASE = "http://localhost:8080";

    public void requestDurationWithoutSum (String url) throws IOException {
        GetMethod getMethod = new GetMethod(BASE + url);
//        long start = nanoTime();
//        System.out.println("Start: "+start);
        client.executeMethod(getMethod);
        getMethod.getResponseContentLength();
//        long end = nanoTime();
//        System.out.println("End: "+end);
//        measurements.add(end - start);
    }

    public void requestDuration (String url) throws IOException {
        GetMethod getMethod = new GetMethod(BASE + url);
        long start = nanoTime() / 1000;
//        System.out.println("Start: "+start);
        client.executeMethod(getMethod);
        getMethod.getResponseContentLength();
        long end = nanoTime() / 1000;
//        System.out.println("End: "+end);
        long value = end - start;
        measurements.add(value);
        System.err.println(value);
    }

    public double desvio () {
        long[] desvios = new long[measurements.size()];
        long avg = average();
        for (int i=0;i<measurements.size();i++)
            desvios[i] = Math.abs(measurements.get(i) - avg);

        long sum = 0;
        for (Long value : desvios)
            sum += value;

        return sum / measurements.size();
    }

    public long average() {
        long sum = 0;
        for (Long value : measurements)
            sum += value;

        return sum / measurements.size();
    }

    public Pair<Integer, Long> max () {
        if (measurements.isEmpty()) return new Pair<Integer, Long>(-1, 0L);
        int position = 0;
        long max = measurements.get(0);
        for (int i = 1;i<measurements.size();i++) {
            if (max < measurements.get(i)) {
                max = measurements.get(i);
                position = i;
            }
        }
        return new Pair<Integer, Long>(position, max);
    }

    public Pair<Integer, Long> min () {
        if (measurements.isEmpty()) return new Pair<Integer, Long>(-1, 0L);
        int position = 0;
        long max = measurements.get(0);
        for (int i = 1;i<measurements.size();i++) {
            if (max > measurements.get(i)) {
                max = measurements.get(i);
                position = i;
            }
        }
        return new Pair<Integer, Long>(position, max);
    }

    public long normalDist () {
        NormalDistribution distribution = new NormalDistribution(average(), desvio());
        long sum = 0;
        double sumProb = 0;
        for (long value : measurements) {
            double probability = distribution.probability(value);
            sumProb += probability;
            double result = (double) value * probability;
            sum += result;
        }
        double value = (double) sum / sumProb;
        return (long) value;
    }

    public String toString () {
        StringBuilder result = new StringBuilder();
        result.append("Avg: ").append(toString(average())).append("\n");
        result.append("Dsv: ").append(toString((long) desvio())).append("\n");
//        result.append("Nrm: ").append(toString(normalDist())).append("\n");
        result.append("Max: ").append(toString(max())).append("\n");
        result.append("Min: ").append(toString(min())).append("\n");

        return result.toString();
    }

    private String toString(Pair<Integer, Long> max) {
        return "(pos: "+max.getFirst()+") "+toString(max.getSecond());
    }

    private String toString(long average) {
        long averageMicro = average;
        long averageMillis = averageMicro / 1000;
        long averageSeconds = averageMillis / 1000;
        long averageMinutes = averageSeconds / 60;

        StringBuilder result = new StringBuilder();
        result.append(averageMinutes).append(" minutes ");
        result.append(averageSeconds % 60).append(" seconds ");
        result.append(averageMillis % (1000 * 60)).append(" milliseconds ");
        result.append(averageMicro % (1000 * 1000 * 60)).append(" microseconds ");
        return result.toString();
    }
}
