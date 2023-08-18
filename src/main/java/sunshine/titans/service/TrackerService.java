package sunshine.titans.service;

import sunshine.titans.model.Stock;

public interface TrackerService {
	Iterable<Stock> getPortfolio();
	
	Stock getStockById(int id);
	
	Stock addNewStock(Stock stock);
		
	void deleteStock(int id);
	
	void deleteStock(Stock stock);
	
	Stock updateStock(Stock stock);
}
