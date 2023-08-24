package sunshine.titans.model;

import java.util.Map;

public class TimeSeriesData {
    private Map<String, BollingerData> timeSeries;

	public Map<String, BollingerData> getTimeSeries() {
		return timeSeries;
	}

	public void setTimeSeries(Map<String, BollingerData> timeSeries) {
		this.timeSeries = timeSeries;
	}

    
}
