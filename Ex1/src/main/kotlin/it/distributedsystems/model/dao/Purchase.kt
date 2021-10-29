package it.distributedsystems.model.dao

import java.io.Serializable
import javax.persistence.*

@Entity
class Purchase(
    @Id
    @GeneratedValue
    var id : Int,

    @Column(unique = true)
    var purchaseNumber : Int,

    @ManyToOne(
        cascade = [CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH],
        fetch = FetchType.LAZY
    )
    var customer : Customer,

    @OneToMany(
        cascade=[CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH],
        fetch=FetchType.LAZY,
        mappedBy = "purchase"
    )
    var products : Set<Product>
) : Serializable {

    companion object {
        @JvmStatic val serialVersionUID = 4612874195612951296L
    }
}
