
import com.JavaWebProject.JavaWebProject.controllers.AuthController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("userRole")
    public String userRole(HttpServletRequest request) {
        HttpSession session = request.getSession();
        AuthController user = (AuthController) session.getAttribute("scopedTarget.authController");
        return user != null ? user.getRole() : null;
    }
}
