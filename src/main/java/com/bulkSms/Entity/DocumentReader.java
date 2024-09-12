package com.bulkSms.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "document_reader")
public class DocumentReader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "document_entry_time")
    private Timestamp documentEntryTime;
    @Column(name = "job_id")
    private Long jobId;
    @Column(name = "download_count")
    private Long downloadCount;
}
