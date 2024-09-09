package xmut.cs.ojbackend.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTOCourseUserUpdate implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private List<Integer> add;

    private List<Integer> remove;
}
