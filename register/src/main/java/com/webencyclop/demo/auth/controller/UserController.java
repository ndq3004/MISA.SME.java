package com.webencyclop.demo.auth.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.webencyclop.demo.auth.exception.InvalidRequestException;
import com.webencyclop.demo.auth.middlewares.Authenticate;
import com.webencyclop.demo.auth.util.CookieUtil;
import com.webencyclop.demo.auth.util.JwtUtil;
import com.webencyclop.demo.auth.util.ValidateUtil;
import com.webencyclop.demo.model.User;
import com.webencyclop.demo.repository.RoleRepository;
import com.webencyclop.demo.repository.UserRepository;
import com.webencyclop.demo.service.*;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.webencyclop.demo.form.*;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepository;
	RoleRepository roleRepository;
	
	@Autowired
	UserServiceImp userSvIml;
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception{
		JSONParser parser = new JSONParser();
		
        JSONObject json = new JSONObject();
        try {
            json = (JSONObject) parser.parse(httpServletRequest.getReader());
        } catch (Exception e) {

        	throw new InvalidRequestException();
        }
        
	    String email=(String) json.get("email");
		String password=(String) json.get("password");
		if(!ValidateUtil.isValidEmailAddress(email) ||! ValidateUtil.isValidPassword(password)) throw new InvalidRequestException() ;
		
		// cần 1 hàm kiểm tra username ,password trong if ở đây 
		if(!userSvIml.isUser(email, password)) throw new InvalidRequestException();
		//cần 1 hàm đổ các dữ liệu vào đây, dữ liệu để nhận biết người dùng được nhét vào token
		
		User user = userRepository.findByContactEmail(email);
	    long id=user.getId();	
	    int roleId=1;

	    
	    // Tạo token,set cookie
	    String iss="MISA";
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis=86400000;
        Date exp=new Date(nowMillis+expMillis);
		String token = JwtUtil.generateToken(iss,now,exp,email,id,roleId);
		CookieUtil.create(httpServletResponse, token);
		System.out.println(token);
		// một số thông tin basic có thể trả về kèm luôn cho client tiện routing,gửi tiếp req,...,
		JSONObject obj = new JSONObject();
//		obj.put("issuer", iss);
//		obj.put("roleId", roleId);
//		obj.put("email",email);
//		obj.put("issuedAt",now);
//		obj.put("expiration", exp);
		obj.put("token", token);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
//		
//	@RequestMapping(value = "/api/home", method = RequestMethod.GET)
//	public ResponseEntity<Object> home(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {		
//		Authenticate.Auth(httpServletRequest, httpServletResponse);
//		// tra ve du lieu home neu req duoc xac thuc nguoi dung
//		String result="this is home data for authen req";
//		
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
	
//	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
//	public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) throws Exception{
//		
//	    String email= loginForm.getEmail();
//		String password= loginForm.getPassword();
//		
//		System.out.println("flag" + email + password);
//		//if(!ValidateUtil.isValidEmailAddress(email) || !ValidateUtil.isValidPassword(password)) throw new InvalidRequestException() ;
//
//		// cần 1 hàm kiểm tra username ,password trong if ở đây 
//
//		if(!userSvIml.isUser(email, password)) throw new InvalidRequestException();
//		
//		System.out.println("flag2");
//		//cần 1 hàm đổ các dữ liệu vào đây, dữ liệu để nhận biết người dùng được nhét vào token
//		User user = userRepository.findByContactEmail(email);
//    	System.out.println("flag3" + user.getContactEmail());
//	    long id=user.getId();	
//	    int roleId=1;
//
//			
//	    // Tạo token,set cookie
//	    String iss="MISA";
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//        long expMillis=86400000;
//        Date exp=new Date(nowMillis+expMillis);
//		String token = JwtUtil.generateToken(iss,now,exp,email,id,roleId);
//
//		// một số thông tin basic có thể trả về kèm luôn cho client tiện routing,gửi tiếp req,...,
//		JSONObject obj = new JSONObject();
//
//		obj.put("token", token);
//
//		return new ResponseEntity<>(obj, HttpStatus.OK);
//	}
	@RequestMapping(value = "/api/home", method = RequestMethod.GET)
	public ResponseEntity<Object> home(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {		
		Authenticate.Auth(httpServletRequest, httpServletResponse);
		// tra ve du lieu home neu req duoc xac thuc nguoi dung
		String result="this is home data for authen req";
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/api/logout", method = RequestMethod.GET)
	public ResponseEntity<Object> logout(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {	
		Authenticate.Auth(httpServletRequest, httpServletResponse);
		CookieUtil.clear(httpServletResponse);
		String result="Logout success";
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
}
