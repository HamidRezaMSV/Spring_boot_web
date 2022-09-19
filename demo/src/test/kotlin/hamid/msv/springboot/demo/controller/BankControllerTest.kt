package hamid.msv.springboot.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import hamid.msv.springboot.demo.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper : ObjectMapper

    private val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            //when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1234") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return the bank with the given account number`() {
            //given
            val accountNumber = "1234"

            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value(1.0) }
                }
        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            //given
            val accountNumber = "does_not_exist"

            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add new bank`() {
            //given
            val newBank = Bank("8585", 85.85, 85)

            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }

            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }
        }

        @Test
        fun `should return BAD REQUEST if bank with given account number already exists`(){
            //given
            val invalidBank = Bank("1234", 8.5, 85)

            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            
            //then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        @Test
        fun `should update existing bank`(){
            //given
            val updatedBank = Bank("1234" , 97.97 , 97)
            
            //when
            val performPatchRequest = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
            
            //then
            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }
        }
        
        @Test
        fun `should return NOT FOUND if no bank with given account number exists`(){
            //given
            val invalidBank = Bank("does_not_exist" , 1.0 , 1)
            
            //when
            val performPatchRequest = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            
            //then
            performPatchRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}