package sunshine.titans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import sunshine.titans.model.Stock;
import sunshine.titans.repo.StockRepository;

import java.util.Optional;

@Service
public class TrackerServiceImpl implements TrackerService{
	//TODO logging
	@Autowired
	private StockRepository dao;
	
	
	@Override
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStock(Stock stock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stock updateStock(Stock stock) {
		// TODO Auto-generated method stub
		return null;
	}

}
