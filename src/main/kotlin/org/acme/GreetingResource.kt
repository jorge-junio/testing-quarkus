package org.acme

import java.util.logging.Logger
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
class GreetingResource {

    @Inject
    lateinit var entityTestRepository: EntityTestRepository

    @GET()
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello RESTEasy"

    @GET
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    fun updateEntityTest(): String {
        save()
        return "OK"
    }

    @GET
    @Path("insert")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    fun insertEntityTest(): String {
        save()
        return "OK"
    }

    @GET
    @Path("findall")
    @Produces(MediaType.TEXT_PLAIN)
    fun findEntityTest(): String {
        val resultList = entityTestRepository.findAll()
        return resultList.joinToString(separator = "\n") { "(${it.id}, ${it.nome})" }
    }

    @GET
    @Path("find/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    fun findEntityTest(@PathParam("id") id: String): String =
        entityTestRepository.findById(id).toString()

    @GET
    @Path("findnome/{nome}")
    @Produces(MediaType.TEXT_PLAIN)
    fun findEntityTestByNome(@PathParam("nome") nome: String): String =
        entityTestRepository.findByNome(nome).toString()

    fun save() {
        val entities = listOf(
            EntityTest("1", "Teste1"),
            EntityTest("2", "Teste1"),
            EntityTest("3", "Teste3"),
            EntityTest("4", "Teste7"),
        )
        entities.onEach {
            safeCallTransaction(onError = ::handleError) {
                entityTestRepository.save(it)
            }
/*
            safeCallTransaction(
                onError = { e -> handleError(e) },
                block = { entityTestRepository.save(it) }
            )
*/
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    fun save(entityTest: EntityTest) {
        entityTestRepository.save(entityTest)
    }

    @GET
    @Path("update/{id}/{nome}")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    fun update(
        @PathParam("id") id: String,
        @PathParam("nome") nome: String,
    ): String {
        safeCallTransaction(onError = ::handleError) {
            val entity = EntityTest(id, nome)
            entityTestRepository.save(entity)
        }
        return "OK"
    }

    fun handleError(exception: Exception) {
        Logger.getLogger("QUARKUS").info("EROU!")
    }
}
