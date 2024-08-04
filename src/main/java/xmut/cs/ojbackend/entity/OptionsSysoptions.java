package xmut.cs.ojbackend.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.Fastjson2TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
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
@Table(value = "options_sysoptions")
public class OptionsSysoptions implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String key;

    @Column(typeHandler = Fastjson2TypeHandler.class)
    private Object value;

}
