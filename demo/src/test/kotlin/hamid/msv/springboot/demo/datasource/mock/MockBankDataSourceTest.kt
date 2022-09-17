package hamid.msv.springboot.demo.datasource.mock

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest{

    private val mockDataSource = MockBankDataSource()
    
    @Test
    fun `should provide a collection of banks`(){
        //given
        //in this case we do not need given block
        
        //when
        val banks = mockDataSource.retrieveBanks()
        
        //then
        assertThat(banks).isNotEmpty
        assertThat(banks.size).isGreaterThanOrEqualTo(1)
    }

    @Test
    fun `should provide some mock data`(){
        //given
        //in this case we do not need given block

        //when
        val banks = mockDataSource.retrieveBanks()

        //then
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).anyMatch { it.transactionFee != 0 }
    }
}