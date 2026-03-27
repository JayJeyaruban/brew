package com.jayjeyaruban.brew.domain.bean

import com.jayjeyaruban.brew.domain.Id
import io.availe.Replicate
import io.availe.models.DtoVariant
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Replicate.Model(variants = [DtoVariant.DATA, DtoVariant.CREATE])
private interface Bean {
    @Replicate.Property(exclude = [DtoVariant.CREATE])
    val id: BeanId
    val name: String
}

@Serializable
@JvmInline
value class BeanId(override val id: Long): Id<Long>
