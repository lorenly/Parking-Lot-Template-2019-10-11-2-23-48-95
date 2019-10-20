package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking_lot")
public class ParkingLotController {
    @Autowired
    ParkingLotService parkingLotService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = {"application/json"})
    public Iterable<ParkingLot> listAllParkingLot(@RequestParam (required = false) Integer page, @RequestParam (required = false) Integer pageSize){
        return parkingLotService.findAll(page, pageSize);
    }


    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingLot addParkingLot(@RequestBody ParkingLot parkingLot){
        return parkingLotService.save(parkingLot);
    }


}
