package com.project.beautysalonreservationapi.util;

import com.project.beautysalonreservationapi.dto.EmployeeDto;
import com.project.beautysalonreservationapi.dto.ServiceIdNameDto;
import com.project.beautysalonreservationapi.models.Employee;
import com.project.beautysalonreservationapi.models.SalonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeMapperTest {

    private ModelMapper modelMapper;
    private EmployeeMapper employeeMapper;

    @BeforeEach
    public void setup(){
        modelMapper = new ModelMapper();
        employeeMapper = new EmployeeMapper(modelMapper);
    }

    @Test
    public void givenEmployee_whenMap_returnEmployeeDtoWithCorrectMappedValues(){
        //arrange
        Employee employee = new Employee("Hello", "Kitty","1234567890","hellokitty@example.com");
        employee.setId(1l);
        SalonService service1 = new SalonService("Massage","Give a relax massage", 45D);
        service1.setId(1l);
        SalonService service2 = new SalonService("Haircut","Simple haircut", 32D);
        service2.setId(2l);
        employee.getServices().add(service1);
        employee.getServices().add(service2);

        ServiceIdNameDto serviceIdNameDto1 = new ServiceIdNameDto();
        serviceIdNameDto1.setId(1l);
        serviceIdNameDto1.setName("Massage");

        ServiceIdNameDto serviceIdNameDto2 = new ServiceIdNameDto();
        serviceIdNameDto2.setId(2l);
        serviceIdNameDto2.setName("Haircut");

        //act
        EmployeeDto employeeDto = employeeMapper.toDto(employee);
        //assert
        assertThat(employeeDto).hasFieldOrPropertyWithValue("id",1l);

        assertThat(employeeDto).hasFieldOrPropertyWithValue("firstName", "Hello");

        assertThat(employeeDto).hasFieldOrPropertyWithValue("lastName","Kitty");

        assertThat(employeeDto).hasFieldOrPropertyWithValue("phone", "1234567890");

        assertThat(employeeDto).hasFieldOrPropertyWithValue("email","hellokitty@example.com");

        assertThat(employeeDto).hasFieldOrProperty("services");
        assertThat(employeeDto.getServices()).containsExactly(serviceIdNameDto1,serviceIdNameDto2);

    }
}
