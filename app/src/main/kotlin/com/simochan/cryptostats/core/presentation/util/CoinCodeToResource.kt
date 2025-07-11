package com.simochan.cryptostats.core.presentation.util

import com.simochan.cryptostats.R

fun getDrawableIdForCoin(symbol: String): Int {
    return when (symbol.uppercase()) {
        "BTC" -> R.drawable.bitcoin__btc_
        "ETH" -> R.drawable.ethereum__eth_
        "USDT" -> R.drawable.tether__usdt_
        "XRP" -> R.drawable.xrp__xrp_
        "BNB" -> R.drawable.binance_coin__bnb_
        "SOL" -> R.drawable.solana__sol_
        "USDC" -> R.drawable.usd_coin__usdc_
        "DOGE" -> R.drawable.dogecoin__doge_
        "ADA" -> R.drawable.cardano__ada_
        "TRX" -> R.drawable.tron__trx_
        "STETH" -> R.drawable.steth_steth
        "WBTC" -> R.drawable.wrapped_bitcoin__wbtc_
        "TON" -> R.drawable.toncoin
        "LINK" -> R.drawable.chainlink__link_
        "LEO" -> R.drawable.unus_sed_leo__leo_
        "XLM" -> R.drawable.stellar__xlm_
        "AVAX" -> R.drawable.avalanche__avax_
        "SUI" -> R.drawable.sui
        "SHIB" -> R.drawable.shiba_inu__shib_
        "HBAR" -> R.drawable.hedera_hashgraph__hbar_
        "LTC" -> R.drawable.litecoin__ltc_
        "DOT" -> R.drawable.polkadot__dot_
        "WETH" -> R.drawable.weth
        "BCH" -> R.drawable.bitcoin_cash__bch_
        "OM" -> R.drawable.mantra_om_logo
        "DAI" -> R.drawable.dai__dai_
        "USDE" -> R.drawable.ethena_usde_usde_logo
        "HYPE" -> R.drawable.hypercash_hc_logo
        "XMR" -> R.drawable.monero__xmr_
        "UNI" -> R.drawable.uniswap__uni_
        "APT" -> R.drawable.aptos_apt_logo
        "NEAR" -> R.drawable.near_protocol__near_
        "PEPE" -> R.drawable.pepe_pepe_logo
        "OKB" -> R.drawable.okb__okb_
        "CRO" -> R.drawable.cronos_cro_logo
        "MNT" -> R.drawable.mantle_mnt_logo
        "ICP" -> R.drawable.internet_computer__icp_
        "ONDO" -> R.drawable.ondo_finance_ondo_logo
        "AAVE" -> R.drawable.aave__aave_
        "ETC" -> R.drawable.ethereum_classic__etc_
        "FDUSD" -> R.drawable.first_digital_usd_fdusd_logo
        "POL" -> R.drawable.polygon__matic_
        "TAO" -> R.drawable.bittensor_tao_logo
        "VET" -> R.drawable.vechain__vet_
        "ENA" -> R.drawable.ethena_ena_logo
        "TIA" -> R.drawable.celestia_tia_logo
        "GT" -> R.drawable.gate_token_logo
        "RENDER" -> R.drawable.render_token_logo
        "FIL" -> R.drawable.filecoin__fil_
        "KAS" -> R.drawable.kaspa_kas_logo
        "ATOM" -> R.drawable.cosmos__atom_
        "ALGO" -> R.drawable.algorand__algo_
        "ARB" -> R.drawable.arbitrum_arb_logo
        "KCS" -> R.drawable.kucoin_token__kcs_
        "JUP" -> R.drawable.jupiter_ag_jup_logo
        "OP" -> R.drawable.optimism_ethereum_op_logo
        "FET" -> R.drawable.fet_logo
        "MKR" -> R.drawable.maker__mkr_
        "XDC" -> R.drawable.xinfin_network__xdc_
        "WLD" -> R.drawable.worldcoin_org_wld_logo
        "IMX" -> R.drawable.immutable_x_imx_logo
        "STX" -> R.drawable.stacks__stx_
        "BONK" -> R.drawable.bonk1_bonk_logo
        "SEI" -> R.drawable.sei_sei_logo
        "GRT" -> R.drawable.the_graph__grt_
        "INJ" -> R.drawable.injective_inj_logo
        "EOS" -> R.drawable.eos__eos_
        "THETA" -> R.drawable.theta__theta_
        "QNT" -> R.drawable.quant__qnt_
        "FLR" -> R.drawable.flare_flr_logo
        "LDO" -> R.drawable.lido_dao_ldo_logo
        "PYUSD" -> R.drawable.paypal_usd_pyusd_logo
        "XAUT" -> R.drawable.tether_gold_xaut_logo
        "GALA" -> R.drawable.gala_gala_logo
        "NEXO" -> R.drawable.nexo__nexo_
        "XTZ" -> R.drawable.tezos__xtz_
        "SAND" -> R.drawable.the_sandbox__sand_
        "IOTA" -> R.drawable.iota__miota_
        "CRV" -> R.drawable.curve_dao_token__crv_
        "BTTOLD" -> R.drawable.bittorrent__btt_
        "BSV" -> R.drawable.bitcoin_sv__bsv_
        "PAXG" -> R.drawable.paxos_standard__pax_
        "KAIA" -> R.drawable.kaia_kaia_logo
        "CAKE" -> R.drawable.pancakeswap__cake_
        "FLOW" -> R.drawable.flow__flow_
        "FLOKI" -> R.drawable.floki_inu_floki_logo
        "ENS" -> R.drawable.ethereum_name_service_ens_logo
        "ZEC" -> R.drawable.zcash__zec_
        else -> R.drawable.question_mark_svgrepo_com
    }
}
