package com.example.securecard.controllers
import com.example.securecard.DTO.LoginDTO
import com.example.securecard.DTO.Message
import com.example.securecard.exceptions.NotFoundException
import com.example.securecard.model.Person
import com.example.securecard.services.PersonService
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/persons")
class PersonController(private val personService: PersonService) {

    @PostMapping
    fun createPerson(@RequestBody person: Person): ResponseEntity<Any> {
        return if (this.personService.findByEmail(person.email) == null) {
            personService.save(person)
            ResponseEntity.ok().body(Message("Created!"))
        } else {
            ResponseEntity.badRequest().body("Email in use!")
        }
    }

    @PostMapping("/login")
    fun loginPerson(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<Any> {
        val user = this.personService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(Message("User not found!"))

        if (!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body(Message("Invalid password!"))
        }

        val jwt = Jwts.builder()
            .claim("name", user.name)
            .claim("email", user.email)
            .claim("admin", user.admin.toString())
            .compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)
        return ResponseEntity.ok(jwt)
    }

    @GetMapping("/admin")
    fun adminHome(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        if(jwt == null) {
            return ResponseEntity.status(401).body(Message("Unauthenticated!"))
        }

        return when (Jwts.parserBuilder().build().parseClaimsJwt(jwt).body["admin"] == "true") {
            true -> {return ResponseEntity.ok().body(personService.findAll())}
            else -> {return ResponseEntity.badRequest().body(Message("You're not an admin!"))}
        }
    }

    @PutMapping("/{id}")
    fun updatePerson(@CookieValue("jwt") jwt: String?, @PathVariable id: Long, @RequestBody updatedPerson: Person): Person {

        if(jwt == null) {
            throw Exception("Unauthenticated!")
        }

        val person = personService.findById(id) ?: throw NotFoundException("Person not found")
        return personService.save(updatedPerson.copy(id = person.id))
    }

    @DeleteMapping("/{id}")
    fun deletePerson(@CookieValue("jwt") jwt: String?, @PathVariable id: Long) {

        if(jwt == null) {
            throw Exception("Unauthenticated!")
        }

        val person = personService.findById(id) ?: throw NotFoundException("Person not found")

        return personService.deleteById(person) ?: throw Exception("Error deleting person")
    }
}
