package sunshine.titans.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sunshine.titans.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

	public Iterable<Stock> findByTicker(String ticker);
}
