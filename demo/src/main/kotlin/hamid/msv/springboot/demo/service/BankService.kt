package hamid.msv.springboot.demo.service

import hamid.msv.springboot.demo.datasource.BankDataSource
import hamid.msv.springboot.demo.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource){

    fun getBanks() : Collection<Bank> = dataSource.retrieveBanks()
}