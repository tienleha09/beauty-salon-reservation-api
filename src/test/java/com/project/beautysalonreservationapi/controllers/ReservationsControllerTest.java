package com.project.beautysalonreservationapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.beautysalonreservationapi.dto.CustomerReservationDto;
import com.project.beautysalonreservationapi.exceptions.InvalidTimeRangeException;
import com.project.beautysalonreservationapi.services.ReservationServicesImp;
import org.hibernate.cfg.NotYetImplementedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReservationsController.class)
public class ReservationsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationServicesImp reservationServicesImp;
    private static final String PATH = "/reservations";


    @Test
    public void givenInvalidReservation_whenCreate_returnBadRequest() throws Exception {
        CustomerReservationDto customerReservationDto = new CustomerReservationDto(1l,1l,1l,
                LocalDate.parse("2019-05-29"), LocalTime.MIDNIGHT,LocalTime.MIDNIGHT);
        String data = objectMapper.writeValueAsString(customerReservationDto);

        //act
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenInvalidTimeRange_whenCreate_returnBadRequest() throws Exception {
        CustomerReservationDto customerReservationDto = new CustomerReservationDto(1l,1l,1l,
                LocalDate.parse("2024-05-29"), LocalTime.parse("18:30"),LocalTime.parse("16:00"));
        String data = objectMapper.writeValueAsString(customerReservationDto);

        given(reservationServicesImp.createReservation(any(CustomerReservationDto.class)))
                .willThrow(new InvalidTimeRangeException(customerReservationDto.getStartTime(),customerReservationDto.getEndTime()));

        mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andDo(print())
                .andExpect(status().isBadRequest());
        ;
    }

    @Test
    public void givenValidReservation_whenCreate_returnCreated() throws Exception {
        CustomerReservationDto customerReservationDto = new CustomerReservationDto(1l,1l,1l,
                LocalDate.parse("2024-05-29"), LocalTime.parse("09:30:00"),LocalTime.parse("12:00:00"));
        String data = objectMapper.writeValueAsString(customerReservationDto);

        given(reservationServicesImp.createReservation(any(CustomerReservationDto.class)))
                .willReturn(customerReservationDto);

        mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(1l))
                .andExpect(jsonPath("$.customerId").value(1l))
                .andExpect(jsonPath("$.serviceId").value(1l))
                .andExpect(jsonPath("$.date").value(customerReservationDto.getDate().toString()))
                .andExpect(jsonPath("$.startTime").value(customerReservationDto.getStartTime().toString()))
                .andExpect(jsonPath("$.endTime").value(customerReservationDto.getEndTime().toString()));
        ;
    }
}
