package org.acme

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="EntityTest")
class EntityTest (
    @Id
    @Column
    var id: String = "",

    @Column
    var nome: String = "",
)