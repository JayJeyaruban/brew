package com.jayjeyaruban.brew.domain

import com.jayjeyaruban.brew.data.database.BrewDatabase
import kotlin.jvm.JvmInline

sealed interface ExistingOrCreate<out Id,out Create> {
    @JvmInline
    value class Existing<Id>(val id: Id): ExistingOrCreate<Id, Nothing>

    @JvmInline
    value class Create<Create>(val create: Create): ExistingOrCreate<Nothing, Create>
}
