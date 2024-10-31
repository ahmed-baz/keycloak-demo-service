package org.demo.app.controller;


import lombok.RequiredArgsConstructor;
import org.demo.app.dto.EmployeeDto;
import org.demo.app.payload.AppResponse;
import org.demo.app.service.EmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping
    @PreAuthorize("hasAnyRole('emp_admin','emp_user')")
    public AppResponse<List<EmployeeDto>> findEmployeeList() {
        List<EmployeeDto> list = employeeService.findList();
        return AppResponse.ok(list);
    }

    @GetMapping(value = "/create/{size}")
    @PreAuthorize("hasRole('emp_admin')")
    public AppResponse<List<EmployeeDto>> createRandomList(@PathVariable int size) {
        List<EmployeeDto> randomList = employeeService.createRandomList(size);
        return AppResponse.created(randomList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('emp_admin','emp_user')")
    public AppResponse<EmployeeDto> findById(@PathVariable Long id) {
        return AppResponse.ok(employeeService.findById(id));
    }

    @GetMapping("/query")
    @PreAuthorize("hasAnyRole('emp_admin','emp_user')")
    public AppResponse<EmployeeDto> findByEmail(@RequestParam String email) {
        return AppResponse.ok(employeeService.findByEmail(email));
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyRole('emp_admin')")
    public AppResponse<Long> count() {
        return AppResponse.ok(employeeService.count());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('emp_admin')")
    public AppResponse<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        return AppResponse.created(employeeService.create(employeeDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('emp_admin')")
    public AppResponse<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        return AppResponse.ok(employeeService.update(id, employeeDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('emp_admin')")
    public AppResponse<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return AppResponse.noContent();
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('emp_admin')")
    public AppResponse<Void> deleteAll() {
        employeeService.deleteAll();
        return AppResponse.noContent();
    }
}
