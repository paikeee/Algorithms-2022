@file:Suppress("UNUSED_PARAMETER")

package lesson2

import kotlin.math.sqrt

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    TODO()
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 *
 * Общий комментарий: решение из Википедии для этой задачи принимается,
 * но приветствуется попытка решить её самостоятельно.
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    TODO()
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
// Время: O(N*M)
// Память: O(N*M)
fun longestCommonSubstring(first: String, second: String): String {
    if (first == "" || second == "")
        return ""
    val matrix = Array(first.length) { Array(second.length) { 0 } }
    var maxValue = 0
    var maxIndex = 0
    first.forEachIndexed { i, firstChar ->
        second.forEachIndexed { j, secondChar ->
            if (firstChar == secondChar) {
                if (i > 0 && j > 0)
                    matrix[i][j] = 1 + matrix[i - 1][j - 1]
                else matrix[i][j]++

                if (matrix[i][j] > maxValue) {
                    maxValue = matrix[i][j]
                    maxIndex = i
                }
            }
        }
    }
    return if (maxValue != 0)
        first.substring(maxIndex - maxValue + 1, maxIndex + 1)
    else ""
}

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
// Реализовано решето Аткина
// Время О(N/ln(lnN))
// Память O(N^(1/2+o(1)))
fun calcPrimesNumber(limit: Int): Int {
    if (limit <= 1) return 0
    val primes = MutableList(limit + 1) { false }
    val sqrtLimit = sqrt(limit.toDouble()).toInt()
    for (x in 1..sqrtLimit) {
        for (y in 1..sqrtLimit) {
            val x2 = x * x
            val y2 = y * y
            var num = 4 * x2 + y2
            if (num <= limit && (num % 12 == 1 || num % 12 == 5))
                primes[num] = !primes[num]
            num -= x2
            if (num <= limit && num % 12 == 7)
                primes[num] = !primes[num]
            num -= 2 * y2
            if (x > y && num <= limit && num % 12 == 11)
                primes[num] = !primes[num]
        }
    }
    for (i in 5..sqrtLimit)
        if (primes[i]) {
            val i2 = i * i
            for (j in i2..limit step i2)
                primes[j] = false
        }
    return if (limit > 2) primes.count { it } + 2 // учитывая числа 2 и 3
    else primes.count { it } + 1 // учитывая число 2
}