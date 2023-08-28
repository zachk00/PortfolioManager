package sunshine.titans.controller;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sunshine.titans.model.Stock;
import sunshine.titans.model.Transaction;
import sunshine.titans.repo.StockRepository;
import sunshine.titans.repo.TransactionRepository;
import sunshine.titans.repo.WatchlistRepository;
import sunshine.titans.service.BollingerService;
import sunshine.titans.service.TrackerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ExtendWith(SpringExtension.class)
public class TestTrackerController {

    @Autowired
    private TrackerController controller;
    
    private static final Logger logger = LoggerFactory.getLogger(TestTrackerController.class);

    
    @TestConfiguration
    protected static class Config {
        @Bean
        @Primary
        public StockRepository stockRepo() {
            return mock(StockRepository.class);
        }
        
        @Bean
        @Primary
        public WatchlistRepository watchlistRepo() {
            return mock(WatchlistRepository.class);
        }
        
        @Bean
        @Primary
        public TransactionRepository transactionRepo() {
            return mock(TransactionRepository.class);
        }
        
        @Bean
        @Primary
        public TrackerService trackerService() {
            Stock stock = new Stock();
            List<Stock> stocks = new ArrayList<>();
            stocks.add(stock);
            
            
            TrackerService service = mock(TrackerService.class);
            when(service.getPortfolio()).thenReturn(stocks);
            when(service.getStockById(1)).thenReturn(stock);
            return service;


        }

        @Bean
        @Primary
        public BollingerService bollingerService() {
        	BollingerService service = mock(BollingerService.class);
        	
        	return service;
        }
        
        @Bean
        @Primary
        public TrackerController controller() {
            return new TrackerController();
        }
    }

    //@Disabled
    @Test
    public void testFindAll() {
        Iterable<Stock> stocks = controller.findAll();
        Stream<Stock> stream = StreamSupport.stream(stocks.spliterator(), false);
        assertThat(stream.count(), equalTo(1L));
    }

    //@Disabled
    @Test
    public void testStockById() {
        Stock stock = controller.getStockById(1);
        logger.debug(stock.toString());
        assertNotNull(stock);
    }

}
