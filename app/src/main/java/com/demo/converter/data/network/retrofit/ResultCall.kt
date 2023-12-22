package com.demo.converter.data.network.retrofit
import com.demo.converter.domain.entity.Result

interface ResultCall<R : Any> {
     fun get():Result<R>
     suspend fun execute():Result<R>
     suspend fun await():Result<R>
}