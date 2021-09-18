package sillock.icelandicdiscordbot

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.forAll
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import sillock.icelandicdiscordbot.services.SetupService

@SpringBootTest
class IcelandicDiscordBotApplicationTests {

    @Autowired
    private lateinit var setupService: SetupService

    @Test
    fun contextLoads() {
        assertThat(setupService).isNotNull
    }
}