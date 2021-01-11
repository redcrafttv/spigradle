package eu.brickpics.staffsys.punishment

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime
import java.util.*

//data class Punishment(val punished: UUID, val reason: String, val beginningTime: LocalDateTime, val endTime:
//        LocalDateTime, val punishmentType: PunishmentType, val staffUUID: UUID) {
//
//
//}

object Punishments : IntIdTable("staffsys_cases") {
    val punished = uuid("punished")
    val reason = text("reason")
    val beginningTime = datetime("beginning").clientDefault { LocalDateTime.now() }
    val endTime = datetime("end")
    val punishmentType = enumeration("punishementtype", PunishmentType::class)
    val staffUUID = uuid("staffuuid")
}

class Punishment(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Punishment>(Punishments) {
        fun findByPlayer(uuid: UUID) = find { Punishments.punished eq uuid }.toList()
        fun findByStaff(uuid: UUID) = find { Punishments.staffUUID eq uuid }.toList()
        fun findLast(hours: Long) = find {
            Punishments.beginningTime greaterEq LocalDateTime.now()
                    .minusHours(hours)
        }.toList()
    }

    var punished by Punishments.punished
    var reason by Punishments.reason
    var beginningTime by Punishments.beginningTime
    var endTime by Punishments.endTime
    var punishmentType by Punishments.punishmentType
    var staffUUID by Punishments.staffUUID

}

enum class PunishmentType {
    BAN, MUTE, KICK
}
