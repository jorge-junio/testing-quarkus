package org.acme

import org.springframework.data.jpa.repository.JpaRepository

interface EntityTestRepository : JpaRepository<EntityTest, String> {

    fun findByNome(nome: String): EntityTest?
}
