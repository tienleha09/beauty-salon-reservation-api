package com.project.beautysalonreservationapi.controllers;

import com.project.beautysalonreservationapi.dto.CustomerReservationDto;
import com.project.beautysalonreservationapi.exceptions.InvalidTimeRangeException;
import com.project.beautysalonreservationapi.services.ReservationServicesImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private ReservationServicesImp reservationServicesImp;

    public ReservationsController(ReservationServicesImp reservationServicesImp) {
        this.reservationServicesImp = reservationServicesImp;
    }

    @GetMapping()
    public ResponseEntity<List<CustomerReservationDto>> getReservations(){
        List<CustomerReservationDto> list = this.reservationServicesImp.getReservations();
        if(list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @PostMapping()
    public ResponseEntity<?> createReservation(@Valid @RequestBody CustomerReservationDto customerReservationDto, HttpServletRequest request) throws InvalidTimeRangeException {
        CustomerReservationDto reservationDto = reservationServicesImp.createReservation(customerReservationDto);
        URI uri = URI.create(request.getRequestURI());

        return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(reservationDto);
    }

}
