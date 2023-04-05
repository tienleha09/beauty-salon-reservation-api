package com.project.beautysalonreservationapi.controllers;

import com.project.beautysalonreservationapi.dto.EmployeeAppointmentDto;
import com.project.beautysalonreservationapi.dto.EmployeeDto;
import com.project.beautysalonreservationapi.dto.EmployeeScheduleDto;
import com.project.beautysalonreservationapi.exceptions.CustomExceptionDetails;
import com.project.beautysalonreservationapi.exceptions.EmployeeNotFoundException;
import com.project.beautysalonreservationapi.models.CustomConfirmationMessage;
import com.project.beautysalonreservationapi.services.EmployeeServicesImp;
import com.project.beautysalonreservationapi.services.ReservationServicesImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final ReservationServicesImp reservationServicesImp;
    private final EmployeeServicesImp employeeServicesImp;

    public EmployeesController(ReservationServicesImp reservationServicesImp,EmployeeServicesImp employeeServicesImp) {
        this.reservationServicesImp = reservationServicesImp;
        this.employeeServicesImp =  employeeServicesImp;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees(){
        List<EmployeeDto> list = this.employeeServicesImp.getAllEmployees();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable long employeeId){
        return ResponseEntity.ok(employeeServicesImp.getEmployee(employeeId));
    }
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeDto newEmpDto, @PathVariable long employeeId){
//        if(bindingResult.hasErrors()){
//            return ResponseEntity.badRequest().body(handleValidationErrors(bindingResult));
//        }

            EmployeeDto emp = employeeServicesImp.updateEmployee(newEmpDto,employeeId);
            return ResponseEntity.status(HttpStatus.CREATED).body(emp);


    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable long employeeId){

        CustomConfirmationMessage confirmationMessage = this.employeeServicesImp.deleteEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(confirmationMessage);
    }


    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){

        EmployeeDto newEmp = this.employeeServicesImp.createEmployee(employeeDto);
        URI uri = URI.create("/employees/" + newEmp.getId());
        //return ResponseEntity.status(HttpStatus.CREATED).body(newEmp);
        return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(newEmp);
    }

    @GetMapping("{employeeId}/appointments")
    public ResponseEntity<List<EmployeeAppointmentDto>> getAppointmentsByEmployee(@PathVariable long employeeId,
                                                                                  @RequestParam(required = false) Map<String,String> queryParams){
        List<EmployeeAppointmentDto> list;
        if(queryParams.isEmpty()){
            list = this.reservationServicesImp.getAppointmentByEmployeeId(employeeId);
        }
        else{
           list = this.reservationServicesImp.getAppointmentByEmployeeIdWithFilters(employeeId,queryParams);
        }

        if(list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @PostMapping("{employeeId}/schedule")
    public ResponseEntity<?> createScheduleForEmployee(@PathVariable long employeeId, @Valid @RequestBody List<EmployeeScheduleDto> employeeScheduleDtoList,
                                                       HttpServletRequest request){
        for(var schedule : employeeScheduleDtoList){
            if(schedule.getEmployeeId()!= employeeId){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CustomExceptionDetails(Timestamp.valueOf(LocalDateTime.now()),
                                "Updated employeeId and employeeId in request body does not match",
                                "Bad Request param or Id in request body"));
            }
        }


        CustomConfirmationMessage confirmationMessage = employeeServicesImp.createEmployeeSchedule(employeeId,employeeScheduleDtoList);
        URI uri = URI.create(request.getRequestURI());
        return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(confirmationMessage);
    }

//    public List<String> handleValidationErrors(BindingResult bindingResult){
//        //get error messages and display
//        List<String> errors = bindingResult.getAllErrors()
//                .stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.toList());
//        return errors;
//    }
}
