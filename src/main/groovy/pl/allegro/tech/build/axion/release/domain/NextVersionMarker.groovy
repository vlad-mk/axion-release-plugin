package pl.allegro.tech.build.axion.release.domain

import org.gradle.api.logging.Logger
import pl.allegro.tech.build.axion.release.domain.scm.ScmService
import pl.allegro.tech.build.axion.release.domain.scm.ScmPosition

class NextVersionMarker {

    private final ScmService repository

    private final Logger logger

    NextVersionMarker(ScmService repository, Logger logger) {
        this.repository = repository
        this.logger = logger
    }

    void markNextVersion(VersionConfig versionConfig, String nextVersion) {
        VersionWithPosition positionedVersion = versionConfig.getRawVersion()
		ScmPosition position = positionedVersion.position

        String tagName = versionConfig.tag.serialize(versionConfig.tag, nextVersion, position)
        String nextVersionTag = versionConfig.nextVersion.serializer(versionConfig.nextVersion, tagName, position)

        logger.quiet("Creating next version marker tag: $nextVersionTag")
        repository.tag(nextVersionTag)
        repository.push()
    }
}
