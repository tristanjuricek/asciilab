package com.tristanjuricek.asciilab.api.model

/**
 * The source of a project contributes to a publication.
 *
 * Exact workflow is TBD, but we're going to simply assign a nice name to a Git URL for now.
 *
 * @param name A "nice" human-readable ID. Generally should be unique by the account.
 * @param url The Git URL of our source
 */
data class Source(val id: Int = -1, val name: String, val url: String)
