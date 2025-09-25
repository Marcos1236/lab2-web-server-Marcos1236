package es.unizar.webeng.lab2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ErrorPageTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    /**
     * Test de integración que verifica que un 404 devuelve una página de error personalizada.
     */
    @Test
    fun `should display custom 404 error page`() {
        val headers = org.springframework.http.HttpHeaders()
        headers.set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        val entity = org.springframework.http.HttpEntity<String>(headers)
        
        val response = restTemplate.exchange(
            "http://localhost:$port/ruta-que-no-existe",
            org.springframework.http.HttpMethod.GET,
            entity,
            String::class.java
        )
        
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(response.headers.contentType?.toString()).contains("text/html")
        assertThat(response.body).contains("404")
        assertThat(response.body).contains("Not Found")
    }
}