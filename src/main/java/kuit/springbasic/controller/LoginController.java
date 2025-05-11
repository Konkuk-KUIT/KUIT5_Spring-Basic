package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 1. Controller Annotation
 * 2. 컨트롤러에서 반환할때 파라미터를 넘겨야 하냐? 아니면 뷰(=redirect)만 넘기면 되냐? 에 따라서 리턴 형태가 달라짐
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    /**
     * TODO: showLoginForm
     */
    @RequestMapping("/user/loginForm")
    public String showLoginForm() {
        return "user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/user/loginFailed")
    public String showLoginFailed() {
        return "user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @RequestMapping("/user/login")
    public String login(@RequestParam("userId") String userId,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {
        // RequestParam으로 필드값에 파라미터 바인딩 가능
        User byUserId = userRepository.findByUserId(userId);
        User loginUser = new User(userId, password);

        if (byUserId != null && loginUser.isSameUser(byUserId)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loginUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */

}
