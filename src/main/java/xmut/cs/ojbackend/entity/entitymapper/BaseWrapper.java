package xmut.cs.ojbackend.entity.entitymapper;

import com.mybatisflex.core.paginate.Page;

import java.util.List;

public interface BaseWrapper<Vo, E> {
    E toEntity(Vo vo);
    Vo toVo(E entity);

    List<E> toEntity(List<Vo> voList);
    List<Vo> toVo(List<E> entityList);
}