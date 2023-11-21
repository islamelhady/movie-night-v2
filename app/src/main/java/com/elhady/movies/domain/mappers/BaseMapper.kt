package com.elhady.movies.domain.mappers

interface BaseMapper<INPUT, OUTPUT>{
    fun map(input: INPUT): OUTPUT
    fun map(input: List<INPUT>): List<OUTPUT>{
        return input.map(::map)
    }
}