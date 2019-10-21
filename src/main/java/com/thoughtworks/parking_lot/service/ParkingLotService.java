package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.errorExceptionHandler.BadRequestException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {

    public static final String NOT_FOUND = "Not Found!";
    public static final String BAD_REQUEST = "Bad Request!";
    @Autowired
    ParkingLotRepository parkingLotRepository;

    public Iterable<ParkingLot> findAll( Integer page, Integer pageSize) {
        return parkingLotRepository.findAll(PageRequest.of(page, pageSize, Sort.by("name").ascending()));
    }


    public ParkingLot saveParkingLot(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public ParkingLot deleteParkingLot(String name) throws BadRequestException {
        Optional<ParkingLot> parkingLot = Optional.ofNullable(parkingLotRepository.findByNameContaining(name));
        if(parkingLot.isPresent()) {
            parkingLotRepository.delete(parkingLot.get());
            return parkingLot.get();
        }
        throw new BadRequestException(BAD_REQUEST);

    }

    public ParkingLot findByName(String name) throws NotFoundException {
        Optional<ParkingLot> parkingLot = Optional.ofNullable(parkingLotRepository.findByNameContaining(name));
        if(parkingLot.isPresent()){
            parkingLotRepository.findByNameContaining(name);
            return parkingLot.get();
        }
        throw new NotFoundException(NOT_FOUND);

    }

    public ParkingLot updateParkingLot(String name, ParkingLot parkingLot) throws BadRequestException {
        Optional<ParkingLot> updateCapacity = Optional.ofNullable((parkingLotRepository.findByNameContaining(name)));
        if(updateCapacity.isPresent()){
            updateCapacity.get().setCapacity(parkingLot.getCapacity());
            parkingLotRepository.save(updateCapacity.get());
            return updateCapacity.get();
        }
        throw new BadRequestException(BAD_REQUEST);
    }
}
