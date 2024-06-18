package com.ra.project.repository;

import com.ra.project.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> getAddressByUserId(Long userId);
    List<Address> getAddressById(Long addressId);
}
