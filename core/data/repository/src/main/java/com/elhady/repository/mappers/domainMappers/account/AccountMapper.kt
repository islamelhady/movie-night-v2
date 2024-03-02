package com.elhady.movies.domain.mappers.account

import com.elhady.movies.data.remote.response.account.AccountDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Account
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class AccountMapper @Inject constructor() : Mapper<AccountDto, Account> {
    override fun map(input: AccountDto): Account {
        return Account(
            avatarPath = Constants.IMAGE_PATH + input.avatar?.avatarPath?.avatarPath,
            name = input.name ?: "",
            username = input.username ?: ""
        )
    }
}