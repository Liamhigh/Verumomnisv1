app/src/main/java/foundation/verumglobal/app/

package foundation.verumglobal.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import foundation.verumglobal.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.hello.text = "Verum Omnis — Hello!"
    }
}