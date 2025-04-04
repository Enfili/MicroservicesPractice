package telekom.com.fidelityservice.service;

import telekom.com.fidelityservice.model.User;

import java.util.Optional;

public interface FidelityService {

    Optional<User> save(User user);

    Optional<User> findById(int id);

    double calculateDiscount(double spentMoney, double productPrice);

    Optional<User> updateUser(User user, double productPrice);
}
