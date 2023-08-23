package sunshine.titans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sunshine.titans.model.Stock;
import sunshine.titans.model.Transaction;
import sunshine.titans.model.WatchlistStock;
import sunshine.titans.repo.StockRepository;
import sunshine.titans.repo.TransactionRepository;
import sunshine.titans.repo.WatchlistRepository;

import java.util.Optional;

@Service
public class TrackerServiceImpl implements TrackerService{
	//TODO logging
	@Autowired
	private StockRepository stockDAO;
	
	@Autowired
	private WatchlistRepository watchlistDAO;

	@Autowired
    private TransactionRepository transactionDAO;
	
	
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

	@Transactional
	public void addToWaitlist(String ticker) {
		//WatchlistStock newWatchlistStock = new WatchlistStock(ticker);
		
		WatchlistStock newWatchlistStock = watchlistDAO.findByTicker(ticker);
		
		if (newWatchlistStock == null){
			watchlistDAO.save(new WatchlistStock(ticker));	
		}
		
		
		
	}

	@Transactional
	public void removeStockFromWaitlist(String ticker) {
		try{
			watchlistDAO.deleteByTicker(ticker);
		}catch (Exception e) {
		    e.printStackTrace();
		}
			
	}

//logic for buying stock , saving to transaction.
	@Override
    public Stock addToPortfolio(Stock stock) {
        return stockDAO.save(stock);
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        return transactionDAO.save(transaction);
    }
}
