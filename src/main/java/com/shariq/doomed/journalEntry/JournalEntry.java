package com.shariq.doomed.journalEntry;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@NoArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;

    private String name;
    private String content;
    private LocalDateTime time;
}
