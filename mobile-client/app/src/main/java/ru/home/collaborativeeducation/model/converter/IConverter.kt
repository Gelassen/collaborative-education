package ru.home.collaborativeeducation.model.converter

interface IConverter<I, O> {
    fun convert(input: I): O
    fun convert(input: List<I>): List<O>
}