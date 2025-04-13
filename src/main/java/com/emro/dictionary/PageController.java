package com.emro.dictionary;

import com.emro.dictionary.security.SecurityUtil;
import com.emro.dictionary.users.service.CustomUserDetailsService;
import com.emro.dictionary.utils.JwtUtil;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PageController {

	private final CustomUserDetailsService userDetailsService;
	private final SecurityUtil securityUtil;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // 로그인 페이지 렌더링
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", securityUtil.getUsername());
        }
        return "home";

    }

    @GetMapping("/glo/lists")
    public String gloHome(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", securityUtil.getUsername());
        }
        return "gloList";
    }

    @GetMapping("/multlang/lists")
    public String langHome(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", securityUtil.getUsername());
        }
        return "multlangList";
    }

    @GetMapping("/user/lists")
    public String userHome(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", securityUtil.getUsername());
        }
        return "userList";
    }

	@GetMapping("/registration")
	public String registration(Model model, Authentication authentication) {
		if (authentication != null) {
			model.addAttribute("username", securityUtil.getUsername());
		}
		return "registration_modal";
	}

	@GetMapping("/ssoLogin")
	public String ssoLogin(Model model, HttpServletRequest request, @RequestParam("token") String token) {
		UserDetails user = userDetailsService.loadUserByUsername(new JwtUtil().extractUsername(token));
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		if (authentication != null) {
			model.addAttribute("username", securityUtil.getUsername());
		}

		request.getSession().setAttribute(
				HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext()
		);

		return "redirect:/multlang/lists";
	}


	@GetMapping("/req/{acptSts}/lists")
	public String requestLists(
			@PathVariable(value = "acptSts") String acptSts,
			Model model,
			Authentication authentication) {

		if (authentication != null) {
			model.addAttribute("username", securityUtil.getUsername());
		}
		// acptSts 값을 모델에 추가 (없으면 null)
		model.addAttribute("acptSts", acptSts);

		return "requestList"; // 동일한 HTML 파일 사용
	}

	@GetMapping("/registrationVertical")
	public String registrationVertical(Model model, Authentication authentication) {
		if (authentication != null) {
			model.addAttribute("username", securityUtil.getUsername());
		}
		return "registration_modal_vertical";
	}

	@GetMapping("/req/{acptSts}/detail/{reqId}")
	public String requestDetail(
			@PathVariable(value = "reqId") String reqId,
			@PathVariable(value = "acptSts") String acptSts,
	                            Model model, Authentication authentication) {
		if (authentication != null) {
			model.addAttribute("username", securityUtil.getUsername());
		}

		model.addAttribute("reqId", reqId);
		model.addAttribute("acptSts", acptSts);
		return "requestDetail";
	}

	@GetMapping("/req/{acptSts}/detail/{reqId}/history/{dtlId}")
	public String requestDetailHistory(@PathVariable(value = "reqId") String reqId,
	                                   @PathVariable(value = "dtlId") Long dtlId,
	                                   @PathVariable(value = "acptSts") String acptSts,
	                                   Model model, Authentication authentication) {
		if (authentication != null) {
			model.addAttribute("username", securityUtil.getUsername());
		}
		model.addAttribute("dtlId", dtlId);
		model.addAttribute("reqId", reqId);
		model.addAttribute("acptSts", acptSts);
		return "requestDetailHistory";
	}

	@GetMapping("/multlang/history/{multlangKey}")
	public String multlangHistory(
			@PathVariable(value = "multlangKey") String multlangKey,
			Model model, Authentication authentication) {

		if (authentication != null) {
			model.addAttribute("username", securityUtil.getUsername());
		}
		model.addAttribute("prevMenu", ("all".equalsIgnoreCase(multlangKey) ? "history" : "multilang"));
		model.addAttribute("multlangKey", multlangKey);

		return "multlangHistory";
	}

}
