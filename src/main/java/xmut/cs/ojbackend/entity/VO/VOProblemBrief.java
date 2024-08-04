package xmut.cs.ojbackend.entity.VO;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "problem")
public class VOProblemBrief implements Serializable {
    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String title;

    private String displayId;

    private String difficulty;
}