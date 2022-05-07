package com.example.recipes.data.datasources.cloud.mappers

interface Mapper<FromEntity, ToEntity> {

    fun mapEntity(fromEntity: FromEntity): ToEntity

}