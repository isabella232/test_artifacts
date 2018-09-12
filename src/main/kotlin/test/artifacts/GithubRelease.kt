package test.artifacts

import com.jcabi.github.Coordinates
import com.jcabi.github.Release
import com.jcabi.github.Releases
import com.jcabi.github.RtGithub
import java.io.File

object GithubRelease {

    private val latestRelease: Release.Smart

    init {
        val token = getEnv("GITHUB_TOKEN")
        val repoUrl = "Flank/test_artifacts"
        val releaseTag = "latest"
        val github = RtGithub(token)
        val repo = github.repos().get(Coordinates.Simple(repoUrl))
        val releases = Releases.Smart(repo.releases())
        latestRelease = Release.Smart(releases.find(releaseTag))
    }

    private fun getEnv(key: String): String {
        return System.getenv(key) ?: throw RuntimeException("Environment variable '$key' not found!")
    }

    private fun removeReleaseAssets() {
        latestRelease.assets().iterate().forEach { asset ->
            println("Removing $asset")
            asset.remove()
        }
    }

    private fun uploadRelease(asset: File) {
        val bytes = asset.readBytes()
        val md5 = bytes.md5()
        latestRelease.assets().upload(bytes, "application/zip", "$md5.zip")
        latestRelease.body(md5)
        latestRelease.name("Test Artifacts")
        latestRelease.body("Test artifacts for Flank.")
    }

    private fun inputFile(filePath: String): File {
        val file = File(filePath)
        if (!file.exists()) {
            throw RuntimeException("File '${file.canonicalPath}' does not exist!")
        }
        if (file.extension != "zip") {
            throw RuntimeException("Expected file '${file.canonicalPath}' to be a '.zip' file!")
        }
        return file
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val file = inputFile("archive.zip")
        removeReleaseAssets()
        uploadRelease(file)
    }
}
