package sunshine.titans.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sunshine.titans.model.Stock;
import sunshine.titans.repo.StockRepository;
import sunshine.titans.service.TrackerServiceImpl;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTrackerService {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private TrackerServiceImpl trackerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddToPortfolio() {
        Stock existingStock = new Stock();
        existingStock.setTicker("AAPL");
        existingStock.setQuantity(10);
        existingStock.setTotalInvestment(BigDecimal.valueOf(2000));

        when(stockRepository.findByTicker("AAPL")).thenReturn(existingStock);
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Stock stockToAdd = new Stock();
        stockToAdd.setTicker("AAPL");
        stockToAdd.setQuantity(5);
        stockToAdd.setTotalInvestment(BigDecimal.valueOf(1500));

        Stock result = trackerService.addToPortfolio(stockToAdd);

        assertEquals(15, result.getQuantity());
        assertEquals(BigDecimal.valueOf(3500), result.getTotalInvestment());
    }

    @Test
    public void testSellFromPortfolioSufficientStocks() {
        Stock existingStock = new Stock();
        existingStock.setTicker("AAPL");
        existingStock.setQuantity(10);
        existingStock.setTotalInvestment(BigDecimal.valueOf(2000));

        when(stockRepository.findByTicker("AAPL")).thenReturn(existingStock);
        when(stockRepository.save(existingStock)).thenReturn(existingStock);

        Stock stockToSell = new Stock();
        stockToSell.setTicker("AAPL");
        stockToSell.setQuantity(5);

        Stock result = trackerService.sellFromPortfolio(stockToSell);

        assertEquals(5, result.getQuantity());
        assertEquals(BigDecimal.valueOf(1000), result.getTotalInvestment());
    }

    @Test
    public void testSellFromPortfolioInsufficientStocks() {
        Stock existingStock = new Stock();
        existingStock.setTicker("AAPL");
        existingStock.setQuantity(3);
        existingStock.setTotalInvestment(BigDecimal.valueOf(600));

        when(stockRepository.findByTicker("AAPL")).thenReturn(existingStock);

        Stock stockToSell = new Stock();
        stockToSell.setTicker("AAPL");
        stockToSell.setQuantity(5);

        assertThrows(IllegalArgumentException.class, () -> trackerService.sellFromPortfolio(stockToSell));
    }

    @Test
    public void testSellFromPortfolioStockNotFound() {
        when(stockRepository.findByTicker("AAPL")).thenReturn(null);

        Stock stockToSell = new Stock();
        stockToSell.setTicker("AAPL");
        stockToSell.setQuantity(5);

        assertThrows(IllegalArgumentException.class, () -> trackerService.sellFromPortfolio(stockToSell));
    }
}
