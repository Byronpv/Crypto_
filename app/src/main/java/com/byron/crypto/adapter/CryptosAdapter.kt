package com.byron.crypto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.byron.crypto.R
import com.byron.crypto.model.Crypto
import java.util.ArrayList


class CryptosAdapter (val cryptosAdapterListener: CryptosAdapterListener) : RecyclerView.Adapter<CryptosAdapter.ViewHolderCrypto> () {

    var cryptoList: List<Crypto> =ArrayList ()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCrypto {
       val view =LayoutInflater.from(parent.context).inflate(R.layout.crypto_row,parent,false)
        return ViewHolderCrypto(view)
    }

    override fun getItemCount() = cryptoList.size

    override fun onBindViewHolder(holder: ViewHolderCrypto, position: Int) {
        val crypo = cryptoList[position]

    }

    //Mapeamos todos los componentes que están en la vista de crypto_row
    class ViewHolderCrypto (view: View): RecyclerView.ViewHolder(view) {

        val imageCrypto = view.findViewById<ImageView>(R.id.imageCrypto)
        val tvNamecrypto = view.findViewById<TextView>(R.id.tvNameCrypto)
        val tvAvailable = view.findViewById<TextView>(R.id.tvAvailable)
        val btnBuy = view.findViewById<TextView>(R.id.btnBuy)

    }


}