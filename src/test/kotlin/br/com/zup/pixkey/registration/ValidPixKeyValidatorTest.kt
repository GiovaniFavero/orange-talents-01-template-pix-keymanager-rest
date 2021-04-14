package br.com.zup.pixkey.registration

import br.com.zup.AccountType
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

@MicronautTest
internal class ValidPixKeyValidatorTest(private val validator: ValidPixKeyValidator) {

    @ParameterizedTest
    @MethodSource("keyTypeArguments")
    fun `Must validate the Pix Key Type`(argument: ValidPixKeyArgument) {
        val pixKey = NewPixKeyRequestDto(
            keyType = argument.pixKeyType,
            key = argument.key,
            accountType = AccountType.CURRENT_ACCOUNT
        )
        val result = validator.isValid(pixKey, null, null)
        assertTrue(result == argument.valid, argument.message)

    }

    companion object {
        @JvmStatic
        fun keyTypeArguments(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(ValidPixKeyArgument(PixKeyType.CPF, "07048283084", "Should validate CPF", true)),
                Arguments.of(ValidPixKeyArgument(PixKeyType.CPF, "8222283901", "Should validate CPF", false)),
                Arguments.of(ValidPixKeyArgument(PixKeyType.EMAIL, "email@email.com", "Should validate Email", true)),
                Arguments.of(ValidPixKeyArgument(PixKeyType.EMAIL, "emailemail.com", "Should validate Email", false)),
                Arguments.of(ValidPixKeyArgument(PixKeyType.PHONE, "+5547991341695", "Should validate Phone Number", true)),
                Arguments.of(ValidPixKeyArgument(PixKeyType.PHONE, "1234", "Should validate Phone Number", false)),
                Arguments.of(ValidPixKeyArgument(PixKeyType.RANDOM, "", "Should validate random key", true)),
                Arguments.of(ValidPixKeyArgument(PixKeyType.RANDOM, "123", "Should validate random key", false))
            )
        }
    }

    data class ValidPixKeyArgument (val pixKeyType: PixKeyType, val key: String, val message: String, val valid: Boolean)

}