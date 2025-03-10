package com.emro.dictionary;

import com.emro.dictionary.users.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PageController {

	private final CustomUserDetailsService userDetailsService;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // 로그인 페이지 렌더링
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
        }
        return "home";

    }

    @GetMapping("/glo/lists")
    public String gloHome(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
        }
        return "gloList";
    }

    @GetMapping("/lang/lists")
    public String langHome(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
        }
        return "langList";
    }

    @GetMapping("/user/lists")
    public String userHome(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
        }
        return "userList";
    }

	@GetMapping("/registration")
	public String registration(Model model, Authentication authentication) {
		if (authentication != null) {
			model.addAttribute("username", authentication.getName());
		}
		return "registration_modal";
	}

	@GetMapping("/ssoLogin")
	public String ssoLogin(Model model, HttpServletRequest request) {
		UserDetails user = userDetailsService.loadUserByUsername("sys_admin");
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		if (authentication != null) {
			model.addAttribute("username", authentication.getName());
		}

		request.getSession().setAttribute(
				HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext()
		);

		return "redirect:/lang/lists";
	}


	@GetMapping("/req/lists")
	public String reqLists(
			@RequestParam(value = "acptSts", required = false) String acptSts,
			Model model,
			Authentication authentication) {

		if (authentication != null) {
			model.addAttribute("username", authentication.getName());
		}
		// acptSts 값을 모델에 추가 (없으면 null)
		model.addAttribute("acptSts", acptSts);

		return "requestList"; // 동일한 HTML 파일 사용
	}

	@GetMapping("/registrationVertical")
	public String registrationVertical(Model model, Authentication authentication) {
		if (authentication != null) {
			model.addAttribute("username", authentication.getName());
		}
		return "registration_modal_vertical";
	}

}
