import com.jcabi.github.Coordinates
import com.jcabi.github.Release
import com.jcabi.github.Releases
import com.jcabi.github.RtGithub
import java.io.File
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

object GithubRelease {

    private val release: Release.Smart

    init {
        val token = getEnv("GITHUB_TOKEN")
        val repoUrl = getEnv("GITHUB_REPO")
        val releaseTag = getEnv("GITHUB_RELEASE_TAG")
        val github = RtGithub(token)
        val repo = github.repos().get(Coordinates.Simple(repoUrl))
        val releases = Releases.Smart(repo.releases())
        release = Release.Smart(releases.find(releaseTag))
    }

    private fun getEnv(key: String): String {
        return System.getenv(key) ?: throw RuntimeException("Environment variable '$key' not found!")
    }

    private fun removeReleaseAssets() {
        release.assets().iterate().forEach {
            it.remove()
        }
    }

    private fun uploadRelease(asset: File) {
        val bytes = asset.readBytes()
        val md5 = bytes.md5()
        release.assets().upload(bytes, "application/zip", "$md5.zip")
        release.body(md5)
        release.name("Test Artifacts")
        release.body("Test artifacts for Flank.")
    }

    private fun inputFile(args: Array<String>): File {
        if (args.size != 1) {
            throw RuntimeException("Must provide program argument for path to asset file!")
        }
        val file = File(args.first())
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
        val file = inputFile(args)
        removeReleaseAssets()
        uploadRelease(file)
    }
}

fun ByteArray.md5(): String {
    val md5 = MessageDigest.getInstance("MD5")
    md5.update(this)
    return DatatypeConverter.printHexBinary(md5.digest())
}
