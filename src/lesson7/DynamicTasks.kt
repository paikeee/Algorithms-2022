@file:Suppress("UNUSED_PARAMETER")

package lesson7

import kotlin.math.*

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
// https://ao.ms/find-the-longest-common-subsequence-in-java/
// Время: O(N * M)
// Память: O(N * M)
fun longestCommonSubSequence(first: String, second: String): String {
    val array = Array(first.length + 1) { IntArray(second.length + 1) }
    for (i in first.length - 1 downTo 0) {
        for (j in second.length - 1 downTo 0) {
            array[i][j] =
                if (first[i] == second[j]) array[i + 1][j + 1] + 1
                else max(array[i + 1][j], array[i][j + 1])
        }
    }
    val result = StringBuilder()
    var i = 0
    var j = 0
    while (i < first.length && j < second.length) {
        when {
            first[i] == second[j] -> {
                result.append(first[i])
                i++
                j++
            }
            array[i + 1][j] >= array[i][j + 1] -> i++
            else -> j++
        }
    }
    return result.toString()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
// https://stackoverflow.com/questions/54885750/first-longest-increasing-subsequence
// Время: в худшем случае O(N^2)
// Память: O(N)
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val array = Array(list.size) { IntArray(2) }
    var maxLength = 0
    for (i in list.indices) {
        array[i][0] = -1
        array[i][1] = 1
        for (j in i - 1 downTo 0) {
            if (list[i] > list[j] && array[i][1] <= array[j][1] + 1) {
                array[i][1] = array[j][1] + 1
                array[i][0] = j
            }
        }
        maxLength = max(maxLength, array[i][1])
    }
    val result = mutableListOf<Int>()
    for (i in list.indices) {
        if (array[i][1] == maxLength) {
            var current = i
            while (current != -1) {
                result.add(list[current])
                current = array[current][0]
            }
            break
        }
    }
    result.reverse()
    return result
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}
