package com.andrewshawcare.smooks_api

import org.milyn.edi.unedifact.d01b.D01BInterchangeFactory
import org.milyn.edi.unedifact.d96a.D96AInterchangeFactory
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchangeFactory

class UNEdifactInterchangeFactoryStrategy {
    private val unEdifactVersionNumberAndReleaseNumberToUNEdifactInterchangeFactory = mapOf(
        UNEdifactVersionNumberAndReleaseNumber.D01B to D01BInterchangeFactory.getInstance(),
        UNEdifactVersionNumberAndReleaseNumber.D96A to D96AInterchangeFactory.getInstance()
    )

    fun getInstance(unEdifactVersionNumberAndReleaseNumber: UNEdifactVersionNumberAndReleaseNumber): UNEdifactInterchangeFactory {
        return unEdifactVersionNumberAndReleaseNumberToUNEdifactInterchangeFactory[unEdifactVersionNumberAndReleaseNumber]!!
    }
}