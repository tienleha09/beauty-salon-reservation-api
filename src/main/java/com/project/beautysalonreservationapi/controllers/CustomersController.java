package com.project.beautysalonreservationapi.controllers;

import com.project.beautysalonreservationapi.dto.CustomerReservationDto;
import com.project.beautysalonreservationapi.services.ReservationServicesImp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private ReservationServicesImp reservationServicesImp;

    public CustomersController(ReservationServicesImp reservationServicesImp) {
        this.reservationServicesImp = reservationServicesImp;
    }

    @GetMapping("{customerId}/reservations")
    public ResponseEntity<List<CustomerReservationDto>> getReservationsByCustomer(@PathVariable long customerId){
        List<CustomerReservationDto> list = this.reservationServicesImp.getReservationByCustomerId(customerId);
        if(list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

}
