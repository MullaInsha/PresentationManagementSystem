package com.ty.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.dto.EmailDto;
import com.ty.dto.PresentationDto;
import com.ty.dto.RatingDto;
import com.ty.entity.Presentation;
import com.ty.entity.Rating;
import com.ty.entity.User;
import com.ty.enums.PresentationStatus;
import com.ty.enums.Role;
import com.ty.exception.PresentationNotFound;
import com.ty.exception.UserNotFound;
import com.ty.repository.PresentationRepository;
import com.ty.repository.RatingRepository;
import com.ty.repository.UserRepository;
import com.ty.responseStructure.ResponseStructure;

@Service
public class RatingServiceImp implements RatingService{

	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private PresentationRepository pr;
	
	
	@Autowired
	private RatingRepository rr;
	
	@Autowired
	private EmailService es;
	
	
	
	
	@Override
	public ResponseEntity<?> rate(Integer a, Integer s, Integer p, RatingDto rdto) {
	    User admin = ur.findById(a).orElseThrow(() -> new UserNotFound("User does not exist"));

	    // Ensure the user is an admin
	    if (admin.getRole() == Role.ADMIN) {
	        User stu = ur.findById(s).orElseThrow(() -> new UserNotFound("Student does not exist"));

	        // Ensure the user to be rated is a student
	        if (stu.getRole() == Role.STUDENT) {
	            Presentation pre = pr.findById(p).orElseThrow(() -> new PresentationNotFound("Presentation does not exist"));
	         
	            // Ensure the presentation status is COMPLETED
	            if (pre.getPresentationStatus() == PresentationStatus.COMPLETED) {
	                // Calculate the total score for the rating
	            	Rating r=new Rating();
	            	BeanUtils.copyProperties(rdto, r);
	            	
	                double score1=(r.getCommunication() + r.getConfidence() + r.getContent() +
                            r.getInteraction() + r.getLiveliness() + r.getUsageProps()) / 6.0;
                     double score2=Math.round(score1);
	                		r.setTotalScore(score2);
	                r.setUser(stu);
	                r.setPresentation(pre);
	                Rating save = rr.save(r);

	                // Update presentation total score
	                List<Rating> all = rr.findAll();
	                double d = 0.0;
	                int count = 0;
	                for (Rating rating : all) {
	                    if (rating.getPresentation().getPid() == p) {
	                        d += rating.getTotalScore();
	                        count++;
	                    }
	                }
	                double sc=(d/count);
	                double set=Math.round(sc);
	                
	                pre.setUserTotalScore(set);
	                pr.save(pre);

	                // Update student total score
	                double dd = 0.0;
	                int count2 = 0;
	                List<Presentation> presentations = stu.getPresentation();
	                for (Presentation presentation : presentations) {
	                    dd += presentation.getUserTotalScore();
	                    count2++;
	                }
	                double sc1=(dd/count);
	                double set1=Math.round(sc1);
	                    stu.setUserTotalScore(set1);
	                    ur.save(stu);
	               

	                // Construct response
	                ResponseStructure<Rating> rs = new ResponseStructure<>();
	                rs.setStatusCode(HttpStatus.CREATED.value());
	                rs.setMessage("Rating Added");
	                rs.setData(save);
	                
	                EmailDto dto=new EmailDto();
	                dto.setTo(stu.getEmail());	               
	                dto.setBody(stu.getName()+stu.getUserTotalScore());
	                dto.setCc("inshasmulla@gmail.com");
	                dto.setSubject("Rating");
	                
	                es.test(dto);
	                
	                


	                return new ResponseEntity<>(rs, HttpStatus.OK);
	            } else {
	                // Handle case where presentation is not completed
	                ResponseStructure<String> rs = new ResponseStructure<>();
	                rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
	                rs.setMessage("Presentation is not completed.");
	                return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
	            }
	        }

	        // User is not a student
	        ResponseStructure<String> rs = new ResponseStructure<>();
	        rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
	        rs.setMessage("User is not a student.");
	        return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
	    }

	    // User is not an admin
	    ResponseStructure<String> rs = new ResponseStructure<>();
	    rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
	    rs.setMessage("Only ADMIN can rate the presentation.");
	    return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
	}


	@Override
	public ResponseEntity<?> get(Integer p) {
	   
	    Presentation pre = pr.findById(p).orElseThrow(() -> new PresentationNotFound("Presentation does not exist"));
	    
	    
	    List<Rating> allRatings = rr.findAll();

	    
	    if (allRatings == null) {
	        ResponseStructure<String> rs = new ResponseStructure<>();
	        rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
	        rs.setMessage("No Ratings found for the Presentations");
	        return new ResponseEntity<>(rs, HttpStatus.OK);
	    } else {
	      
	        List<Rating> matchedRatings = new ArrayList<>();
	        for (Rating rating : allRatings) {
	            if (rating.getPresentation().getPid() == p) {
	                matchedRatings.add(rating);
	            }
	        }

	        
	        if (matchedRatings.isEmpty()) {
	            ResponseStructure<String> rs = new ResponseStructure<>();
	            rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
	            rs.setMessage("No Ratings found for this Presentation");
	            return new ResponseEntity<>(rs, HttpStatus.OK);
	        }

	        
	        ResponseStructure<List<Rating>> rs = new ResponseStructure<>();
	        rs.setStatusCode(HttpStatus.OK.value());
	        rs.setMessage("Ratings of Presentation");
	        rs.setData(matchedRatings);
	        return new ResponseEntity<ResponseStructure<List<Rating>>>(rs, HttpStatus.OK);
	    }
	}

	@Override
	public ResponseEntity<?> getAll(Integer s) {
            User c = ur.findById(s).orElseThrow(() -> new UserNotFound("User not found"));
		
		
		if (c.getRole() == Role.STUDENT) {
			
			List<Rating> allRatings = rr.findAll();

		    
		    if (allRatings == null) {
		        ResponseStructure<String> rs = new ResponseStructure<>();
		        rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		        rs.setMessage("No Ratings found for the Presentations");
		        return new ResponseEntity<>(rs, HttpStatus.OK);
		    } else {
		      
		        List<Rating> matchedRatings = new ArrayList<>();
		        for (Rating rating : allRatings) {
		        
		            if (rating.getUser().getId()==s) {
		                matchedRatings.add(rating);
		            }
		        }
		       
		        
		        if (matchedRatings.isEmpty()) {
		            ResponseStructure<String> rs = new ResponseStructure<>();
		            rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		            rs.setMessage("No Ratings of Presentation found for these Student");
		            return new ResponseEntity<>(rs, HttpStatus.OK);
		        }

		        
		        ResponseStructure<List<Rating>> rs = new ResponseStructure<>();
		        rs.setStatusCode(HttpStatus.OK.value());
		        rs.setMessage("Ratings of Presentation of these student");
		        rs.setData(matchedRatings);
		        return new ResponseEntity<>(rs, HttpStatus.OK);
		    }
	}
		 ResponseStructure<String> rs = new ResponseStructure<>();
	        rs.setStatusCode(HttpStatus.OK.value());
	        rs.setMessage("Ratings is only for student");
	     
	        return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.OK);


	}}
