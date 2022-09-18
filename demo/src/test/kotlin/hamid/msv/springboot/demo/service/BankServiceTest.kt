package hamid.msv.springboot.demo.service

import hamid.msv.springboot.demo.datasource.BankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class BankServiceTest{

    private val dataSource : BankDataSource = mockk()
    private val bankService = BankService(dataSource = dataSource)
    
    @Test
    fun `should call its data source to retrieve banks`(){
        //given
        every { dataSource.retrieveBanks() } returns emptyList()
        
        //when
        bankService.getBanks()
        
        //then
        verify(exactly = 1) { dataSource.retrieveBanks() }
    }
}