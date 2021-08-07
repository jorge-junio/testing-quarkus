package org.acme

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EntityTestRepository : JpaRepository<EntityTest, String> {

    fun findByNome(nome: String): EntityTest?

    @Query("SELECT * FROM EntityTest")
    fun findQualquerCoisa(): List<EntityTest>
}
