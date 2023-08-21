package com.bashirli.mymovie.common.diffUtil

import androidx.recyclerview.widget.DiffUtil

fun <T>diffList(oldList: List<T>, newList: List<T>, sameItem: (a: T, b: T) -> Boolean): DiffUtil.DiffResult {
    val callback: DiffUtil.Callback = object : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return sameItem(oldList[oldItemPosition], newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    return DiffUtil.calculateDiff(callback) }