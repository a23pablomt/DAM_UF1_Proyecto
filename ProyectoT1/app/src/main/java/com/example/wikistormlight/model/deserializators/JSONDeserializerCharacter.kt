package com.example.wikistormlight.model.deserializators

import com.example.wikistormlight.model.dataclasses.Character
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class JSONDeserializerCharacter : JsonDeserializer<Character> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext?
    ): Character {
        val jsonObject = json.asJsonObject

        // Verifica si "characterInfo" es un JsonObject o un JsonArray
        val characterInfoElement = jsonObject.get("characterInfo")
        val characterInfo = when {
            characterInfoElement.isJsonObject -> characterInfoElement.asJsonObject
            characterInfoElement.isJsonArray -> {
                // Si es un JsonArray, obtenemos el primer elemento (suponiendo que siempre hay al menos uno)
                characterInfoElement.asJsonArray.firstOrNull()?.asJsonObject
            }
            else -> null
        }

        val character = Character(
            name = jsonObject.get("characterName").asString,
            etnithity = characterInfo?.get("Ethnicity")?.asString ?: "Unknown",
            nationality = characterInfo?.get("Nationality")?.asString ?: "Unknown",
            gender = characterInfo?.get("Gender")?.asString ?: "Unknown",
            img = if (jsonObject.has("characterImage") && !jsonObject.get("characterImage").isJsonNull) {
                "has"
            } else {
                "null"
            },
            description = jsonObject.get("characterDescription")?.asString ?: "Unknown"
        )

        return character
    }
}