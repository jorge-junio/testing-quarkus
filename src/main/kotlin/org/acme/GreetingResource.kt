package org.acme

import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@Path("/")
class GreetingResource {

    @Inject
    lateinit var entityTestRepository: EntityManager

//    @Inject
//    lateinit var entityTestRepository: EntityTestRepository

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello RESTEasy"

    @GET
    @Path("insert")
    @Produces(MediaType.TEXT_PLAIN)
    fun insertEntityTest():String {
        val entityTest1 = EntityTest("1", "Teste")
        val entityTest2 = EntityTest("2", "Teste")
        val entityTest3 = EntityTest("3", "Teste")
        val entityTest4 = EntityTest("1", "Teste")

        val listPersist: List<EntityTest> = listOf(
            entityTest1, entityTest2, entityTest3, entityTest4)

        listPersist.onEach {
            try {
                entityTestRepository.transaction.begin()
                entityTestRepository.persist(it)
                entityTestRepository.transaction.commit()
            }catch (e: Exception){
                entityTestRepository.transaction.rollback()
            }
        }

        return "OK"
    }


    @GET
    @Path("find/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    fun findEntityTest(@PathParam ("id") id:String):String {
        val query = entityTestRepository.createQuery("SELECT t FROM EntityTest t WHERE t.nome = :id")
        query.setParameter("id", id);

        val resultList = query.getResultList()

        return resultList.size.toString() //resultList[0]?."nome" ?:"Erro"
    }



}