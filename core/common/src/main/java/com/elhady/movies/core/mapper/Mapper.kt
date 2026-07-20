package com.elhady.movies.core.mapper

interface Mapper<INPUT, OUTPUT> {
    fun map(input: INPUT): OUTPUT
    fun map(input: List<INPUT>): List<OUTPUT>{
        return input.map(::map)
    }
}