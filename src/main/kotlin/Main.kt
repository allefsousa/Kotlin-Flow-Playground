import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.lang.Exception

fun main() {

    runBlocking {
        doSomething().collect{value -> println("Valor $value")}
        getFlow().collect{ value -> println("GetFlow = $value")}
        valuesFlow().collect{ value -> println("Values $value")}
        transformFlow().collect{ value -> println("Transform $value")}
        flowZip().collect{ value -> println("Flow Zip $value")}
        flowCombine().collect{ value -> println("Flow Combine $value")}
        flowReturn().collect{ value -> println("Flow Return $value")}
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

fun flowReturn(): Flow<Int> = flow {
    for (i in 1..5) {
        delay(100)
        if (i == 4){
            return@flow
        }
        emit(i)
    }
}

/**
 * Flow Values
 */
fun valuesFlow(): Flow<Int> {
    return flow {
        var x = 0
        do {
            delay(1000)
            emit(x)
            x++
        } while (x < 20)
    }
}

/**
 * Flow Zip
 */
fun flowZip() = flow {
    val flowA = (1..3).asFlow()
    val flowB = flowOf("one", "two", "three")
    flowA.zip(flowB) { a, b -> "$a and $b" }
        .collect { emit(it) }
}.flowOn(Dispatchers.IO)

/**
 * Flow Combine
 */
fun flowCombine() = flow {
    val flowA = (1..3).asFlow()
    val flowB = flowOf("single item")
    flowA.combine(flowB) { a, b -> "$a and $b" }
        .collect { emit(it) }
}.flowOn(Dispatchers.IO)


/**
 * Transforming Flows
 */
fun transformFlow(): Flow<String> {
    val numbersList = listOf(1,2,3,4,5,6)
    return numbersList.asFlow()
        .transform {
            number ->
            emit("Numero = $number")
            emit(" Numero * 5 = ${number * 5}")
            delay(100)
            emit("Numero * 10 = ${number * 10}")
        }
}