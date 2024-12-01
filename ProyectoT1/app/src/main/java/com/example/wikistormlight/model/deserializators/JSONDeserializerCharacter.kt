package com.example.wikistormlight.model.deserializators

import com.example.wikistormlight.model.dataclasses.Character
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class JSONDeserializerCharacter: JsonDeserializer<Character> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext?
    ): Character {
        val jsonobject = json.asJsonObject
        val character = Character(
            name = jsonobject.get("characterName").asString,
            etnithity = jsonobject.getAsJsonObject("characterInfo").get("Ethnicity").asString,
            description = jsonobject.get("characterDescription").asString
        )
        return character
    }

}