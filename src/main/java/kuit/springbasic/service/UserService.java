package kuit.springbasic.service;

import java.util.List;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByIdAndPassword(String userId, String password) {
        User findUser = findById(userId);
        User loginUser = new User(userId, password);

        if (findUser != null && loginUser.isSameUser(findUser)) {
            return findUser;
        }
        return null;
    }

    public User findByIdAndPassword(User loginUser) {
        User findUser = findById(loginUser.getUserId());

        if (findUser != null && loginUser.isSameUser(findUser)) {
            return findUser;
        }
        return null;
    }

    public User findById(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void save(User user) {
        userRepository.insert(user);
    }

    public List<User> findAll() {
        return userRepository.findAll().stream().toList();
    }

    public boolean isSame(User user, User findUser) {
        if (findUser != null && user != null) {
            if (findUser.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void update(User modifiedUser) {
        userRepository.update(modifiedUser);
    }
}
