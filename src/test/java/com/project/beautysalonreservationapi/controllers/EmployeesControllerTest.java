package com.project.beautysalonreservationapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.beautysalonreservationapi.dto.EmployeeDto;
import com.project.beautysalonreservationapi.dto.EmployeeScheduleDto;
import com.project.beautysalonreservationapi.exceptions.EmployeeNotFoundException;
import com.project.beautysalonreservationapi.models.CustomConfirmationMessage;
import com.project.beautysalonreservationapi.models.OperationStatus;
import com.project.beautysalonreservationapi.services.EmployeeServicesImp;
import com.project.beautysalonreservationapi.services.ReservationServicesImp;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeesController.class)
public class EmployeesControllerTest {
    private static final String ENDPOINT_PATH = "/employees";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeServicesImp employeeServicesImp;

    @MockBean
    private ReservationServicesImp reservationServicesImp;

    //GET /employees
    @Test
    public void givenExpectedEmployees_whenGet_returnListOfEmployees() throws Exception {
        //arrange
        EmployeeDto employee1 = new EmployeeDto("Hello","Kitty","1213450987" ,"HelloKitty@example.com");
        employee1.setId(1L);
        EmployeeDto employee2 = new EmployeeDto("Hello2","Kitty2","3212450987" ,"HelloKitty2@example.com");
        employee2.setId(2L);
        List<EmployeeDto> employeeDtoList = List.of(employee1,employee2);
        given(employeeServicesImp.getAllEmployees()).willReturn(employeeDtoList);
        //act & expect
        mockMvc.perform(get(ENDPOINT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[1].id",is(2)));

    }

    @Test
    public void givenEmptyList_whenGet_returnEmpty() throws Exception {
        //arrange
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        given(employeeServicesImp.getAllEmployees()).willReturn(employeeDtoList);
        //act
        mockMvc.perform(get(ENDPOINT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(0)));
    }
    //GET /employees/id
    @Test
    public void givenValidId_whenGet_returnCorrectEmployee() throws Exception {
        //set up
        EmployeeDto employee = new EmployeeDto("Hello","Kitty","1213450987" ,"HelloKitty@example.com");
        employee.setId(1l);

        String uri = ENDPOINT_PATH +"/" +employee.getId();

        given(employeeServicesImp.getEmployee(1)).willAnswer(invocation -> employee);

        //act & expect
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1l))
                .andExpect(jsonPath("$.firstName").value("Hello"))
                .andExpect(jsonPath("$.lastName").value("Kitty"))
                .andExpect(jsonPath("$.phone").value("1213450987"))
                .andExpect(jsonPath("$.email").value("HelloKitty@example.com"));

    }

