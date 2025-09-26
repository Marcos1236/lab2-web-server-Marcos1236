package es.unizar.webeng.lab2

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.BDDMockito.given
import org.springframework.http.MediaType
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class TimeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var timeProvider: TimeProvider

    @Test
    fun `GET time returns fixed time JSON`() {
        val fixed = LocalDateTime.of(2025, 9, 25, 18, 32, 45, 123_000_000) 

        given(timeProvider.now()).willReturn(fixed)

        mockMvc.perform(get("/time").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.time").value(fixed.toString()))
    }
}
