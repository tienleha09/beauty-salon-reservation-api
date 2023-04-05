package com.project.beautysalonreservationapi.DAL;

import com.project.beautysalonreservationapi.models.Employee;
import com.project.beautysalonreservationapi.models.Reservation;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long>, JpaSpecificationExecutor<Reservation> {
    List<Reservation> findByCustomerId(long customerId);
    List<Reservation> findByEmployeeId(long employeeId);

    default List<Reservation> findByEmployeeIdAndFilters(long employeeId, Map<String, String> queryParams){
        Specification<Reservation> specification = Specification.where(
                ((root, query, criteriaBuilder) -> criteriaBuilder.equal((root.get("employee")).get("id"),employeeId)));
        for(Map.Entry<String,String> filter: queryParams.entrySet() ){
            String key = filter.getKey();
            String value = filter.getValue();
            switch (key){
                case "startDate":
                    LocalDate start = LocalDate.parse(value);
                    specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("date"),value)));
                    break;
                case "endDate":
                    LocalDate end = LocalDate.parse(value);
                    specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("date"),value)));
                    break;
                case "serviceName":
                    specification = specification.and(((root, query, criteriaBuilder) ->
                            criteriaBuilder.like(root.get("service").get("name"),value)));
                    break;
                case "startTime":
                    specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("startTime"),value)));
                    break;
                case "endTime":
                    specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("endTime"),value)));
                    break;
            }
        }

        return findAll(specification);
    }
}
