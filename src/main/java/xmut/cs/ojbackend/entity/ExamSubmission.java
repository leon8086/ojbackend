package xmut.cs.ojbackend.entity;

import com.mybatisflex.annotation.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 *  实体类。
 *
 * @author leon
 * @since 2024-06-26
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "exam_submission")
public class ExamSubmission extends Submission {

    private Integer examId;
}
