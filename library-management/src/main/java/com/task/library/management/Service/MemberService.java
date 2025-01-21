package com.task.library.management.Service;

import java.util.List;

import com.task.library.management.Entity.Member;
import com.task.library.management.RequestEntity.RequestMember;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseMember;

public interface MemberService {
	
	DefaultResponse findAll();
	
	DefaultResponse findById(int memberId);
	
	DefaultResponse joinMembership(RequestMember requestMember);
	
//	DefaultResponse update(RequestMember requestMember);
//	
	DefaultResponse deleteById(int memberId);
	
	// -------------------------------Member-DTO-Methods----------------------------------------
	
	//Request
	Member requestMember_To_Member(RequestMember requestMember );
				
	//Response
	ResponseMember member_To_ResponseMember(Member member);
					
	//Request List
	List<Member> list_requestMember_To_Member(List<RequestMember> requestMembers);
					
	//Response List
	List<ResponseMember> list_member_To_ResponseMember(List<Member> members);
}
