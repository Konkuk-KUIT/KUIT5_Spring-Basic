package kuit.springbasic.service;

import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    //IOC 제어의 역전 : questionRepository가 언제 쓰이는지, 어디서 온지 모름
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByIdAndPassword(String userId, String password){
        User findUser = findById(userId);

        if (findUser != null && passwordEncoder.matches(password, findUser.getPassword())){
            return findUser;
        }

        return null;
    }
    public User findById(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.insert(user);
    }

    public List<User> findAll() {
        return userRepository.findAll().stream().toList();
    }

    public boolean isSame(User user, User findUser) {
        if (findUser != null && user != null) {
            return findUser.equals(user);
        }
        return false;
    }

    public void update(User modifiedUser) {
        modifiedUser.setPassword(passwordEncoder.encode(modifiedUser.getPassword()));
        userRepository.update(modifiedUser);
    }


}
