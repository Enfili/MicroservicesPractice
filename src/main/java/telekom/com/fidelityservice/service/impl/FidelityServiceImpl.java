package telekom.com.fidelityservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telekom.com.fidelityservice.constants.Constants;
import telekom.com.fidelityservice.database.FidelityDatabase;
import telekom.com.fidelityservice.model.User;
import telekom.com.fidelityservice.service.FidelityService;

import java.util.Optional;

@Service
public class FidelityServiceImpl implements FidelityService {

    @Autowired
    private FidelityDatabase fidelityDatabase;

    public Optional<User> save(User user) {
        return Optional.of(fidelityDatabase.save(user));
    }

    public Optional<User> findById(int id) {
        return fidelityDatabase.findById(id);
    }

    public double calculateDiscount(double spentMoney, double productPrice) {
        if (spentMoney > Constants.DISCOUNT_BOUNDARY) {
            return productPrice * 0.9;
        }

        return productPrice;
    }

    public Optional<User> updateUser(User user, double productPrice) {
        double productPriceAfterDiscount = calculateDiscount(user.getMoneySpent(), productPrice);
        user.setMoneySpent(user.getMoneySpent() + productPriceAfterDiscount);
        Optional<User> updatedUser = save(user);

        if (updatedUser.isPresent()) {
            return updatedUser;
        } else
            return Optional.empty();
    }
}
