package xmut.cs.ojbackend.entity.VO;

import com.mybatisflex.annotation.*;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xmut.cs.ojbackend.entity.ProblemTag;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "problem")
public class VOProblemBrief implements Serializable {
    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    @Column(value = "_id")
    private String displayId;

    private Integer contestId;
}