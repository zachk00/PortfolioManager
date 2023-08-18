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
import sunshine.titans.repo.StockRepository;
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
        public StockRepository repo() {
            return mock(StockRepository.class);
        }

        @Bean
        @Primary
        public TrackerService service() {
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
        Stock stock = controller.getStockByID(1);
        logger.debug(stock.toString());
        assertNotNull(stock);
    }

}
