package com.elhady.movies.domain.mappers

interface Mapper<I, O> : BaseMapper<I, O> {
    override fun map(input: I): O
}