package foundation.verumglobal.app

import android.app.Application
import foundation.verumglobal.app.security.RulesVerifier

class VerumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RulesVerifier.verifyAndLoad(this) // throws if tampered/rolled back
    }
}
