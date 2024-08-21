package ru.netology

fun calculateCommission(
    cardType: String = "Мир",
    monthlyTransfers: Int = 0,
    transferAmount: Int
): Int {
    // Лимиты
    val dailyLimit = 150_000
    val monthlyLimit = 600_000
    val masterCardMonthlyLimit = 75_000

    // Проверка лимитов
    if (transferAmount > dailyLimit) {
        throw IllegalArgumentException("Сумма перевода превышает дневной лимит")
    }
    if (monthlyTransfers + transferAmount > monthlyLimit) {
        throw IllegalArgumentException("Сумма перевода превышает месячный лимит")
    }

    return when (cardType) {
        "Mastercard" -> {
            if (monthlyTransfers >= masterCardMonthlyLimit) {
                val commission = (transferAmount * 0.006).toInt() + 20
                commission
            } else if (monthlyTransfers + transferAmount > masterCardMonthlyLimit) {
                // Комиссия только на сумму, превышающую лимит
                val taxableAmount = (monthlyTransfers + transferAmount - masterCardMonthlyLimit).toInt()
                val commission = (taxableAmount * 0.006).toInt() + 20
                commission
            } else {
                0
            }
        }

        "Visa" -> {
            val commission = (transferAmount * 0.0075).toInt()
            if (commission < 35) 35 else commission
        }

        "Мир" -> 0
        else -> throw IllegalArgumentException("Неизвестный тип карты")
    }
}

// Пример использования:
fun main() {
    val cardType = "Mastercard"
    val previousTransfers = 75_000
    val transferAmount = 150_000

    try {
        val commission = calculateCommission(cardType, previousTransfers, transferAmount)
        println("Комиссия составит: $commission руб.")
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }
}
