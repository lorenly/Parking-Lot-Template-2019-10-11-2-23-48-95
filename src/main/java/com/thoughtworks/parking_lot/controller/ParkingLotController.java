package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.errorExceptionHandler.BadRequestException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking_lot")
public class ParkingLotController {
    @Autowired
    ParkingLotService parkingLotService;
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ParkingLot> listAllParkingLot(@RequestParam (required = false, defaultValue = "0") Integer page,
                                                  @RequestParam (required = false, defaultValue = "15") Integer pageSize){
        return parkingLotService.findAll(page, pageSize);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingLot addParkingLot(@RequestBody ParkingLot parkingLot){
        return parkingLotService.saveParkingLot(parkingLot);
    }

    @DeleteMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ParkingLot deleteParkingLot(@PathVariable String name) throws BadRequestException {
        return parkingLotService.deleteParkingLot(name);
    }


    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ParkingLot findByName(@PathVariable String name) throws NotFoundException {
        return parkingLotService.findByName(name);
    }

    @PatchMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ParkingLot updateParkingLot(@PathVariable("name") String name, @RequestBody ParkingLot parkingLot) throws BadRequestException {
        return parkingLotService.updateParkingLot(name, parkingLot);
    }

}
