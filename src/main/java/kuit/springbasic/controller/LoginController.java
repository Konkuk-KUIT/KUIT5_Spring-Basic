package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.auth.JwtInfo;
import kuit.springbasic.auth.JwtTokenProvider;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    /**
     * TODO: showLoginForm
     */
    @RequestMapping("/user/loginForm")
    public String showLoginFrom(){
        return "user/login/jwt";
    }

    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/user/loginFailed")
    public String showLoginFailed(){
        return "user/loginFailed";
    }
    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @RequestMapping(value = "/user/login/jwt", method = RequestMethod.POST)
    public String login(@RequestParam("userId")String userId, @RequestParam("password")String password, HttpServletRequest request){
        User findUser = userService.findById(userId);
        User loginUser = new User(userId,password);

        if(findUser != null && loginUser.isSameUser(findUser)){
            JwtInfo jwtInfo = jwtTokenProvider.createToken(findUser.getEmail(),findUser.getUserId());
            response.addHeader("Set-Cookie", "accessToken=" + jwtInfo.accessToken() + "; Path=/; HttpOnly");
            response.addHeader("Set-Cookie", "refreshToken=" + jwtInfo.refreshToken() + "; Path=/; HttpOnly");
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }

}
