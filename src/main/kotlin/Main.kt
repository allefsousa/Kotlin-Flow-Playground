import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

fun main() {

    runBlocking {
        doSomething().collect{value -> println("Valor $value")}
        getFlow().collect{ value -> println("GetFlow = $value")}
        valuesFlow().collect{ value -> println("Values $value")}
    }

}

fun getFlow() = flow { emit(1) }
    .flowOn(Dispatchers.IO)

fun doSomething(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}
fun valuesFlow(): Flow<Int> {
    return flow {
        var x = 0
        do {
            delay(1000)
            emit(x)
            x++
        } while (x < 1000)
    }
}