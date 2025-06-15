package plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import model.PortfolioTargets
import model.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("ktor.database.driverClassName").getString()
        val jdbcURL = config.property("ktor.database.jdbcURL").getString()
        val user = config.property("ktor.database.user").getString()
        val password = config.property("ktor.database.password").getString()
        val poolSize = config.property("ktor.database.poolSize").getString().toInt()

        // HikariCP로 커넥션 풀 생성
        val hikari = createHikariDataSource(
            url = jdbcURL,
            user = user,
            password = password,
            poolSize = poolSize,
            driver = driverClassName
        )

        // Exposed와 커넥션 풀 연결
        Database.connect(hikari)
        
        transaction {
            SchemaUtils.create(Users)
            SchemaUtils.create(PortfolioTargets) // ✅ 이 줄을 추가
        }
    }

    private fun createHikariDataSource(
        url: String,
        user: String,
        password: String,
        poolSize: Int,
        driver: String
    ): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = driver
            jdbcUrl = url
            username = user
            maximumPoolSize = poolSize
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        config.password = password

        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: suspend Transaction.() -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}