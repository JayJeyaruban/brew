package com.jayjeyaruban.brew.domain.recipe

import com.jayjeyaruban.brew.data.database.models.RecentRecipes
import com.jayjeyaruban.brew.domain.ExistingOrCreate
import com.jayjeyaruban.brew.domain.Id
import com.jayjeyaruban.brew.domain.Mass
import com.jayjeyaruban.brew.domain.bean.BeanId
import com.jayjeyaruban.brew.domain.bean.BeanSchema
import io.availe.Replicate
import io.availe.models.DtoVariant
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline


@Replicate.Model(variants = [DtoVariant.DATA, DtoVariant.CREATE])
private interface Recipe {
    @Replicate.Property(exclude = [DtoVariant.CREATE])
    val id: RecipeId

    @Replicate.Property(exclude = [DtoVariant.CREATE])
    val bean: BeanSchema.Data
    @Replicate.Property(include = [DtoVariant.CREATE])
    val existingOrCreateBean: ExistingOrCreate<BeanId, BeanSchema.CreateRequest>

    @Replicate.Property(exclude = [DtoVariant.CREATE])
    val grinder: GrinderSchema.Data?
    @Replicate.Property(include = [DtoVariant.CREATE])
    val existingOrCreateGrinder: ExistingOrCreate<GrinderId, GrinderSchema.CreateRequest>?

    @Replicate.Property(exclude = [DtoVariant.CREATE])
    val espressoMachine: EspressoMachineSchema.Data
    @Replicate.Property(include = [DtoVariant.CREATE])
    val existingOrCreateEspressoMachine: ExistingOrCreate<EspressoMachineId, EspressoMachineSchema.CreateRequest>

    val dose: Mass
    val targetOutput: Mass?
}

@Serializable
@JvmInline
value class RecipeId(override val id: Long): Id<Long>

fun RecipeSchema.Data.Companion.fromPersistence(recipe: RecentRecipes) = RecipeSchema.Data(
        RecipeId(recipe.id),
        BeanSchema.Data(
            BeanId(recipe.beanId),
            recipe.beanName,
        ),
    recipe.grinderId?.let {
        GrinderSchema.Data(
            GrinderId(it),
            recipe.grinderName!!
        )
    },
    EspressoMachineSchema.Data(
        EspressoMachineId(recipe.espressoMachineId),
        recipe.espressoMachineName,
    ),
        Mass(recipe.dose),
        recipe.targetOutput?.let { Mass(it) }
    )
