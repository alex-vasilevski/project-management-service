package com.mangement.api.rest.controller.employee;

import com.mangement.api.dto.Employee;
import com.mangement.api.dto.PersonalInformation;
import com.mangement.service.employee.EmployeeService;
import com.mangement.store.domain.employee.Division;
import com.mangement.store.domain.employee.Role;
import com.mangement.exception.EmployeeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Set;

@RestController
public class EmployeeController{

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private static final String GET_EMPLOYEE_BY_ID = "/employees/{employee_id}";
    private static final String GET_ALL_MATCHING_EMPLOYEES = "/employees";
    private static final String GET_ALL_MATCHING_EMPLOYEES_ON_PROJECT = "/employees/{project_id}";
    private static final String GET_ALL_EMPLOYEES_ASSIGNED_ON_PROJECT = "/employees/{project_id}";
    private static final String PUT_EMPLOYEE = "/employees/{employee_id}";
    private static final String POST_NEW_EMPLOYEE = "/employees";
    private static final String DELETE_EMPLOYEE = "/employees/{employee_id}";

    @Autowired
    @Qualifier("employeeServiceImpl")
    private EmployeeService employeeService;

    @PostMapping(POST_NEW_EMPLOYEE)
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) {
        logger.info("started to handle POST request on end point " + POST_NEW_EMPLOYEE);
        employeeService.create(employee);
        return ResponseEntity.created(URI.create(POST_NEW_EMPLOYEE)).build();
    }

    @GetMapping(GET_EMPLOYEE_BY_ID)
    public ResponseEntity<Employee> findById(@NotNull @PathVariable Long id) throws EmployeeNotFoundException {
        logger.info("started to handle GET request on end point " + GET_EMPLOYEE_BY_ID);
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @GetMapping(GET_ALL_MATCHING_EMPLOYEES)
    public ResponseEntity<Page<Employee>> findAllMatchingAndSort(@RequestParam(name = "start_day", required = false) PersonalInformation personalInfo,
                                                                 @RequestParam(name = "salary", required = false) Double salary,
                                                                 @RequestParam(name = "division", required = false) Division division,
                                                                 @RequestParam (name = "role", required = false) Role role,
                                                                 @RequestParam(name = "direction", required = false) String direction,
                                                                 @RequestParam(name = "sort_param", required = false) Set<String> sortParams)
                                                                 throws EmployeeNotFoundException {
        logger.info("started to handle GET request on end point " + GET_ALL_MATCHING_EMPLOYEES);
        Employee employee = new Employee(salary, division, role, personalInfo);
        return ResponseEntity.ok(employeeService.findAllMatchingAndSort(employee, direction, sortParams));
    }

    @GetMapping(GET_ALL_EMPLOYEES_ASSIGNED_ON_PROJECT)
    public ResponseEntity<Page<Employee>> findAllEmployeesOnProject(@NotNull @PathVariable Long projectId,
                                                                    @RequestParam(name = "direction", required = false) String direction,
                                                                    @RequestParam(name = "sort_param", required = false) Set<String> sortParams)
                                                                    throws EmployeeNotFoundException {

        logger.info("started to handle GET request on end point " + GET_ALL_MATCHING_EMPLOYEES);
        return ResponseEntity.ok(employeeService.findAllOnProject(projectId, direction, sortParams));
    }

    @PutMapping(PUT_EMPLOYEE)
    public ResponseEntity<Employee> update(@NotNull @PathVariable Long id, @Valid @RequestBody Employee source) throws EmployeeNotFoundException {
        logger.info("started to handle PUT request on end point " + PUT_EMPLOYEE);
        return ResponseEntity.ok(employeeService.update(id, source));
    }

    @DeleteMapping(DELETE_EMPLOYEE)
    public ResponseEntity<Employee> deleteById(@NotNull @PathVariable Long id) {
        logger.info("started to handle DELETE request on end point " + DELETE_EMPLOYEE);
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}