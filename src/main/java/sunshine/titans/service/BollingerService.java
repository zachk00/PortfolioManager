package sunshine.titans.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

import org.springframework.stereotype.Service;

import sunshine.titans.model.BollingerBands;
import sunshine.titans.model.TimeSeriesData;

@Service
public interface BollingerService {
	
    public SortedMap<Date, BollingerBands> calculateBollingerBands(TimeSeriesData timeSeriesData);
}