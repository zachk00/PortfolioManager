package sunshine.titans.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import sunshine.titans.model.BollingerBands;
import sunshine.titans.model.BollingerData;
import sunshine.titans.model.TimeSeriesData;

@Service
public class BollingerServiceImpl implements BollingerService {

    @Override
    public SortedMap<Date, BollingerBands> calculateBollingerBands(TimeSeriesData timeSeriesData) {
        Map<Date, BollingerData> timeSeries = timeSeriesData.getTimeSeries();
        SortedMap<Date, BollingerBands> bands = this.calculateBollingerBands(timeSeries, 30, 2);
        return bands;
    }

    public SortedMap<Date, BollingerBands> calculateBollingerBands(Map<Date, BollingerData> data, int windowSize, double numStdDeviations) {
        SortedMap<Date, BollingerBands> bollingerBands = new TreeMap<>();

        List<BollingerData> dataPoints = new ArrayList<>(data.values());
        List<Date> dates = new ArrayList<>(data.keySet());

        for (int i = windowSize - 1; i < dataPoints.size(); i++) {
            double[] typicalPrices = new double[windowSize];

            // Extract closing prices for the window
            for (int j = 0; j < windowSize; j++) {
                BollingerData dataPoint = dataPoints.get(i - j);
                double high = Double.parseDouble(dataPoint.getHigh());
                double low = Double.parseDouble(dataPoint.getLow());
                double close = Double.parseDouble(dataPoint.getClose());
                typicalPrices[j] = (high + low + close) / 3;
            }

            // Calculate the moving average for the window
            double movingAverage = Arrays.stream(typicalPrices).average().orElse(0);

            // Calculate the standard deviation for the window
            double sumSquaredDeviations = 0;
            for (double typicalPrice : typicalPrices) {
                double deviation = typicalPrice - movingAverage;
                sumSquaredDeviations += deviation * deviation;
            }
            double standardDeviation = Math.sqrt(sumSquaredDeviations / windowSize);

            // Calculate upper and lower Bollinger Bands
            double upperBand = movingAverage + numStdDeviations * standardDeviation;
            double lowerBand = movingAverage - numStdDeviations * standardDeviation;

            // Store the results in the bollingerBands map
            Date currentDate = dates.get(i);

            BollingerBands band = new BollingerBands();
            band.setLowerBand(lowerBand);
            band.setMiddleBand(movingAverage);
            band.setUpperBand(upperBand);

            bollingerBands.put(currentDate, band);
        }

        return bollingerBands;
    }
}
