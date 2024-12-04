package com.ty.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.ty.dto.RequestLogin;
import com.ty.dto.UserDto;
import com.ty.dto.UserInputDto;
import com.ty.entity.User;
import com.ty.enums.Role;
import com.ty.enums.Status;
import com.ty.exception.UserNotFound;
import com.ty.repository.UserRepository;
import com.ty.responseStructure.ResponseStructure;

@Service
public class UserServiceImp implements UserService{

	
	@Autowired
	private UserRepository ur;
	
	@Override
	public ResponseEntity<?> register(UserInputDto u) {
		User user =new User();
		BeanUtils.copyProperties(u, user);
		
		Optional<User> check=ur.findByEmail(user.getEmail());
		if(check.isPresent()) {
			ResponseStructure<String> rs=new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setMessage("User with these email already exist");
			rs.setData(user.getEmail());
			
			return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
			
		}
		else {
			user.setUserTotalScore(0.0);			
			User save=ur.save(user);
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(save, dto);			
			ResponseStructure<UserDto> rs=new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.CREATED.value());
			rs.setMessage("User Added");
			rs.setData(dto);
			
			return new ResponseEntity<ResponseStructure<UserDto> >(rs,HttpStatus.OK);
		
	}

	
	
	}
	

	@Override
	public ResponseEntity<?> login(RequestLogin rl) {
		User c = ur.findByEmail(rl.getEmail()).orElseThrow(() -> new UserNotFound("User Does not exist"));
		ResponseStructure<String> rs=new ResponseStructure<>();
		if(c.getPassword().equals(rl.getPassword())) {			
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Login Successfully");			
			return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
			
		}
		rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		rs.setMessage("Credentials does not match");		
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}
	

	@Override
	public ResponseEntity<?> get(Integer i) {
		User c = ur.findById(i).orElseThrow(() -> new UserNotFound("User Does not exist"));	
		
		if(c.getStatus()==Status.ACTIVE) {
			UserDto u=new UserDto();
			BeanUtils.copyProperties(c, u);
			ResponseStructure<UserDto> rs=new ResponseStructure<>();	
				rs.setStatusCode(HttpStatus.OK.value());
				rs.setMessage("Fetch Successfully");
				rs.setData(u);
				return new ResponseEntity<ResponseStructure<UserDto>>(rs,HttpStatus.OK);
		}
		ResponseStructure<String> rs=new ResponseStructure<>();	
		rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		rs.setMessage("User is not Active");
		
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
			
		
	}

	@Override
	public ResponseEntity<?> FetchAll(Integer a) {
		User c = ur.findById(a).orElseThrow(() -> new UserNotFound("User Does not exist"));
		if(c.getStatus()==Status.ACTIVE) {
			if (c.getRole() == Role.ADMIN) {
				
				List<User> all = ur.findAll();
				
				
				
				  // Convert List<User> to List<UserDto>
	            List<UserDto> userDtoList = new ArrayList<>();
	            for (User u : all) {
	            	if(u.getRole()==Role.STUDENT) {
	            		 UserDto dto = new UserDto();
	 	                // Copy properties from User to UserDto, excluding the password
	 	                BeanUtils.copyProperties(u, dto);
	 	                userDtoList.add(dto);
	            	}
	               
	            }
	            
	            
					ResponseStructure< List<UserDto>> rs=new ResponseStructure<>();			
					rs.setStatusCode(HttpStatus.OK.value());
					rs.setMessage("Fetch Successfully");
					rs.setData(userDtoList);
					return new ResponseEntity<ResponseStructure< List<UserDto>>>(rs,HttpStatus.OK);
			}
			ResponseStructure<String> rs=new ResponseStructure<>();
				rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
				rs.setMessage("Access Denied: Only ADMIN can fetch user data");			
				return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
		}
		ResponseStructure<String> rs=new ResponseStructure<>();	
		rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		rs.setMessage("User is not Active");
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
			
	}
	
//
	@Override
	public ResponseEntity<?> updateStatus(Integer i,Integer s,Status st) {
		User c = ur.findById(i)
				.orElseThrow(() -> new UserNotFound("User does not exist"));
		User ch = ur.findById(s)
				.orElseThrow(() -> new UserNotFound("User does not exist"));
		if(c.getStatus()==Status.ACTIVE) {
			if (c.getRole() == Role.ADMIN) {
				
				if(c.getId()==s) {
					c.setStatus(st);
					ur.save(c);
					ResponseStructure<String> rs=new ResponseStructure<>();			
					rs.setStatusCode(HttpStatus.OK.value());
					rs.setMessage("Status Updated Successfully");
				
					return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
				}else if(ch.getRole()==Role.STUDENT) {
					ch.setStatus(st);
					ur.save(ch);
					ResponseStructure<String> rs=new ResponseStructure<>();			
					rs.setStatusCode(HttpStatus.OK.value());
					rs.setMessage("Status Updated Successfully");
				
					return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
				}else {
					ResponseStructure<String> rs=new ResponseStructure<>();			
					rs.setStatusCode(HttpStatus.OK.value());
					rs.setMessage("ADMIN can change status of Student Only");
				
					return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
				}
				
				
			}
			ResponseStructure<String> rs=new ResponseStructure<>();
				rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
				rs.setMessage("Access Denied: Only ADMIN can Update Status");			
				return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
		}
		ResponseStructure<String> rs=new ResponseStructure<>();	
		rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		rs.setMessage("User is not Active");
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	
		
		
	}



}
