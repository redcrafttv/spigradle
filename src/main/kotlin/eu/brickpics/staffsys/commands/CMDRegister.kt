package eu.brickpics.staffsys.commands

import eu.brickpics.staffsys.punishment.Punishment
import eu.brickpics.staffsys.punishment.PunishmentType
import eu.brickpics.staffsys.util.DateUtil
import me.schlaubi.kaesk.api.Command
import me.schlaubi.kaesk.api.CommandClass
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.transactions.transaction

@CommandClass(name = "register", permission = "staff.test.register")
class CMDRegister {

    @Command(root = true)
    fun usage(player: Player) = player.sendMessage("Usage: /blablabla")

    @Command(root = true)
    fun rootCommand(player: Player, type: PunishmentType, target: OfflinePlayer, length: String, vararg reasonString:
                    String) {
        val reason = reasonString.joinToString(" ")
        val end = DateUtil.parseDate(length) ?: return player.sendMessage("Invalid date time thing lol!")
        val punishment = transaction {
            Punishment.new {
                punished = target.uniqueId
                this.reason = reason
                endTime = end
                punishmentType = type
                staffUUID = player.uniqueId
            }
        }

        player.sendMessage("Punishment erstellt: $punishment")
    }

}