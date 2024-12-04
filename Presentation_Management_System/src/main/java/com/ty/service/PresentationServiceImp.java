package com.ty.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.dto.PresentationDto;
import com.ty.dto.PresentationInputDto;
import com.ty.dto.UserDto;

import com.ty.entity.Presentation;
import com.ty.entity.User;
import com.ty.enums.PresentationStatus;
import com.ty.enums.Role;
import com.ty.enums.Status;
import com.ty.exception.PresentationNotFound;
import com.ty.exception.UserNotFound;
import com.ty.repository.PresentationRepository;
import com.ty.repository.UserRepository;
import com.ty.responseStructure.ResponseStructure;

@Service
public class PresentationServiceImp implements PresentationService{

	@Autowired
	private PresentationRepository pr;
	
	@Autowired
	private UserRepository ur;
	
	@Override
	public ResponseEntity<?> assign(Integer a, Integer s, PresentationInputDto pdto) {
		Presentation p=new Presentation();
		BeanUtils.copyProperties(pdto, p);
		User admin = ur.findById(a).orElseThrow(() -> new UserNotFound("User does  not  exist"));
		if(admin.getRole()==Role.ADMIN) {		
		User stu = ur.findById(s).orElseThrow(() -> new UserNotFound("Student does not exist"));
			if(stu.getRole()==Role.STUDENT) {
		
			
			p.setUser(stu);
			Presentation save=pr.save(p);			
			
			admin.getPresentation().add(save);
			
			
			p.setUserTotalScore(0.0);
			
			double dd=0.0;
			int count2=0;
			
			List<Presentation> presentation = stu.getPresentation();
			
			for (Presentation presentation2 : presentation) {
				dd=dd+presentation2.getUserTotalScore();
				count2++;
			}
			  double sc1=(dd/count2);
              double set1=Math.round(sc1);
			stu.setUserTotalScore(set1);
		    ur.save(stu);
		    
			PresentationDto dto = new PresentationDto();
			BeanUtils.copyProperties(save, dto);			
			ResponseStructure<PresentationDto> rs=new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.CREATED.value());
			rs.setMessage("Presentation Added");
			rs.setData(dto);
			
			
			
			
			return new ResponseEntity<ResponseStructure<PresentationDto>>(rs,HttpStatus.OK);
			}
			ResponseStructure<String> rs=new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setMessage("Student not found to assign Presentation");
		
			
			return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);}
	
		ResponseStructure<String> rs=new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		rs.setMessage("Only ADMIN can assign Presentation");
	
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
		}
		
	@Override
	public ResponseEntity<?> get(Integer p) {
Presentation c = pr.findById(p).orElseThrow(() -> new PresentationNotFound("Presentation Does not exist"));	
		
		
			PresentationDto u=new PresentationDto();
			BeanUtils.copyProperties(c, u);
			ResponseStructure<PresentationDto> rs=new ResponseStructure<>();	
				rs.setStatusCode(HttpStatus.OK.value());
				rs.setMessage("Fetch Successfully");
				rs.setData(u);
				return new ResponseEntity<ResponseStructure<PresentationDto>>(rs,HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> getAll(Integer s) {
		User c = ur.findById(s).orElseThrow(() -> new UserNotFound("User not found"));
		
			if (c.getRole() == Role.STUDENT) {
				List<Presentation> all = pr.findAll();
					ResponseStructure<List<Presentation>> rs=new ResponseStructure<>();			
					rs.setStatusCode(HttpStatus.OK.value());
					rs.setMessage("Fetch Successfully");
					rs.setData(all);
					return new ResponseEntity<ResponseStructure<List<Presentation>>>(rs,HttpStatus.OK);
			}
			ResponseStructure<String> rs=new ResponseStructure<>();
				rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
				rs.setMessage("Access Denied: Only Student can fetch Presentation data");			
				return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
		}
		
	

	@Override
	public ResponseEntity<?> updateStatus(Integer s, Integer p,PresentationStatus ps) {
		User c = ur.findById(s).orElseThrow(() -> new UserNotFound("User not found"));
		
		
		if (c.getRole() == Role.STUDENT) {
			Presentation pre = pr.findById(p).orElseThrow(() -> new PresentationNotFound("Presentation not found"));
			
				pre.setPresentationStatus(ps);
				pr.save(pre);
				
					ResponseStructure<String> rs=new ResponseStructure<>();			
					rs.setStatusCode(HttpStatus.OK.value());
					rs.setMessage("Status Updated Successfully");
				
					return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
			}
			ResponseStructure<String> rs=new ResponseStructure<>();
				rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
				rs.setMessage("Access Denied: Only Student can Update Status");			
				return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
		}
		
		
	
	

	@Override
	public ResponseEntity<?> score(Integer a, Integer p,Double d) {
     User c = ur.findById(a).orElseThrow(() -> new UserNotFound("User not found"));
		
		
		if (c.getRole() == Role.ADMIN) {
			Presentation pre = pr.findById(p).orElseThrow(() -> new PresentationNotFound("Presentation not found"));
			
				pre.setUserTotalScore(d);
				pr.save(pre);
				
					ResponseStructure<String> rs=new ResponseStructure<>();			
					rs.setStatusCode(HttpStatus.OK.value());
					rs.setMessage("Score Updated Successfully");
				
					return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
			}
			ResponseStructure<String> rs=new ResponseStructure<>();
				rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
				rs.setMessage("Access Denied: Only ABMIN can give Score");			
				return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}

}
