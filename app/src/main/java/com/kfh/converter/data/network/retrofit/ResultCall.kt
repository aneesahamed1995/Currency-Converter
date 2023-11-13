package com.kfh.converter.data.network.retrofit
import com.kfh.converter.domain.entity.Result

interface ResultCall<R : Any> {
     fun get():Result<R>
     suspend fun execute():Result<R>
     suspend fun await():Result<R>
}