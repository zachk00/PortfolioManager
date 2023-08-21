package sunshine.titans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sunshine.titans.model.Stock;
import sunshine.titans.model.WatchlistStock;
import sunshine.titans.repo.StockRepository;
import sunshine.titans.repo.WatchlistRepository;

import java.util.Optional;

@Service
public class TrackerServiceImpl implements TrackerService{
	//TODO logging
	@Autowired
	private StockRepository stockDAO;
	
	@Autowired
	private WatchlistRepository watchlistDAO;
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Iterable<Stock> getPortfolio() {
		
		return stockDAO.findAll();
	}
		
	
	@Override
	public Stock getStockById(int id) {
		Optional<Stock> stockOptional = stockDAO.findById(id);
		if (stockOptional.isPresent()) {
			return stockOptional.get();
		}
		return null;
	}

	@Override
	public Stock addNewStock(Stock stock) {
		stock.setId(0);
		return stockDAO.save(stock);
	}

	@Override
	public void deleteStock(int id) {
		//TODO add error handling in case stock doesnt exists
	
		Stock deleteStock = stockDAO.findById(id).get();
		deleteStock(deleteStock);
		
	}

	@Override
	public void deleteStock(Stock stock) {
		stockDAO.delete(stock);
		
	}

	@Override
	public Stock updateStock(Stock stock) {
		return stockDAO.save(stock);
	}


	@Transactional(propagation = Propagation.REQUIRED)
	public Iterable<WatchlistStock> getWatchlist() {
		return watchlistDAO.findAll();
	}

	@Override
	public void addToWaitlist(String ticker) {
		WatchlistStock newWatchlistStock = new WatchlistStock(ticker);
		watchlistDAO.save(newWatchlistStock);
	}

	@Override
	public void removeStockFromWaitlist(String ticker) {
		watchlistDAO.deleteByTicker(ticker);	
	}

}
