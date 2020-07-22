package com.byron.crypto.adapter

import com.byron.crypto.model.Crypto

interface CryptosAdapterListener {

    fun onBuyCryptoClicked (cripto: Crypto)
}