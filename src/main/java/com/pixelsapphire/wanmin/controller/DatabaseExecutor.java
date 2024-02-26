package com.pixelsapphire.wanmin.controller;

import com.pixelsapphire.wanmin.data.DictTuple;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DatabaseExecutor {

    List<DictTuple> executeQuery(@NotNull String sql, Object @NotNull ... params);

    void executeDML(@NotNull String sql, Object @NotNull ... params);
}
