package br.com.zup.shared.validators

import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.ReportAsSingleViolation
import javax.validation.constraints.Pattern
import kotlin.reflect.KClass

@ReportAsSingleViolation
@Constraint(validatedBy = [])
@Pattern(regexp = "^[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}$",
    flags = [Pattern.Flag.CASE_INSENSITIVE], message = "It must be a valid UUID format!")
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER
)
annotation class ValidUUID (
    val message: String = "It must be a valid UUID format!",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)