    @Test
    public void givenNonExistentId_whenGet_returnNotFound() throws Exception {
        //arrange
        long id = 1100;
        String uri = ENDPOINT_PATH +"/" +id;
        given(employeeServicesImp.getEmployee(id))
                .willThrow(EmployeeNotFoundException.class);

        //act & expect
        mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    //PUT /employees/id
    @Test
    public void givenInvalidEmployee_whenPut_returnBadRequestException() throws Exception {
        //setup
        EmployeeDto employee = new EmployeeDto("", "", "", "");
        employee.setId(1100l);
        String empData = objectMapper.writeValueAsString(employee);
        long id = 1100;
        String uri = ENDPOINT_PATH +"/" +id;

        //act & expect
        mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(empData))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void givenValidId_whenUpdate_returnUpdatedEmployee() throws Exception {
        //arrange
        EmployeeDto employee = new EmployeeDto("Hello","Kitty","1213450987" ,"HelloKitty@example.com");
        employee.setId(1100l);
        String empData = objectMapper.writeValueAsString(employee);
        long id = 1100;
        String uri = ENDPOINT_PATH +"/" +id;

        given(employeeServicesImp.updateEmployee(ArgumentMatchers.any(EmployeeDto.class),eq(id)))
                .willReturn(employee);

        //act & expect
        mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(empData))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void givenNonExistentId_whenPut_returnNotFound() throws Exception {
        //arrange
        EmployeeDto employee = new EmployeeDto("Hello","Kitty","1213450987" ,"HelloKitty@example.com");
        employee.setId(1l);
        String empData = objectMapper.writeValueAsString(employee);
        long id = 1100;
        String uri = ENDPOINT_PATH +"/" +id;

        given(employeeServicesImp.updateEmployee(ArgumentMatchers.any(EmployeeDto.class),eq(id)))
                .willThrow( new EmployeeNotFoundException(id));

        //act & expect
        mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(empData))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    //DELETE /employees/id
    @Test
    public void givenNonExistentId_whenDelete_returnFailedMessage() throws Exception {
        //arrange
        long id = 1000l;
        given(employeeServicesImp.deleteEmployee(id)).willThrow(EmployeeNotFoundException.class);
        //act & expect
        mockMvc.perform(delete(ENDPOINT_PATH  + id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidId_whenDelete_returnSuccessfulMessage() throws Exception {
        //arrange
        long id = 1000l;
        CustomConfirmationMessage message = new CustomConfirmationMessage("Employee with id "+ id +" is deleted successfully", OperationStatus.Succeeded);
        given(employeeServicesImp.deleteEmployee(id)).willReturn(message);
        //act & expect
        mockMvc.perform(delete(ENDPOINT_PATH +"/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(message.message()))
                .andExpect(jsonPath("$.status").value(OperationStatus.Succeeded.toString()));
    }

    @Test
    //post /employees
    void givenValidNewEmployee_whenSave_returnNewCreatedEmployee() throws Exception {

        //set up
        EmployeeDto employee = new EmployeeDto("Hello","Kitty","1213450987" ,"HelloKitty@example.com");
        String empData = objectMapper.writeValueAsString(employee);


//        when(employeeServicesImp.createEmployee(ArgumentMatchers.any(EmployeeDto.class)))
//                .thenAnswer(invocation -> {
//                    EmployeeDto emp = invocation.getArgument(0);
//                    emp.setId(1L);
//                    return emp;
//                });

        given(employeeServicesImp.createEmployee(ArgumentMatchers.any(EmployeeDto.class)))
                .willAnswer(invocation ->{
                    EmployeeDto emp = invocation.getArgument(0);
                    emp.setId(1l);
                    return emp;
                        }
                );
        //action
        ResultActions resultActions = mockMvc.perform(post(ENDPOINT_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(empData));
        //expect
        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location","/employees/1"))
                .andExpect(jsonPath("$.firstName").value("Hello"))
                .andExpect(jsonPath("$.lastName").value("Kitty"))
                .andExpect(jsonPath("$.phone").value("1213450987"))
                .andExpect(jsonPath("$.email").value("HelloKitty@example.com"));
    }
    @Test
    public void givenInvalidEmployee_whenSave_returnBadRequestException() throws Exception {
        //setup
        EmployeeDto emp = new EmployeeDto("", "", "", "");
        String data = objectMapper.writeValueAsString(emp);

        //act & expect
        mockMvc.perform(post(ENDPOINT_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(status().isBadRequest());

    }

    //POST employees/{employeeId}/availability
    //create available dates for an employee, without service that they do yet
    @Test
    void givenInvalidEmployeeId_WhenCreatedNewSchedule_ReturnEmployeeNotFound() throws Exception {
        long id = 1000l;
        EmployeeScheduleDto employeeSchedule = new EmployeeScheduleDto(LocalDate.parse("2023-04-04"),
                LocalTime.parse("08:00",DateTimeFormatter.ofPattern("HH:mm")),
                LocalTime.parse("16:00",DateTimeFormatter.ofPattern("HH:mm")) );
        employeeSchedule.setEmployeeId(1000l);
        List<EmployeeScheduleDto> list = List.of(employeeSchedule);

        String scheduleData = objectMapper.writeValueAsString(list);
        given(employeeServicesImp.createEmployeeSchedule(eq(id), anyList()))
                .willThrow(EmployeeNotFoundException.class);

        //act & expected
        mockMvc.perform(post("/employees/{employeeId}/schedule",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleData))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    @Test
    void givenEmployeeIdAndAListOfTheirAvailabilityFor_whenPost_ReturnAConfirmation() throws Exception {
        //what do I need to set my schedule: Id, a date, startTime ,endTime
        //arrange
        EmployeeScheduleDto employeeSchedule1 = new EmployeeScheduleDto(LocalDate.parse("2023-04-07"),
                LocalTime.parse("08:00", DateTimeFormatter.ofPattern("HH:mm")),
                LocalTime.parse("16:00",DateTimeFormatter.ofPattern("HH:mm")) );
        employeeSchedule1.setEmployeeId(1l);

        EmployeeScheduleDto employeeSchedule2 = new EmployeeScheduleDto(LocalDate.parse("2023-04-09"),
                LocalTime.parse("08:00", DateTimeFormatter.ofPattern("HH:mm")),
                LocalTime.parse("16:00",DateTimeFormatter.ofPattern("HH:mm")) );
        employeeSchedule2.setEmployeeId(1l);

        List<EmployeeScheduleDto> list = List.of(employeeSchedule1,employeeSchedule2);

        String scheduleData = objectMapper.writeValueAsString(list);
        CustomConfirmationMessage message = new CustomConfirmationMessage("Schedule on 2023-04-7, 2023-04-09 have " +
                "been successfully created.", OperationStatus.Succeeded);
        given(employeeServicesImp.createEmployeeSchedule(eq(1l), anyList()))
                .willReturn(message);

        //act & expect
        mockMvc.perform(post("/employees/1/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(scheduleData))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Schedule on 2023-04-7, 2023-04-09 have " +
                        "been successfully created."))
                .andExpect(jsonPath("$.status").value(OperationStatus.Succeeded.toString()));
    }



    //GET appointments by emp Id
    @Test
    void givenEmployeeIdThatHasAppointment_whenGet_ReturnListOfAppointments(){
        //03/31: too lazy for this now. Will comeback -,-
    }
}
