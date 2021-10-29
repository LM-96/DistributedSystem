package it.distributedsystems.model.dao

import javax.persistence.*

@Entity
class Producer(
    @Id
    @GeneratedValue
    var id : Int,

    @Column(unique = true)
    var name : String,

    @OneToMany(
        cascade=[CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH],
        fetch=FetchType.LAZY,
        mappedBy = "producer"
    )
    var products : Set<Product>
) {
}