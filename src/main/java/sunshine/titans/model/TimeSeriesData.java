package sunshine.titans.model;

import java.util.Date;
import java.util.Map;

public class TimeSeriesData {
    private Map<Date, BollingerData> timeSeries;

	public Map<Date, BollingerData> getTimeSeries() {
		return timeSeries;
	}

	public void setTimeSeries(Map<Date, BollingerData> timeSeries) {
		this.timeSeries = timeSeries;
	}
	

    
}
