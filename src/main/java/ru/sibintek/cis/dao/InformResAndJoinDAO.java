package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.dto.InformResAndJoin;

import java.util.List;

public interface InformResAndJoinDAO {
    List<InformResAndJoin> getAll(Integer isId);
}
