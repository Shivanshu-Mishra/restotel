package com.shiva.restotel.internal;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@ApiModel(description = "All details about employees in restotel .")
//@JsonFilter("EmployeeIdFilter")
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    @Size(min = 2)
    @NotNull
    @ApiModelProperty(notes = "Employee name should neither be null and should contain atleast 3 characters")
    private String name;
    private String designation;
    private int age;
    private long salaryInDollars;

    public Employee(){}

    public Employee(@Size(min = 2) @NotNull String name, String designation, int age, long salaryInDollars) {
        this.name = name;
        this.designation = designation;
        this.age = age;
        this.salaryInDollars = salaryInDollars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getSalaryInDollars() {
        return salaryInDollars;
    }

    public void setSalaryInDollars(Long salaryInDollars) {
        this.salaryInDollars = salaryInDollars;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", age=" + age +
                ", salary=" + salaryInDollars +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age &&
                id.equals(employee.id) &&
                name.equals(employee.name) &&
                designation.equals(employee.designation) &&
                salaryInDollars==employee.salaryInDollars;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, designation, age, salaryInDollars);
    }
}

