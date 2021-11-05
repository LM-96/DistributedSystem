package it.distributedsystems.model.ejb

import javax.persistence.*

@Entity
@Table
class Department(
    @Id
    @GeneratedValue
    var id : Long? = null,

    var name : String,

    @OneToMany(mappedBy="department",cascade=[CascadeType.PERSIST], targetEntity = Employee::class)
    var employees: List<Employee> = mutableListOf()
) {
}