package com.example.securecard.model

import jakarta.persistence.*

@Entity
@Table(name = "person")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long? = 0,

    @Column(name = "name")
    var name: String = "",

    @Column(name = "email")
    var email: String = "",

    @Column(name = "password")
    var password: String = "",

    @Column(name = "admin")
    var admin: Boolean = false
) {
    fun comparePassword(password: String): Boolean {
        return this.password == password
    }
}
