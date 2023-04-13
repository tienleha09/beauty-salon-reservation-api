package com.project.beautysalonreservationapi.services;

import com.project.beautysalonreservationapi.DAL.EmployeeScheduleRepository;
import com.project.beautysalonreservationapi.DAL.SalonServiceRepository;
import com.project.beautysalonreservationapi.dto.EmpAvailabilityPerServiceDto;
import com.project.beautysalonreservationapi.models.Employee;
import com.project.beautysalonreservationapi.models.EmployeeSchedule;
import com.project.beautysalonreservationapi.models.Reservation;
import com.project.beautysalonreservationapi.models.SalonService;
import com.project.beautysalonreservationapi.util.SalonServiceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class SalonServiceServicesImplTest {

    @Mock
    private SalonServiceRepository serviceRepo;

    @Mock
    private SalonServiceMapper salonServiceMapper;
    @Mock
    private EmployeeScheduleRepository employeeScheduleRepository;

    @InjectMocks
    private SalonServiceServicesImpl salonServiceServices;

    @Test
    public void whenGivenAValidServiceIdAndDateRange_whenGet_returnAListOfEmployeeAvailability(){
        //arrange
        long serviceId = 1L;
        SalonService service = new SalonService("Haircut", "A simple hair cut", 25.50d);
        service.setId(serviceId);
        LocalDate startDate = LocalDate.parse("2023-06-29");
        LocalDate endDate = LocalDate.parse("2023-06-30");

        EmployeeSchedule schedule1 = new EmployeeSchedule(startDate,
                LocalTime.parse("09:00"), LocalTime.parse("11:00"));

        Employee employee = new Employee("John","Doe","123456789","sample@email.com");
        employee.setId(1000L);

        schedule1.setEmployee(employee);

        Reservation reservation = new Reservation(employee,service,startDate,LocalTime.parse("09:30"),LocalTime.parse("10:30"));
        employee.setReservations(List.of(reservation));

        given(serviceRepo.findById(1L)).willReturn(Optional.of(service));
        given(employeeScheduleRepository.findByServiceAndDateRange(serviceId,startDate,endDate))
                .willReturn(List.of(schedule1));

        EmpAvailabilityPerServiceDto expected = new EmpAvailabilityPerServiceDto(serviceId,employee.getId(),startDate,
                List.of(LocalTime.parse("09:00"), LocalTime.parse( "10:30")));
        expected.setServiceName(service.getName());
        expected.setEmployeeFullName(employee.getFullName());
        //act
        List<EmpAvailabilityPerServiceDto> availabilityPerServiceDtoList = salonServiceServices.getEmAvailabilityByServiceAndDate(serviceId,startDate,endDate);

        //expect

        assertThat(availabilityPerServiceDtoList).isNotEmpty();
        assertThat(availabilityPerServiceDtoList.size()).isEqualTo(1);

        System.out.println(availabilityPerServiceDtoList);

        EmpAvailabilityPerServiceDto actual = availabilityPerServiceDtoList.get(0);
        assertThat(actual).isEqualTo(expected);


//        assertThat(availabilityPerServiceDtoList).extracting("employeeId").contains(1000L);
//        assertThat(availabilityPerServiceDtoList).extracting("serviceId").contains(1L);
//        assertThat(availabilityPerServiceDtoList).extracting("date").contains(LocalDate.parse("2023-06-29"));
//        assertThat(availabilityPerServiceDtoList).flatExtracting("timeSlots")
//                .containsExactly(LocalTime.parse("09:00"), LocalTime.parse( "10:30"));


    }

}
