package it.distributedsystems.model.dao

import java.io.Serializable
import javax.persistence.*

@Entity
class Product(
    @Id
    @GeneratedValue
    var id : Int,

    @Column(unique = true)
    var productNumber : Int,

    var name : String,

    var price : Int,

    @ManyToOne(
        cascade = [CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH],
        fetch = FetchType.LAZY
    )
    var purchase : Purchase,

    @ManyToOne(
        cascade = [CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH],
        fetch = FetchType.LAZY
    )
    var producer : Producer
) : Serializable {

    companion object {
        @JvmStatic val serialVersionUID = 7879128649212648629L
    }
}