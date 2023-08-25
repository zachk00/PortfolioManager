package sunshine.titans.service;

import sunshine.titans.model.BollingerBands;
import sunshine.titans.model.Stock;
import sunshine.titans.model.TimeSeriesData;
import sunshine.titans.model.Transaction;
import sunshine.titans.model.WatchlistStock;

public interface TrackerService {
	Iterable<Stock> getPortfolio();
	
	Stock getStockById(int id);
	
	Stock addNewStock(Stock stock);
		
	void deleteStock(int id);
	
	void deleteStock(Stock stock);
	
	Stock updateStock(Stock stock); 

	Iterable<WatchlistStock> getWatchlist();
	
	void addToWaitlist(String ticker);
	
	void removeStockFromWaitlist(String ticker);
	
	Stock addToPortfolio(Stock stock);

    Transaction addTransaction(Transaction transaction);
	
}
	
	
