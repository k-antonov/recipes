package com.example.recipes.data

interface Mapper<FromEntity, ToEntity> {

    fun mapEntity(fromEntity: FromEntity): ToEntity

}