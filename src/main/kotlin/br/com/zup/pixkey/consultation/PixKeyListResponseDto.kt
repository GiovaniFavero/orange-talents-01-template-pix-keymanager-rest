package br.com.zup.pixkey.consultation

import br.com.zup.PixKeyListResponse

class PixKeyListResponseDto (response: PixKeyListResponse) {

    val customerId = response.customerId

    val keys = response.keysList.map {
        mapOf(
            Pair("pixId", it.pixId),
            Pair("keyType", it.type.name),
            Pair("key", it.key),
            Pair("accountType", it.accountType.name)
        )
    }

}