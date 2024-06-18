package xmut.cs.ojbackend.entity.VO;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *  实体类。
 *
 * @author leon
 * @since 2024-06-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user")
public class VOUserLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String adminType;

    private Boolean isDisabled;

    private String token;
}
