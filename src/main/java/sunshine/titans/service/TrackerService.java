package sunshine.titans.service;

import sunshine.titans.model.Stock;
import sunshine.titans.model.WatchlistStock;

public interface TrackerService {
	Iterable<Stock> getPortfolio();
	
	Stock getStockById(int id);
	
	Stock addNewStock(Stock stock);
		
	void deleteStock(int id);
	
	void deleteStock(Stock stock);
	
	Stock updateStock(Stock stock); //TODO check if already ownned, if so add/sub, if not add new entry
	
	//TODO
	// watchlist table, need request,  need endpoint, need entity
	
	Iterable<WatchlistStock> getWatchlist();
	
	void addToWaitlist(String ticker);
	
	void removeStockFromWaitlist(String ticker);
	
	
	
	// new routes are needed for update, delete, and todo services
		
	
	// new Backend services
	
	// Calculate PE
	// Calculate Portfolio Value
	// Get Transaction History
	// Add new transaction
	
	
	
	// getAvg High & Low
			// 1. front calls stock api
			// 2. front calls update stock our backend
			// 3. front sends it request
	// getPerformance 5 shares of citi at 50, now =100
		// 1. front calls stock api
		// 2. front calls update stock our backend
		// 3. front sends it request
	
	// getPerformance (strictly in fronted do not implement in backend)
		// *get from stock API
		
	//getBallinger(stock history)
	
	// view Portfolio
	// onPortfolioLoad - when a user looks at their portofolio
	// 1.) get the stockers owned by user
	//	2.) update current prices in db
	// 3.) get stock info
	
	// ticker, purchased price, #shares, current price, *****
	
	
	//Search for -> use stock API
	
	// add --> call 
	
}
	
	
