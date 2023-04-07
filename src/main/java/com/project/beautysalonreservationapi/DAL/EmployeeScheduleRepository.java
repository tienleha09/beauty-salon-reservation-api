package com.project.beautysalonreservationapi.DAL;

import com.project.beautysalonreservationapi.models.EmployeeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeScheduleRepository extends JpaRepository<EmployeeSchedule,Long>{

    @Query("select es from EmployeeSchedule es " +
            "inner join es.employee e " +
            "inner join e.services s " +
            "where s.id = :serviceId and es.date between :startDate and :endDate")
    List<EmployeeSchedule> findByServiceAndDateRange(@Param("serviceId") long serviceId, @Param("startDate") LocalDate start, @Param("endDate") LocalDate end);
}
