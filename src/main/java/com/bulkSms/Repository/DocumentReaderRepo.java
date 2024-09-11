package com.bulkSms.Repository;

import com.bulkSms.Entity.DocumentReader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentReaderRepo extends JpaRepository<DocumentReader,Long> {
}
