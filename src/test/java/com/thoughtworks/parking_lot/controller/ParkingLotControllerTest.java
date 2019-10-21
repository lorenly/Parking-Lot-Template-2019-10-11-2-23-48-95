package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.errorExceptionHandler.BadRequestException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;

import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(value = "Test")
public class ParkingLotControllerTest {
    @MockBean
    private ParkingLotService parkingLotService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    // TODO: 10/21/2019  Assert parkinglot data correctly
    @Test
    public void should_return_status_created_when_save_parking_lot() throws Exception {
        when(parkingLotService.saveParkingLot(myParkingLot())).thenReturn(myParkingLot());

        ResultActions resultActions = mvc.perform(post("/parking_lot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(myParkingLot())));
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void should_return_the_save_parking_lot_data() throws Exception {
        when(parkingLotService.saveParkingLot(any())).thenReturn(myParkingLot());

        ResultActions resultActions = mvc.perform(post("/parking_lot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(myParkingLot())));

        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name",is("MyParkingLot")))
                .andExpect(jsonPath("$.location", is("MyLocation")))
                .andExpect(jsonPath("$.capacity", is(50)));
    }

    @Test
    public void should_return_200_when_delete_parking_lot() throws Exception {
        when(parkingLotService.deleteParkingLot("MyParkingLot")).thenReturn(myParkingLot());

        ResultActions resultActions = mvc.perform(delete("/parking_lot/{name}", "MyParkingLot"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void should_return_400_when_deleting_not_exist_parking_lot() throws Exception {
        doThrow(BadRequestException.class).when(parkingLotService).deleteParkingLot("NotExistParkingLot");

        ResultActions resultActions = mvc.perform(delete("/parking_lot/{name}", "NotExistParkingLot"));

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void should_show_parking_lot_list_with_15_page_size_each_page() throws Exception {
        when(parkingLotService.findAll(0, 15)).thenReturn(Collections.singleton(myParkingLot()));

        ResultActions resultActions = mvc.perform(get("/parking_lot"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",is("MyParkingLot")))
                .andExpect(jsonPath("$[0].location", is("MyLocation")))
                .andExpect(jsonPath("$[0].capacity", is(50)));
    }

    @Test
    public void should_show_specific_parking_lot_detail() throws Exception {
        when(parkingLotService.findByName("MyParkingLot")).thenReturn(myParkingLot());

        ResultActions resultActions = mvc.perform(get("/parking_lot/MyParkingLot"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("MyParkingLot")))
                .andExpect(jsonPath("$.location", is("MyLocation")))
                .andExpect(jsonPath("$.capacity", is(50)));
    }

    @Test
    public void should_return_404_not_found_when_parking_lot_not_exist() throws Exception {
        doThrow(NotFoundException.class).when(parkingLotService).findByName("NotExistParkingLot");

        ResultActions resultActions = mvc.perform(get("/parking_lot/{name}", "NotExistParkingLot"));

        resultActions.andExpect(status().isNotFound());
    }



    private ParkingLot myParkingLot(){
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("MyParkingLot");
        parkingLot.setLocation("MyLocation");
        parkingLot.setCapacity(50);
        return parkingLot;
    }

}
