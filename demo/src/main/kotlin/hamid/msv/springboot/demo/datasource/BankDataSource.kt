package hamid.msv.springboot.demo.datasource

import hamid.msv.springboot.demo.model.Bank

interface BankDataSource {
    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(accountNumber:String) : Bank
    fun createBank(newBank: Bank): Bank
}