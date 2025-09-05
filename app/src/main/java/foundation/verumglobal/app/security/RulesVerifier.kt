package foundation.verumglobal.app.security

import android.content.Context
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.security.KeyFactory
import java.security.PublicKey
import java.security.Signature
import java.security.spec.X509EncodedKeySpec
import java.security.MessageDigest
import java.util.zip.ZipInputStream

data class SealedRules(val version: Int, val json: ByteArray)

object RulesVerifier {
    private const val MIN_ALLOWED_VERSION = 1

    private fun ByteArray.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(this).joinToString("") { "%02x".format(it) }
    }

    private fun unzip(zipBytes: ByteArray, name: String): ByteArray {
        ZipInputStream(zipBytes.inputStream()).use { zis ->
            var e = zis.nextEntry
            val out = ByteArrayOutputStream()
            val buf = ByteArray(8192)
            while (e != null) {
                if (!e.isDirectory && e.name == name) {
                    var n: Int
                    while (zis.read(buf).also { n = it } > 0) out.write(buf, 0, n)
                    return out.toByteArray()
                }
                e = zis.nextEntry
            }
        }
        throw SecurityException("rules.vop missing: $name")
    }

    private fun loadAsset(context: Context, name: String) =
        context.assets.open(name).use { it.readBytes() }

    private fun loadPublicKey(context: Context): PublicKey {
        val pem = context.resources.openRawResource(
            foundation.verumglobal.app.R.raw.public_key
        ).bufferedReader().use { it.readText() }
        val cleaned = pem.replace("-----BEGIN PUBLIC KEY-----","")
            .replace("-----END PUBLIC KEY-----","")
            .replace("\\s".toRegex(), "")
        val bytes = Base64.decode(cleaned, Base64.DEFAULT)
        val spec = X509EncodedKeySpec(bytes)
        return KeyFactory.getInstance("RSA").generatePublic(spec)
    }

    fun verifyAndLoad(context: Context): SealedRules {
        val vop = loadAsset(context, "rules.vop")
        val json = unzip(vop, "rules.json")
        val sig  = unzip(vop, "rules.sig")
        val sha  = unzip(vop, "rules.sha256").toString(Charsets.UTF_8).trim()

        val actual = json.sha256()
        if (!actual.equals(sha, true)) throw SecurityException("Rules hash mismatch")

        val pub = loadPublicKey(context)
        val s = Signature.getInstance("SHA256withRSA")
        s.initVerify(pub); s.update(json)
        if (!s.verify(sig)) throw SecurityException("Rules signature invalid")

        val m = Regex("\"version\"\\s*:\\s*(\\d+)").find(String(json))
            ?: throw SecurityException("Rules version missing")
        val ver = m.groupValues[1].toInt()
        if (ver < MIN_ALLOWED_VERSION) throw SecurityException("Rules rollback not allowed")

        return SealedRules(ver, json)
    }
}
