package com.example.AmitSbTcsJwtV3Project.repository;

import com.example.AmitSbTcsJwtV3Project.model.CartProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartProductRepo extends JpaRepository<CartProduct, Integer> {
    Optional<CartProduct> findByCartUserUserIdAndProductProductId(Integer userId, Integer productId);

    @Transactional
    void deleteByCartUserUserIdAndProductProductId(Integer userId, Integer productId);
    //No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
    //if not writting transactional here
    //checking the spring documentation it looks like by default the PersistenceContext is of type Transaction,
    // so that's why the method has to be transactional
    //If a method without the @Transactional annotion calls a method with the @Transactional annotation in the same
    // classfile, you also will be struck by this error
    //143
    //
    //I got this exception while attempting to use a deleteBy custom method in the spring data repository.
    // The operation was attempted from a JUnit test class
    //The exception does not occur upon using the @Transactional annotation at the JUnit class level.
    //best is place at repopsitory that is where db call is done finally
    //Actually here using 3 classes.. cart, product, user.. so total 3 Enity, this is shared Entity
    //so while deleteing in such cases, it should be always Transactional
    //if deleting by cart id only then no issue as only one entity involved
}
