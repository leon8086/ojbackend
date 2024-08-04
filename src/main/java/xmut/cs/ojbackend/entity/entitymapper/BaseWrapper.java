package xmut.cs.ojbackend.entity.entitymapper;

import java.util.List;

public interface BaseWrapper<O, E> {
    E toEntity(O vo);
    O toO(E entity);

    List<E> toEntity(List<O> voList);
    List<O> toVo(List<E> entityList);
}