/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.filters;

import com.JavaWebProject.JavaWebProject.controllers.AuthController;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author DELL
 */
public class CustomerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) rq;
        HttpServletResponse response = (HttpServletResponse) rs;
        HttpSession session = request.getSession();
        AuthController authController = (AuthController) session.getAttribute("scopedTarget.authController");
        if (authController == null || authController.getRole() == null || !authController.getRole().equals("Customer")) {
            response.sendRedirect("/");
        } 
        else {
            fc.doFilter(request, response);
        }
    }
}
