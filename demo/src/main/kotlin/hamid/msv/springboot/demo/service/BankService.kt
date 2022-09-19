package hamid.msv.springboot.demo.service

import hamid.msv.springboot.demo.datasource.BankDataSource
import hamid.msv.springboot.demo.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService(@Qualifier("mock") private val dataSource: BankDataSource){
    fun getBanks() : Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)
    fun addBank(newBank: Bank): Bank = dataSource.createBank(newBank)
    fun updateBank(updatedBank: Bank): Bank = dataSource.updateBank(updatedBank)
    fun deleteBank(accountNumber: String) = dataSource.deleteBank(accountNumber)
}