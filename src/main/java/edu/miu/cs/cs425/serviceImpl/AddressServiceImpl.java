package edu.miu.cs.cs425.serviceImpl;

import java.util.List;

import edu.miu.cs.cs425.exception.AddressException;
import edu.miu.cs.cs425.model.Address;
import edu.miu.cs.cs425.model.User;
import edu.miu.cs.cs425.repository.AddressRepository;
import edu.miu.cs.cs425.repository.UserRepository;
import edu.miu.cs.cs425.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;


    @Override
    public Address addAddressToUser(Integer userId, Address address) throws AddressException {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Fouund"));

        Address saveaAddress = addressRepository.save(address);
        saveaAddress.setUser(existingUser);

        existingUser.getAddress().add(saveaAddress);
        userRepository.save(existingUser);
        return saveaAddress;
    }

    @Override
    public Address updateAddress(Address address,Integer addressId) throws AddressException {

        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException("Address not found"));

        existingAddress.setFlatNo(address.getFlatNo());
        existingAddress.setZipCode(address.getZipCode());
        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        // Save the updated address in the repository
        return addressRepository.save(existingAddress);
    }

    @Override
    public void removeAddress(Integer addressId) throws AddressException {
        // Find the existing address by ID
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException("Address not found"));

        // Delete the address from the repository
        addressRepository.delete(existingAddress);
    }

    @Override
    public List<Address> getAllUserAddress(Integer userId) throws AddressException {
        List<Address> userAddressList = addressRepository.getUserAddressList(userId);
        if (userAddressList.isEmpty()) {
            System.out.println("empty");
            throw new AddressException("User does not have any address");
        }
        return userAddressList;
    }


}