package sunshine.titans.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sunshine.titans.model.WatchlistStock;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchlistStock, Integer>{
	
	public WatchlistStock findByTicker(String ticker);
	
	public void deleteByTicker(String ticker);
}
