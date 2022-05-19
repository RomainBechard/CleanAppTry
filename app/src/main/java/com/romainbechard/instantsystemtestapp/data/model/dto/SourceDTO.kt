package com.romainbechard.instantsystemtestapp.data.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SourceDTO(
    @JsonProperty("id") val id: String?,
    @JsonProperty("name") val name: String?
)