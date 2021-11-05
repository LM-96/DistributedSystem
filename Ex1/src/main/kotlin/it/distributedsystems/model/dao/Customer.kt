package it.distributedsystems.model.dao

import java.io.Serializable
import javax.persistence.*

@Entity
class Customer (
    @Id
    @GeneratedValue
    var id: Int,

    @Column(unique = true)
    var name : String,

    @OneToMany(
        cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH],
        fetch = FetchType.LAZY,
        mappedBy = "customer",
        targetEntity = Purchase::class
    )
    var purchases : Set<Purchase>
) : Serializable {

    companion object {
        @JvmStatic private val serialVersionUID = 318274891481L
    }
}
