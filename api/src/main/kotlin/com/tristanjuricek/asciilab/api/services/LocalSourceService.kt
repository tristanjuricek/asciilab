package com.tristanjuricek.asciilab.api.services

import com.tristanjuricek.asciilab.api.model.LocalSource
import com.tristanjuricek.asciilab.api.model.Source
import com.tristanjuricek.asciilab.api.repository.LocalSourceRepository
import kotlinx.coroutines.experimental.async
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.FileAttribute

/**
 * High level routines for fetching local copies of sources.
 *
 * <p> NOTE: This concept might evolve significantly.
 */
interface LocalSourceService {

    /**
     * Assuming this source is new, will trigger a copy.
     */
    fun createLocalSource(source: Source)
}

class LocalSourceServiceImpl: LocalSourceService {

    private val localSourceRepository: LocalSourceRepository
    private val workingDir: String

    constructor(localSourceRepository: LocalSourceRepository, workingDir: String) {
        this.localSourceRepository = localSourceRepository
        this.workingDir = workingDir
    }

    override fun createLocalSource(source: Source) {
        async {
            val localSource = LocalSource(source = source, localURL = getURLForSource(source))
            localSourceRepository.save(localSource)
            setupLocalSource(localSource)
        }
    }


    fun setupLocalSource(localSource: LocalSource) {
        async {
            val localPath = Paths.get(localSource.localURL)
            if (!Files.exists(localPath)) {
                Files.createDirectories(localPath)
            }

            Git.cloneRepository()
                    .setURI(localSource.source.url)
                    .setDirectory(localPath.toFile())
                    .call()

            TODO("OK, let's do some shit")
        }
    }

    fun getURLForSource(source: Source): String {
        // TODO: We really should sanitize this
        return "$workingDir/${source.id}"
    }
}