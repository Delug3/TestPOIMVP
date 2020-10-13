package com.delug3.testpoi.model

class Poi {
    var id: String
    var title: String? = null
    var address: String? = null
    var transport: String? = null
    var email: String? = null
    var geocoordinates: String? = null
    var description: String? = null


    constructor(id: String, title: String?, address: String?, transport: String?, email: String?, geocoordinates: String?, description: String?) {
        this.id = id
        this.title = title
        this.address = address
        this.transport = transport
        this.email = email
        this.geocoordinates = geocoordinates
        this.description = description
    }
}