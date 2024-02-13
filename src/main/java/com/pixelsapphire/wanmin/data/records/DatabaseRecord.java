package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.data.DictTuple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface DatabaseRecord {

    @Contract(pure = true)
    int getId();

    @Contract(value = "-> new", pure = true)
    @NotNull DictTuple toRecord();
}
