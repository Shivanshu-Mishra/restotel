package com.shiva.restotel.internal;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private EmployeeRegister register;
    private EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRegister register,EmployeeModelAssembler assembler) {
        this.assembler=assembler;
        this.register = register;
    }

    @GetMapping("/internal/employees")
    public List<EntityModel<Employee>> getAllEmployeesId() {
        return register.findAll().stream()
                .map(employee -> EntityModel.of(employee,
                        linkTo(methodOn(EmployeeController.class).getEmployeeDetails(employee.getId())).withRel("id")))
                .collect(Collectors.toList());
    }

    @GetMapping("/internal/ids/{id}")
    public EntityModel<Employee> getEmployeeDetails(@PathVariable Long id) {
        Employee employee=register.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No employee found with this id"));
        return assembler.toModel(employee);
    }

/*    @GetMapping("/internal/ids")
    public MappingJacksonValue trimmedEmployee(){
        List<Employee> employees=register.findAll();
        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("id");
        FilterProvider filters=new SimpleFilterProvider().addFilter("EmployeeIdFilter",filter);
        MappingJacksonValue mapping= new MappingJacksonValue(employees);
        mapping.setFilters(filters);
        return mapping;
    }*/

}
