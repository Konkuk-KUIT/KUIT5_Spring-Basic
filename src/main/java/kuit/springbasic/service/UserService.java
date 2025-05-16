package kuit.springbasic.service;

import java.util.List;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByIdAndPassword(String userId, String password) {
        User findUser = findById(userId);

        // 비밀번호 해시 값 비교
        if (findUser != null && passwordEncoder.matches(password, findUser.getPassword())) {
            return findUser;
        }
        return null;
    }

    public User findByIdAndPassword(User loginUser) {
        User findUser = findById(loginUser.getUserId());

        // 비밀번호 해시 값 비교
        if (findUser != null && passwordEncoder.matches(loginUser.getPassword(), findUser.getPassword())) {
            return findUser;
        }
        return null;
    }

    public User findById(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void save(User user) {
        // 비밀번호 암호화
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
