package com.leandrosouza.voting.domain.model;

import com.leandrosouza.voting.adapters.out.persistence.TopicEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class TopicModel implements Comparable {

    private UUID id;
    private String name;
    private Integer duration;
    private boolean open = false;
    private LocalDateTime startTime;
    private LocalDateTime predictedEndTime;
    private LocalDateTime effectiveEndTime;
    public boolean isClosed() {
        return ! open;
    }

    public boolean hasConditionToClose() {
        return (LocalDateTime.now().isAfter(predictedEndTime) && isOpen());
    }

    public void setDurationDefaultIfNull() {
        if (duration == null) duration = 1;
    }

    public boolean hasConditionsToOpen() {

        return !isOpen() &&
                startTime == null &&
                predictedEndTime == null &&
                effectiveEndTime == null;
    }

    @Override
    public int compareTo(Object o) {
        return this.name.compareTo( ((TopicModel)o).getName() );
    }
}
