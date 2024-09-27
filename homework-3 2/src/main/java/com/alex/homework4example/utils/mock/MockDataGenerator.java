package com.alex.homework4example.utils.mock;

import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Address;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.entity.User;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@UtilityClass
public class MockDataGenerator {
    private static final Random RANDOM = new Random();

    public static Account generateTestAccount(Customer customer) {
        Account account = new Account();

        String accountNumber = "ACC" + RANDOM.nextInt(100000);
        account.setAccountNumber(accountNumber.substring(0, Math.min(accountNumber.length(), 20)));

        String accountType = "Type" + RANDOM.nextInt(100);
        account.setAccountType(accountType.substring(0, Math.min(accountType.length(), 20)));

        BigDecimal balance = BigDecimal.valueOf(1000);
        account.setBalance(balance);

        String currency = "USD";
        account.setCurrency(currency.substring(0, Math.min(currency.length(), 3)));

        String iban = "IBAN" + RANDOM.nextInt(100000000);
        account.setIban(iban.substring(0, Math.min(iban.length(), 28)));

        LocalDateTime createdAt = LocalDateTime.now();
        account.setCreatedAt(createdAt);

        account.setCustomer(customer);

        return account;
    }

    public static Address generateTestAddress() {
        Address address = new Address();

        String street = "Street " + RANDOM.nextInt(1000);
        address.setStreet(street.substring(0, Math.min(street.length(), 100)));

        String city = "City " + RANDOM.nextInt(100);
        address.setCity(city.substring(0, Math.min(city.length(), 50)));

        String postalCode = "Postal" + RANDOM.nextInt(10000);
        address.setPostalCode(postalCode.substring(0, Math.min(postalCode.length(), 20)));

        return address;
    }

    public static Customer generateTestCustomer(Address address, User user) {
        Customer customer = new Customer();

        String firstName = "First" + RANDOM.nextInt(1000);
        customer.setFirstName(firstName.substring(0, Math.min(firstName.length(), 50)));

        String lastName = "Last" + RANDOM.nextInt(1000);
        customer.setLastName(lastName.substring(0, Math.min(lastName.length(), 50)));

        String email = "user" + RANDOM.nextInt(1000) + "@example.com";
        customer.setEmail(email.substring(0, Math.min(email.length(), 100)));

        String phone = String.valueOf(RANDOM.nextInt(1000000));
        customer.setPhone(phone);

        customer.setAddress(address);
        customer.setUser(user);

        return customer;
    }

    public static Role generateTestRole() {
        Role role = new Role();
        String roleName = "Role " + RANDOM.nextInt(100000);
        role.setName(roleName.substring(0, Math.min(roleName.length(), 50)));
        return role;
    }

    public static User generateTestUser(Role role) {
        User user = new User();

        String username = "user" + RANDOM.nextInt(1000);
        user.setUsername(username.substring(0, Math.min(username.length(), 50)));

        String password = "pass" + RANDOM.nextInt(10000);
        user.setPassword(password.substring(0, Math.min(password.length(), 100)));

        user.setRole(role);

        return user;
    }
}
