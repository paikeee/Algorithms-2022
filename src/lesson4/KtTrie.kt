package lesson4

import java.lang.IllegalStateException
import java.lang.StringBuilder
import java.util.*

/**
 * Префиксное дерево для строк
 */
class KtTrie : AbstractMutableSet<String>(), MutableSet<String> {

    private class Node {
        val children: SortedMap<Char, Node> = sortedMapOf()
    }

    private val root = Node()

    private var deleteNode = root
    private var deleteChar = 0.toChar()

    override var size: Int = 0
        private set

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()

    private fun findNode(element: String): Node? {
        var current = root
        deleteChar = element[0]

        fun check(char: Char) {
            if (current.children.keys.size > 1) {
                deleteNode = current
                deleteChar = char
            }
        }

        for (char in element) {
            check(char)
            current = current.children[char] ?: return null
        }

        check(0.toChar())
        return current
    }

    override fun contains(element: String): Boolean =
        findNode(element.withZero()) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        findNode(element) ?: return false
        if (deleteNode.children.remove(deleteChar) != null) {
            size--
            return true
        }
        return false
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    override fun iterator(): MutableIterator<String> = KtTrieIterator()

    inner class KtTrieIterator internal constructor() : MutableIterator<String> {

        private var foundWords = 0
        private var word = StringBuilder()
        private var current = root
        private var charSetForNode = mutableMapOf<Node, Set<Char>>()
        private var lengthNode = mutableMapOf<Node, Int>()
        private var stack = Stack<Node>()
        private var last = ""

        // Время: O(N)
        // Память: O(N)
        override fun next(): String {

            if (!hasNext()) throw NoSuchElementException()

            // Функция добавления буквы к слову
            fun wordAppend(char: Char) {
                word.append(char)
                charSetForNode[current] = charSetForNode[current]!! - char
            }

            // Поиск слова
            while (!current.children.containsKey(0.toChar()) && current.children.keys.size != 0) {
                if (!charSetForNode.containsKey(current)) {
                    charSetForNode[current] = current.children.keys
                }
                if (current.children.keys.size > 1) {
                    stack.add(current)
                }
                val char = charSetForNode[current]!!.first()
                wordAppend(char)
                lengthNode[current] = word.length - 1
                current = current.children[char]!!
            }

            val result = word.toString()
            last = result
            foundWords++

            // Переход на следующую ветку
            if (current.children.keys.size > 1) {
                if (!charSetForNode.containsKey(current)) {
                    charSetForNode[current] = current.children.keys - 0.toChar()
                }
                val char = charSetForNode[current]!!.first()
                wordAppend(char)
                if (charSetForNode[current]!!.isNotEmpty()) {
                    stack.add(current)
                }
                lengthNode[current] = word.length - 1
                current = current.children[char]!!
            } else if (stack.isNotEmpty()) { // Если отсутствует ветка-сосед, переходим к родителю
                current = stack.lastElement()
                word.delete(lengthNode[current]!!, word.length)
                val char = charSetForNode[current]!!.first()
                wordAppend(char)
                if (charSetForNode[current]!!.isEmpty()) {
                    stack.removeLast()
                }
                current = current.children[char]!!
            }

            return result
        }

        // Время: O(1)
        // Память: O(1)
        override fun hasNext(): Boolean = foundWords < size

        // Время: O(N)
        // Память: O(1)
        override fun remove() {
            if (last == "") throw IllegalStateException()
            if (remove(last)) foundWords--
            last = ""
        }
    }

}