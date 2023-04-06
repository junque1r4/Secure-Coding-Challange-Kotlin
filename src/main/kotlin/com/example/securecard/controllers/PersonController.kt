package com.example.securecard.controllers
import com.example.securecard.exceptions.NotFoundException
import com.example.securecard.model.Person
import com.example.securecard.services.PersonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/persons")
class PersonController(private val personService: PersonService) {

    @GetMapping
    fun getAllPersons(): Iterable<Person> = personService.findAll()

    @GetMapping("/{id}")
    fun getPersonById(@PathVariable id: Long): Person? = personService.findById(id)

    @PostMapping
    fun createPerson(@RequestBody person: Person): Person = personService.save(person)

    @PutMapping("/{id}")
    fun updatePerson(@PathVariable id: Long, @RequestBody updatedPerson: Person): Person {
        val person = personService.findById(id) ?: throw NotFoundException("Person not found")
        return personService.save(updatedPerson.copy(id = person.id))
    }

    @DeleteMapping("/{id}")
    fun deletePerson(@PathVariable id: Long) = personService.deleteById(id)
}
