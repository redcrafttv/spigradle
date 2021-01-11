package eu.brickpics.staffsys.manager

import org.apache.commons.io.IOUtils
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.JSONValue
import org.json.simple.parser.ParseException

import java.io.IOException
import java.net.URL

object NameManager {
    fun getName(uuid: String): String {
        val url = "https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names"
        try {
            val nameJson = IOUtils.toString(URL(url))
            val nameValue = JSONValue.parseWithException(nameJson) as JSONArray
            val playerSlot = nameValue[nameValue.size - 1].toString()
            val nameObject = JSONValue.parseWithException(playerSlot) as JSONObject
            return nameObject["name"].toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return "error"
    }
}