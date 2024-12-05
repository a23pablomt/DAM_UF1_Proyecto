package com.example.wikistormlight.model.deserializators

import com.example.wikistormlight.model.dataclasses.Character
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.wikistormlight.model.dataclasses.Characters

class CharacterListCreator {

    fun createCharacterList(json: String): List<String> {
        val gson = Gson()
        val characterListType = object : TypeToken<List<Characters>>() {}.type
        val characters: List<Characters> = gson.fromJson(json, characterListType)


        return characters.map { it.characterName }
    }
}