package sunshine.titans.controller;
//import org.apache.log4j.Logger;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public Iterable<TrackerController> findAll() {
        logger.info("managed to call a Get request for findAll");
        return service.getCatalog();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public TrackerController getCdById(@PathVariable("id") int id) { //TrackerController or Service?
        return service.get(id);
    }

    @RequestMapping(method=RequestMethod.GET, value="/404/{id}")
    public ResponseEntity<TrackerController> getByIdWith404(@PathVariable("id") int id) {
        TrackerController disc = service.getCompactDiscById(id);
        if (disc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(disc, HttpStatus.OK);
        }
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteCd(@PathVariable("id") int id) {
        service.deleteTrackerService(id);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCd(@RequestBody TrackerController disc) {
        service.deleteTrackerController(disc);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addCd(@RequestBody TrackerController disc) {
        service.addNewTrackerController(disc);
    }

}
