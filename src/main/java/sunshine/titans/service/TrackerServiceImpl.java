package sunshine.titans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sunshine.titans.model.Stock;
import sunshine.titans.repo.StockRepository;

import java.util.Optional;

@Service
public class TrackerServiceImpl implements TrackerService{
	//TODO logging
	@Autowired
	private StockRepository dao;
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Iterable<Stock> getPortfolio() {
		
		return dao.findAll();
	}
		
	
	@Override
	public Stock getStockById(int id) {
		Optional<Stock> stockOptional = dao.findById(id);
		if (stockOptional.isPresent()) {
			return stockOptional.get();
		}
		return null;
	}

	@Override
	public Stock addNewStock(Stock stock) {
		stock.setId(0);
		return dao.save(stock);
	}

	@Override
	public void deleteStock(int id) {
		Stock deleteStock = dao.findById(id).get();
		deleteStock(deleteStock);
		
	}

	@Override
	public void deleteStock(Stock stock) {
		dao.delete(stock);
		
	}

	@Override
	public Stock updateStock(Stock stock) {
		return dao.save(stock);
	}

}
