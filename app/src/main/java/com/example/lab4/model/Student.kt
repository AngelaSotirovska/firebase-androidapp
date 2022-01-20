package com.example.lab4.model

class Student{
    var user: String? = null
    var index: String? = null
    var name: String? = null
    var surname: String? = null
    var number: String? = null
    var address: String? = null
    var key: String = ""

    constructor() {}
    constructor(
        user: String?,
        index: String?,
        name: String?,
        surname: String?,
        number: String?,
        address: String?
    ) {
        this.user = user
        this.index = index
        this.name = name
        this.surname = surname
        this.number = number
        this.address = address
    }

    fun insertKey(key: String) {
        this.key=key
    }
    fun takeKey(): String {
        return this.key
    }
}
