package it.distributedsystems.model.ejb

import javax.persistence.*

@Entity
@Table
class Employee(
    @Id
    @GeneratedValue
    var id : Long,

    var name : String,

    @ManyToOne
    var department: Department
) {

    override fun toString(): String {
        return "Employee [id=$id, name=$name, department=${department.name}]"
    }

}