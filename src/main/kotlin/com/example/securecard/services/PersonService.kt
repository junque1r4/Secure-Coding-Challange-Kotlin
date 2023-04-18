package com.example.securecard.services

import com.example.securecard.model.Person
import com.example.securecard.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService (private val personRepository: PersonRepository) {

    fun findAll(): MutableIterable<Person> = personRepository.findAll()

    fun findById(id: Long): Person? = personRepository.findById(id).orElse(null)

    fun findByEmail(email: String): Person? = personRepository.findByEmail(email)

    fun save(person: Person): Person = personRepository.save(person)

    fun deleteById(id: Person) = id.id?.let { personRepository.deleteById(it) }
}
