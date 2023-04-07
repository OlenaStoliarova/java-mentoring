package pl.mentoring.currencyexchange.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.mentoring.currencyexchange.exception.EntityAlreadyExistsException;
import pl.mentoring.currencyexchange.exception.NoEntityFoundException;
import pl.mentoring.currencyexchange.model.Account;
import pl.mentoring.currencyexchange.model.AccountHolder;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.LongFunction;

@Service
public class AccountHolderService {

    private static final Logger logger = LoggerFactory.getLogger(AccountHolderService.class);

    private static final LongFunction<String> accountHolderFileName = accountHolderId -> String.format("%s.json", accountHolderId);

    private final ObjectMapper objectMapper;

    private final Map<Long, ReadWriteLock> accountLockMap;

    public AccountHolderService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.accountLockMap = new ConcurrentHashMap<>();
        Iterator<File> fileIterator = FileUtils.iterateFiles(new File(""), new RegexFileFilter("^[0-9]*.json$"), null);
        while (fileIterator.hasNext()) {
            Long accountId = Long.valueOf(FilenameUtils.getBaseName(fileIterator.next().getName()));
            this.accountLockMap.put(accountId, new ReentrantReadWriteLock());
        }
    }

    public AccountHolder acquireAccountHolder(long accountHolderId) {
        getAccountHolderLock(accountHolderId).writeLock().lock();
        logger.debug("Thread {}. AccountHolder's {} writeLock locked", Thread.currentThread().getId(), accountHolderId);

        return readAccountHolderFromFile(accountHolderId);
    }

    public void saveLockedAccountHolder(AccountHolder accountHolder) throws IOException {
        long accountHolderId = accountHolder.getId();
        logger.debug("Thread {}. Saving AccountHolder {} to file", Thread.currentThread().getId(), accountHolderId);

        File accountHolderFile = getAccountHolderFile(accountHolderFileName.apply(accountHolderId));
        objectMapper.writeValue(accountHolderFile, accountHolder);
    }

    public void releaseAccountHolder(long accountHolderId) {
        getAccountHolderLock(accountHolderId).writeLock().unlock();
        logger.debug("Thread {}. AccountHolder's {} writeLock unlocked", Thread.currentThread().getId(), accountHolderId);
    }

    public void addAccountHolder(AccountHolder newAccountHolder) throws IOException {
        long accountHolderId = newAccountHolder.getId();
        this.accountLockMap.putIfAbsent(accountHolderId, new ReentrantReadWriteLock());

        getAccountHolderLock(accountHolderId).writeLock().lock();

        try {
            File accountHolderFile = getAccountHolderFile(accountHolderFileName.apply(accountHolderId));

            if (accountHolderFile.createNewFile()) {
                objectMapper.writeValue(accountHolderFile, newAccountHolder);
            } else {
                throw new EntityAlreadyExistsException("AccountHolder " + accountHolderId + " already exists");
            }
        } finally {
            getAccountHolderLock(accountHolderId).writeLock().unlock();
        }
    }

    public void addAccount(long accountHolderId, Account newAccount) throws IOException {
        logger.info("Thread {}. Adding Account {} to AccountHolder {}",
            Thread.currentThread().getId(), newAccount.getAccountNumber(), accountHolderId);
        try {
            AccountHolder accountHolder = acquireAccountHolder(accountHolderId);

            boolean accountAlreadyExists = accountHolder.getAccounts().containsKey(newAccount.getAccountNumber());

            if (accountAlreadyExists) {
                throw new EntityAlreadyExistsException(
                    "Account " + newAccount.getAccountNumber() + " already exists for AccountHolder " + accountHolderId);
            } else {
                accountHolder.getAccounts().put(newAccount.getAccountNumber(), newAccount);
                saveLockedAccountHolder(accountHolder);
            }
        } finally {
            releaseAccountHolder(accountHolderId);
        }
    }

    public AccountHolder readAccountHolder(long accountHolderId) {
        getAccountHolderLock(accountHolderId).readLock().lock();
        logger.debug("Thread {}. AccountHolder's {} readLock locked", Thread.currentThread().getId(), accountHolderId);

        try {
            return readAccountHolderFromFile(accountHolderId);
        } finally {
            getAccountHolderLock(accountHolderId).readLock().unlock();
            logger.debug("Thread {}. AccountHolder's {} readLock unlocked", Thread.currentThread().getId(), accountHolderId);
        }
    }

    public Account readAccount(long accountHolderId, String accountNumber) {
        AccountHolder accountHolder = readAccountHolder(accountHolderId);

        boolean accountExists = accountHolder.getAccounts().containsKey(accountNumber);

        if (accountExists) {
            return accountHolder.getAccounts().get(accountNumber);
        } else {
            throw new NoEntityFoundException("Account " + accountNumber + " not found for AccountHolder " + accountHolderId);
        }
    }

    private AccountHolder readAccountHolderFromFile(long accountHolderId) {
        File accountHolderFile = getAccountHolderFile(accountHolderFileName.apply(accountHolderId));

        if (accountHolderFile.exists()) {
            try {
                return objectMapper.readValue(accountHolderFile, AccountHolder.class);
            } catch (IOException e) {
                throw new NoEntityFoundException("AccountHolder " + accountHolderId + " not parsable");
            }
        } else {
            throw new NoEntityFoundException("AccountHolder " + accountHolderId + " not found");
        }
    }

    private ReadWriteLock getAccountHolderLock(long accountHolderId) {
        ReadWriteLock lock = this.accountLockMap.get(accountHolderId);

        if (lock == null) {
            throw new NoEntityFoundException("ReadWriteLock for AccountHolder " + accountHolderId + " not found");
        } else {
            return lock;
        }
    }

    private File getAccountHolderFile(String filename) {
        return new File(filename);
    }
}
