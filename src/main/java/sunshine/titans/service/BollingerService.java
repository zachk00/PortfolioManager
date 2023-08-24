package sunshine.titans.service;

import org.springframework.stereotype.Service;

import sunshine.titans.model.BollingerBands;
import sunshine.titans.model.TimeSeriesData;

@Service
public interface BollingerService {

    public BollingerBands calculateBollingerBands(TimeSeriesData timeSeriesData);
}