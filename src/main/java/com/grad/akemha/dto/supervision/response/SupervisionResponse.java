package com.grad.akemha.dto.supervision.response;

import com.grad.akemha.dto.user.response.UserLessResponse;
import com.grad.akemha.entity.Supervision;
import com.grad.akemha.entity.enums.SupervisionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupervisionResponse {
    private Long id;

    private UserLessResponse supervisor;

    private UserLessResponse supervised;

    private SupervisionStatus supervisionStatus;

    public SupervisionResponse(Supervision supervision) {
        this.id = supervision.getId();
        this.supervisionStatus = supervision.getSupervisionStatus();
        this.supervisor = new UserLessResponse(supervision.getSupervisor());
        this.supervised = new UserLessResponse(supervision.getSupervised());
    }

}
