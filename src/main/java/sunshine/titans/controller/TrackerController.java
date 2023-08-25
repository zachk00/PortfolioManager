package sunshine.titans.controller;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sunshine.titans.model.BollingerBands;
import sunshine.titans.model.BollingerData;
import sunshine.titans.model.Stock;
import sunshine.titans.model.TimeSeriesData;
import sunshine.titans.model.Transaction;
import sunshine.titans.model.WatchlistStock;
import sunshine.titans.service.BollingerService;
import sunshine.titans.service.TrackerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/api/Tracker-Controller")
@CrossOrigin(origins = "http://localhost:4200") // allows requests from all domains
public class TrackerController {

    private static Logger logger = LogManager.getLogger(TrackerController.class);

    @Autowired
    private TrackerService trackerService;
    
    @Autowired
    private BollingerService bollingerService;

    @ApiOperation(value = "findAll", nickname = "findAll")
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Stock> findAll() {
        logger.info("managed to call a Get request for findAll");
        return trackerService.getPortfolio();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Stock getStockById(@PathVariable("id") int id) {
        return trackerService.getStockById(id);
    }

    @RequestMapping(method=RequestMethod.GET, value="/404/{id}")
    public ResponseEntity<Stock> getByIdWith404(@PathVariable("id") int id) {
        Stock stock = trackerService.getStockById(id);
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(stock, HttpStatus.OK);
        }
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteStock(@PathVariable("id") int id) {
        trackerService.deleteStock(id);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteStock(@RequestBody Stock stock) {
        trackerService.deleteStock(stock);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addCd(@RequestBody Stock stock) {
        trackerService.addNewStock(stock);
    }
    
    // watchlist methods
    
    @RequestMapping(method = RequestMethod.GET, value = "/watchlist")
    public Iterable<WatchlistStock> getWatchlist() {
        return trackerService.getWatchlist();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/watchlist/add{ticker}")
    public ResponseEntity<String> addToWatchlist(@RequestBody String ticker) {
    	System.out.println(ticker);
        trackerService.addToWaitlist(ticker);
        return ResponseEntity.status(HttpStatus.CREATED).body("Stock added to watchlist");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/watchlist/remove/{ticker}")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable String ticker) {
        System.out.println(ticker);
    	trackerService.removeStockFromWaitlist(ticker);
    	
    	JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", "Stock removed from watchlist");
    	
    	
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
    }

    //added request mappings for the search page.. 

    @PostMapping("/portfolio/addToPortfolio")
    public ResponseEntity<Stock> addStockToPortfolio(@RequestBody Stock stock) {
        Stock addedStock = trackerService.addToPortfolio(stock);
        return new ResponseEntity<>(addedStock, HttpStatus.CREATED);
    }
    //sell stock mapping for home page

    @PutMapping("/portfolio/sellFromPortfolio")
    public ResponseEntity<Stock> sellFromPortfolio(@RequestBody Stock stock) {

        Stock soldStock = trackerService.sellFromPortfolio(stock);
        return new ResponseEntity<>(soldStock, HttpStatus.CREATED);
    }
    

    @PostMapping("/transaction/addTransaction")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        Transaction addedTransaction = trackerService.addTransaction(transaction);
        return new ResponseEntity<>(addedTransaction, HttpStatus.CREATED);
    }
     @PostMapping("/transaction/sellTransaction")
    public ResponseEntity<Transaction> sellTransaction(@RequestBody Transaction transaction) {
        Transaction addedTransaction = trackerService.addTransaction(transaction);
        return new ResponseEntity<>(addedTransaction, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/calculate-bollinger")
    public SortedMap<Date, BollingerBands>  calculateBollingerBands(@RequestBody JSONObject requestData){
        // Implement your Bollinger Bands calculation logic here using the timeSeriesData
        // Return the BollingerBandsResult
    	
      
    	System.out.print(requestData.size());
    	Iterator<String> keys = requestData.keySet().iterator();
    	
    	//String key = keys.next();
    	Map<Date, BollingerData> timeSeries = new HashMap<Date, BollingerData>();
    	
    	
    	while (keys.hasNext()) {
    		
    		String key = keys.next();
    		
    		BollingerData dataPoint = new BollingerData();
    		LinkedHashMap<String, String> innerData = (LinkedHashMap<String, String>) requestData.get(key);
    		
    		String open = (String) innerData.get("open");
    		String close = (String) innerData.get("close");
    		String high = (String) innerData.get("high");
    		String low = (String) innerData.get("low");
    		String volume = (String) innerData.get("volume");
    		
    		dataPoint.setOpen(open);
    		dataPoint.setClose(close);
    		dataPoint.setHigh(high);
    		dataPoint.setLow(low);
    		dataPoint.setVolume(volume);
    		
    		String pattern = "yyyy-MM-dd"; // The pattern should match the format of your date string

            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    		
            Date date;
			try {
				date = dateFormat.parse(key);
				timeSeries.put(date, dataPoint);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		
    					
    	}
    	
    	TimeSeriesData data = new TimeSeriesData();
    	data.setTimeSeries(timeSeries);
    	
    	SortedMap<Date, BollingerBands> bands = bollingerService.calculateBollingerBands(data);
    	
    	return bands;
    }
}
