package com.trailblazers.freewheelers.service.impl;

import com.trailblazers.freewheelers.mappers.AccountMapper;
import com.trailblazers.freewheelers.mappers.AccountRoleMapper;
import com.trailblazers.freewheelers.mappers.MyBatisUtil;
import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.AccountRole;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.CountryService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";

    private final AccountRoleMapper accountRoleMapper;
    private SqlSession sqlSession;
    private AccountMapper accountMapper;

    public AccountServiceImpl() {
        this(MyBatisUtil.getSqlSessionFactory().openSession());
    }

    public AccountServiceImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        this.accountMapper = sqlSession.getMapper(AccountMapper.class);
        this.accountRoleMapper = sqlSession.getMapper(AccountRoleMapper.class);
    }

    @Override
    public List<Account> findAll() {
        return accountMapper.getAllAccounts();
    }

    @Override
    public Account getAccountIdByName(String userName) {
        return accountMapper.getByName(userName);
    }

    @Override
    public Account get(Long account_id) {
        return accountMapper.getById(account_id);
    }

    @Override
    public void delete(Account account) {
        accountMapper.delete(account);
        sqlSession.commit();
    }

    @Override
    public void createAdmin(Account account) {
        create(account, ADMIN);
    }

    @Override
    public Account createAccount(Account account) {
        if (accountMapper.getEmailCount(account.getEmail_address()) == 0) {
            create(account, USER);
            return account;
        }
        return null;
    }

    @Override
    public String getRole(String emailId) {
        return accountRoleMapper.getByAccountEmail(emailId).getRole();
    }

    @Override
    public Account getAccountFromEmail(String email) {
        return accountMapper.getFromEmail(email);
    }

    private void create(Account account, String role) {
        CountryService countryService=new CountryServiceImpl();
        for (String country: countryService.getCountries()) {
            if (account.getCountry().equals(country)){
                accountMapper.insert(account);
                accountRoleMapper.insert(roleFor(account, role));
                sqlSession.commit();
                return;
            }
        }
    }

    private AccountRole roleFor(Account account, String role) {
        return new AccountRole()
                .setAccount_name(account.getAccount_name())
                .setEmail_address(account.getEmail_address())
                .setRole(role);
    }
}
