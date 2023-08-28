package sunshine.titans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sunshine.titans.model.BollingerBands;
import sunshine.titans.model.Stock;
import sunshine.titans.model.TimeSeriesData;
import sunshine.titans.model.Transaction;
import sunshine.titans.model.WatchlistStock;
import sunshine.titans.repo.StockRepository;
import sunshine.titans.repo.TransactionRepository;
import sunshine.titans.repo.WatchlistRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TrackerServiceImpl implements TrackerService {
	// TODO logging
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
		// TODO add error handling in case stock doesnt exists

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
		// WatchlistStock newWatchlistStock = new WatchlistStock(ticker);

		WatchlistStock newWatchlistStock = watchlistDAO.findByTicker(ticker);

		if (newWatchlistStock == null) {
			watchlistDAO.save(new WatchlistStock(ticker));
		}

	}

	@Transactional
	public void removeStockFromWaitlist(String ticker) {
		try {
			watchlistDAO.deleteByTicker(ticker);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Stock addToPortfolio(Stock stock) {
		Stock existingStock = stockDAO.findByTicker(stock.getTicker());

		if (existingStock != null) {
			// Stock already exists, update the quantity and total investment
			int newQuantity = existingStock.getQuantity() + stock.getQuantity();
			BigDecimal newTotalInvestment = existingStock.getTotalInvestment().add(stock.getTotalInvestment());

			existingStock.setQuantity(newQuantity);
			existingStock.setTotalInvestment(newTotalInvestment);
			return stockDAO.save(existingStock);
		} else {
			// Stock doesn't exist, add a new stock entry
			return stockDAO.save(stock);
		}
	}

	@Override
	public Transaction addTransaction(Transaction transaction) {
		return transactionDAO.save(transaction);
	}
	
	@Override
	public Transaction sellTransaction(Transaction transaction) {
		return transactionDAO.save(transaction);
	}

	// implement sell logic... make it uodate that value
	@Override
	public Stock sellFromPortfolio(Stock stock) {
		Stock existingStock = stockDAO.findByTicker(stock.getTicker());

		if (existingStock != null) {
			int availableQuantity = existingStock.getQuantity();
			int sellQuantity = stock.getQuantity();

			if (sellQuantity <= availableQuantity) {
				// Reduce quantity and update total investment
				int newQuantity = availableQuantity - sellQuantity;
				BigDecimal newTotalInvestment = existingStock.getTotalInvestment()
						.subtract(existingStock.getTotalInvestment().multiply(
								BigDecimal.valueOf(sellQuantity).divide(BigDecimal.valueOf(availableQuantity), 2, RoundingMode.HALF_UP)));

				existingStock.setQuantity(newQuantity);
				existingStock.setTotalInvestment(newTotalInvestment);

				return stockDAO.save(existingStock);
			} else {
				// Not enough stocks to sell
				throw new IllegalArgumentException("Insufficient stocks to sell.");
			}
		} else {
			// Stock doesn't exist
			throw new IllegalArgumentException("Stock not found.");
		}
	}
	
	

}
