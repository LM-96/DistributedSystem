package it.distributedsystems.model.ejb

import javax.persistence.*

@Entity
@Table
class Department(
    @Id
    @GeneratedValue
    var id : Long,

    var name : String,

    @OneToMany(mappedBy="department",cascade=[CascadeType.PERSIST])
    var employees: List<Employee> = mutableListOf<Employee>()
) {
}