package com.pixelsapphire.wanmin.data.records;

import org.jetbrains.annotations.NotNull;

public interface DatabaseRecord {

    int getId();

    @NotNull String getTableName();
}
