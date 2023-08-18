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
import sunshine.titans.service.TrackerService;

import java.util.Collection;

@RestController
@RequestMapping("/api/Tracker-Controller") //Renamed
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
    public Stock getStockByID(@PathVariable("id") int id) { //TrackerController or Service?
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
    public void deleteCd(@PathVariable("id") int id) {
        service.deleteStock(id);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCd(@RequestBody Stock stock) {
        service.deleteStock(stock);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addCd(@RequestBody Stock stock) {
        service.addNewStock(stock);
    }

}
