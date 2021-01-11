package eu.brickpics.staffsys

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import eu.brickpics.staffsys.commands.CMDInspect
import eu.brickpics.staffsys.commands.CMDRegister
import eu.brickpics.staffsys.commands.CMDStaffSys
import eu.brickpics.staffsys.inventories.InputSystem
import eu.brickpics.staffsys.punishment.PunishmentType
import eu.brickpics.staffsys.punishment.Punishments
import eu.brickpics.staffsys.util.ConverterUtil
import me.schlaubi.kaesk.api.CommandClientBuilder
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class StaffSys : JavaPlugin() {

    lateinit var dataSource: HikariDataSource
    lateinit var protocolManager: ProtocolManager
    lateinit var inputRequest: InputSystem

//    val punishmentManager = PunishmentManager()



    override fun onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager()
    }

    override fun onEnable() {
        inputRequest = InputSystem(protocolManager, this)

        val commandClient = CommandClientBuilder(this)
                .addDeserializer(PunishmentType::class.java, ConverterUtil.punishmentTypeDeserializer())
                .setArgumentHandler { exception, sender ->  sender.sendMessage("Please enter valid ${exception
                        .parameterType.simpleName}")}
                .setNoPermissionHandler { sender, permission -> sender.sendMessage("You need the $permission permission to proceed") }
                .build()
        commandClient.registerCommand(CMDRegister())
        getCommand("staff").executor = CMDStaffSys(this)
        getCommand("inspect").executor = CMDInspect(this)
        saveDefaultConfig()
        connectMySQL()
    }

    override fun onDisable() {
        dataSource.close()
    }

    private fun connectMySQL() {
        val config = HikariConfig().apply {
            driverClassName = "eu.brickpics.staffsys.dependencies.com.mysql.cj.jdbc.Driver"
            jdbcUrl = "jdbc:mysql://localhost:3306/minecraft?useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin"
            username = config!!.getString("user")
            password = config!!.getString("password")

        }
        dataSource = HikariDataSource(config)
        Database.connect(dataSource)
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Punishments)
        }
    }
}
