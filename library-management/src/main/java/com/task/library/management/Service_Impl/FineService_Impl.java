package com.task.library.management.Service_Impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.task.library.management.Entity.BorrowingRecord;
import com.task.library.management.Entity.Fine;
import com.task.library.management.Entity.Member;
import com.task.library.management.GlobalException.CustomException;
import com.task.library.management.JpaRepo.BorrowingRecordRepo;
import com.task.library.management.JpaRepo.FineRepo;
import com.task.library.management.JpaRepo.MemberRepo;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.Service.FineService;

import jakarta.transaction.Transactional;

@Service
public class FineService_Impl implements FineService {
	// Dependency Injection
	@Autowired
	private BorrowingRecordRepo borrowingRecordRepo;
	
	@Autowired
	private FineRepo fineRepo;
	
	@Autowired
	private MemberRepo memberRepo;


    @Scheduled(cron = "0 0 0 * * ?")// Run daily at midnight 
	@Transactional
//    @Scheduled(cron = "*/59 * * * * *")
    public void addFinesForOverdueBooks() {
        // Fetch all books that are overdue
        List<BorrowingRecord> overdueRecords = borrowingRecordRepo.findOverdueBooks(LocalDate.now());

        for (BorrowingRecord record : overdueRecords) {
            // 5 rs per day after due date
            LocalDate dueDate = record.getDueDate();
            long daysOverdue = LocalDate.now().toEpochDay() - dueDate.toEpochDay(); // epoch day convert date to num of days
            
            if (daysOverdue > 0) {
                int fineAmount = (int) (daysOverdue * 5);  // 5 is the fine amount per day

                Member member = record.getMember();
                
                Fine fine = new Fine();
                if(member.getFine() != null) {
                	fine = member.getFine();
                	fine.setAmount(fineAmount);
                	fine.setPaidStatus("Unpaid");
                	fine.setDueDate(LocalDate.now().plusDays(7));  // Fine due in 7 days
                }
                else {
                	// Create a new Fine entry
                    fine.setAmount(fineAmount);
                    fine.setPaidStatus("Unpaid");
                    fine.setDueDate(LocalDate.now().plusDays(7));  // Fine due in 7 days
                 // Associate with the member
                    fine.setMember(member);
                }
 
                member.setFine(fine);

                // Save fine record
                fineRepo.save(fine);
            }
        }
    }

    
    @Override
    public DefaultResponse payFine(Integer fineId) {
    	Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
    	
        Optional<Fine> fineOptional = fineRepo.findById(fineId);
        
        if(fineOptional.isEmpty()) {
        	map.put("status: ", false);
			map.put("message: ", "invalid fine id, fine record not found.");
			throw new CustomException(map);
        }
        
        Fine fine = fineOptional.get();
                
        // Check if the fine is already paid
        if (fine.getPaidStatus().equalsIgnoreCase("Paid")) {
        	map.put("status: ", false);
			map.put("message: ", "Fine already paid.");
			throw new CustomException(map);
        }

        fine.setPaidStatus("Paid");
        fine.setAmount(0);  

        Fine resultFine = fineRepo.save(fine);
        if(resultFine == null) {
        	map.put("status: ", false);
			map.put("message: ", "Fine not paid, Something wrong.");
			throw new CustomException(map);
        }
        else {
        	map.put("status: ", true);
			map.put("message: ", "Fine paid successfully.");
			throw new CustomException(map);
        }
    }
    

}
