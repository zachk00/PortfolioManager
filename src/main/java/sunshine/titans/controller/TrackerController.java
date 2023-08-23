package sunshine.titans.controller;
//import org.apache.log4j.Logger;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import sunshine.titans.model.Stock;
import sunshine.titans.model.Transaction;
import sunshine.titans.model.WatchlistStock;
import sunshine.titans.service.TrackerService;

import java.util.Collection;
import org.json.simple.JSONObject;

@RestController
@RequestMapping("/api/Tracker-Controller")
@CrossOrigin // allows requests from all domains
public class TrackerController {

    private static Logger logger = LogManager.getLogger(TrackerController.class);

    @Autowired
    private TrackerService service;

    @ApiOperation(value = "findAll", nickname = "findAll")
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Stock> findAll() {
        logger.info("managed to call a Get request for findAll");
        return service.getPortfolio();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Stock getStockById(@PathVariable("id") int id) {
        return service.getStockById(id);
    }

    @RequestMapping(method=RequestMethod.GET, value="/404/{id}")
    public ResponseEntity<Stock> getByIdWith404(@PathVariable("id") int id) {
        Stock stock = service.getStockById(id);
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(stock, HttpStatus.OK);
        }
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteStock(@PathVariable("id") int id) {
        service.deleteStock(id);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteStock(@RequestBody Stock stock) {
        service.deleteStock(stock);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addCd(@RequestBody Stock stock) {
        service.addNewStock(stock);
    }
    
    // watchlist methods
    
    @RequestMapping(method = RequestMethod.GET, value = "/watchlist")
    public Iterable<WatchlistStock> getWatchlist() {
        return service.getWatchlist();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/watchlist/add{ticker}")
    public ResponseEntity<String> addToWatchlist(@RequestBody String ticker) {
    	System.out.println(ticker);
        service.addToWaitlist(ticker);
        return ResponseEntity.status(HttpStatus.CREATED).body("Stock added to watchlist");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/watchlist/remove/{ticker}")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable String ticker) {
        System.out.println(ticker);
    	service.removeStockFromWaitlist(ticker);
    	
    	JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", "Stock removed from watchlist");
    	
    	
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    //added request mappings for the search page.. 

    @PostMapping("/portfolio/addToPortfolio")
    public ResponseEntity<Stock> addStockToPortfolio(@RequestBody Stock stock) {
        Stock addedStock = service.addToPortfolio(stock);
        return new ResponseEntity<>(addedStock, HttpStatus.CREATED);
    }

    @PostMapping("/transaction/addTransaction")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        Transaction addedTransaction = service.addTransaction(transaction);
        return new ResponseEntity<>(addedTransaction, HttpStatus.CREATED);
    }
    
    
}
