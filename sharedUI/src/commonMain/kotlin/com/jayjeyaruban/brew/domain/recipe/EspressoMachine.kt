package com.jayjeyaruban.brew.domain.recipe

import com.jayjeyaruban.brew.domain.Id
import io.availe.Replicate
import io.availe.models.DtoVariant
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Replicate.Model(variants = [DtoVariant.DATA, DtoVariant.CREATE])
private interface EspressoMachine {
    @Replicate.Property(exclude = [DtoVariant.CREATE])
    val id: EspressoMachineId
    val name: String
}

@Serializable
@JvmInline
value class EspressoMachineId(override val id: Long): Id<Long>
