package com.tristanjuricek.asciilab.api.model

/**
 * Mostly a DTO to avoid a top level array reference that seems to add stupidity
 * by most JSON processors.
 */
data class Sources(val sources: List<Source>)