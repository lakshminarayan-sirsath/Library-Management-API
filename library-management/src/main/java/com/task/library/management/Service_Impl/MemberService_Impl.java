package com.task.library.management.Service_Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.task.library.management.Entity.Member;
import com.task.library.management.Entity.Role;
import com.task.library.management.Entity.UserAccount;
import com.task.library.management.GlobalException.CustomException;
import com.task.library.management.JpaRepo.MemberRepo;
import com.task.library.management.JpaRepo.UserAccountRepo;
import com.task.library.management.RequestEntity.RequestMember;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseMember;
import com.task.library.management.Service.MemberService;

import jakarta.transaction.Transactional;

@Service
public class MemberService_Impl implements MemberService {
	
	// Dependency Injection
	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	UserAccountRepo userAccountRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	// save
	@Transactional
	@Override
	public DefaultResponse joinMembership(RequestMember requestMember) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
	
		Map<String, Object> map = new HashMap<>();
		
		Member member = this.requestMember_To_Member(requestMember);
		
		if(memberRepo.findMemberByEmail(member.getEmail()) != null) {
			map.put("status: ", false);
			map.put("message: ", "Member already exist. (checked by email.)");
			
			throw new CustomException(map);
		}
		else {
			Member savedMember = memberRepo.save(member); // save member
			
			if(savedMember != null) {
				// adding Member to UserAccount
				UserAccount userAccount = userAccountRepo.findByUsername(authentication.getName());
				// set member role
				Role role = new Role();
				role.setName("MEMBER");
				userAccount.getRoles().add(role);
				
				userAccount.setMember(member);
				userAccountRepo.save(userAccount);
				
				map.put("status: ", true);
				map.put("message: ", "Member saved successfully.");
				return new DefaultResponse(map);
			}
			else {
				map.put("status: ", false);
				map.put("message: ", "Something is wrong, Member not saved.");
				
				throw new CustomException(map);
			}
		}
		
	}
	

	@Override
	public DefaultResponse findAll() {
		Map<String, Object> map = new HashMap<>(); // Using for Error response and Success Response
		
		List<Member> members = memberRepo.findAll();
		List<ResponseMember> responseMembers = this.list_member_To_ResponseMember(members);
		
		if(responseMembers != null) {
			map.put("status: ", true);
			map.put("num of members: ", map.size());
			map.put("mesage: ", "Members fetched successfully");
			map.put("student-list: ", members);
			return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Somethig wrong or no data available in member table.");
			
			throw new CustomException(map);
		}
	}

	@Override
	public DefaultResponse findById(int memberId) {
		Map<String, Object> map = new HashMap<>();
		
		// Container for null handing, avoid NullPointerException.
		Optional<Member> memberOptional = memberRepo.findById(memberId);
			
		Member member;
		
		if(memberOptional.isPresent()) {
			member = memberOptional.get();
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Member not found!!, Invalid id.");
			
			throw new CustomException(map);
		}
			
		ResponseMember responseMember = this.member_To_ResponseMember(member);
	   	map.put("status: ", true);
		map.put("mesage: ", "Member fetched successfully");
	    map.put("student: ", responseMember);
	    
	    return new DefaultResponse(map);
	}

//	@Override
//	public DefaultResponse update(RequestMember requestMember) {
//		Map<String, Object> map = new HashMap<>();
//		
//		Member member = this.requestMember_To_Member(requestMember);
//		
//		if(memberRepo.findById(member.getMemberId()) != null) {
//			map.put("status: ", false);
//			map.put("message: ", "Member not exist. Invalid memberId");
//			
//			throw new CustomException(map);
//		}
//		else {
//			Member savedMember = memberRepo.save(member); // save member
//			
//			if(savedMember != null) {
//				map.put("status: ", true);
//				map.put("message: ", "Member updated successfully.");
//				return new DefaultResponse(map);
//			}
//			else {
//				map.put("status: ", false);
//				map.put("message: ", "Something is wrong, Member not updated.");
//				
//				throw new CustomException(map);
//			}
//		}
//		
//		
//
//	}
//
	@Override
	public DefaultResponse deleteById(int memberId) {
		Map<String, Object> map = new HashMap<>();
		
		Optional<Member> memberOptional = memberRepo.findById(memberId);
		if(memberOptional.isPresent()) {
			
			 memberRepo.deleteById(memberId);
			
			 map.put("status: ", true);
			 map.put("message: ", "Member deleted successfuly. MemberId: "+memberId);
	    	 return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Invalid id, Member can not deleted..");
					
			throw new CustomException(map);
		}
	}

	// -------------------------------Member-DTO-Methods----------------------------------------
	
	@Override
	public Member requestMember_To_Member(RequestMember requestMember) {
		Member member = modelMapper.map(requestMember, Member.class);
		return member;
	}

	@Override
	public ResponseMember member_To_ResponseMember(Member member) {
		ResponseMember responseMember = modelMapper.map(member, ResponseMember.class);
		return responseMember;
	}

	@Override
	public List<Member> list_requestMember_To_Member(List<RequestMember> requestMembers) {
		List<Member> members = new ArrayList<>();
		
		for(RequestMember tmpRequestMember: requestMembers) {
			Member member = modelMapper.map(tmpRequestMember, Member.class);
			members.add(member);
		}
		
		return members;
	}

	@Override
	public List<ResponseMember> list_member_To_ResponseMember(List<Member> members) {
		List<ResponseMember> responseMembers = new ArrayList<>();
		
		for(Member tmpMember: members) {
			ResponseMember responseMember = modelMapper.map(tmpMember, ResponseMember.class);
			responseMembers.add(responseMember);
		}
		return responseMembers;
	}

	
}
