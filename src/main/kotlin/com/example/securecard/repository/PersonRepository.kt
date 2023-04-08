package com.example.securecard.repository

import com.example.securecard.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<Person, Long>{
    fun findByEmail(email: String): Person?
}

