package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking_lot")
public class ParkingLotController {
    @Autowired
    ParkingLotService parkingLotService;

    @GetMapping(value = "/all", produces = {"application/json"})
    public Iterable<ParkingLot> listAllParkingLot(@RequestParam (required = false) Integer page, @RequestParam (required = false) Integer pageSize){
        return parkingLotService.findAll(page, pageSize);
    }


}
