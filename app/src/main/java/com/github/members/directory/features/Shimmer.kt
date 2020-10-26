package com.github.members.directory.features

import com.github.members.directory.data.mapper.MembersMapper

data class ShimmerUser(
    val id: Int,
    val name: String)

class Shimmer {
    fun getShimmerList(): List<ShimmerUser> {
        val shimmerList: MutableList<ShimmerUser> = arrayListOf()
        val sh2 = ShimmerUser(2, "Wick")
        val sh3 = ShimmerUser(3, "Martha")
        val sh4 = ShimmerUser(4, "Carlos")
        val sh5 = ShimmerUser(5, "Joseph")
        val sh6 = ShimmerUser(6, "Jame")
        val sh7 = ShimmerUser(7, "Jake")
        shimmerList.add(sh2)
        shimmerList.add(sh3)
        shimmerList.add(sh4)
        shimmerList.add(sh5)
        shimmerList.add(sh6)
        shimmerList.add(sh7)
        return shimmerList
    }

    companion object {
        private var INSTANCE: Shimmer? = null

        fun getInstance(): Shimmer =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Shimmer().also { INSTANCE = it }
            }
    }
}