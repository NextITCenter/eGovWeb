package egovframework.example.login.web;

import egovframework.example.exception.MemberNotFoundException;
import egovframework.example.login.service.LoginRequest;
import egovframework.example.login.service.LoginService;
import egovframework.example.login.service.impl.LoginServiceImpl;
import egovframework.example.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService service;

    @GetMapping("/login")
    public String loginView(String location, Model model) {
        model.addAttribute("location", location);
        return "common/login";
    }

    @PostMapping("/login")
    public String login(LoginRequest login, HttpSession session, Model model) {
        Member member = service.findMember(login);
        if (member == null) {
            throw new MemberNotFoundException();
        }
        session.setAttribute("member", member);
        return "redirect:/";
//        if (member != null) {
//            session.setAttribute("member", member);
//            return "redirect:/";
//        }
//        model.addAttribute("msg", "로그인 실패");
//        return "common/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션의 모든 데이터 지우기
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/ajaxLogin")
    @ResponseBody
    public Map<String, String> ajaxLogin(LoginRequest login, @RequestParam(required = false) String location, HttpSession session) {
        // 성공할 경우 {"msg": "success"}
        // 실패할 경우 {"msg": "failure"}
        log.debug("login: {}", login);
        HashMap<String, String> map = new HashMap<>();
        Member member = service.findMember(login);
        if (member == null) {
            throw new MemberNotFoundException();
        }
        session.setAttribute("member", member);
        if (location != null && !"".equals(location)) {
            map.put("msg", location);
        } else {
            map.put("msg", "/");
        }
        return map;
    }
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseBody
    public Map<String, String> memberNotFound(Model model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("msg", "failure");
        return map;
    }

}
