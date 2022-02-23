package it.distributedsystems.model.ejb;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
 
    private String name;
     
    @ManyToOne
    private Department department;
 
    public Employee() {}
 
    public Employee(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public Employee(String name) {
        this.name = name;
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
 
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
 
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", department="
                + department.getName() + "]";
    }
}