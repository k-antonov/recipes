package com.example.recipes.data

interface Mapper<DataEntity, DomainEntity> {

    fun mapToDomain(cloudEntity: DataEntity): DomainEntity

}