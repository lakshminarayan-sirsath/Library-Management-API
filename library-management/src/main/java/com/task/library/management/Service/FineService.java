package com.task.library.management.Service;

import com.task.library.management.ResponseEntity.DefaultResponse;

public interface FineService {

	public void addFinesForOverdueBooks();

	DefaultResponse payFine(Integer fineId);
}
